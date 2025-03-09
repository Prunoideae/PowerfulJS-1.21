package moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.effects.chemical;

import mekanism.api.Action;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalHandler;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.chemical.storage.ChemicalHandler;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import org.jetbrains.annotations.Nullable;

public class DrainChemicalEffect extends ChemicalEffect {
    private final ChemicalStack chemicalStack;
    private final boolean forced;

    public DrainChemicalEffect(ChemicalStack chemicalStack, boolean forced, @Nullable Direction context) {
        super(context);
        this.chemicalStack = chemicalStack;
        this.forced = forced;
    }

    @Override
    protected void runEffect(IChemicalHandler chemicalHandler) {
        if (chemicalHandler == null) return;

        if (!forced || !(chemicalHandler instanceof ChemicalHandler handler)) {
            chemicalHandler.insertChemical(chemicalStack.copy(), Action.EXECUTE);
        } else {
            handler.insertChemical(0, chemicalStack, Action.EXECUTE, true);
        }
    }
}
