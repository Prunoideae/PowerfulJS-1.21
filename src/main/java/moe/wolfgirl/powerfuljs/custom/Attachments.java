package moe.wolfgirl.powerfuljs.custom;

import moe.wolfgirl.powerfuljs.serde.ItemContent;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Unit;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.List;

public class Attachments {
    public static final AttachmentType<Integer> FORGE_ENERGY = AttachmentType.builder(() -> 0)
            .serialize(ExtraCodecs.NON_NEGATIVE_INT)
            .build();

    public static final AttachmentType<FluidStack> FLUID = AttachmentType.builder(() -> FluidStack.EMPTY)
            .serialize(FluidStack.OPTIONAL_CODEC)
            .build();

    public static final AttachmentType<List<FluidStack>> FLUID_MULTITANK = AttachmentType.builder(() -> List.of(FluidStack.EMPTY))
            .serialize(FluidStack.CODEC.listOf())
            .build();

    public static final AttachmentType<ItemContent> ITEM = AttachmentType.builder(ItemContent.EMPTY)
            .serialize(ItemContent.CODEC)
            .build();

    public static final AttachmentType<Unit> DISABLED = AttachmentType.builder(() -> Unit.INSTANCE)
            .serialize(Unit.CODEC)
            .build();

    public static final AttachmentType<Float> TICK_SPEED = AttachmentType.builder(() -> 1f)
            .serialize(ExtraCodecs.POSITIVE_FLOAT)
            .build();

    public static void initAttachments(RegisterEvent.RegisterHelper<AttachmentType<?>> helper) {
        helper.register(MCID.create("forge_energy"), FORGE_ENERGY);
        helper.register(MCID.create("fluid"), FLUID);
        helper.register(MCID.create("fluid_multitank"), FLUID_MULTITANK);
        helper.register(MCID.create("item"), ITEM);
        helper.register(MCID.create("disabled"), DISABLED);
        helper.register(MCID.create("tick_speed"), TICK_SPEED);
    }
}
