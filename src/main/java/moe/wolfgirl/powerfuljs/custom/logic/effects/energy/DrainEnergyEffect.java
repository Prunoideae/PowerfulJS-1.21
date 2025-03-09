package moe.wolfgirl.powerfuljs.custom.logic.effects.energy;

import moe.wolfgirl.powerfuljs.custom.forge_energy.storage.BaseEnergyStorage;
import net.minecraft.core.Direction;
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
    protected void runEffect(IEnergyStorage cap) {
        if (cap == null) return;
        if (!forced || !(cap instanceof BaseEnergyStorage baseEnergyStorage)) {
            cap.extractEnergy(energy, false);
        } else {
            baseEnergyStorage.extractEnergy(energy, false, true);
        }
    }
}
