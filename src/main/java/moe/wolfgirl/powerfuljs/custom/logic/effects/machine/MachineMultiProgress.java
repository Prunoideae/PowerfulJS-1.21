package moe.wolfgirl.powerfuljs.custom.logic.effects.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class MachineMultiProgress<T extends BlockEntity> extends MachineEffect<T> {
    private final int ticks;

    protected MachineMultiProgress(Class<T> machineClass, int ticks) {
        super(machineClass);
        this.ticks = ticks;
    }

    protected abstract void setProgress(int[] progress, T machine);

    protected abstract int[] getProgress(T machine);

    protected abstract int[] getMaxProgress(T machine);

    @Override
    protected void applyEffect(ServerLevel level, BlockPos pos, BlockState state, T blockEntity) {
        int[] current = getProgress(blockEntity);
        int[] max = getMaxProgress(blockEntity);

        int[] advanced = new int[current.length];
        for (int i = 0; i < current.length; i++) {
            advanced[i] = Math.min(current[i] + ticks, max[i] - 1);
        }
        setProgress(advanced, blockEntity);
    }
}
