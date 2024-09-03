package moe.wolfgirl.powerfuljs.serde;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.fluids.FluidStack;

public record FluidContent(FluidStack fluidStack) {
    public static final FluidContent EMPTY = new FluidContent(FluidStack.EMPTY);
    public static final Codec<FluidContent> CODEC = FluidStack.OPTIONAL_CODEC
            .xmap(FluidContent::new, FluidContent::fluidStack);
    public static final StreamCodec<RegistryFriendlyByteBuf, FluidContent> STREAM_CODEC = FluidStack.OPTIONAL_STREAM_CODEC
            .map(FluidContent::new, FluidContent::fluidStack);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FluidContent that)) return false;
        return FluidStack.isSameFluidSameComponents(fluidStack, that.fluidStack)
                && fluidStack.getAmount() == that.fluidStack.getAmount();
    }
}
