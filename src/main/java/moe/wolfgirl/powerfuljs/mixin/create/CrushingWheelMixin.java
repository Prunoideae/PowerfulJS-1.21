package moe.wolfgirl.powerfuljs.mixin.create;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import com.simibubi.create.content.processing.recipe.ProcessingInventory;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.EqualityCache;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(CrushingWheelControllerBlockEntity.class)
public abstract class CrushingWheelMixin implements RecipeProvider, ProgressProvider {
    @Shadow
    public ProcessingInventory inventory;

    @Shadow
    public abstract Optional<RecipeHolder<ProcessingRecipe<RecipeWrapper>>> findRecipe();

    @Unique
    private EqualityCache<List<ItemStack>, ResourceLocation> pjs$cache = null;

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
        inventory.remainingTime = Math.max(1, inventory.recipeDuration - progress);
    }

    @Override
    public boolean pjs$running() {
        return inventory.recipeDuration > 0f;
    }

    @Override
    public @Nullable ResourceLocation pjs$getRunningRecipe() {
        if (!pjs$running()) return null;

        if (pjs$cache == null) {
            pjs$cache = new EqualityCache<>(
                    () -> {
                        ArrayList<ItemStack> arr = new ArrayList<>();
                        for (int i = 0; i < inventory.getSlots(); i++) {
                            arr.add(inventory.getStackInSlot(i));
                        }
                        return arr;
                    },
                    () -> this.findRecipe().map(RecipeHolder::id).orElse(null)
            );
        }

        return pjs$cache.get();
    }
}
