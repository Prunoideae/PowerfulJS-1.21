package moe.wolfgirl.powerfuljs.custom.registries.logic.rules.fluid;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.Nullable;

public class HasFluidRule extends FluidRule {
    private final SizedFluidIngredient fluidIngredient;

    public HasFluidRule(SizedFluidIngredient fluidIngredient, @Nullable Direction context) {
        super(context);
        this.fluidIngredient = fluidIngredient;
    }

    @Override
    protected boolean evaluateCap(BlockCapabilityCache<IFluidHandler, Direction> capabilityCache) {
        var handler = capabilityCache.getCapability();
        if (handler == null) return false;
        var fluid = handler.getFluidInTank(0);
        if (fluid.isEmpty()) return false;
        return fluidIngredient.test(fluid);
    }
}
