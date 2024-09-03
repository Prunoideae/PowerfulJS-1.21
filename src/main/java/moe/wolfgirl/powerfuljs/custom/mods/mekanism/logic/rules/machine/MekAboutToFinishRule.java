package moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.machine;

import mekanism.common.tile.prefab.TileEntityProgressMachine;
import moe.wolfgirl.powerfuljs.custom.logic.rules.machine.AboutToFinishRule;

public class MekAboutToFinishRule extends AboutToFinishRule<TileEntityProgressMachine<?>> {

    @SuppressWarnings("unchecked")
    public MekAboutToFinishRule(int spareTicks) {
        super((Class<TileEntityProgressMachine<?>>) ((Object) TileEntityProgressMachine.class), spareTicks);
    }

    @Override
    protected int getProgress(TileEntityProgressMachine<?> machine) {
        return machine.getOperatingTicks();
    }

    @Override
    protected int getMaxProgress(TileEntityProgressMachine<?> machine) {
        return machine.getTicksRequired();
    }
}
