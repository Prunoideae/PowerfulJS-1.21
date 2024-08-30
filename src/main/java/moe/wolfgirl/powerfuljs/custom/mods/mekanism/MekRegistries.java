package moe.wolfgirl.powerfuljs.custom.mods.mekanism;

import moe.wolfgirl.powerfuljs.custom.mods.mekanism.chemical.storage.FixedChemicalStorage;
import moe.wolfgirl.powerfuljs.custom.registries.BlockCapabilityRegistry;
import moe.wolfgirl.powerfuljs.custom.registries.EntityCapabilityRegistry;
import moe.wolfgirl.powerfuljs.custom.registries.ItemCapabilityRegistry;

public class MekRegistries {
    public static void init() {
        BlockCapabilityRegistry.registerBE(FixedChemicalStorage.BLOCK_ENTITY);
        ItemCapabilityRegistry.register(FixedChemicalStorage.ITEM);
        EntityCapabilityRegistry.register(FixedChemicalStorage.ENTITY);
    }
}
