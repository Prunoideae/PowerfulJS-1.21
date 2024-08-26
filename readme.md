# PowerfulJS

Rewritten from 1.20.1, PowerfulJS is now indeed powerful!

PowerfulJS 1.21 now has better handling for capabilities, full [ProbeJS](https://www.curseforge.com/minecraft/mc-mods/probejs) support, and way better performance!

## Introduction to the Capability System

Neoforge's capability unified the implementation of items, blocks, entities... to handle a common behavior. So, any block that implements `item handler` capability will be able to receive or provide items to a hopper, any items that implements `fluid handler` capability will be able to be filled by a Mekanism fluid tank.

PowerfulJS makes it possible for modpack makers to add capabilities to these objects. So, with capabilities attached, you can make a furnace to have FE or fluid storage, or a battery hull that can be filled to make a battery... The possibility is infinite.

## Quick usage

This section introduces you how to use PowerfulJS to implement some easy stuffs, the code can run with PowerfulJS installed, but it is always advised to install ProbeJS so you can get IDE support in VSCode.

### 1. Adding capabilities

Capabilities can be attached to 3 types of objects: `item`, `block` and `entity`, there's also a fourth type which is `block entity`, but it's inclued in `block` capabilities.

Capability registration is fired at startup, a simple example to add capabilities will be:

```js
PowerfulEvents.registerCapabilities(event => {
    // Adds a capability that has constant FE supply
    //
    // So the redstone block now can produce 100FE/t constantly,
    // however, you need an extractor (like MEK cable with
    // extract mode) in order to get the energy, as the block 
    // itself won't distribute energy to surroundings

    event.registerBlock('powerfuljs:constant_energy', {
        maxExtract: 100,
        maxReceive: 0,
    }, "redstone_block")

    // Adds a tank that has a fixed capacity with defined
    // max I/O
    event.registerBlockEntity('powerfuljs:fixed_storage_fluid', {
        capacity: 2000,
        maxExtract: 250,
        maxReceive: 250,
    }, 'minecraft:furnace')

    // and so does the FE energy storage
    event.registerBlockEntity('powerfuljs:fixed_storage_fe', {
        maxExtract: 1000,
        maxReceive: 1000,
        capacity: 10000,
    }, 'minecraft:furnace')

    // Adds a fluid capability that has constant fluid supply
    //
    // Just connect a pipe and extract infinite lava!
    // OVERPOWERED!!!!!
    event.registerBlock('powerfuljs:constant_fluid', {
        content: Fluid.of('lava', 1000),
        maxReceive: 0
    }, 'minecraft:lava')
        // ...so you can limit it by making it only available
        // at certain condition, like here we check if it's a
        // source block and in a "nether" biome, which is the
        // nether_waste.
        //
        // However, you shall not rely on this validator if the
        // block can change its state while working, like furnace
        // The capability will be reused for many machines for
        // performance reason, and `validate` will not be called
        // as the capability is already provided
        .validate((blockContext, info) => {
            return blockContext.state().fluidState.source &&
                blockContext.level().getBiome(blockContext.pos())
                    .registeredName
                    .includes("nether")
        })

    // Also make the `netherrack` and `soul sand` able to be filled
    // with lava
    //
    // When filled, they are turned into `magma block`
    //
    // There are some differences in different capability builders,
    // you can use ProbeJS to find out what kind of stuffs are
    // supported by each builder
    event.registerItem('powerfuljs:fixed_storage_fluid', {
        capacity: 1000,
        maxReceive: 20,
        maxExtract: 20,
        validator: "minecraft:lava",
        changeItemWhen: {
            full: [
                { fluid: "minecraft:lava", item: "minecraft:magma_block" }
            ]
        }
    }, 'netherrack', 'soul_sand')
})
```

### 2. Intercepting ticking

To make the capabilities attached to the block more useful, PowerfulJS also implemented a way to intercept the original ticking logic of a block, as well as forcing a block that does not tick to tick.

A system called `RuleSet` is used to define the actual logic to balance the performance with flexibility, theoretically speaking, implementing any logic with this system has comparable performance to native Java, though it still highly depends on your way to implement.

The ticking interception is registered when server script is loaded, so you can also reload the server script to refresh the behavior. However, for any block that already has injected logic, you need to break and replace them to refresh the ticker.

The example is shown as below:

```js
PowerfulEvents.interceptTicking(event => {
    let burningFurnaceOfAnyDirection = [
        "minecraft:furnace[lit=true, facing=west]",
        "minecraft:furnace[lit=true, facing=east]",
        "minecraft:furnace[lit=true, facing=north]",
        "minecraft:furnace[lit=true, facing=south]"
    ]

    event.intercept('minecraft:furnace', () => [
        // We test for a running furnace
        Rules.matchBlock(burningFurnaceOfAnyDirection)
            // We enable the furance if it's not running
            // so it can detect recipes normally
            .negate()
            // If it's running
            .or(
                // If it has 1000mB of lava, then keep it running
                Rules.hasFluid(Fluid.lava(1000), null)
                    // And we also drain 1 mB of lava and produce 5 FE
                    .effect(Effects.drainFluid(Fluid.lava(1), null))
                    .effect(Effects.fillEnergy(5, null))
            )
            // So, if the furnace is not running, or the furnace is 
            // running with fluid, we enable the furnace logic (
            // previous rule evaluates to true), otherwise we disable
            // it (evaluates to false)
            .effect(Effects.toggleEnable())
    ])
    // The logic described above will make the furnace requires at
    // least 1000mB of lava to run, and produce 5FE/t at the cost
    // of 1mB/t lava

    // The chest does not tick on its own, we force the chest to
    // start ticking with empty logic, so we add our logic by
    // interception later
    event.forceTicker('chest', true)
    event.intercept('chest', () => [
        // Every 20 ticks
        Rules.every(20).effect(
            // Have a cookie!
            Effects.insertItem('cookie', null)
        )
    ])
})
```
