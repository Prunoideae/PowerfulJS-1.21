package moe.wolfgirl.powerfuljs.custom.logic.rules.cooker;

import moe.wolfgirl.powerfuljs.custom.logic.rules.machine.AboutToFinishRule;
import moe.wolfgirl.powerfuljs.utils.MathUtils;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;

public class CampfireAboutToFinishRule extends AboutToFinishRule<CampfireBlockEntity> {
    public CampfireAboutToFinishRule(int spareTicks) {
        super(CampfireBlockEntity.class, spareTicks);
    }

    @Override
    protected int getProgress(CampfireBlockEntity machine) {
        return MathUtils.max(machine.cookingProgress);
    }

    @Override
    protected int getMaxProgress(CampfireBlockEntity machine) {
        return MathUtils.max(machine.cookingTime);
    }
}
