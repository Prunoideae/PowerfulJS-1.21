package moe.wolfgirl.powerfuljs.custom.logic.effects.machine;

import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.MultiProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MachineClearProgress extends Effect {
    @Override
    public void apply(boolean condition, ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (!condition) return;
        if (blockEntity instanceof ProgressProvider provider) {
            provider.pjs$setProgress(0);
        } else if (blockEntity instanceof MultiProgressProvider multiProvider) {
            int[] arr = new int[multiProvider.pjs$getSlots()];
            multiProvider.pjs$setProgress(arr);
        }
    }
}
