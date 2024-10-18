package moe.wolfgirl.powerfuljs.custom.logic.behavior;

import dev.latvian.mods.rhino.util.HideFromJS;

@HideFromJS
public interface FuelProvider {
    int pjs$getFuelTime();

    int pjs$getMaxFuelTime();

    void pjs$setFuelTime(int fuelTime);

    void pjs$setMaxFuelTime(int maxFuelTime);
}
