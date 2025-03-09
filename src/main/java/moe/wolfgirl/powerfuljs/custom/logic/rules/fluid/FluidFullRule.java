package moe.wolfgirl.powerfuljs.custom.logic.rules.fluid;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class FluidFullRule extends FluidRule {
    private final int tank;

    public FluidFullRule(int tank, @Nullable Direction context) {
        super(context);
        this.tank = tank;
    }

    @Override
    protected boolean evaluateCap(IFluidHandler handler) {
        return handler.getFluidInTank(tank).getAmount() >= handler.getTankCapacity(tank);
    }
}
