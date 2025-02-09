package moe.wolfgirl.powerfuljs.mixin.minecraft;

import dev.latvian.mods.kubejs.util.Cast;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.IdentityCache;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(CampfireBlockEntity.class)
public class CampfireMixin implements MultiRecipeProvider, MultiProgressProvider {

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
    private List<IdentityCache<ItemStack, ResourceLocation>> pjs$cache;

    @Unique
    public CampfireBlockEntity pjs$self() {
        return Cast.to(this);
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
    public boolean pjs$running() {
        return !items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public List<Optional<ResourceLocation>> pjs$getRunningRecipe() {
        if (pjs$cache == null) {
            pjs$cache = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                int idx = i;
                pjs$cache.add(new IdentityCache<>(
                        () -> items.get(idx),
                        () -> quickCheck.getRecipeFor(new SingleRecipeInput(items.get(idx)), pjs$self().getLevel())
                                .map(RecipeHolder::id)
                                .orElse(null)
                ));
            }
        }

        return pjs$cache.stream()
                .map(IdentityCache::get)
                .map(Optional::ofNullable)
                .toList();
    }
}
