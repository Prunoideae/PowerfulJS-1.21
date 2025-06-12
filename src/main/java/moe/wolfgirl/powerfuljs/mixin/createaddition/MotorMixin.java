package moe.wolfgirl.powerfuljs.mixin.createaddition;

import com.mrh0.createaddition.blocks.electric_motor.ElectricMotorBlockEntity;
import moe.wolfgirl.powerfuljs.custom.mods.create.KineticModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElectricMotorBlockEntity.class)
public class MotorMixin implements KineticModifier {

    @Inject(method = "getGeneratedSpeed", at = @At("RETURN"), cancellable = true)
    public void applyNewSpeed(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(cir.getReturnValueF() * pjs$getGeneratingSpeedModifier());
    }

    @Inject(method = "calculateAddedStressCapacity", at = @At("RETURN"), cancellable = true)
    public void applyNewStressCapacity(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(cir.getReturnValueF() * pjs$getStressCapacityModifier());
    }
}
