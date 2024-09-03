package moe.wolfgirl.powerfuljs.custom.mods.mekanism;

import com.mojang.serialization.Codec;
import mekanism.api.chemical.ChemicalStack;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.RegisterEvent;

public class MekAttachments {
    public static final AttachmentType<ChemicalStack> CHEMICAL = AttachmentType.builder(() -> ChemicalStack.EMPTY)
            .serialize(ChemicalStack.OPTIONAL_CODEC)
            .build();

    public static final AttachmentType<Double> HEAT = AttachmentType.builder(() -> -1d)
            .serialize(Codec.DOUBLE)
            .build();

    public static void initAttachments(RegisterEvent.RegisterHelper<AttachmentType<?>> helper) {
        helper.register(MCID.create("chemical"), CHEMICAL);
        helper.register(MCID.create("heat"), HEAT);
    }
}
