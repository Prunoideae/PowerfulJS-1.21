package moe.wolfgirl.powerfuljs.mixin.create;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import moe.wolfgirl.powerfuljs.PowerfulJS;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.EqualityCache;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.IdentityCache;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.RecipeProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DeployerBlockEntity.class)
public abstract class DeployerMixin implements RecipeProvider, ProgressProvider {

    @Shadow
    protected int timer;

    @Shadow
    @Nullable
    public abstract RecipeHolder<? extends Recipe<? extends RecipeInput>> getRecipe(ItemStack stack);

    @Unique
    private EqualityCache.KeyAccessible<List<ItemStack>, ResourceLocation> pjs$cache = null;

    @Unique
    private boolean pjs$useMode = true;

    @Override
    public int pjs$getProgress() {
        return 1000 - timer;
    }

    @Override
    public int pjs$getMaxProgress() {
        return 1000;
    }

    @Override
    public void pjs$setProgress(int progress) {
        timer = Math.max(1, 1000 - progress);
        pjs$self().sendData();
    }

    @Override
    public boolean pjs$running() {
        return timer > 0;
    }

    @Unique
    private DeployerBlockEntity pjs$self() {
        return (DeployerBlockEntity) (Object) this;
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
        DeployerBlockEntity self = pjs$self();
        if (!pjs$running() || !pjs$useMode) return null;
        if (self.getBlockState().getValue(DirectionalKineticBlock.FACING) != Direction.DOWN) return null;

        if (pjs$cache == null) {
            pjs$cache = new EqualityCache.KeyAccessible<>(
                    this::pjs$getItem,
                    itemStacks -> {
                        for (ItemStack itemStack : itemStacks) {
                            var r = getRecipe(itemStack.copyWithCount(1));
                            if (r != null) return r.id();
                        }
                        return null;
                    }
            );
        }

        return pjs$cache.get();
    }

    @Inject(method = "changeMode", at = @At("TAIL"), remap = false)
    private void onModeChange(CallbackInfo ci) {
        this.pjs$useMode = !this.pjs$useMode;
    }

    @Inject(method = "read", at = @At("HEAD"))
    private void onReadFromNbt(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket, CallbackInfo ci) {
        if (clientPacket) return;
        var s = compound.getString("Mode");
        this.pjs$useMode = !s.equals("PUNCH");
    }
}
