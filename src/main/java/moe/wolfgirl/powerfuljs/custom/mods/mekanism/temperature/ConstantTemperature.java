package moe.wolfgirl.powerfuljs.custom.mods.mekanism.temperature;

import mekanism.api.heat.IHeatHandler;

public class ConstantTemperature implements IHeatHandler {

    @Override
    public int getHeatCapacitorCount() {
        return 1;
    }


    @Override
    public double getTemperature(int capacitor) {
        return 0;
    }

    @Override
    public double getInverseConduction(int capacitor) {
        return 0;
    }

    @Override
    public double getHeatCapacity(int capacitor) {
        return 0;
    }

    @Override
    public void handleHeat(int capacitor, double transfer) {

    }
}
