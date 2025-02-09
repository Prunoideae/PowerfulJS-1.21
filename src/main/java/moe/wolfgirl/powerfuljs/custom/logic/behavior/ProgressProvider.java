package moe.wolfgirl.powerfuljs.custom.logic.behavior;

import dev.latvian.mods.rhino.util.HideFromJS;

@HideFromJS
public interface ProgressProvider {
    int pjs$getProgress();

    int pjs$getMaxProgress();

    void pjs$setProgress(int progress);

    boolean pjs$running();

    default void pjs$addProgress(int progress) {
        int newProgress = Math.max(pjs$getProgress() + progress, 0);
        pjs$setProgress(Math.min(newProgress, pjs$getMaxProgress() - 1));
    }

    default void pjs$clearProgress() {
        pjs$setProgress(0);
    }
}
