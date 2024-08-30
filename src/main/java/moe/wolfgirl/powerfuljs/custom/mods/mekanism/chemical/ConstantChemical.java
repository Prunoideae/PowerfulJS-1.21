package moe.wolfgirl.powerfuljs.custom.mods.mekanism.chemical;

import mekanism.api.Action;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalHandler;
import org.jetbrains.annotations.NotNull;

/**
 * A chemical handler that holds a constant amount of chemical.
 */
public class ConstantChemical implements IChemicalHandler {

    private final ChemicalStack chemical;

    public ConstantChemical(ChemicalStack chemical) {
        this.chemical = chemical;
    }

    @Override
    public int getChemicalTanks() {
        return 1;
    }

    @Override
    public @NotNull ChemicalStack getChemicalInTank(int tank) {
        return chemical.copy();
    }

    @Override
    public void setChemicalInTank(int tank, @NotNull ChemicalStack   stack) {
    }

    @Override
    public long getChemicalTankCapacity(int tank) {
        return chemical.getAmount();
    }

    @Override
    public boolean isValid(int tank, @NotNull ChemicalStack stack) {
        return ChemicalStack.isSameChemical(stack, chemical);
    }

    @Override
    public @NotNull ChemicalStack insertChemical(int tank, @NotNull ChemicalStack stack, @NotNull Action action) {
        return stack;
    }

    @Override
    public @NotNull ChemicalStack extractChemical(int tank, long amount, @NotNull Action action) {
        return chemical.copyWithAmount(Math.min(chemical.getAmount(), amount));
    }
}
