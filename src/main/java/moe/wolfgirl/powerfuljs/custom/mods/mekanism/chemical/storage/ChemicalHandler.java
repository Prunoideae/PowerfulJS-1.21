package moe.wolfgirl.powerfuljs.custom.mods.mekanism.chemical.storage;

import mekanism.api.Action;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalHandler;
import moe.wolfgirl.powerfuljs.utils.MathUtils;
import org.jetbrains.annotations.NotNull;


public abstract class ChemicalHandler implements IChemicalHandler {
    @Override
    public int getChemicalTanks() {
        return 1;
    }

    @Override
    public @NotNull ChemicalStack getChemicalInTank(int tank) {
        return getChemicalData();
    }

    @Override
    public void setChemicalInTank(int tank, @NotNull ChemicalStack stack) {
        setChemicalData(stack);
    }

    @Override
    public @NotNull ChemicalStack insertChemical(int tank, @NotNull ChemicalStack stack, @NotNull Action action) {
        return insertChemical(tank, stack, action, false);
    }

    public @NotNull ChemicalStack insertChemical(int tank, @NotNull ChemicalStack stack, @NotNull Action action, boolean forced) {
        if (tank != 0 || stack.isEmpty() || !isValid(0, stack)) return stack;
        var currentStack = getChemicalData();
        if (!currentStack.isEmpty() && !ChemicalStack.isSameChemical(currentStack, stack)) {
            return stack;
        }

        long inserted = MathUtils.min(
                getChemicalTankCapacity(0) - currentStack.getAmount(),
                stack.getAmount(),
                forced ? Long.MAX_VALUE : getMaxReceive()
        );

        if (inserted <= 0) return stack;
        if (action.execute()) {
            setChemicalData(stack.copyWithAmount(currentStack.getAmount() + inserted));
            onReceived(stack.copyWithAmount(inserted));
            onChanged();
        }

        if (stack.getAmount() == inserted) return ChemicalStack.EMPTY;
        return stack.copyWithAmount(stack.getAmount() - inserted);
    }

    @Override
    public @NotNull ChemicalStack extractChemical(int tank, long amount, @NotNull Action action) {
        return extractChemical(tank, amount, action, false);
    }

    public @NotNull ChemicalStack extractChemical(int tank, long amount, @NotNull Action action, boolean forced) {
        if (tank != 0 || amount <= 0) return ChemicalStack.EMPTY;
        var currentStack = getChemicalData();
        long extracted = MathUtils.min(
                currentStack.getAmount(),
                amount,
                forced ? Long.MAX_VALUE : getMaxExtract()
        );
        if (extracted <= 0) return ChemicalStack.EMPTY;
        ChemicalStack extractedStack = currentStack.copyWithAmount(extracted);
        if (action.execute()) {
            setChemicalData(extractedStack.getAmount() == currentStack.getAmount() ?
                    ChemicalStack.EMPTY :
                    currentStack.copyWithAmount(currentStack.getAmount() - extractedStack.getAmount())
            );
        }
        return extractedStack;
    }

    protected abstract long getMaxReceive();

    protected abstract long getMaxExtract();

    protected abstract void setChemicalData(ChemicalStack stack);

    protected abstract ChemicalStack getChemicalData();

    protected void onChanged() {
    }

    protected void onReceived(ChemicalStack stack) {
    }

    protected void onExtracted(int amount) {
    }

}
