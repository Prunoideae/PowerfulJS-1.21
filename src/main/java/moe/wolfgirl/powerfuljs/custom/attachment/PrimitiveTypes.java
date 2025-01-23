package moe.wolfgirl.powerfuljs.custom.attachment;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import java.util.function.Supplier;

public class PrimitiveTypes {

    public static class ObjectType extends AttachmentBuilder<Object> {

        public ObjectType(ResourceLocation id) {
            super(id);
        }

        @Override
        public AttachmentBuilder<Object> defaultGetter(Supplier<Object> defaultGetter) {
            return super.defaultGetter(defaultGetter);
        }
    }

    public static class IntegerType extends AttachmentBuilder<Integer> {

        public IntegerType(ResourceLocation id) {
            super(id);
            typeClass = Integer.class;
            defaultGetter = () -> 0;
            codec = Codec.INT;
        }

        @Override
        public AttachmentBuilder<Integer> defaultGetter(Supplier<Integer> defaultGetter) {
            return super.defaultGetter(defaultGetter);
        }
    }

    public static class FloatType extends AttachmentBuilder<Float> {

        public FloatType(ResourceLocation id) {
            super(id);
            typeClass = Float.class;
            defaultGetter = () -> 0f;
            codec = Codec.FLOAT;
        }

        @Override
        public AttachmentBuilder<Float> defaultGetter(Supplier<Float> defaultGetter) {
            return super.defaultGetter(defaultGetter);
        }
    }

    public static class StringType extends AttachmentBuilder<String> {

        public StringType(ResourceLocation id) {
            super(id);
            typeClass = String.class;
            defaultGetter = () -> "";
            codec = Codec.STRING;
        }

        @Override
        public AttachmentBuilder<String> defaultGetter(Supplier<String> defaultGetter) {
            return super.defaultGetter(defaultGetter);
        }
    }

    public static class BoolType extends AttachmentBuilder<Boolean> {

        public BoolType(ResourceLocation id) {
            super(id);
            typeClass = Boolean.class;
            defaultGetter = () -> false;
            codec = Codec.BOOL;
        }

        @Override
        public AttachmentBuilder<Boolean> defaultGetter(Supplier<Boolean> defaultGetter) {
            return super.defaultGetter(defaultGetter);
        }
    }
}
