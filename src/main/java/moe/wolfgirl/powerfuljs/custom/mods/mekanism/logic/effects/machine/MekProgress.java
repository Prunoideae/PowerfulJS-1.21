package moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.effects.machine;

import mekanism.common.tile.prefab.TileEntityProgressMachine;
import moe.wolfgirl.powerfuljs.custom.logic.effects.machine.MachineProgress;
import moe.wolfgirl.powerfuljs.mixin.mekanism.MachineRecipeAccessor;

public class MekProgress extends MachineProgress<TileEntityProgressMachine<?>> {

    @SuppressWarnings("unchecked")
    public MekProgress(int ticks) {
        super((Class<TileEntityProgressMachine<?>>) ((Object) TileEntityProgressMachine.class), ticks);
    }

    @Override
    protected void setProgress(int progress, TileEntityProgressMachine<?> machine) {
        var cache = ((MachineRecipeAccessor<?>) machine).getRecipeCacheLookupMonitor().getCachedRecipe(0);
        if (cache != null) cache.loadSavedOperatingTicks(progress);
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
