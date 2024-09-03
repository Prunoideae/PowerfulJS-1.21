package moe.wolfgirl.powerfuljs.custom;

import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

public interface CapabilityWrapper {
    static BlockCapability<?, ?> block(BlockCapability<?, ?> blockCapability) {
        return blockCapability;
    }

    static ItemCapability<?, ?> item(ItemCapability<?, ?> itemCapability) {
        return itemCapability;
    }

    static EntityCapability<?, ?> entity(EntityCapability<?, ?> entityCapability) {
        return entityCapability;
    }
}
