package moe.wolfgirl.powerfuljs.custom.registries;

import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemCapabilityRegistry {
    public static final Map<ResourceLocation, CapabilityBuilder<ItemStack, ?>> ITEM = new HashMap<>();

    public static void register(CapabilityBuilder<ItemStack, ?> builder) {
        ITEM.put(builder.name(), builder);
    }
}
