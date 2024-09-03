package moe.wolfgirl.powerfuljs.custom.logic.rules.fluid;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class CanReceiveFluid extends FluidRule {
    private final FluidStack fluidStack;

    public CanReceiveFluid(FluidStack fluidStack, @Nullable Direction context) {
        super(context);
        this.fluidStack = fluidStack;
    }

    @Override
    protected boolean evaluateCap(BlockCapabilityCache<IFluidHandler, Direction> capabilityCache) {
        IFluidHandler handler = capabilityCache.getCapability();
        if (handler == null) return false;
        return handler.fill(fluidStack, IFluidHandler.FluidAction.SIMULATE) > 0;
    }
}
