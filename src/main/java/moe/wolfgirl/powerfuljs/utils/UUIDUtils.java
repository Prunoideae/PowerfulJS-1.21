package moe.wolfgirl.powerfuljs.utils;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UUIDUtils {
    public static final Codec<UUID> CODEC = Codec.STRING
            .xmap(UUID::fromString, UUID::toString);

    public static final StreamCodec<ByteBuf, UUID> STREAM_CODEC = new StreamCodec<ByteBuf, UUID>() {
        @Override
        public @NotNull UUID decode(@NotNull ByteBuf buffer) {
            return FriendlyByteBuf.readUUID(buffer);
        }

        @Override
        public void encode(@NotNull ByteBuf buffer, @NotNull UUID value) {
            FriendlyByteBuf.writeUUID(buffer, value);
        }
    };
}
