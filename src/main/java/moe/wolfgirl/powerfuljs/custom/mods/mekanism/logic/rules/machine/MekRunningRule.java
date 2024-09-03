package moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.machine;

import mekanism.common.tile.prefab.TileEntityProgressMachine;
import moe.wolfgirl.powerfuljs.custom.logic.rules.machine.RunningRule;

public class MekRunningRule extends RunningRule<TileEntityProgressMachine<?>> {

    @SuppressWarnings("unchecked")
    public MekRunningRule() {
        super((Class<TileEntityProgressMachine<?>>) ((Object) TileEntityProgressMachine.class));
    }

    @Override
    protected int getMachineProgress(TileEntityProgressMachine<?> machine) {
        return machine.getOperatingTicks();
    }
}
