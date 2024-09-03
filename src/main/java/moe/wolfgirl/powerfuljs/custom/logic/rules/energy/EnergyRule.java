package moe.wolfgirl.powerfuljs.custom.logic.rules.energy;

import moe.wolfgirl.powerfuljs.custom.logic.rules.CapabilityRule;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;


public abstract class EnergyRule extends CapabilityRule<IEnergyStorage, @Nullable Direction> {

    protected EnergyRule(@Nullable Direction context) {
        super(Capabilities.EnergyStorage.BLOCK, context);
    }
}
