package moe.wolfgirl.powerfuljs.custom.logic.rules.machine;

import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class MachineRule<T extends BlockEntity> extends Rule {
    private final Class<T> machineClass;

    protected MachineRule(Class<T> machineClass) {
        this.machineClass = machineClass;
    }

    protected abstract boolean evaluateMachine(ServerLevel level, BlockPos pos, BlockState state, T machine);

    @Override
    @SuppressWarnings("unchecked")
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (machineClass.isInstance(blockEntity)) {
            return evaluateMachine(level, pos, state, (T) blockEntity);
        }
        return false;
    }
}
