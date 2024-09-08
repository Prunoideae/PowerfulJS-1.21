package moe.wolfgirl.powerfuljs.custom.logic.effects.fluid;

import moe.wolfgirl.powerfuljs.custom.fluid.storage.BaseFluidTank;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class FillFluidEffect extends FluidEffect {
    private final FluidStack fluidStack;
    private final boolean forced;

    public FillFluidEffect(FluidStack fluidStack, boolean forced, @Nullable Direction context) {
        super(context);
        this.fluidStack = fluidStack;
        this.forced = forced;
    }

    @Override
    protected void runEffect(BlockCapabilityCache<IFluidHandler, Direction> cache) {
        IFluidHandler handler = cache.getCapability();
        if (handler == null) return;
        if (!forced || !(handler instanceof BaseFluidTank baseFluidTank)) {
            handler.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
        } else {
            baseFluidTank.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE, true);
        }
    }
}
