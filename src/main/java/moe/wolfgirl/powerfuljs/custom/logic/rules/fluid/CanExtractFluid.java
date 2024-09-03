package moe.wolfgirl.powerfuljs.custom.logic.rules.fluid;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class CanExtractFluid extends FluidRule {
    private final FluidStack stack;

    public CanExtractFluid(FluidStack stack, @Nullable Direction context) {
        super(context);
        this.stack = stack;
    }

    @Override
    protected boolean evaluateCap(BlockCapabilityCache<IFluidHandler, Direction> capabilityCache) {
        var handler = capabilityCache.getCapability();
        if (handler == null) return false;
        return handler.drain(stack, IFluidHandler.FluidAction.SIMULATE).getAmount() == stack.getAmount();
    }
}
