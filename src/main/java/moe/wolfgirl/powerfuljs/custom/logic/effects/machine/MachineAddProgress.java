package moe.wolfgirl.powerfuljs.custom.logic.effects.machine;

import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.MultiProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MachineAddProgress extends Effect {
    private final int progress;

    public MachineAddProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public void apply(boolean condition, ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (!condition) return;
        if (blockEntity instanceof ProgressProvider provider) {
            int updatedProgress = Math.max(0, provider.pjs$getProgress() + progress);
            provider.pjs$setProgress(Math.min(updatedProgress, provider.pjs$getMaxProgress() - 1));
        } else if (blockEntity instanceof MultiProgressProvider multiProvider) {
            int[] progress = multiProvider.pjs$getProgress();
            int[] maxProgress = multiProvider.pjs$getMaxProgress();
            int[] newProgress = new int[multiProvider.pjs$getSlots()];

            for (int i = 0; i < maxProgress.length; i++) {
                if (maxProgress[i] == 0) continue;
                int updatedProgress = Math.max(0, progress[i] + this.progress);
                newProgress[i] = Math.min(updatedProgress, maxProgress[i] - 1);
            }
            multiProvider.pjs$setProgress(newProgress);
        }
    }
}
