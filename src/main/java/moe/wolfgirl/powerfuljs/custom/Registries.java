package moe.wolfgirl.powerfuljs.custom;

import moe.wolfgirl.powerfuljs.custom.fluid.ConstantFluid;
import moe.wolfgirl.powerfuljs.custom.fluid.storage.FixedFluidTank;
import moe.wolfgirl.powerfuljs.custom.fluid_item.ConstantItemFluid;
import moe.wolfgirl.powerfuljs.custom.fluid_item.FixedItemFluidTank;
import moe.wolfgirl.powerfuljs.custom.forge_energy.ConstantEnergy;
import moe.wolfgirl.powerfuljs.custom.forge_energy.storage.FixedEnergyStorage;
import moe.wolfgirl.powerfuljs.custom.item.ConstantItem;
import moe.wolfgirl.powerfuljs.custom.item.storage.FixedItemStorage;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.MekRegistries;
import moe.wolfgirl.powerfuljs.custom.registries.BlockCapabilityRegistry;
import moe.wolfgirl.powerfuljs.custom.registries.EntityCapabilityRegistry;
import moe.wolfgirl.powerfuljs.custom.registries.ItemCapabilityRegistry;
import moe.wolfgirl.powerfuljs.utils.ModUtils;

public class Registries {

    public static void init() {
        BlockCapabilityRegistry.register(ConstantEnergy.BLOCK);
        BlockCapabilityRegistry.registerBE(ConstantEnergy.BLOCK_ENTITY);
        ItemCapabilityRegistry.register(ConstantEnergy.ITEM);
        EntityCapabilityRegistry.register(ConstantEnergy.ENTITY);

        BlockCapabilityRegistry.registerBE(FixedEnergyStorage.BLOCK_ENTITY);
        ItemCapabilityRegistry.register(FixedEnergyStorage.ITEM);
        EntityCapabilityRegistry.register(FixedEnergyStorage.ENTITY);

        BlockCapabilityRegistry.register(ConstantFluid.BLOCK);
        BlockCapabilityRegistry.registerBE(ConstantFluid.BLOCK_ENTITY);
        ItemCapabilityRegistry.register(ConstantItemFluid.ITEM);
        EntityCapabilityRegistry.register(ConstantFluid.ENTITY);

        BlockCapabilityRegistry.registerBE(FixedFluidTank.BLOCK_ENTITY);
        ItemCapabilityRegistry.register(FixedItemFluidTank.ITEM);
        EntityCapabilityRegistry.register(FixedFluidTank.ENTITY);

        BlockCapabilityRegistry.register(ConstantItem.BLOCK);
        BlockCapabilityRegistry.registerBE(ConstantItem.BLOCK_ENTITY);
        ItemCapabilityRegistry.register(ConstantItem.ITEM);
        EntityCapabilityRegistry.register(ConstantItem.ENTITY);
        EntityCapabilityRegistry.register(ConstantItem.ENTITY_AUTOMATION);

        BlockCapabilityRegistry.registerBE(FixedItemStorage.BLOCK_ENTITY);
        ItemCapabilityRegistry.register(FixedItemStorage.ITEM);
        EntityCapabilityRegistry.register(FixedItemStorage.ENTITY);
        EntityCapabilityRegistry.register(FixedItemStorage.ENTITY_AUTOMATION);

        ModUtils.whenLoaded("mekanism", MekRegistries::init);
    }
}
