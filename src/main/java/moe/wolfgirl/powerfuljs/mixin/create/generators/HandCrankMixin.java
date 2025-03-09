package moe.wolfgirl.powerfuljs.mixin.create.generators;

import com.simibubi.create.content.kinetics.crank.HandCrankBlockEntity;
import moe.wolfgirl.powerfuljs.custom.mods.create.KineticModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandCrankBlockEntity.class)
public class HandCrankMixin implements KineticModifier {

    @Inject(method = "getGeneratedSpeed", at = @At("RETURN"), cancellable = true)
    public void applyNewSpeed(CallbackInfoReturnable<Float> cir) {
        float speed = pjs$getGeneratingSpeedModifier() * cir.getReturnValue();
        cir.setReturnValue(speed);
    }
}
