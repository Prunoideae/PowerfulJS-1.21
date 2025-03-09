package moe.wolfgirl.powerfuljs.custom.fluid.storage;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * A fluid tank with input / output sub-tank definitions
 */
public abstract class ProcessingFluidTank implements IFluidHandler {
    protected final Predicate<FluidStack> inputValidator;
    protected final Predicate<FluidStack> outputValidator;

    protected ProcessingFluidTank(Predicate<FluidStack> inputValidator, Predicate<FluidStack> outputValidator) {
        this.inputValidator = inputValidator;
        this.outputValidator = outputValidator;
    }

    @Override
    public int getTanks() {
        return 2;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return tank == 0 ? getInputFluidData() : getOutputFluidData();
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        var validator = tank == 0 ? inputValidator : outputValidator;
        return validator == null || validator.test(stack);
    }

    @Override
    public int fill(@NotNull FluidStack resource, @NotNull FluidAction action) {
        return fill(resource, action, 0);
    }

    public int fill(@NotNull FluidStack resource, @NotNull FluidAction action, int tank) {
        if (resource.isEmpty() || !this.isFluidValid(tank, resource)) return 0;
        FluidStack tankFluid = getFluidInTank(tank);
        int capacity = getTankCapacity(tank);
        int maxReceive = tank == 1 ? resource.getAmount() : Math.min(resource.getAmount(), getMaxReceive());

        if (action.simulate()) {
            if (tankFluid.isEmpty()) return Math.min(capacity, maxReceive);
            if (!FluidStack.isSameFluidSameComponents(tankFluid, resource)) return 0;
            return Math.min(capacity - tankFluid.getAmount(), maxReceive);
        }

        if (tankFluid.isEmpty()) {
            FluidStack received = resource.copyWithAmount(Math.min(capacity, maxReceive));
            if (tank == 0) setInputFluidData(received);
            else setOutputFluidData(received);
            onReceived(received.getAmount());
            onChanged();
            return received.getAmount();
        }

        if (!FluidStack.isSameFluidSameComponents(tankFluid, resource)) return 0;
        int filled = Math.min(capacity - tankFluid.getAmount(), maxReceive);
        if (filled > 0) {
            var filledStack = tankFluid.copyWithAmount(filled + tankFluid.getAmount());
            if (tank == 0) setInputFluidData(filledStack);
            else setOutputFluidData(filledStack);
            onReceived(filled);
            onChanged();
        }
        return filled;
    }

    @Override
    public @NotNull FluidStack drain(@NotNull FluidStack resource, @NotNull FluidAction action) {
        if (resource.isEmpty() || !FluidStack.isSameFluidSameComponents(getOutputFluidData(), resource)) {
            return FluidStack.EMPTY;
        }
        return drain(resource.getAmount(), action);
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, @NotNull FluidAction action) {
        return drain(maxDrain, action, 1);
    }

    public @NotNull FluidStack drain(int maxDrain, @NotNull FluidAction action, int tank) {
        FluidStack tankFluid = getFluidInTank(tank);
        int drained = tank == 0 ? maxDrain : Math.min(getMaxExtract(), maxDrain);
        drained = Math.min(tankFluid.getAmount(), drained);

        if (action.execute() && drained > 0) {
            var drainedStack = tankFluid.copyWithAmount(tankFluid.getAmount() - drained);
            if (tank == 0) setInputFluidData(drainedStack);
            else setOutputFluidData(drainedStack);
            onExtracted(drained);
            onChanged();
        }
        return tankFluid.copyWithAmount(drained);
    }

    protected abstract FluidStack getInputFluidData();

    protected abstract FluidStack getOutputFluidData();

    protected abstract void setInputFluidData(FluidStack fluidStack);

    protected abstract void setOutputFluidData(FluidStack fluidStack);

    protected abstract int getMaxReceive();

    protected abstract int getMaxExtract();

    protected void onReceived(int amount) {

    }

    protected void onExtracted(int extracted) {

    }

    protected void onChanged() {

    }
}
