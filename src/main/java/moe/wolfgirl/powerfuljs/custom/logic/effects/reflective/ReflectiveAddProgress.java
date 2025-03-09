package moe.wolfgirl.powerfuljs.custom.logic.effects.reflective;

import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ReflectiveAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.lang.reflect.InvocationTargetException;

public class ReflectiveAddProgress extends Effect {
    private final ReflectiveAccessor progress;
    private final ReflectiveAccessor maxProgress;
    private final int ticks;

    public ReflectiveAddProgress(Class<BlockEntity> machineClass, int ticks, String progress, String maxProgress) throws NoSuchFieldException, NoSuchMethodException {
        this.ticks = ticks;
        this.progress = ReflectiveAccessor.of(machineClass, progress);
        this.maxProgress = maxProgress == null ? null : ReflectiveAccessor.of(machineClass, maxProgress);
    }

    @Override
    public void apply(boolean condition, Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        try {
            int maxProgress = this.maxProgress == null ? Integer.MAX_VALUE : (int) this.maxProgress.get(blockEntity);
            int progress = Math.max(0, ticks + (int) this.progress.get(blockEntity));
            this.progress.set(blockEntity, Math.min(maxProgress - 1, progress));
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
