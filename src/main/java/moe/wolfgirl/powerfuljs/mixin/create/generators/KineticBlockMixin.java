package moe.wolfgirl.powerfuljs.mixin.create.generators;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import moe.wolfgirl.powerfuljs.custom.mods.create.KineticModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KineticBlockEntity.class)
public class KineticBlockMixin implements KineticModifier {
    @Inject(method = "calculateAddedStressCapacity", at = @At("RETURN"), cancellable = true)
    public void applyNewStressCapacity(CallbackInfoReturnable<Float> cir) {
        float newCapacity = cir.getReturnValue() * pjs$getStressCapacityModifier();
        cir.setReturnValue(newCapacity);
    }

    @Inject(method = "getSpeed", at = @At("RETURN"), cancellable = true)
    public void applyNewSpeed(CallbackInfoReturnable<Float> cir) {
        float newSpeed = cir.getReturnValue() * pjs$getMachineSpeedModifier();
        cir.setReturnValue(newSpeed);
    }
}
