package moe.wolfgirl.powerfuljs.custom.logic.rules.energy;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class CanExtractEnergy extends EnergyRule {
    private final int energy;

    public CanExtractEnergy(int energy, @Nullable Direction context) {
        super(context);
        this.energy = energy;
    }

    @Override
    protected boolean evaluateCap(BlockCapabilityCache<IEnergyStorage, @Nullable Direction> capabilityCache) {
        IEnergyStorage storage = capabilityCache.getCapability();
        if (storage == null) return false;
        return storage.extractEnergy(energy, true) == energy;
    }
}
