package moe.wolfgirl.powerfuljs.mixin.minecraft;

import moe.wolfgirl.powerfuljs.custom.logic.behavior.FuelProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class FurnaceMixin implements RecipeProvider, ProgressProvider, FuelProvider {

    @Shadow
    @Nullable
    public abstract RecipeHolder<?> getRecipeUsed();

    @Shadow
    public int cookingProgress;

    @Shadow
    public int cookingTotalTime;

    @Shadow
    public int litTime;

    @Shadow
    public int litDuration;

    @Override
    public ResourceLocation pjs$getRunningRecipe() {
        RecipeHolder<?> used = getRecipeUsed();
        return used == null ? null : used.id();
    }

    @Override
    public int pjs$getProgress() {
        return this.cookingProgress;
    }

    @Override
    public void pjs$setProgress(int progress) {
        this.cookingProgress = progress;
    }

    @Override
    public int pjs$getMaxProgress() {
        return this.cookingTotalTime;
    }

    @Override
    public int pjs$getFuelTime() {
        return this.litTime;
    }

    @Override
    public int pjs$getMaxFuelTime() {
        return this.litDuration;
    }

    @Override
    public void pjs$setFuelTime(int fuelTime) {
        this.litTime = fuelTime;
    }

    @Override
    public void pjs$setMaxFuelTime(int maxFuelTime) {
        this.litDuration = maxFuelTime;
    }
}
