package moe.wolfgirl.powerfuljs.mixin.farmers_delight;

import moe.wolfgirl.powerfuljs.custom.logic.behavior.IdentityCache;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.MultiProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.MultiRecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import vectorwing.farmersdelight.common.block.entity.StoveBlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(StoveBlockEntity.class)
public abstract class StoveMixin implements MultiRecipeProvider, MultiProgressProvider {
    @Shadow
    @Final
    private static int INVENTORY_SLOT_COUNT;

    @Shadow
    @Final
    private int[] cookingTimes;

    @Shadow
    @Final
    private int[] cookingTimesTotal;

    @Shadow
    @Final
    private ItemStackHandler inventory;
    @Shadow
    @Final
    private RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> quickCheck;
    @Unique
    private List<IdentityCache<ItemStack, ResourceLocation>> pjs$cache;

    @Unique
    public StoveBlockEntity pjs$self() {
        return (StoveBlockEntity) (Object) this;
    }

    @Override
    public int pjs$getSlots() {
        return INVENTORY_SLOT_COUNT;
    }

    @Override
    public int[] pjs$getProgress() {
        return cookingTimes;
    }

    @Override
    public int[] pjs$getMaxProgress() {
        return cookingTimesTotal;
    }

    @Override
    public void pjs$setProgress(int[] progress) {
        System.arraycopy(progress, 0, cookingTimes, 0, progress.length);
    }

    @Override
    public List<Optional<ResourceLocation>> pjs$getRunningRecipe() {
        if (pjs$cache == null) {
            pjs$cache = new ArrayList<>();
            for (int i = 0; i < inventory.getSlots(); i++) {
                int idx = i;
                pjs$cache.add(new IdentityCache<>(
                        () -> inventory.getStackInSlot(idx),
                        () -> quickCheck.getRecipeFor(new SingleRecipeInput(inventory.getStackInSlot(idx)), pjs$self().getLevel())
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
