package moe.wolfgirl.powerfuljs.custom.logic.effects.cooker;

import moe.wolfgirl.powerfuljs.custom.logic.effects.machine.MachineMultiProgress;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;

public class CampfireProgress extends MachineMultiProgress<CampfireBlockEntity> {

    public CampfireProgress(int ticks) {
        super(CampfireBlockEntity.class, ticks);
    }

    @Override
    protected void setProgress(int[] progress, CampfireBlockEntity machine) {
        System.arraycopy(progress, 0, machine.cookingProgress, 0, progress.length);
    }

    @Override
    protected int[] getProgress(CampfireBlockEntity machine) {
        return machine.cookingProgress;
    }

    @Override
    protected int[] getMaxProgress(CampfireBlockEntity machine) {
        return machine.cookingTime;
    }
}
