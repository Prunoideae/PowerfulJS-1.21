package moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.chemical;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalHandler;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import org.jetbrains.annotations.Nullable;

public class HasChemical extends ChemicalCapability {
    private final ChemicalStack chemicalStack;

    protected HasChemical(ChemicalStack chemicalStack, @Nullable Direction context) {
        super(context);
        this.chemicalStack = chemicalStack;
    }

    @Override
    protected boolean evaluateCap(BlockCapabilityCache<IChemicalHandler, @Nullable Direction> capabilityCache) {
        IChemicalHandler handler = capabilityCache.getCapability();
        if (handler == null) return false;
        for (int i = 0; i < handler.getChemicalTanks(); i++) {
            var tankChemical = handler.getChemicalInTank(i);
            if (tankChemical.is(chemicalStack.getChemical()) && tankChemical.getAmount() >= chemicalStack.getAmount()) {
                return true;
            }
        }
        return false;
    }
}
