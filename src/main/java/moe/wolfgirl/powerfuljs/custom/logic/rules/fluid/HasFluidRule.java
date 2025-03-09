package moe.wolfgirl.powerfuljs.custom.logic.rules.fluid;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.Nullable;

public class HasFluidRule extends FluidRule {
    private final SizedFluidIngredient fluidIngredient;
    private final int tank;

    public HasFluidRule(SizedFluidIngredient fluidIngredient, int tank, @Nullable Direction context) {
        super(context);
        this.fluidIngredient = fluidIngredient;
        this.tank = tank;
    }

    @Override
    protected boolean evaluateCap(IFluidHandler handler) {
        if (handler == null || handler.getTanks() <= tank) return false;
        return fluidIngredient.test(handler.getFluidInTank(tank));
    }
}
