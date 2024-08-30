package moe.wolfgirl.powerfuljs.custom.mods.mekanism;

import mekanism.api.chemical.ChemicalStack;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.RegisterEvent;

public class MekAttachments {
    public static final AttachmentType<ChemicalStack> CHEMICAL = AttachmentType.builder(() -> ChemicalStack.EMPTY)
            .serialize(ChemicalStack.OPTIONAL_CODEC)
            .build();

    public static void initAttachments(RegisterEvent.RegisterHelper<AttachmentType<?>> helper) {
        helper.register(MCID.create("chemical"), CHEMICAL);
    }
}
