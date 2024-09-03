package moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.machine;

import mekanism.common.tile.factory.TileEntityFactory;
import moe.wolfgirl.powerfuljs.custom.logic.rules.machine.AboutToFinishRule;
import moe.wolfgirl.powerfuljs.utils.MathUtils;

public class MekFactoryAboutToFinishRule extends AboutToFinishRule<TileEntityFactory<?>> {

    @SuppressWarnings("unchecked")
    public MekFactoryAboutToFinishRule(int spareTicks) {
        super((Class<TileEntityFactory<?>>) ((Object) TileEntityFactory.class), spareTicks);
    }

    @Override
    protected int getProgress(TileEntityFactory<?> machine) {
        return MathUtils.max(machine.progress);
    }

    @Override
    protected int getMaxProgress(TileEntityFactory<?> machine) {
        return machine.getTicksRequired();
    }
}
