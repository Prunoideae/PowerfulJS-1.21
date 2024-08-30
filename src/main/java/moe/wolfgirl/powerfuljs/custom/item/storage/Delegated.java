package moe.wolfgirl.powerfuljs.custom.item.storage;

import dev.latvian.mods.kubejs.block.entity.BlockEntityAttachment;
import dev.latvian.mods.kubejs.block.entity.InventoryAttachment;
import dev.latvian.mods.kubejs.block.entity.KubeBlockEntity;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Delegates the item handling to the KubeBlockEntity's InventoryAttachment
 */
public class Delegated implements IItemHandler {
    public static final ResourceLocation ID = MCID.create("item_storage_delegated");

    public static final CapabilityBuilder<BlockEntity, IItemHandler> BLOCK_ENTITY = CapabilityBuilder.create(
            ID, Capabilities.ItemHandler.BLOCK,
            TypeInfo.OBJECT, Delegated::wraps
    );

    public static <O extends BlockEntity> CapabilityBuilder.CapabilityFactory<O, IItemHandler> wraps(Context ctx, Object configuration) {
        return object -> {
            InventoryAttachment inventoryAttachment = null;
            if (object instanceof KubeBlockEntity kubeBlockEntity) {
                for (BlockEntityAttachment attachment : kubeBlockEntity.attachments) {
                    if (attachment instanceof InventoryAttachment inv) inventoryAttachment = inv;
                }
            }
            return new Delegated(inventoryAttachment);
        };
    }

    @Nullable
    private final InventoryAttachment attachment;

    public Delegated(@Nullable InventoryAttachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public int getSlots() {
        if (attachment == null) return 0;
        return attachment.getContainerSize();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        if (attachment == null) return ItemStack.EMPTY;
        return attachment.getItem(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (attachment == null) return stack.copy();
        return attachment.kjs$insertItem(slot, stack, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (attachment == null) return ItemStack.EMPTY;
        return attachment.kjs$extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        if (attachment == null) return 99;
        return attachment.getMaxStackSize();
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return attachment != null && attachment.kjs$isItemValid(slot, stack);
    }
}
