package moe.wolfgirl.powerfuljs.custom.item.storage;

import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.RecordTypeInfo;
import dev.latvian.mods.rhino.type.TypeInfo;
import moe.wolfgirl.powerfuljs.custom.Attachments;
import moe.wolfgirl.powerfuljs.custom.DataComponents;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.serde.ItemContent;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.attachment.AttachmentHolder;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public abstract class FixedItemStorage extends BaseItemStorage {
    public static final ResourceLocation ID = MCID.create("fixed_storage_item");
    public static final ResourceLocation ID_AUTOMATION = MCID.create("fixed_storage_item_automation");

    public record Configuration(int size, int maxStack, Ingredient validator) {
        public static final RecordTypeInfo TYPE_INFO = (RecordTypeInfo) TypeInfo.of(Configuration.class);
    }

    public static final CapabilityBuilder<BlockEntity, IItemHandler> BLOCK_ENTITY = CapabilityBuilder.create(
            ID, Capabilities.ItemHandler.BLOCK,
            Configuration.TYPE_INFO, FixedItemStorage::wrapsAttachment
    );

    public static final CapabilityBuilder<Entity, IItemHandler> ENTITY = CapabilityBuilder.create(
            ID, Capabilities.ItemHandler.ENTITY,
            Configuration.TYPE_INFO, FixedItemStorage::wrapsAttachment
    );

    public static final CapabilityBuilder<Entity, IItemHandler> ENTITY_AUTOMATION = CapabilityBuilder.create(
            ID_AUTOMATION, Capabilities.ItemHandler.ENTITY,
            Configuration.TYPE_INFO, FixedItemStorage::wrapsAttachment
    );

    public static final CapabilityBuilder<ItemStack, IItemHandler> ITEM = CapabilityBuilder.create(
            ID, Capabilities.ItemHandler.ITEM,
            Configuration.TYPE_INFO, FixedItemStorage::wrapsComponent
    );

    public static <O extends AttachmentHolder> CapabilityBuilder.CapabilityFactory<O, IItemHandler> wrapsAttachment(Context ctx, Object configuration) {
        Configuration c = (Configuration) Configuration.TYPE_INFO.wrap(ctx, configuration, Configuration.TYPE_INFO);
        return object -> new Attachment(c.size, c.maxStack, c.validator, object);
    }

    public static <O extends MutableDataComponentHolder> CapabilityBuilder.CapabilityFactory<O, IItemHandler> wrapsComponent(Context ctx, Object configuration) {
        Configuration c = (Configuration) Configuration.TYPE_INFO.wrap(ctx, configuration, Configuration.TYPE_INFO);
        return object -> new Component(c.size, c.maxStack, c.validator, object);
    }

    private final int size;
    private final int slotLimit;
    private final Ingredient validator;

    protected FixedItemStorage(int size, int slotLimit, Ingredient validator) {
        this.size = size;
        this.slotLimit = slotLimit;
        this.validator = validator;
    }

    @Override
    public int getSlotLimit(int slot) {
        return slotLimit;
    }

    @Override
    public int getSlots() {
        return size;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return validator == null || validator.test(stack);
    }

    public static class Component extends FixedItemStorage {
        private final MutableDataComponentHolder parent;

        protected Component(int size, int slotLimit, Ingredient validator, MutableDataComponentHolder parent) {
            super(size, slotLimit, validator);
            this.parent = parent;
        }

        private ItemContent getItemData() {
            return parent.get(DataComponents.ITEM);
        }

        @Override
        protected void setStackInSlot(int slot, ItemStack stack) {
            parent.set(DataComponents.ITEM, getItemData().withStackInSlot(slot, stack));
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slot) {
            return getItemData().getStackInSlot(slot);
        }
    }

    public static class Attachment extends FixedItemStorage {
        private final AttachmentHolder parent;

        protected Attachment(int size, int slotLimit, Ingredient validator, AttachmentHolder parent) {
            super(size, slotLimit, validator);
            this.parent = parent;
        }

        private ItemContent getItemData() {
            return parent.getData(Attachments.ITEM);
        }

        @Override
        protected void setStackInSlot(int slot, ItemStack stack) {
            parent.setData(Attachments.ITEM, getItemData().withStackInSlot(slot, stack));
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slot) {
            return getItemData().getStackInSlot(slot);
        }

        @Override
        protected void onChanged() {
            if (parent instanceof BlockEntity blockEntity) {
                blockEntity.setChanged();
            }
        }
    }
}
