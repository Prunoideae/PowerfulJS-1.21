package moe.wolfgirl.powerfuljs.custom;

import moe.wolfgirl.powerfuljs.serde.FluidContent;
import moe.wolfgirl.powerfuljs.serde.ItemContent;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.registries.RegisterEvent;

public class DataComponents {
    public static final DataComponentType<Integer> FORGE_ENERGY = DataComponentType.<Integer>builder()
            .persistent(ExtraCodecs.POSITIVE_INT)
            .networkSynchronized(ByteBufCodecs.VAR_INT)
            .build();

    public static final DataComponentType<FluidContent> FLUID = DataComponentType.<FluidContent>builder()
            .persistent(FluidContent.CODEC)
            .networkSynchronized(FluidContent.STREAM_CODEC)
            .build();

    public static final DataComponentType<ItemContent> ITEM = DataComponentType.<ItemContent>builder()
            .persistent(ItemContent.CODEC)
            .networkSynchronized(ItemContent.STREAM_CODEC)
            .build();

    public static void initComponents(RegisterEvent.RegisterHelper<DataComponentType<?>> helper) {
        helper.register(MCID.create("forge_energy"), FORGE_ENERGY);
        helper.register(MCID.create("fluid"), FLUID);
        helper.register(MCID.create("item"), ITEM);
    }
}
