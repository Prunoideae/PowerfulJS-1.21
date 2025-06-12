package moe.wolfgirl.powerfuljs.mixin.create_enchantment_industry;

import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import plus.dragons.createenchantmentindustry.common.processing.enchanter.BlazeEnchanterBlockEntity;

@Mixin(BlazeEnchanterBlockEntity.class)
public abstract class BlazeEnchanterMixin implements ProgressProvider {

    @Shadow
    protected int processingTime;

    @Shadow
    public abstract boolean isActive();

    @Override
    public int pjs$getProgress() {
        return processingTime;
    }

    @Override
    public int pjs$getMaxProgress() {
        return 200;
    }

    @Override
    public void pjs$setProgress(int progress) {
        processingTime = progress;
    }

    @Override
    public boolean pjs$running() {
        return isActive();
    }
}
