package moe.wolfgirl.powerfuljs.custom.logic.effects.energy;

import moe.wolfgirl.powerfuljs.custom.logic.effects.CapabilityEffect;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public abstract class EnergyEffect extends CapabilityEffect<IEnergyStorage, @Nullable Direction> {
    protected EnergyEffect(@Nullable Direction context) {
        super(Capabilities.EnergyStorage.BLOCK, context);
    }
}
