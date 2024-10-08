package moe.wolfgirl.powerfuljs.custom.logic.rules.reflective;

import moe.wolfgirl.powerfuljs.custom.logic.rules.machine.RunningRule;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.lang.reflect.Field;

public class ReflectiveRunning extends RunningRule<BlockEntity> {
    private final Field progress;

    public ReflectiveRunning(Class<BlockEntity> machineClass, String progress) throws NoSuchFieldException {
        super(machineClass);
        this.progress = machineClass.getDeclaredField(progress);
        this.progress.setAccessible(true);
    }

    @Override
    protected int getMachineProgress(BlockEntity machine) {
        try {
            return this.progress.getInt(machine);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
