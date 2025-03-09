package moe.wolfgirl.powerfuljs.custom.logic.effects.energy;

import moe.wolfgirl.powerfuljs.custom.forge_energy.storage.BaseEnergyStorage;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class FillEnergyEffect extends EnergyEffect {
    private final int energy;
    private final boolean forced;

    public FillEnergyEffect(int energy, boolean forced, @Nullable Direction context) {
        super(context);
        this.energy = energy;
        this.forced = forced;
    }

    @Override
    protected void runEffect(IEnergyStorage storage) {
        if (storage == null) return;

        if (!forced || !(storage instanceof BaseEnergyStorage baseEnergyStorage)) {
            storage.receiveEnergy(energy, false);
        } else {
            baseEnergyStorage.receiveEnergy(energy, false, true);
        }
    }
}
