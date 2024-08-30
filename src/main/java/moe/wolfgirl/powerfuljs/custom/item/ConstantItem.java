package moe.wolfgirl.powerfuljs.custom.item;

import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.RecordTypeInfo;
import dev.latvian.mods.rhino.type.TypeInfo;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.base.info.BlockContext;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConstantItem implements IItemHandler {
    public static final ResourceLocation ID = MCID.create("constant_item");
    public static final ResourceLocation ID_AUTOMATION = MCID.create("constant_item_automation");

    public record Configuration(List<ItemStack> items) {
        public static final RecordTypeInfo TYPE_INFO = (RecordTypeInfo) TypeInfo.of(Configuration.class);
    }


    private final List<ItemStack> stacks;

    public static <O> CapabilityBuilder.CapabilityFactory<O, IItemHandler> wraps(Context ctx, Object configuration) {
        Configuration c = (Configuration) Configuration.TYPE_INFO.wrap(ctx, configuration, Configuration.TYPE_INFO);
        return object -> new ConstantItem(List.copyOf(c.items));
    }

    public static final CapabilityBuilder<BlockContext, IItemHandler> BLOCK = CapabilityBuilder.create(
            ID, Capabilities.ItemHandler.BLOCK,
            Configuration.TYPE_INFO, ConstantItem::wraps
    );

    public static final CapabilityBuilder<BlockEntity, IItemHandler> BLOCK_ENTITY = CapabilityBuilder.create(
            ID, Capabilities.ItemHandler.BLOCK,
            Configuration.TYPE_INFO, ConstantItem::wraps
    );

    public static final CapabilityBuilder<Entity, IItemHandler> ENTITY = CapabilityBuilder.create(
            ID, Capabilities.ItemHandler.ENTITY,
            Configuration.TYPE_INFO, ConstantItem::wraps
    );

    public static final CapabilityBuilder<Entity, IItemHandler> ENTITY_AUTOMATION = CapabilityBuilder.create(
            ID_AUTOMATION, Capabilities.ItemHandler.ENTITY_AUTOMATION,
            Configuration.TYPE_INFO, ConstantItem::wraps
    );

    public static final CapabilityBuilder<ItemStack, IItemHandler> ITEM = CapabilityBuilder.create(
            ID, Capabilities.ItemHandler.ITEM,
            Configuration.TYPE_INFO, ConstantItem::wraps
    );


    public ConstantItem(List<ItemStack> stacks) {
        this.stacks = stacks;
    }

    @Override
    public int getSlots() {
        return stacks.size();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return stacks.get(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return getStackInSlot(slot).copyWithCount(amount);
    }

    @Override
    public int getSlotLimit(int slot) {
        return getStackInSlot(slot).getCount();
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return false;
    }
}
