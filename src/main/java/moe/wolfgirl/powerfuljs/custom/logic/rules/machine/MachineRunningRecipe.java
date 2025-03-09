package moe.wolfgirl.powerfuljs.custom.logic.rules.machine;

import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.MultiRecipeProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.RecipeProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.Set;

public class MachineRunningRecipe extends Rule {

    private final Set<ResourceLocation> recipesToCheck;

    public MachineRunningRecipe(Set<ResourceLocation> recipesToCheck) {
        this.recipesToCheck = recipesToCheck;
    }

    @Override
    public boolean evaluate(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (blockEntity instanceof RecipeProvider provider) {
            ResourceLocation running = provider.pjs$getRunningRecipe();
            return running != null && recipesToCheck.contains(running);
        } else if (blockEntity instanceof MultiRecipeProvider multiProvider) {
            for (Optional<ResourceLocation> resourceLocation : multiProvider.pjs$getRunningRecipe()) {
                if (resourceLocation.map(recipesToCheck::contains).orElse(false)) {
                    return true;
                }
            }
        }
        return false;
    }
}
