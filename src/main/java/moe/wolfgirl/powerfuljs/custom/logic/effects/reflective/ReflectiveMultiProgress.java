package moe.wolfgirl.powerfuljs.custom.logic.effects.reflective;

import moe.wolfgirl.powerfuljs.custom.logic.effects.machine.MachineMultiProgress;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.lang.reflect.Field;

public class ReflectiveMultiProgress extends MachineMultiProgress<BlockEntity> {
    private final Field progress;
    private final Field maxProgress;

    public ReflectiveMultiProgress(Class<BlockEntity> machineClass, int ticks, String progress, String maxProgress) throws NoSuchFieldException {
        super(machineClass, ticks);

        this.progress = machineClass.getDeclaredField(progress);
        this.progress.setAccessible(true);
        this.maxProgress = machineClass.getDeclaredField(maxProgress);
        this.maxProgress.setAccessible(true);
    }

    @Override
    protected void setProgress(int[] progress, BlockEntity machine) {
        try {
            this.progress.set(progress, machine);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected int[] getProgress(BlockEntity machine) {
        try {
            return (int[]) this.progress.get(machine);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected int[] getMaxProgress(BlockEntity machine) {
        try {
            return (int[]) this.maxProgress.get(machine);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
