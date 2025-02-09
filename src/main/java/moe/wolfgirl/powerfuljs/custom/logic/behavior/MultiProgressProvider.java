package moe.wolfgirl.powerfuljs.custom.logic.behavior;

import dev.latvian.mods.rhino.util.HideFromJS;

@HideFromJS
public interface MultiProgressProvider {

    int pjs$getSlots();

    int[] pjs$getProgress();

    int[] pjs$getMaxProgress();

    void pjs$setProgress(int[] progress);

    boolean pjs$running();

    default void pjs$addProgress(int added) {
        int[] progress = pjs$getProgress();
        int[] maxProgress = pjs$getMaxProgress();
        int[] newProgress = new int[pjs$getSlots()];

        for (int i = 0; i < maxProgress.length; i++) {
            if (maxProgress[i] == 0) continue;
            int updatedProgress = Math.max(0, progress[i] + added);
            newProgress[i] = Math.min(updatedProgress, maxProgress[i] - 1);
        }
        pjs$setProgress(newProgress);
    }

    default void pjs$clearProgress() {
        pjs$setProgress(new int[pjs$getSlots()]);
    }
}
