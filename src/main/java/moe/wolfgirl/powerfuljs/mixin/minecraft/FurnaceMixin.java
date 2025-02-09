package moe.wolfgirl.powerfuljs.mixin.minecraft;

import moe.wolfgirl.powerfuljs.custom.logic.behavior.*;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractFurnaceBlockEntity.class)
public class FurnaceMixin implements RecipeProvider, ProgressProvider, FuelProvider {

    @Shadow
    public int cookingProgress;

    @Shadow
    public int cookingTotalTime;

    @Shadow
    public int litTime;

    @Shadow
    public int litDuration;

    @Shadow
    protected NonNullList<ItemStack> items;
    @Shadow
    @Final
    private RecipeManager.CachedCheck<SingleRecipeInput, ? extends AbstractCookingRecipe> quickCheck;

    @Unique
    public AbstractFurnaceBlockEntity pjs$getSelf() {
        return (AbstractFurnaceBlockEntity) (Object) this;
    }

    @Override
    public ResourceLocation pjs$getRunningRecipe() {
        return quickCheck.getRecipeFor(new SingleRecipeInput(items.getFirst()), pjs$getSelf().getLevel())
                .map(RecipeHolder::id)
                .orElse(null);
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
    public boolean pjs$running() {
        return litTime > 0 && pjs$getRunningRecipe() != null;
    }

    @Override
    public int pjs$getFuel() {
        return this.litTime;
    }

    @Override
    public void pjs$setFuel(int fuelTime) {
        fuelTime = Math.max(0, fuelTime);
        if (fuelTime == 1 && this.litTime == 0) fuelTime++; // Prevent tick flickering

        if (fuelTime > 0 && this.litTime == 0) {
            AbstractFurnaceBlockEntity self = pjs$getSelf();
            if (self.getLevel() != null) {
                self.getLevel().setBlock(
                        self.getBlockPos(),
                        self.getBlockState().setValue(AbstractFurnaceBlock.LIT, true),
                        3
                );
            }
        }

        this.litDuration = fuelTime;
        this.litTime = fuelTime;
    }
}
