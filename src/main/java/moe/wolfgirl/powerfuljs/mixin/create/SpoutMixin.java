package moe.wolfgirl.powerfuljs.mixin.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.EqualityCache;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.RecipeProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(SpoutBlockEntity.class)
public abstract class SpoutMixin implements RecipeProvider, ProgressProvider {

    @Shadow
    public int processingTicks;

    @Shadow
    protected abstract FluidStack getCurrentFluidInTank();

    @Unique
    private EqualityCache.KeyAccessible<List<ItemStack>, ResourceLocation> pjs$cache = null;

    @Override
    public int pjs$getProgress() {
        return 20 - processingTicks;
    }

    @Override
    public int pjs$getMaxProgress() {
        return 20;
    }

    @Override
    public void pjs$setProgress(int progress) {
        processingTicks = Math.max(-1, 20 - progress);
        pjs$self().notifyUpdate();
    }

    @Override
    public boolean pjs$running() {
        return processingTicks != -1;
    }

    @Unique
    private SpoutBlockEntity pjs$self() {
        return (SpoutBlockEntity) (Object) this;
    }

    @Unique
    private List<ItemStack> pjs$getItem() {
        Level level = pjs$self().getLevel();
        BlockPos pos = pjs$self().getBlockPos();
        var be = level.getBlockEntity(pos.below(2));
        if (be instanceof BeltBlockEntity beltBlockEntity) {
            return beltBlockEntity.getInventory().getTransportedItems().stream().map(t -> t.stack).toList();
        } else if (be instanceof DepotBlockEntity depotBlockEntity) {
            return List.of(depotBlockEntity.getHeldItem());
        }
        return List.of();
    }

    @Override
    public @Nullable ResourceLocation pjs$getRunningRecipe() {
        if (processingTicks == -1) return null;
        if (pjs$cache == null) {
            pjs$cache = new EqualityCache.KeyAccessible<>(
                    this::pjs$getItem,
                    (itemStacks) -> {
                        Level level = pjs$self().getLevel();
                        for (ItemStack item : itemStacks) {
                            for (RecipeHolder<Recipe<SingleRecipeInput>> holder : level.getRecipeManager().getRecipesFor(AllRecipeTypes.FILLING.getType(), new SingleRecipeInput(item.copyWithCount(1)), level)) {
                                FillingRecipe fillingRecipe = (FillingRecipe) holder.value();
                                if (fillingRecipe.getRequiredFluid().test(getCurrentFluidInTank())) {
                                    return holder.id();
                                }
                            }
                        }
                        return null;
                    }
            );
        }
        return pjs$cache.get();
    }
}
