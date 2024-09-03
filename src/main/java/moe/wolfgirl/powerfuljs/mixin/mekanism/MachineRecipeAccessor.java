package moe.wolfgirl.powerfuljs.mixin.mekanism;

import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.recipe.lookup.monitor.RecipeCacheLookupMonitor;
import mekanism.common.tile.prefab.TileEntityRecipeMachine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = TileEntityRecipeMachine.class, remap = false)
public interface MachineRecipeAccessor<RECIPE extends MekanismRecipe<?>> {
    @Accessor
    RecipeCacheLookupMonitor<RECIPE> getRecipeCacheLookupMonitor();
}
