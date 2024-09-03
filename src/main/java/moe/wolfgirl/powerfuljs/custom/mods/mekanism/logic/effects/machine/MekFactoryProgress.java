package moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.effects.machine;

import mekanism.common.tile.factory.TileEntityFactory;
import moe.wolfgirl.powerfuljs.custom.logic.effects.machine.MachineProgress;
import moe.wolfgirl.powerfuljs.mixin.mekanism.FactoryRecipeAccessor;

public class MekFactoryProgress extends MachineProgress<TileEntityFactory<?>> {

    @SuppressWarnings("unchecked")
    public MekFactoryProgress(int ticks) {
        super((Class<TileEntityFactory<?>>) ((Object) TileEntityFactory.class), ticks);
    }

    @Override
    protected void setProgress(int progress, TileEntityFactory<?> machine) {
        var caches = ((FactoryRecipeAccessor<?>) machine).getRecipeCacheLookupMonitors();
        for (int i = 0; i < caches.length; i++) {
            var cache = caches[i].getCachedRecipe(i);
            if (cache != null) cache.loadSavedOperatingTicks(progress);
        }
    }

    @Override
    protected int getProgress(TileEntityFactory<?> machine) {
        return 0;
    }

    @Override
    protected int getMaxProgress(TileEntityFactory<?> machine) {
        return 0;
    }
}
