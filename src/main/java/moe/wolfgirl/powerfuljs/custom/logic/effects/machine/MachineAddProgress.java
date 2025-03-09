package moe.wolfgirl.powerfuljs.custom.logic.effects.machine;

import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.MultiProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MachineAddProgress extends Effect {
    private final float progress;
    private float accumulatedProgress = 0;
    private BlockEntity cache;

    public MachineAddProgress(float progress) {
        this.progress = progress;
    }

    @Override
    public void apply(boolean condition, Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (!condition) return;
        if (cache == null) cache = blockEntity;
        if (cache != blockEntity) throw new RuntimeException("The same block entity shall not be used by multiple block entities! Make a new instance instead.");
        accumulatedProgress += progress;
        int advancedTicks = (int) Math.floor(accumulatedProgress);
        accumulatedProgress -= advancedTicks;
        if (blockEntity instanceof ProgressProvider provider) {
            provider.pjs$addProgress(advancedTicks);
        } else if (blockEntity instanceof MultiProgressProvider multiProvider) {
            multiProvider.pjs$addProgress(advancedTicks);
        }
    }
}
