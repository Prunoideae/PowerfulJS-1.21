package moe.wolfgirl.powerfuljs.mixin.create;

import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import moe.wolfgirl.powerfuljs.custom.mods.create.logic.ModifyCapacityEffect;
import moe.wolfgirl.powerfuljs.custom.mods.create.logic.ModifyRotationSpeedEffect;
import moe.wolfgirl.powerfuljs.custom.registries.LogicRegistry;
import moe.wolfgirl.powerfuljs.serde.SpeedModifiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = LogicRegistry.Effects.class, remap = false)
@RemapPrefixForJS("pjs$")
public abstract class ExtendedEffects {

    @Unique
    public Effect pjs$modifyRotationSpeed(SpeedModifiers.SpeedModifier modifier) {
        return new ModifyRotationSpeedEffect(modifier);
    }

    @Unique
    public Effect pjs$modifyStressCapacity(SpeedModifiers.SpeedModifier modifier) {
        return new ModifyCapacityEffect(modifier);
    }
}
