package moe.wolfgirl.powerfuljs.custom.logic.rules.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class RunningRule<T extends BlockEntity> extends MachineRule<T> {
    private int lastProgress = 0;

    protected RunningRule(Class<T> machineClass) {
        super(machineClass);
    }

    protected abstract int getMachineProgress(T machine);

    @Override
    protected boolean evaluateMachine(ServerLevel level, BlockPos pos, BlockState state, T machine) {
        int curProgress = getMachineProgress(machine);
        boolean flag = curProgress > 0 || lastProgress > 0;
        lastProgress = curProgress;
        return flag;
    }
}
