package moe.wolfgirl.powerfuljs.custom.item.storage;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public abstract class BaseItemStorage implements IItemHandler {

    protected abstract void setStackInSlot(int slot, ItemStack stack);

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (stack.isEmpty() || !isSlotValid(slot) || !isItemValid(slot, stack)) return stack;

        int limit = Math.min(getSlotLimit(slot), stack.getMaxStackSize());

        var current = getStackInSlot(slot);
        if (!current.isEmpty() && !ItemStack.isSameItemSameComponents(stack, current)) {
            return stack;
        }

        int inserted = Math.min(limit - current.getCount(), stack.getCount());
        if (inserted <= 0) return stack;

        if (!simulate) {
            setStackInSlot(slot, stack.copyWithCount(inserted + current.getCount()));
            onReceived(slot, stack.copyWithCount(inserted));
            onChanged();
        }

        return inserted == stack.getCount() ? ItemStack.EMPTY : stack.copyWithCount(stack.getCount() - inserted);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0 || !isSlotValid(slot)) return ItemStack.EMPTY;

        var current = getStackInSlot(slot);
        if (current.isEmpty()) return ItemStack.EMPTY;

        int toExtract = Math.min(amount, current.getCount());
        var extracted = current.copyWithCount(toExtract);

        if (!simulate) {
            setStackInSlot(slot, toExtract == current.getCount() ?
                    ItemStack.EMPTY :
                    current.copyWithCount(current.getCount() - toExtract)
            );
            onExtracted(slot, extracted);
            onChanged();
        }

        return extracted;
    }

    protected boolean isSlotValid(int slot) {
        return slot >= 0 && slot < getSlots();
    }

    protected void onExtracted(int slot, ItemStack stack) {

    }

    protected void onReceived(int slot, ItemStack stack) {

    }

    protected void onChanged() {

    }
}
