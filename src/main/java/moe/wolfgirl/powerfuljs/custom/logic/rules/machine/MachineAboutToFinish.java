package moe.wolfgirl.powerfuljs.custom.logic.rules.machine;

import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.MultiProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MachineAboutToFinish extends Rule {
    private final int spareTicks;

    public MachineAboutToFinish(int spareTicks) {
        this.spareTicks = spareTicks;
    }

    @Override
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (blockEntity instanceof ProgressProvider provider) {
            return provider.pjs$getMaxProgress() - provider.pjs$getProgress() <= spareTicks;
        } else if (blockEntity instanceof MultiProgressProvider multiProvider) {
            int[] progress = multiProvider.pjs$getProgress();
            int[] maxProgress = multiProvider.pjs$getMaxProgress();
            for (int i = 0; i < maxProgress.length; i++) {
                if (maxProgress[i] - progress[i] <= spareTicks) return true;
            }
        }

        return false;
    }
}
