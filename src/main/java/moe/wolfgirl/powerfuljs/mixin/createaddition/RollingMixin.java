package moe.wolfgirl.powerfuljs.mixin.createaddition;

import com.mrh0.createaddition.blocks.rolling_mill.RollingMillBlockEntity;
import com.mrh0.createaddition.config.CommonConfig;
import com.mrh0.createaddition.recipe.rolling.RollingRecipe;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.IdentityCache;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

@Mixin(RollingMillBlockEntity.class)
public abstract class RollingMixin implements ProgressProvider, RecipeProvider {

    @Shadow
    public ItemStackHandler inputInv;

    @Shadow
    public abstract Optional<RecipeHolder<RollingRecipe>> find(RecipeWrapper inv, Level level);

    @Shadow
    public int timer;
    @Unique
    private IdentityCache<ItemStack, ResourceLocation> pjs$cache = null;

    @Override
    public int pjs$getProgress() {
        return pjs$getMaxProgress() - timer;
    }

    @Override
    public int pjs$getMaxProgress() {
        return CommonConfig.ROLLING_MILL_PROCESSING_DURATION.get();
    }

    @Override
    public void pjs$setProgress(int progress) {
        timer = pjs$getMaxProgress() - progress;
    }

    @Override
    public boolean pjs$running() {
        return timer != 0;
    }

    @Unique
    public RollingMillBlockEntity pjs$self() {
        return (RollingMillBlockEntity) (Object) this;
    }

    @Override
    public @Nullable ResourceLocation pjs$getRunningRecipe() {
        if (pjs$cache == null) {
            pjs$cache = new IdentityCache<>(
                    () -> inputInv.getStackInSlot(0),
                    () -> find(new RecipeWrapper(inputInv), pjs$self().getLevel())
                            .map(RecipeHolder::id)
                            .orElse(null)
            );
        }
        return pjs$cache.get();
    }
}
