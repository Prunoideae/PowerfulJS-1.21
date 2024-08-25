package moe.wolfgirl.powerfuljs.custom.registries.logic.effects.fluid;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class FillFluidEffect extends FluidEffect {
    private final FluidStack fluidStack;

    public FillFluidEffect(FluidStack fluidStack, @Nullable Direction context) {
        super(context);
        this.fluidStack = fluidStack;
    }

    @Override
    protected void runEffect(BlockCapabilityCache<IFluidHandler, Direction> cache) {
        IFluidHandler handler = cache.getCapability();
        if (handler == null) return;
        handler.drain(fluidStack, IFluidHandler.FluidAction.EXECUTE);
    }
}
