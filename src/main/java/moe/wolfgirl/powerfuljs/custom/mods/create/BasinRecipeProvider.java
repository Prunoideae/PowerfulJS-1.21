package moe.wolfgirl.powerfuljs.custom.mods.create;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface BasinRecipeProvider extends RecipeProvider {
    @Nullable
    ResourceLocation pjs$getBasinRunningRecipe();

    @Override
    @Nullable
    default ResourceLocation pjs$getRunningRecipe() {
        return pjs$getBasinRunningRecipe();
    }

    Optional<BasinBlockEntity> pjs$getBasin();
}
