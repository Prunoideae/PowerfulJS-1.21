package moe.wolfgirl.powerfuljs.mixin.farmers_delight;

import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;

@Mixin(SkilletBlockEntity.class)
public abstract class SkilletMixin implements RecipeProvider, ProgressProvider {

    @Shadow
    private int cookingTime;

    @Shadow
    private int cookingTimeTotal;

    @Shadow
    @Final
    private RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> quickCheck;

    @Shadow
    private ItemStack skilletStack;

    @Unique
    public SkilletBlockEntity pjs$self() {
        return (SkilletBlockEntity) (Object) this;
    }

    @Override
    public int pjs$getProgress() {
        return cookingTime;
    }

    @Override
    public int pjs$getMaxProgress() {
        return cookingTimeTotal;
    }

    @Override
    public void pjs$setProgress(int progress) {
        this.cookingTime = progress;
    }

    @Override
    public @Nullable ResourceLocation pjs$getRunningRecipe() {
        return quickCheck.getRecipeFor(new SingleRecipeInput(skilletStack), pjs$self().getLevel())
                .map(RecipeHolder::id)
                .orElse(null);
    }
}
