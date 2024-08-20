package moe.wolfgirl.powerfuljs.custom.registries;

import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.base.info.BlockContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashMap;
import java.util.Map;

public class BlockCapabilityRegistry {
    public static final Map<ResourceLocation, CapabilityBuilder<BlockEntity, ?>> BLOCK_ENTITIES = new HashMap<>();
    public static final Map<ResourceLocation, CapabilityBuilder<BlockContext, ?>> BLOCKS = new HashMap<>();

    public static void register(CapabilityBuilder<BlockContext, ?> blockBuilder) {
        BLOCKS.put(blockBuilder.name(), blockBuilder);
    }

    public static void registerBE(CapabilityBuilder<BlockEntity, ?> blockEntityBuilder) {
        BLOCK_ENTITIES.put(blockEntityBuilder.name(), blockEntityBuilder);
    }
}
