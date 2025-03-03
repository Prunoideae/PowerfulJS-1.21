package moe.wolfgirl.powerfuljs.mixin.create.generators;

import com.simibubi.create.content.kinetics.motor.CreativeMotorBlockEntity;
import moe.wolfgirl.powerfuljs.custom.mods.create.KineticModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeMotorBlockEntity.class)
public class CreativeMotorMixin implements KineticModifier {

    @Inject(method = "getGeneratedSpeed", at = @At("RETURN"), cancellable = true)
    public void applyNewSpeed(CallbackInfoReturnable<Float> cir) {
        float speed = pjs$getSpeedModifier() * cir.getReturnValue();
        cir.setReturnValue(speed);
    }
}
