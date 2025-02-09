package moe.wolfgirl.powerfuljs.mixin.farmers_delight;

import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;

import java.util.Optional;

@Mixin(CookingPotBlockEntity.class)
public abstract class CookingPotMixin implements RecipeProvider, ProgressProvider {

    @Shadow
    private int cookTime;

    @Shadow
    private int cookTimeTotal;

    @Shadow
    protected abstract Optional<RecipeHolder<CookingPotRecipe>> getMatchingRecipe(RecipeWrapper inventoryWrapper);

    @Shadow
    @Final
    private ItemStackHandler inventory;

    @Override
    public int pjs$getProgress() {
        return cookTime;
    }

    @Override
    public int pjs$getMaxProgress() {
        return cookTimeTotal;
    }

    @Override
    public void pjs$setProgress(int progress) {
        this.cookTime = progress;
    }

    @Override
    public boolean pjs$running() {
        return pjs$getRunningRecipe() != null;
    }

    @Override
    public @Nullable ResourceLocation pjs$getRunningRecipe() {
        return getMatchingRecipe(new RecipeWrapper(inventory))
                .map(RecipeHolder::id)
                .orElse(null);
    }
}
