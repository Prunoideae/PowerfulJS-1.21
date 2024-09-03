package moe.wolfgirl.powerfuljs.custom.logic.rules.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AboutToFinishRule<T extends BlockEntity> extends MachineRule<T> {
    private final int spareTicks;

    protected AboutToFinishRule(Class<T> machineClass, int spareTicks) {
        super(machineClass);
        this.spareTicks = spareTicks;
    }

    protected abstract int getProgress(T machine);

    protected abstract int getMaxProgress(T machine);

    @Override
    protected boolean evaluateMachine(ServerLevel level, BlockPos pos, BlockState state, T machine) {
        int progress = getProgress(machine);
        if (progress == 0) return false;
        return getMaxProgress(machine) - progress <= spareTicks;
    }
}
