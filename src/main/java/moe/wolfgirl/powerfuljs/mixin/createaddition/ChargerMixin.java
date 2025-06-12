package moe.wolfgirl.powerfuljs.mixin.createaddition;

import com.mrh0.createaddition.blocks.tesla_coil.TeslaCoilBlockEntity;
import com.mrh0.createaddition.recipe.charging.ChargingRecipe;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(TeslaCoilBlockEntity.class)
public class ChargerMixin implements RecipeProvider, ProgressProvider {
    @Shadow
    private Optional<RecipeHolder<ChargingRecipe>> recipeCache;

    @Shadow
    private int chargeAccumulator;

    @Override
    public int pjs$getProgress() {
        return recipeCache.isEmpty() ? 0 : chargeAccumulator;
    }

    @Override
    public int pjs$getMaxProgress() {
        return recipeCache.map(chargingRecipeRecipeHolder -> chargingRecipeRecipeHolder.value().getEnergy()).orElse(0);
    }

    @Override
    public void pjs$setProgress(int progress) {
        chargeAccumulator = progress;
    }

    @Override
    public boolean pjs$running() {
        return chargeAccumulator > 0 && recipeCache.isPresent();
    }

    @Override
    public @Nullable ResourceLocation pjs$getRunningRecipe() {
        return recipeCache.map(RecipeHolder::id).orElse(null);
    }
}
