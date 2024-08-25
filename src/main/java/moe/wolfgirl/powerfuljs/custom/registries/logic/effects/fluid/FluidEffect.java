package moe.wolfgirl.powerfuljs.custom.registries.logic.effects.fluid;

import moe.wolfgirl.powerfuljs.custom.registries.logic.effects.CapabilityEffect;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public abstract class FluidEffect extends CapabilityEffect<IFluidHandler, Direction> {

    protected FluidEffect(@Nullable Direction context) {
        super(Capabilities.FluidHandler.BLOCK, context);
    }
}
