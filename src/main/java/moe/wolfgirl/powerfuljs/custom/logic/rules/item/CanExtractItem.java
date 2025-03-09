package moe.wolfgirl.powerfuljs.custom.logic.rules.item;

import moe.wolfgirl.powerfuljs.utils.CapabilityUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class CanExtractItem extends ItemRule {

    private final ItemStack itemStack;

    public CanExtractItem(ItemStack itemStack, @Nullable Direction context) {
        super(context);
        this.itemStack = itemStack;
    }

    @Override
    protected boolean evaluateCap(IItemHandler cap) {
        if (cap == null) return false;
        return CapabilityUtils.extractItem(cap, itemStack, true).getCount() == itemStack.getCount();
    }
}
