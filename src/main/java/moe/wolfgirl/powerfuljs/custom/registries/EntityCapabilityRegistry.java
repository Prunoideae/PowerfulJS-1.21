package moe.wolfgirl.powerfuljs.custom.registries;

import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class EntityCapabilityRegistry {
    public static final Map<ResourceLocation, CapabilityBuilder<Entity, ?>> ENTITY = new HashMap<>();

    public static void register(CapabilityBuilder<Entity, ?> builder) {
        ENTITY.put(builder.name(), builder);
    }
}
