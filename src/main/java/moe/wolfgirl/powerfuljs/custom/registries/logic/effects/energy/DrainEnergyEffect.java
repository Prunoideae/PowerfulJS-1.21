package moe.wolfgirl.powerfuljs.custom.registries.logic.effects.energy;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class DrainEnergyEffect extends EnergyEffect {
    private final int energy;

    public DrainEnergyEffect(int energy, @Nullable Direction context) {
        super(context);
        this.energy = energy;
    }

    @Override
    protected void runEffect(BlockCapabilityCache<IEnergyStorage, @Nullable Direction> cache) {
        IEnergyStorage storage = cache.getCapability();
        if (storage != null) storage.extractEnergy(energy, false);
    }
}
