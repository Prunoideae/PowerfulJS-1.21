package moe.wolfgirl.powerfuljs.custom.logic.effects.fluid;

import moe.wolfgirl.powerfuljs.custom.fluid.storage.BaseFluidTank;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class DrainFluidEffect extends FluidEffect {
    private final FluidStack fluidStack;
    private final boolean forced;

    public DrainFluidEffect(FluidStack fluidStack, boolean forced, @Nullable Direction context) {
        super(context);
        this.fluidStack = fluidStack;
        this.forced = forced;
    }

    @Override
    protected void runEffect(BlockCapabilityCache<IFluidHandler, Direction> cache) {
        IFluidHandler fluidHandler = cache.getCapability();
        if (fluidHandler == null) return;

        if (!forced || !(fluidHandler instanceof BaseFluidTank baseFluidTank)) {
            fluidHandler.drain(fluidStack, IFluidHandler.FluidAction.EXECUTE);
        } else if (!baseFluidTank.getFluid().isEmpty() && FluidStack.isSameFluidSameComponents(baseFluidTank.getFluid(), fluidStack)) {
            baseFluidTank.drain(fluidStack.getAmount(), IFluidHandler.FluidAction.EXECUTE, true);
        }
    }
}
