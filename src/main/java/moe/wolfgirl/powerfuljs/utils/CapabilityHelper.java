package moe.wolfgirl.powerfuljs.utils;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.items.IItemHandler;

public class CapabilityHelper {

    public static boolean hasItem(IItemHandler handler, Ingredient ingredient, int count) {
        for (int i = 0; i < handler.getSlots(); i++) {
            var slotItem = handler.getStackInSlot(i);
            if (ingredient.test(slotItem)) {
                count -= slotItem.getCount();
            }
            if (count == 0) return true;
        }
        return false;
    }

    public static ItemStack insertItem(IItemHandler handler, ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) return stack;
        ItemStack copy = stack.copy();
        for (int i = 0; i < handler.getSlots(); i++) {
            copy = handler.insertItem(i, copy, simulate);
            if (copy.isEmpty()) return copy;
        }
        return copy;
    }

    public static ItemStack extractItem(IItemHandler handler, ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) return stack;
        ItemStack copy = stack.copy();
        for (int i = 0; i < handler.getSlots(); i++) {
            if (ItemStack.isSameItemSameComponents(copy, handler.getStackInSlot(i))) {
                var extracted = handler.extractItem(i, copy.getCount(), simulate);
                copy.shrink(extracted.getCount());
            }
            if (copy.isEmpty()) return stack.copy();
        }
        return stack.copyWithCount(stack.getCount() - copy.getCount());
    }
}
