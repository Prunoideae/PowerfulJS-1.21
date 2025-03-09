package moe.wolfgirl.powerfuljs.custom.mods.create;

import moe.wolfgirl.powerfuljs.serde.SpeedModifiers;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.RegisterEvent;

public class CreateAttachments {
    public static final AttachmentType<SpeedModifiers> ROTATION_SPEED = AttachmentType.builder(() -> SpeedModifiers.EMPTY)
            .serialize(SpeedModifiers.CODEC)
            .build();

    public static final AttachmentType<SpeedModifiers> STRESS_CAPACITY = AttachmentType.builder(() -> SpeedModifiers.EMPTY)
            .serialize(SpeedModifiers.CODEC)
            .build();

    public static final AttachmentType<SpeedModifiers> MACHINE_SPEED = AttachmentType.builder(() -> SpeedModifiers.EMPTY)
            .serialize(SpeedModifiers.CODEC)
            .build();


    public static void initAttachments(RegisterEvent.RegisterHelper<AttachmentType<?>> helper) {
        helper.register(MCID.create("rotation_speed"), ROTATION_SPEED);
        helper.register(MCID.create("stress_capacity"), STRESS_CAPACITY);
        helper.register(MCID.create("machine_speed"), MACHINE_SPEED);
    }
}