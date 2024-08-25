package moe.wolfgirl.powerfuljs.custom.item;

import dev.latvian.mods.kubejs.bindings.ItemWrapper;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.util.ListJS;
import dev.latvian.mods.kubejs.util.RegistryAccessContainer;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.JSObjectTypeInfo;
import dev.latvian.mods.rhino.type.JSOptionalParam;
import dev.latvian.mods.rhino.type.TypeInfo;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.base.info.BlockContext;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConstantItem implements IItemHandler {
    public static final ResourceLocation ID = MCID.create("constant_item");
    public static final ResourceLocation ID_AUTOMATION = MCID.create("constant_item_automation");

    public static final TypeInfo TYPE_INFO = JSObjectTypeInfo.of(
            new JSOptionalParam("items", ItemStackJS.TYPE_INFO.asArray())
    );

    private final List<ItemStack> stacks;

    public static <O> CapabilityBuilder.CapabilityFactory<O, IItemHandler> wraps(Context ctx, Map<String, Object> configuration) {
        List<ItemStack> items = new ArrayList<>();
        List<?> l = ListJS.of(configuration.get("items"));
        if (l != null) {
            for (Object o : l) {
                items.add(ItemStackJS.wrap(RegistryAccessContainer.of(ctx), o));
            }
        }
        return object -> new ConstantItem(List.copyOf(items));
    }

    public static final CapabilityBuilder<BlockContext, IItemHandler> BLOCK = CapabilityBuilder.create(
            ID, Capabilities.ItemHandler.BLOCK,
            TYPE_INFO, ConstantItem::wraps
    );

    public static final CapabilityBuilder<BlockEntity, IItemHandler> BLOCK_ENTITY = CapabilityBuilder.create(
            ID, Capabilities.ItemHandler.BLOCK,
            TYPE_INFO, ConstantItem::wraps
    );

    public static final CapabilityBuilder<Entity, IItemHandler> ENTITY = CapabilityBuilder.create(
            ID, Capabilities.ItemHandler.ENTITY,
            TYPE_INFO, ConstantItem::wraps
    );

    public static final CapabilityBuilder<Entity, IItemHandler> ENTITY_AUTOMATION = CapabilityBuilder.create(
            ID_AUTOMATION, Capabilities.ItemHandler.ENTITY_AUTOMATION,
            TYPE_INFO, ConstantItem::wraps
    );

    public static final CapabilityBuilder<ItemStack, IItemHandler> ITEM = CapabilityBuilder.create(
            ID, Capabilities.ItemHandler.ITEM,
            TYPE_INFO, ConstantItem::wraps
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
