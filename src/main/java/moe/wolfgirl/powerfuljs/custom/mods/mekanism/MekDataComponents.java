package moe.wolfgirl.powerfuljs.custom.mods.mekanism;

import mekanism.api.chemical.ChemicalStack;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.RegisterEvent;

public class MekDataComponents {
    public static final DataComponentType<ChemicalStack> CHEMICAL = DataComponentType.<ChemicalStack>builder()
            .persistent(ChemicalStack.OPTIONAL_CODEC)
            .networkSynchronized(ChemicalStack.OPTIONAL_STREAM_CODEC)
            .build();

    public static void initComponents(RegisterEvent.RegisterHelper<DataComponentType<?>> helper) {
        helper.register(MCID.create("chemical"), CHEMICAL);
    }
}
