package moe.wolfgirl.powerfuljs.custom.logic.effects.energy;

import moe.wolfgirl.powerfuljs.custom.forge_energy.storage.BaseEnergyStorage;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class DrainEnergyEffect extends EnergyEffect {
    private final int energy;
    private final boolean forced;

    public DrainEnergyEffect(int energy, boolean forced, @Nullable Direction context) {
        super(context);
        this.energy = energy;
        this.forced = forced;
    }

    @Override
    protected void runEffect(BlockCapabilityCache<IEnergyStorage, @Nullable Direction> cache) {
        IEnergyStorage storage = cache.getCapability();
        if (storage == null) return;

        if (!forced || !(storage instanceof BaseEnergyStorage baseEnergyStorage)) {
            storage.extractEnergy(energy, false);
        } else {
            baseEnergyStorage.extractEnergy(energy, false, true);
        }
    }
}
