package moe.wolfgirl.powerfuljs.mixin.create.generators;

import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlockEntity;
import moe.wolfgirl.powerfuljs.custom.mods.create.KineticModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PoweredShaftBlockEntity.class)
public class PoweredShaftMixin implements KineticModifier {

    @Inject(method = "getGeneratedSpeed", at = @At("RETURN"), cancellable = true)
    public void applyNewSpeed(CallbackInfoReturnable<Float> cir) {
        float speed = pjs$getSpeedModifier() * cir.getReturnValue();
        cir.setReturnValue(speed);
    }

    @Inject(method = "calculateAddedStressCapacity", at = @At("RETURN"), cancellable = true)
    public void applyNewStressCapacity(CallbackInfoReturnable<Float> cir) {
        float newCapacity = cir.getReturnValue() * pjs$getCapacityModifier();
        cir.setReturnValue(newCapacity);
    }
}
