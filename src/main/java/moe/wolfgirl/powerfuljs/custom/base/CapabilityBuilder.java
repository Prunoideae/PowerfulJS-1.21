package moe.wolfgirl.powerfuljs.custom.base;


import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BaseCapability;

import java.util.Map;

/**
 * Builds a capability from a configuration. Note that this does not filter the capability by conditions.
 * An extra layer of wrapper will be needed to filter by condition.
 */
public record CapabilityBuilder<O, T>(ResourceLocation name, BaseCapability<T, ?> capability,
                                      TypeInfo typeInfo, FactoryProvider<O, T> factory) {
    /**
     * Generates a configuration from input object.
     */
    public CapabilityFactory<O, T> wraps(Context ctx, Map<String, Object> configuration) {
        return factory.create(ctx, configuration);
    }

    public interface CapabilityFactory<O, T> {
        T getCapability(O object);
    }

    public interface FactoryProvider<O, T> {
        CapabilityFactory<O, T> create(Context ctx, Map<String, Object> configuration);
    }

    public static <O, T> CapabilityBuilder<O, T> create(ResourceLocation name, BaseCapability<T, ?> capability,
                                                        TypeInfo typeInfo, FactoryProvider<O, T> factory) {
        return new CapabilityBuilder<>(name, capability, typeInfo, factory);
    }
}
