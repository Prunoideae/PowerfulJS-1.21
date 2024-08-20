package moe.wolfgirl.powerfuljs.utils;

import com.google.gson.JsonPrimitive;
import dev.latvian.mods.kubejs.core.RegistryObjectKJS;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface MCID {
    static ResourceLocation of(@Nullable Object o, String prefer) {
        switch (o) {
            case null -> {
                return null;
            }
            case ResourceLocation id -> {
                return id;
            }
            case ResourceKey<?> key -> {
                return key.location();
            }
            case Holder<?> holder -> {
                return Objects.requireNonNull(holder.getKey()).location();
            }
            case RegistryObjectKJS<?> key -> {
                return key.kjs$getIdLocation();
            }
            default -> {
            }
        }

        var s = o instanceof JsonPrimitive p ? p.getAsString() : o.toString();

        if (s.indexOf(':') == -1) {
            s = prefer + ":" + s;
        }

        try {
            return ResourceLocation.parse(s);
        } catch (ResourceLocationException ex) {
            throw new IllegalArgumentException("Could not create MCID from '%s'!".formatted(s));
        }
    }

    static ResourceLocation create(String path) {
        return ResourceLocation.fromNamespaceAndPath("powerfuljs", path);
    }
}
