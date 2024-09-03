package moe.wolfgirl.powerfuljs.custom.logic.effects.machine;

import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class MachineEffect<T extends BlockEntity> extends Effect {
    private final Class<T> machineClass;

    protected MachineEffect(Class<T> machineClass) {
        this.machineClass = machineClass;
    }

    protected abstract void applyEffect(ServerLevel level, BlockPos pos, BlockState state, T blockEntity);

    @SuppressWarnings("unchecked")
    @Override
    public void apply(boolean condition, ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (condition && machineClass.isInstance(blockEntity)) {
            applyEffect(level, pos, state, (T) blockEntity);
        }
    }
}
