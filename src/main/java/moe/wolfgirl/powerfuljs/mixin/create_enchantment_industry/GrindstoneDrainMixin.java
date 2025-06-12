package moe.wolfgirl.powerfuljs.mixin.create_enchantment_industry;

import com.simibubi.create.content.processing.recipe.ProcessingInventory;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import moe.wolfgirl.powerfuljs.custom.mods.create.KineticModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.createenchantmentindustry.common.kinetics.grindstone.GrindstoneDrainBlockEntity;

@Mixin(GrindstoneDrainBlockEntity.class)
public abstract class GrindstoneDrainMixin implements ProgressProvider, KineticModifier {
    @Shadow
    public ProcessingInventory inventory;

    @Override
    public int pjs$getProgress() {
        return (int) inventory.remainingTime;
    }

    @Override
    public int pjs$getMaxProgress() {
        return 20;
    }

    @Override
    public void pjs$setProgress(int progress) {
        inventory.remainingTime = progress;
    }

    @Override
    public boolean pjs$running() {
        return inventory.remainingTime > 0f;
    }

    @Unique
    private GrindstoneDrainBlockEntity pjs$self() {
        return (GrindstoneDrainBlockEntity) (Object) this;
    }

    @Inject(method = "getRelativeSpeed", cancellable = true, remap = false, at = @At("RETURN"))
    public void modifyRelativeSpeed(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(cir.getReturnValueF() * this.pjs$getMachineSpeedModifier());
    }
}
