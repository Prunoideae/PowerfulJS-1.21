package moe.wolfgirl.powerfuljs.custom.logic.rules.cooker;

import moe.wolfgirl.powerfuljs.custom.logic.rules.machine.RunningRule;
import moe.wolfgirl.powerfuljs.utils.MathUtils;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;

public class CampfireRunningRule extends RunningRule<CampfireBlockEntity> {
    public CampfireRunningRule() {
        super(CampfireBlockEntity.class);
    }

    @Override
    protected int getMachineProgress(CampfireBlockEntity machine) {
        return MathUtils.max(machine.cookingProgress);
    }
}
