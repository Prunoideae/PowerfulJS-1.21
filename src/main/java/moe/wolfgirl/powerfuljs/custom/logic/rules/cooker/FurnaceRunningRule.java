package moe.wolfgirl.powerfuljs.custom.logic.rules.cooker;

import moe.wolfgirl.powerfuljs.custom.logic.rules.machine.RunningRule;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public class FurnaceRunningRule extends RunningRule<AbstractFurnaceBlockEntity> {
    public FurnaceRunningRule() {
        super(AbstractFurnaceBlockEntity.class);
    }

    @Override
    protected int getMachineProgress(AbstractFurnaceBlockEntity machine) {
        return machine.cookingProgress;
    }
}
