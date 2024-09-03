package moe.wolfgirl.powerfuljs.custom.logic.rules.machine;

import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public class FurnaceAboutToFinishRule extends AboutToFinishRule<AbstractFurnaceBlockEntity> {
    public FurnaceAboutToFinishRule(int spareTicks) {
        super(AbstractFurnaceBlockEntity.class, spareTicks);
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
