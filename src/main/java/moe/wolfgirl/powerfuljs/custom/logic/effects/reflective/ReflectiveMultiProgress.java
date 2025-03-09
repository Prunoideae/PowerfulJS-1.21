package moe.wolfgirl.powerfuljs.custom.logic.effects.reflective;

import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ReflectiveAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.lang.reflect.InvocationTargetException;

public class ReflectiveMultiProgress extends Effect {
    private final ReflectiveAccessor progress;
    private final ReflectiveAccessor maxProgress;
    private final int ticks;

    public ReflectiveMultiProgress(Class<BlockEntity> machineClass, int ticks, String progress, String maxProgress) throws NoSuchFieldException, NoSuchMethodException {
        this.ticks = ticks;
        this.progress = ReflectiveAccessor.of(machineClass, progress);
        this.maxProgress = maxProgress == null ? null : ReflectiveAccessor.of(machineClass, maxProgress);
    }

    @Override
    public void apply(boolean condition, Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        try {
            int[] progress = (int[]) this.progress.get(blockEntity);
            int[] maxProgress = this.maxProgress == null ? null : (int[]) this.maxProgress.get(blockEntity);
            int[] newProgress = new int[progress.length];

            for (int i = 0; i < progress.length; i++) {
                int updatedProgress = Math.max(0, progress[i] + ticks);
                newProgress[i] = Math.min(updatedProgress, maxProgress == null ? Integer.MAX_VALUE : maxProgress[i]);
            }
            this.progress.set(blockEntity, newProgress);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
