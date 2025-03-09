package moe.wolfgirl.powerfuljs.custom.logic.effects.fluid;

import moe.wolfgirl.powerfuljs.custom.fluid.storage.BaseFluidTank;
import moe.wolfgirl.powerfuljs.custom.fluid.storage.ProcessingFluidTank;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class DrainFluidEffect extends FluidEffect {
    private final int amount;
    private final boolean internal;

    public DrainFluidEffect(int amount, boolean internal, @Nullable Direction context) {
        super(context);
        this.amount = amount;
        this.internal = internal;
    }

    @Override
    protected void runEffect(IFluidHandler fluidHandler) {
        if (fluidHandler == null) return;
        if (!internal) fluidHandler.drain(amount, IFluidHandler.FluidAction.EXECUTE);
        else switch (fluidHandler) {
            case BaseFluidTank baseFluidTank:
                baseFluidTank.drain(amount, IFluidHandler.FluidAction.EXECUTE, true);
                break;
            case ProcessingFluidTank processingFluidTank:
                processingFluidTank.drain(amount, IFluidHandler.FluidAction.EXECUTE, 0);
                break;
            default:
                fluidHandler.drain(amount, IFluidHandler.FluidAction.EXECUTE);
        }
    }
}
