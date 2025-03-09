package moe.wolfgirl.powerfuljs.custom.logic.effects.fluid;

import moe.wolfgirl.powerfuljs.custom.fluid.storage.BaseFluidTank;
import moe.wolfgirl.powerfuljs.custom.fluid.storage.ProcessingFluidTank;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class FillFluidEffect extends FluidEffect {
    private final FluidStack fluidStack;
    private final boolean forced;

    public FillFluidEffect(FluidStack fluidStack, boolean internal, @Nullable Direction context) {
        super(context);
        this.fluidStack = fluidStack;
        this.forced = internal;
    }

    @Override
    protected void runEffect(IFluidHandler handler) {
        if (handler == null) return;
        if (!forced) handler.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
        else switch (handler) {
            case BaseFluidTank baseFluidTank:
                baseFluidTank.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE, true);
                break;
            case ProcessingFluidTank processingFluidTank:
                processingFluidTank.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE, 1);
                break;
            default:
                handler.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
        }
    }
}
