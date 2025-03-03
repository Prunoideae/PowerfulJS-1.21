package moe.wolfgirl.powerfuljs.mixin.create;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import moe.wolfgirl.powerfuljs.PowerfulJS;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.IdentityCache;
import moe.wolfgirl.powerfuljs.custom.mods.create.BasinRecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

@Mixin(BasinOperatingBlockEntity.class)
public abstract class BasinMachinesMixin implements BasinRecipeProvider {

    @Shadow
    protected abstract Optional<BasinBlockEntity> getBasin();

    @Shadow
    protected abstract Object getRecipeCacheKey();

    @Shadow
    protected abstract boolean matchStaticFilters(RecipeHolder<? extends Recipe<?>> recipeHolder);

    @Shadow
    protected abstract boolean isRunning();

    @Shadow
    protected Recipe<?> currentRecipe;

    @Unique
    private IdentityCache<Recipe<?>, ResourceLocation> pjs$cache;

    @Unique
    private BlockEntity pjs$self() {
        return (BlockEntity) (Object) this;
    }

    @Override
    public @Nullable ResourceLocation pjs$getBasinRunningRecipe() {
        if (!isRunning() || currentRecipe == null) return null;
        if (pjs$cache == null) {
            pjs$cache = new IdentityCache<>(
                    () -> this.currentRecipe,
                    () -> {
                        if (currentRecipe == null) return null;
                        var recipes = RecipeFinder.get(getRecipeCacheKey(), pjs$self().getLevel(), this::matchStaticFilters);
                        for (RecipeHolder<? extends Recipe<?>> recipe : recipes) {
                            if (recipe.value() == this.currentRecipe) return recipe.id();
                        }
                        return null;
                    });
        }
        return pjs$cache.get();
    }

    @Override
    public Optional<BasinBlockEntity> pjs$getBasin() {
        return getBasin();
    }
}
