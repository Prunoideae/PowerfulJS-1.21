package moe.wolfgirl.powerfuljs.mixin.mekanism;

import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.recipe.lookup.monitor.FactoryRecipeCacheLookupMonitor;
import mekanism.common.tile.factory.TileEntityFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = TileEntityFactory.class, remap = false)
public interface FactoryRecipeAccessor<RECIPE extends MekanismRecipe<?>> {
    @Accessor
    FactoryRecipeCacheLookupMonitor<RECIPE>[] getRecipeCacheLookupMonitors();
}
