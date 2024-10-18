package moe.wolfgirl.powerfuljs.custom.logic.behavior;

import dev.latvian.mods.rhino.util.HideFromJS;

@HideFromJS
public interface MultiProgressProvider {
    int[] pjs$getProgress();

    int[] pjs$getMaxProgress();

    void pjs$setProgress(int[] progress);
}
