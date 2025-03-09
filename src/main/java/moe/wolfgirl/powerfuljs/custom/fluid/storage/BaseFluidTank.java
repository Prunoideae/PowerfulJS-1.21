package moe.wolfgirl.powerfuljs.custom.fluid.storage;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.IFluidTank;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public abstract class BaseFluidTank implements IFluidHandler, IFluidTank {
    protected final Predicate<FluidStack> validator;

    protected BaseFluidTank(Predicate<FluidStack> validator) {
        this.validator = validator;
    }

    @Override
    public final int getTanks() {
        return 1;
    }

    @Override
    public final @NotNull FluidStack getFluidInTank(int tank) {
        return getFluid();
    }

    @Override
    public final @NotNull FluidStack getFluid() {
        return getFluidData();
    }

    @Override
    public int getFluidAmount() {
        return getFluid().getAmount();
    }

    @Override
    public final int getTankCapacity(int tank) {
        return getCapacity();
    }

    @Override
    public final boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return this.isFluidValid(stack);
    }

    @Override
    public boolean isFluidValid(@NotNull FluidStack stack) {
        return validator == null || validator.test(stack);
    }

    @Override
    public int fill(@NotNull FluidStack resource, @NotNull FluidAction action) {
        return fill(resource, action, false);
    }

    public int fill(@NotNull FluidStack resource, @NotNull FluidAction action, boolean forced) {
        if (resource.isEmpty() || !this.isFluidValid(resource)) return 0;
        FluidStack tankFluid = getFluid();

        int capacity = getCapacity();
        int maxReceive = forced ? resource.getAmount() : Math.min(resource.getAmount(), getMaxReceive());

        if (action.simulate()) {
            if (tankFluid.isEmpty()) return Math.min(capacity, maxReceive);
            if (!FluidStack.isSameFluidSameComponents(tankFluid, resource)) return 0;
            return Math.min(capacity - tankFluid.getAmount(), maxReceive);
        }

        if (tankFluid.isEmpty()) {
            FluidStack received = resource.copyWithAmount(Math.min(capacity, maxReceive));
            setFluidData(received);
            onReceived(received.getAmount());
            onChanged();
            return received.getAmount();
        }

        if (!FluidStack.isSameFluidSameComponents(tankFluid, resource)) {
            return 0;
        }
        int filled = Math.min(capacity - tankFluid.getAmount(), maxReceive);
        if (filled > 0) {
            setFluidData(tankFluid.copyWithAmount(filled + tankFluid.getAmount()));
            onReceived(filled);
            onChanged();
        }
        return filled;
    }

    @Override
    public @NotNull FluidStack drain(@NotNull FluidStack resource, @NotNull FluidAction action) {
        if (resource.isEmpty() || !FluidStack.isSameFluidSameComponents(resource, getFluid()))
            return FluidStack.EMPTY;
        return drain(resource.getAmount(), action);
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, @NotNull FluidAction action) {
        return drain(maxDrain, action, false);
    }

    public @NotNull FluidStack drain(int maxDrain, @NotNull FluidAction action, boolean forced) {
        FluidStack tankFluid = getFluid();
        int drained = forced ? maxDrain : Math.min(getMaxExtract(), maxDrain);
        drained = Math.min(tankFluid.getAmount(), drained);

        if (action.execute() && drained > 0) {
            setFluidData(tankFluid.copyWithAmount(tankFluid.getAmount() - drained));
            onExtracted(drained);
            onChanged();
        }
        return tankFluid.copyWithAmount(drained);
    }

    protected abstract FluidStack getFluidData();

    protected abstract void setFluidData(FluidStack fluidStack);

    protected abstract int getMaxExtract();

    protected abstract int getMaxReceive();

    protected void onReceived(int amount) {

    }

    protected void onExtracted(int extracted) {

    }

    protected void onChanged() {

    }
}
