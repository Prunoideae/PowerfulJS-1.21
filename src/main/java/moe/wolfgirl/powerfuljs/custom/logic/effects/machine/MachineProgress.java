package moe.wolfgirl.powerfuljs.custom.logic.effects.machine;

import moe.wolfgirl.powerfuljs.custom.logic.rules.machine.MachineRule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class MachineProgress<T extends BlockEntity> extends MachineEffect<T> {
    private final int ticks;

    protected MachineProgress(Class<T> machineClass, int ticks) {
        super(machineClass);
        this.ticks = ticks;
    }

    protected abstract void setProgress(int progress, T machine);

    protected abstract int getProgress(T machine);

    protected abstract int getMaxProgress(T machine);

    @Override
    protected void applyEffect(ServerLevel level, BlockPos pos, BlockState state, T blockEntity) {
        var advancedTicks = Math.min(ticks, getMaxProgress(blockEntity) - getProgress(blockEntity) - 1);
        setProgress(getProgress(blockEntity) + advancedTicks, blockEntity);
    }
}
