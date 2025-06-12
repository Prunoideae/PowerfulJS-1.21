package moe.wolfgirl.powerfuljs.custom.logic.rules.machine;

import moe.wolfgirl.powerfuljs.GameStates;
import moe.wolfgirl.powerfuljs.PowerfulJS;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.MultiRecipeProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.RecipeProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.Set;

public class MachineRunningRecipeTag extends Rule {
    private final Set<ResourceLocation> tags;

    public MachineRunningRecipeTag(Set<ResourceLocation> tags) {
        this.tags = tags;
    }


    @Override
    public boolean evaluate(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (blockEntity instanceof RecipeProvider provider) {
            var recipeTags = GameStates.RECIPE_TAGS.getOrDefault(provider.pjs$getRunningRecipe(), Set.of());
            for (ResourceLocation tag : tags) {
                if (!recipeTags.contains(tag)) return false;
            }
            return true;
        } else if (blockEntity instanceof MultiRecipeProvider multiProvider) {
            for (Optional<ResourceLocation> resourceLocation : multiProvider.pjs$getRunningRecipe()) {
                if (resourceLocation.map((recipeId) -> {
                    var recipeTags = GameStates.RECIPE_TAGS.getOrDefault(recipeId, Set.of());
                    for (ResourceLocation tag : tags) {
                        if (!recipeTags.contains(tag)) return false;
                    }
                    return true;
                }).orElse(false)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
