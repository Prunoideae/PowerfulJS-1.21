package moe.wolfgirl.powerfuljs.custom.logic.effects.reflective;

import moe.wolfgirl.powerfuljs.custom.logic.effects.machine.MachineProgress;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.lang.reflect.Field;

public class ReflectiveAddProgress extends MachineProgress<BlockEntity> {
    private final Field progress;
    private final Field maxProgress;

    public ReflectiveAddProgress(Class<BlockEntity> machineClass, int ticks, String progress, String maxProgress) throws NoSuchFieldException {
        super(machineClass, ticks);
        this.progress = machineClass.getDeclaredField(progress);
        this.progress.setAccessible(true);
        if (maxProgress != null) {
            this.maxProgress = machineClass.getDeclaredField(maxProgress);
            this.maxProgress.setAccessible(true);
        }else {
            this.maxProgress = null;
        }
    }

    @Override
    protected void setProgress(int progress, BlockEntity machine) {
        try {
            this.progress.setInt(machine, progress);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected int getProgress(BlockEntity machine) {
        try {
            return this.progress.getInt(machine);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected int getMaxProgress(BlockEntity machine) {
        try {
            return this.maxProgress != null ? this.maxProgress.getInt(machine): Integer.MAX_VALUE;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
