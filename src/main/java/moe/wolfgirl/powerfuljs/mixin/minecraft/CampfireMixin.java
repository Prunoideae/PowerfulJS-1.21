package moe.wolfgirl.powerfuljs.mixin.minecraft;

import moe.wolfgirl.powerfuljs.custom.logic.behavior.MultiProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.MultiRecipeProvider;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Optional;

@Mixin(CampfireBlockEntity.class)
public abstract class CampfireMixin implements MultiRecipeProvider, MultiProgressProvider {

    @Shadow
    @Final
    public int[] cookingTime;

    @Shadow
    @Final
    public int[] cookingProgress;

    @Shadow
    @Final
    private NonNullList<ItemStack> items;

    @Shadow
    @Final
    private RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> quickCheck;
    @Unique
    private final NonNullList<ItemStack> pjs$cache = NonNullList.withSize(4, ItemStack.EMPTY);

    @Unique
    private final List<Optional<ResourceLocation>> pjs$recipes = NonNullList.withSize(4, Optional.empty());

    @Unique
    public CampfireBlockEntity pjs$self() {
        return (CampfireBlockEntity) (Object) this;
    }

    @Override
    public int pjs$getSlots() {
        return 4;
    }

    @Override
    public int[] pjs$getProgress() {
        return cookingProgress;
    }

    @Override
    public int[] pjs$getMaxProgress() {
        return cookingTime;
    }

    @Override
    public void pjs$setProgress(int[] progress) {
        System.arraycopy(progress, 0, cookingProgress, 0, progress.length);
    }

    @Override
    public List<Optional<ResourceLocation>> pjs$getRunningRecipe() {
        if (!pjs$cache.equals(items)) {
            for (int i = 0; i < items.size(); i++) {
                ItemStack stack = items.get(i);
                pjs$cache.set(i, stack);
                SingleRecipeInput singleRecipeInput = new SingleRecipeInput(stack);
                pjs$recipes.set(i, quickCheck.getRecipeFor(singleRecipeInput, pjs$self().getLevel())
                        .map(RecipeHolder::id));
            }
        }
        return pjs$recipes;
    }
}
