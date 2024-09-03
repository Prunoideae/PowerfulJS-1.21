package moe.wolfgirl.powerfuljs.custom.logic.effects.fluid;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class DrainFluidEffect extends FluidEffect {
    private final FluidStack fluidStack;

    public DrainFluidEffect(FluidStack fluidStack, @Nullable Direction context) {
        super(context);
        this.fluidStack = fluidStack;
    }

    @Override
    protected void runEffect(BlockCapabilityCache<IFluidHandler, Direction> cache) {
        IFluidHandler fluidHandler = cache.getCapability();
        if (fluidHandler == null) return;
        fluidHandler.drain(fluidStack, IFluidHandler.FluidAction.EXECUTE);
    }
}
