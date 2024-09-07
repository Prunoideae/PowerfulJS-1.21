package moe.wolfgirl.powerfuljs.custom.logic.effects.furnace;

import moe.wolfgirl.powerfuljs.custom.logic.effects.machine.MachineProgress;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public class FurnaceProgress extends MachineProgress<AbstractFurnaceBlockEntity> {
    public FurnaceProgress(int ticks) {
        super(AbstractFurnaceBlockEntity.class, ticks);
    }

    @Override
    protected void setProgress(int progress, AbstractFurnaceBlockEntity machine) {
        machine.cookingProgress = progress;
    }

    @Override
    protected int getProgress(AbstractFurnaceBlockEntity machine) {
        return machine.cookingProgress;
    }

    @Override
    protected int getMaxProgress(AbstractFurnaceBlockEntity machine) {
        return machine.cookingTotalTime;
    }
}
