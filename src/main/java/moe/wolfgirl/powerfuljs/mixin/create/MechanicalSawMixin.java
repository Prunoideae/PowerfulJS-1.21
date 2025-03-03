package moe.wolfgirl.powerfuljs.mixin.create;

import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.content.processing.recipe.ProcessingInventory;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.IdentityCache;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(SawBlockEntity.class)
public abstract class MechanicalSawMixin implements RecipeProvider, ProgressProvider {

    @Shadow
    public ProcessingInventory inventory;

    @Shadow
    protected abstract List<RecipeHolder<? extends Recipe<?>>> getRecipes();

    @Shadow
    private int recipeIndex;

    @Unique
    private IdentityCache<ItemStack, ResourceLocation> pjs$cache = null;

    @Override
    public int pjs$getProgress() {
        return (int) (inventory.recipeDuration - inventory.remainingTime);
    }

    @Override
    public int pjs$getMaxProgress() {
        return (int) inventory.recipeDuration;
    }

    @Override
    public void pjs$setProgress(int progress) {
        inventory.remainingTime = inventory.recipeDuration - progress;
        ((SawBlockEntity) (Object) this).sendData();
    }

    @Override
    public boolean pjs$running() {
        return inventory.remainingTime != -1f;
    }

    @Override
    public @Nullable ResourceLocation pjs$getRunningRecipe() {
        if (pjs$cache == null) {
            pjs$cache = new IdentityCache<>(
                    () -> inventory.getStackInSlot(0),
                    () -> {
                        var recipes = getRecipes();
                        if (recipes.isEmpty()) return null;
                        return recipes.get(recipeIndex).id();
                    }
            );
        }
        return pjs$cache.get();
    }
}
