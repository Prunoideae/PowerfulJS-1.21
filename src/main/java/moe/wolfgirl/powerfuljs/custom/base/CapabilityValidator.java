package moe.wolfgirl.powerfuljs.custom.base;

import moe.wolfgirl.powerfuljs.custom.base.info.BlockContext;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Validates a capability to be accessible from a given context. If any validator returns false,
 * the capability will be null.
 */

public interface CapabilityValidator<T> {
    boolean test(T info, @Nullable Object context);

    @FunctionalInterface
    interface BlockValidator extends CapabilityValidator<BlockContext> {
    }

    @FunctionalInterface
    interface ItemValidator extends CapabilityValidator<ItemStack> {
    }

    @FunctionalInterface
    interface EntityValidator extends CapabilityValidator<Entity> {
    }
}
