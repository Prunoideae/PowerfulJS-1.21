package moe.wolfgirl.powerfuljs.custom.attachment;

import com.mojang.serialization.Codec;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.rhino.type.TypeInfo;
import moe.wolfgirl.powerfuljs.GameStates;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Supplier;

public abstract class AttachmentBuilder<T> extends BuilderBase<AttachmentType<T>> {
    protected Class<T> typeClass;
    protected Supplier<T> defaultGetter;
    protected Codec<T> codec;

    public AttachmentBuilder(ResourceLocation id) {
        super(id);
    }

    public AttachmentBuilder<T> defaultGetter(Supplier<T> defaultGetter) {
        this.defaultGetter = defaultGetter;
        return this;
    }

    public AttachmentBuilder<T> typeClass(Class<T> typeClass) {
        this.typeClass = typeClass;
        return this;
    }

    public AttachmentBuilder<T> codec(Codec<T> codec) {
        this.codec = codec;
        return this;
    }

    @Override
    public AttachmentType<T> createObject() {
        AttachmentType<T> type = AttachmentType.builder(defaultGetter)
                .serialize(codec)
                .build();
        GameStates.ATTACHMENT_TYPE_INFO.put(type, TypeInfo.of(typeClass));
        return type;
    }
}
