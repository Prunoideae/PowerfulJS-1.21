package moe.wolfgirl.powerfuljs.custom.mods.create;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import moe.wolfgirl.powerfuljs.serde.SpeedModifiers;

@RemapPrefixForJS("pjs$")
public interface KineticModifier {

    default float pjs$getGeneratingSpeedModifier() {
        return pjs$selfKinetic().getData(CreateAttachments.ROTATION_SPEED).getTickSpeed();
    }

    default float pjs$getStressCapacityModifier() {
        return pjs$selfKinetic().getData(CreateAttachments.STRESS_CAPACITY).getTickSpeed();
    }

    default float pjs$getMachineSpeedModifier() {
        return pjs$selfKinetic().getData(CreateAttachments.MACHINE_SPEED).getTickSpeed();
    }

    default void pjs$addGeneratingSpeedModifier(SpeedModifiers.SpeedModifier modifier) {
        SpeedModifiers modifiers = pjs$selfKinetic().getData(CreateAttachments.ROTATION_SPEED);
        if (!modifiers.hasModifier(modifier.id())) {
            pjs$selfKinetic().setData(CreateAttachments.ROTATION_SPEED, modifiers.withModifier(modifier));
            pjs$selfKinetic().networkDirty = true;

            if (pjs$selfKinetic() instanceof GeneratingKineticBlockEntity generatingKineticBlock) {
                generatingKineticBlock.reActivateSource = true;
            }
        }
    }

    default void pjs$addCapacityModifier(SpeedModifiers.SpeedModifier modifier) {
        SpeedModifiers modifiers = pjs$selfKinetic().getData(CreateAttachments.STRESS_CAPACITY);
        if (!modifiers.hasModifier(modifier.id())) {
            pjs$selfKinetic().setData(CreateAttachments.STRESS_CAPACITY, modifiers.withModifier(modifier));
            pjs$selfKinetic().networkDirty = true;

            if (pjs$selfKinetic() instanceof GeneratingKineticBlockEntity generatingKineticBlock) {
                generatingKineticBlock.reActivateSource = true;
            }
        }
    }

    default void pjs$addMachineSpeedModifier(SpeedModifiers.SpeedModifier modifier) {
        SpeedModifiers modifiers = pjs$selfKinetic().getData(CreateAttachments.MACHINE_SPEED);
        if (!modifiers.hasModifier(modifier.id())) {
            pjs$selfKinetic().setData(CreateAttachments.MACHINE_SPEED, modifiers.withModifier(modifier));
            pjs$selfKinetic().networkDirty = true;

            if (pjs$selfKinetic() instanceof GeneratingKineticBlockEntity generatingKineticBlock) {
                generatingKineticBlock.reActivateSource = true;
            }
        }
    }

    default void pjs$removeGeneratingSpeedModifier(String id) {
        SpeedModifiers modifiers = pjs$selfKinetic().getData(CreateAttachments.ROTATION_SPEED);
        if (modifiers.hasModifier(id)) {
            pjs$selfKinetic().setData(CreateAttachments.ROTATION_SPEED, modifiers.removeModifier(id));
            pjs$selfKinetic().networkDirty = true;

            if (pjs$selfKinetic() instanceof GeneratingKineticBlockEntity generatingKineticBlock) {
                generatingKineticBlock.reActivateSource = true;
            }
        }
    }

    default void pjs$removeCapacityModifier(String id) {
        SpeedModifiers modifiers = pjs$selfKinetic().getData(CreateAttachments.STRESS_CAPACITY);
        if (modifiers.hasModifier(id)) {
            pjs$selfKinetic().setData(CreateAttachments.STRESS_CAPACITY, modifiers.removeModifier(id));
            pjs$selfKinetic().networkDirty = true;

            if (pjs$selfKinetic() instanceof GeneratingKineticBlockEntity generatingKineticBlock) {
                generatingKineticBlock.reActivateSource = true;
            }
        }
    }

    default void pjs$removeMachineSpeedModifier(String id) {
        SpeedModifiers modifiers = pjs$selfKinetic().getData(CreateAttachments.MACHINE_SPEED);
        if (modifiers.hasModifier(id)) {
            pjs$selfKinetic().setData(CreateAttachments.MACHINE_SPEED, modifiers.removeModifier(id));
            pjs$selfKinetic().networkDirty = true;
        }
    }

    default KineticBlockEntity pjs$selfKinetic() {
        return (KineticBlockEntity) this;
    }
}
