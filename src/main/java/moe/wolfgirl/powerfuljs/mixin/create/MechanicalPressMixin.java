package moe.wolfgirl.powerfuljs.mixin.create;

import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.IdentityCache;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.RecipeProvider;
import moe.wolfgirl.powerfuljs.custom.mods.create.BasinRecipeProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Optional;

@Mixin(MechanicalPressBlockEntity.class)
public abstract class MechanicalPressMixin implements RecipeProvider, ProgressProvider {

    @Shadow
    public abstract Optional<RecipeHolder<PressingRecipe>> getRecipe(ItemStack item);

    @Shadow
    public PressingBehaviour pressingBehaviour;

    @Override
    public int pjs$getProgress() {
        return pressingBehaviour.runningTicks;
    }

    @Override
    public int pjs$getMaxProgress() {
        return 241;
    }

    @Override
    public void pjs$setProgress(int progress) {
        pressingBehaviour.runningTicks = Math.min(progress, 241);
        pjs$self().notifyUpdate();
    }

    @Override
    public boolean pjs$running() {
        return pressingBehaviour.running;
    }

    @Unique
    private MechanicalPressBlockEntity pjs$self() {
        return (MechanicalPressBlockEntity) (Object) this;
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
        if (!pressingBehaviour.running) return null;
        Level level = pjs$self().getLevel();
        BlockPos pos = pjs$self().getBlockPos();

        return switch (pressingBehaviour.mode) {
            case WORLD -> {
                AABB bb = new AABB(pos.below(1));
                for (Entity entity : level.getEntities(null, bb)) {
                    if (entity instanceof ItemEntity item) {
                        var recipe = getRecipe(item.getItem().copyWithCount(1));
                        if (recipe.isPresent()) {
                            yield recipe.get().id();
                        }
                    }
                }
                yield null;
            }
            case BELT -> {
                for (ItemStack itemStack : pjs$getItem()) {
                    var recipe = getRecipe(itemStack.copyWithCount(1));
                    if (recipe.isPresent()) yield recipe.get().id();
                }
                yield null;
            }
            case BASIN -> this instanceof BasinRecipeProvider brp ? brp.pjs$getBasinRunningRecipe() : null;
        };
    }
}
