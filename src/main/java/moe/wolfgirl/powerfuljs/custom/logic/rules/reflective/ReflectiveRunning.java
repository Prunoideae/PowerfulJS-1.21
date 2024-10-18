package moe.wolfgirl.powerfuljs.custom.logic.rules.reflective;

import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ReflectiveAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.lang.reflect.InvocationTargetException;

public class ReflectiveRunning extends Rule {
    private final ReflectiveAccessor progress;

    public ReflectiveRunning(Class<BlockEntity> machineClass, String progress) throws NoSuchFieldException, NoSuchMethodException {
        this.progress = ReflectiveAccessor.of(machineClass, progress);
    }

    @Override
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        try {
            return (int) progress.get(blockEntity) > 0;
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
