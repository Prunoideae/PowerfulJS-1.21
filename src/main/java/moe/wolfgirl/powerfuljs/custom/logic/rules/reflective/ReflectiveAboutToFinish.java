package moe.wolfgirl.powerfuljs.custom.logic.rules.reflective;

import moe.wolfgirl.powerfuljs.custom.logic.rules.machine.AboutToFinishRule;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.lang.reflect.Field;

public class ReflectiveAboutToFinish extends AboutToFinishRule<BlockEntity> {
    private final Field progress;
    private final Field maxProgress;

    public ReflectiveAboutToFinish(Class<BlockEntity> machineClass, int spareTicks, String progress, String maxProgress) throws NoSuchFieldException {
        super(machineClass, spareTicks);
        this.progress = machineClass.getDeclaredField(progress);
        this.progress.setAccessible(true);
        this.maxProgress = machineClass.getDeclaredField(maxProgress);
        this.maxProgress.setAccessible(true);
    }

    @Override
    protected int getProgress(BlockEntity machine) {
        try {
            return progress.getInt(machine);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected int getMaxProgress(BlockEntity machine) {
        try {
            return maxProgress.getInt(machine);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
