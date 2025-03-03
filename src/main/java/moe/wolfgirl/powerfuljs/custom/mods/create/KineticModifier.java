package moe.wolfgirl.powerfuljs.custom.mods.create;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import moe.wolfgirl.powerfuljs.serde.SpeedModifiers;

@RemapPrefixForJS("pjs$")
public interface KineticModifier {

    default float pjs$getSpeedModifier() {
        return pjs$self().getData(CreateAttachments.ROTATION_SPEED).getTickSpeed();
    }

    default float pjs$getCapacityModifier() {
        return pjs$self().getData(CreateAttachments.STRESS_CAPACITY).getTickSpeed();
    }

    default void pjs$addSpeedModifier(SpeedModifiers.SpeedModifier modifier) {
        SpeedModifiers modifiers = pjs$self().getData(CreateAttachments.ROTATION_SPEED);
        if (!modifiers.hasModifier(modifier.id())) {
            pjs$self().setData(CreateAttachments.ROTATION_SPEED, modifiers.withModifier(modifier));
            pjs$self().networkDirty = true;
        }
    }

    default void pjs$addCapacityModifier(SpeedModifiers.SpeedModifier modifier) {
        SpeedModifiers modifiers = pjs$self().getData(CreateAttachments.STRESS_CAPACITY);
        if (!modifiers.hasModifier(modifier.id())) {
            pjs$self().setData(CreateAttachments.STRESS_CAPACITY, modifiers.withModifier(modifier));
            pjs$self().networkDirty = true;
        }
    }

    default void pjs$removeSpeedModifier(String id) {
        SpeedModifiers modifiers = pjs$self().getData(CreateAttachments.ROTATION_SPEED);
        if (modifiers.hasModifier(id)) {
            pjs$self().setData(CreateAttachments.ROTATION_SPEED, modifiers.removeModifier(id));
            pjs$self().networkDirty = true;
        }
    }

    default void pjs$removeCapacityModifier(String id) {
        SpeedModifiers modifiers = pjs$self().getData(CreateAttachments.STRESS_CAPACITY);
        if (modifiers.hasModifier(id)) {
            pjs$self().setData(CreateAttachments.STRESS_CAPACITY, modifiers.removeModifier(id));
            pjs$self().networkDirty = true;
        }
    }

    default KineticBlockEntity pjs$self() {
        return (KineticBlockEntity) this;
    }
}
