package moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.chemical;

import mekanism.api.Action;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalHandler;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import org.jetbrains.annotations.Nullable;

public class CanInsertChemical extends ChemicalCapability {
    private final ChemicalStack chemicalStack;

    public CanInsertChemical(ChemicalStack chemicalStack, @Nullable Direction context) {
        super(context);
        this.chemicalStack = chemicalStack;
    }

    @Override
    protected boolean evaluateCap(IChemicalHandler handler) {
        if (handler == null) return false;
        return handler.insertChemical(chemicalStack.copy(), Action.SIMULATE).isEmpty();
    }
}
