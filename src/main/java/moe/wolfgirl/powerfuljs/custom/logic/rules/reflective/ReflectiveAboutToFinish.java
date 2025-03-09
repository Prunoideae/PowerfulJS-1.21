package moe.wolfgirl.powerfuljs.custom.logic.rules.reflective;

import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ReflectiveAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.lang.reflect.InvocationTargetException;

public class ReflectiveAboutToFinish extends Rule {
    private final ReflectiveAccessor progress;
    private final ReflectiveAccessor maxProgress;
    private final int spareTicks;

    public ReflectiveAboutToFinish(Class<BlockEntity> machineClass, int spareTicks, String progress, String maxProgress) throws NoSuchFieldException, NoSuchMethodException {
        this.progress = ReflectiveAccessor.of(machineClass, progress);
        this.maxProgress = ReflectiveAccessor.of(machineClass, maxProgress);
        this.spareTicks = spareTicks;
    }

    @Override
    public boolean evaluate(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        try {
            return (int) maxProgress.get(blockEntity) - (int) progress.get(blockEntity) <= spareTicks;
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
