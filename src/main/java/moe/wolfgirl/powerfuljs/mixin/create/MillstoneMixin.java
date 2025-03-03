package moe.wolfgirl.powerfuljs.mixin.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.IdentityCache;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MillstoneBlockEntity.class)
public abstract class MillstoneMixin implements RecipeProvider, ProgressProvider {
    @Shadow
    public int timer;

    @Shadow
    private MillingRecipe lastRecipe;

    @Shadow
    public ItemStackHandler inputInv;
    @Unique
    private IdentityCache<MillingRecipe, ResourceLocation> pjs$cache = null;

    @Override
    public int pjs$getProgress() {
        return pjs$getMaxProgress() - timer;
    }

    @Override
    public int pjs$getMaxProgress() {
        return lastRecipe.getProcessingDuration();
    }

    @Override
    public void pjs$setProgress(int progress) {
        timer = Math.max(1, pjs$getMaxProgress() - progress);
    }

    @Override
    public boolean pjs$running() {
        return timer > 0;
    }

    @Unique
    private MillstoneBlockEntity pjs$self() {
        return (MillstoneBlockEntity) (Object) this;
    }

    @Override
    public @Nullable ResourceLocation pjs$getRunningRecipe() {
        if (!pjs$running()) return null;
        if (pjs$cache == null) {
            pjs$cache = new IdentityCache<>(
                    () -> this.lastRecipe,
                    () -> {
                        RecipeWrapper inv = new RecipeWrapper(this.inputInv);
                        return AllRecipeTypes.MILLING.find(inv, pjs$self().getLevel()).map(RecipeHolder::id).orElse(null);
                    });
        }
        return pjs$cache.get();
    }
}
