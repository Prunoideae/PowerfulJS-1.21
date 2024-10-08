package moe.wolfgirl.powerfuljs.custom.mods.mekanism;

import moe.wolfgirl.powerfuljs.custom.mods.mekanism.chemical.ConstantChemical;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.chemical.storage.FixedChemicalStorage;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.heat.ConstantHeat;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.heat.storage.FixedHeatStorage;
import moe.wolfgirl.powerfuljs.custom.registries.BlockCapabilityRegistry;
import moe.wolfgirl.powerfuljs.custom.registries.EntityCapabilityRegistry;
import moe.wolfgirl.powerfuljs.custom.registries.ItemCapabilityRegistry;

public class MekRegistries {
    public static void init() {
        BlockCapabilityRegistry.registerBE(FixedChemicalStorage.BLOCK_ENTITY);
        ItemCapabilityRegistry.register(FixedChemicalStorage.ITEM);
        EntityCapabilityRegistry.register(FixedChemicalStorage.ENTITY);

        BlockCapabilityRegistry.registerBE(ConstantChemical.BLOCK_ENTITY);
        BlockCapabilityRegistry.register(ConstantChemical.BLOCK);
        ItemCapabilityRegistry.register(ConstantChemical.ITEM);
        EntityCapabilityRegistry.register(ConstantChemical.ENTITY);

        BlockCapabilityRegistry.register(ConstantHeat.BLOCK);
        BlockCapabilityRegistry.registerBE(ConstantHeat.BLOCK_ENTITY);

        BlockCapabilityRegistry.registerBE(FixedHeatStorage.BLOCK_ENTITY);
    }
}
