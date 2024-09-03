package moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.machine;

import mekanism.common.tile.factory.TileEntityFactory;
import moe.wolfgirl.powerfuljs.custom.logic.rules.machine.RunningRule;

public class MekFactoryRunningRule extends RunningRule<TileEntityFactory<?>> {

    @SuppressWarnings("unchecked")
    public MekFactoryRunningRule() {
        super((Class<TileEntityFactory<?>>) ((Object) TileEntityFactory.class));
    }

    @Override
    protected int getMachineProgress(TileEntityFactory<?> machine) {
        for (int progress : machine.progress) {
            if (progress > 0) return progress;
        }
        return 0;
    }
}
