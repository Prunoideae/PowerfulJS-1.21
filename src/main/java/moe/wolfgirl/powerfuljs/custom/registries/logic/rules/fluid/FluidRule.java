package moe.wolfgirl.powerfuljs.custom.registries.logic.rules.fluid;

import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.CapabilityRule;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public abstract class FluidRule extends CapabilityRule<IFluidHandler, Direction> {
    protected FluidRule(@Nullable Direction context) {
        super(Capabilities.FluidHandler.BLOCK, context);
    }
}
