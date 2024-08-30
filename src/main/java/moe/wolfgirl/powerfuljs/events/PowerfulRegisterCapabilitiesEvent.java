package moe.wolfgirl.powerfuljs.events;

import com.mojang.datafixers.util.Pair;
import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.util.HideFromJS;
import moe.wolfgirl.powerfuljs.GameStates;
import moe.wolfgirl.powerfuljs.custom.registries.BlockCapabilityRegistry;
import moe.wolfgirl.powerfuljs.custom.registries.EntityCapabilityRegistry;
import moe.wolfgirl.powerfuljs.custom.registries.ItemCapabilityRegistry;
import moe.wolfgirl.powerfuljs.custom.registries.info.BlockEntityInfo;
import moe.wolfgirl.powerfuljs.custom.registries.info.BlockInfo;
import moe.wolfgirl.powerfuljs.custom.registries.info.EntityInfo;
import moe.wolfgirl.powerfuljs.custom.registries.info.ItemStackInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PowerfulRegisterCapabilitiesEvent implements KubeEvent {
    private final List<Pair<Block, BlockInfo>> registeredBlocks = new ArrayList<>();
    private final List<Pair<Item, ItemStackInfo>> registeredItems = new ArrayList<>();
    private final List<Pair<EntityType<?>, EntityInfo>> registeredEntities = new ArrayList<>();


    public BlockInfo registerBlock(Context ctx, ResourceLocation builderKey, Object configuration, Block... blocks) {
        var builder = BlockCapabilityRegistry.BLOCKS.get(builderKey);
        if (builder == null) throw new IllegalArgumentException("Unknown capability builder %s!".formatted(builderKey));

        var info = new BlockInfo(builder.capability(), builder.wraps(ctx, configuration));
        for (Block block : blocks) {
            registeredBlocks.add(new Pair<>(block, info));
        }
        return info;
    }

    public BlockEntityInfo registerBlockEntity(Context ctx, ResourceLocation builderKey, Object configuration, BlockEntityType<?> blockEntityType) {
        var builder = BlockCapabilityRegistry.BLOCK_ENTITIES.get(builderKey);
        if (builder == null) throw new IllegalArgumentException("Unknown capability builder %s!".formatted(builderKey));

        var info = new BlockEntityInfo(builder.capability(), builder.wraps(ctx, configuration));
        info.validate((i, c) -> {
            assert i.blockEntity() != null;
            return i.blockEntity().getType() == blockEntityType;
        });

        for (Block validBlock : blockEntityType.getValidBlocks()) {
            registeredBlocks.add(new Pair<>(validBlock, info));
        }

        return info;
    }

    public ItemStackInfo registerItem(Context ctx, ResourceLocation builderKey, Object configuration, Item... items) {
        var builder = ItemCapabilityRegistry.ITEM.get(builderKey);
        if (builder == null) throw new IllegalArgumentException("Unknown capability builder %s!".formatted(builderKey));

        var info = new ItemStackInfo(builder.capability(), builder.wraps(ctx, configuration));

        for (Item item : items) {
            registeredItems.add(new Pair<>(item, info));
        }

        return info;
    }

    public EntityInfo registerEntity(Context ctx, ResourceLocation builderKey, Object configuration, EntityType<?>... entityTypes) {
        var builder = EntityCapabilityRegistry.ENTITY.get(builderKey);
        if (builder == null) throw new IllegalArgumentException("Unknown capability builder %s!".formatted(builderKey));

        var info = new EntityInfo(builder.capability(), builder.wraps(ctx, configuration));

        for (EntityType<?> entityType : entityTypes) {
            registeredEntities.add(new Pair<>(entityType, info));
        }

        return info;
    }

    @HideFromJS
    @SuppressWarnings("unchecked")
    public <T, C> void register(RegisterCapabilitiesEvent event) {
        for (Pair<Block, BlockInfo> registeredBlock : registeredBlocks) {
            var block = registeredBlock.getFirst();
            var info = registeredBlock.getSecond();
            event.registerBlock((BlockCapability<T, C>) info.capability, (IBlockCapabilityProvider<T, C>) info.build(), block);

            GameStates.REGISTERED_BLOCKS.add(block);
        }

        for (Pair<Item, ItemStackInfo> registeredItem : registeredItems) {
            var item = registeredItem.getFirst();
            var info = registeredItem.getSecond();
            event.registerItem((ItemCapability<T, C>) info.capability, (ICapabilityProvider<ItemStack, C, T>) info.build(), item);
        }

        for (Pair<EntityType<?>, EntityInfo> registeredEntity : registeredEntities) {
            var entity = registeredEntity.getFirst();
            var info = registeredEntity.getSecond();
            event.registerEntity((EntityCapability<T, C>) info.capability, entity, (ICapabilityProvider<Entity, C, T>) info.build());
        }
    }
}
