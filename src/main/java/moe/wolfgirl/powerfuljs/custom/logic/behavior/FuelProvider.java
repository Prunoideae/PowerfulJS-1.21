package moe.wolfgirl.powerfuljs.custom.logic.behavior;

import dev.latvian.mods.rhino.util.HideFromJS;

@HideFromJS
public interface FuelProvider {
    int pjs$getFuel();

    void pjs$setFuel(int fuelTime);

    default int pjs$getFuelCost() {
        return 1;
    }
}
