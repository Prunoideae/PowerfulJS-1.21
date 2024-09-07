package moe.wolfgirl.powerfuljs.custom.forge_energy.storage;

import moe.wolfgirl.probejs.ProbeJS;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.energy.IEnergyStorage;

/**
 * Represents an energy storage that is capable of storing energy.
 * So an energy attachment/component will be used to make it persistent.
 */
public abstract class BaseEnergyStorage implements IEnergyStorage {

    @Override
    public int receiveEnergy(int toReceive, boolean simulate) {
        return receiveEnergy(toReceive, simulate, false);
    }

    public int receiveEnergy(int toReceive, boolean simulate, boolean forced) {
        if ((!canReceive() && !forced) || toReceive <= 0) {
            return 0;
        }
        int energy = this.getEnergyStored();
        int received = Mth.clamp(this.getMaxEnergyStored() - energy, 0, forced ? toReceive : Math.min(this.getMaxReceive(), toReceive));
        if (received > 0) {
            if (!simulate) {
                this.setEnergyData(energy + received);
                onChanged();
            }
            onReceived(received, simulate);
        }
        return received;
    }

    @Override
    public int extractEnergy(int toExtract, boolean simulate) {
        return extractEnergy(toExtract, simulate, false);
    }

    public int extractEnergy(int toExtract, boolean simulate, boolean forced) {
        if ((!canExtract() && !forced) || toExtract <= 0) {
            return 0;
        }

        int energy = this.getEnergyStored();
        int extracted = Math.min(energy, forced ? toExtract : Math.min(this.getMaxExtract(), toExtract));

        if (extracted > 0) {
            if (!simulate) {
                this.setEnergyData(energy - extracted);
                onChanged();
            }
            onExtracted(energy, simulate);
        }
        return extracted;
    }

    @Override
    public int getEnergyStored() {
        return Mth.clamp(this.getEnergyData(), 0, this.getMaxEnergyStored());
    }

    protected abstract int getMaxReceive();

    protected abstract int getMaxExtract();

    @Override
    public boolean canExtract() {
        return getMaxExtract() > 0;
    }

    @Override
    public boolean canReceive() {
        return getMaxReceive() > 0;
    }

    protected abstract void setEnergyData(int energy);

    protected abstract int getEnergyData();

    protected void onReceived(int energy, boolean simulate) {
    }

    protected void onExtracted(int energy, boolean simulate) {
    }

    protected void onChanged() {
    }
}
