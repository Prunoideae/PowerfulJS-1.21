package moe.wolfgirl.powerfuljs.custom;

import moe.wolfgirl.powerfuljs.serde.ChunkDataStorage;
import moe.wolfgirl.powerfuljs.serde.ItemContent;
import moe.wolfgirl.powerfuljs.serde.SpeedModifiers;
import moe.wolfgirl.powerfuljs.utils.MCID;
import moe.wolfgirl.powerfuljs.utils.UUIDUtils;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Unit;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.List;
import java.util.UUID;

public class Attachments {
    public static final AttachmentType<Integer> FORGE_ENERGY = AttachmentType.builder(() -> 0)
            .serialize(ExtraCodecs.NON_NEGATIVE_INT)
            .build();

    public static final AttachmentType<FluidStack> FLUID = AttachmentType.builder(() -> FluidStack.EMPTY)
            .serialize(FluidStack.OPTIONAL_CODEC)
            .build();

    public static final AttachmentType<FluidStack> INPUT_FLUID = AttachmentType.builder(() -> FluidStack.EMPTY)
            .serialize(FluidStack.OPTIONAL_CODEC)
            .build();

    public static final AttachmentType<FluidStack> OUTPUT_FLUID = AttachmentType.builder(() -> FluidStack.EMPTY)
            .serialize(FluidStack.OPTIONAL_CODEC)
            .build();

    public static final AttachmentType<ItemContent> ITEM = AttachmentType.builder(ItemContent.EMPTY)
            .serialize(ItemContent.CODEC)
            .build();

    public static final AttachmentType<Unit> DISABLED = AttachmentType.builder(() -> Unit.INSTANCE)
            .serialize(Unit.CODEC)
            .build();

    public static final AttachmentType<SpeedModifiers> TICK_SPEED = AttachmentType.builder(() -> SpeedModifiers.EMPTY)
            .serialize(SpeedModifiers.CODEC)
            .build();

    public static final AttachmentType<UUID> OWNER = AttachmentType.builder(UUID::randomUUID)
            .serialize(UUIDUtils.CODEC)
            .build();

    public static final AttachmentType<ChunkDataStorage> CHUNK_DATA = AttachmentType.builder(ChunkDataStorage::newEmpty)
            .serialize(ChunkDataStorage.CODEC)
            .build();

    public static void initAttachments(RegisterEvent.RegisterHelper<AttachmentType<?>> helper) {
        helper.register(MCID.create("forge_energy"), FORGE_ENERGY);
        helper.register(MCID.create("fluid"), FLUID);
        helper.register(MCID.create("input_fluid"), INPUT_FLUID);
        helper.register(MCID.create("output_fluid"), OUTPUT_FLUID);
        helper.register(MCID.create("item"), ITEM);
        helper.register(MCID.create("disabled"), DISABLED);
        helper.register(MCID.create("tick_speed"), TICK_SPEED);
        helper.register(MCID.create("owner"), OWNER);
        helper.register(MCID.create("chunk_data"), CHUNK_DATA);
    }
}
