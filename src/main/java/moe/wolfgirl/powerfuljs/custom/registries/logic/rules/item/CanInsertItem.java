package moe.wolfgirl.powerfuljs.custom.registries.logic.rules.item;

import moe.wolfgirl.powerfuljs.utils.CapabilityHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class CanInsertItem extends ItemRule {

    private final ItemStack itemStack;

    public CanInsertItem(ItemStack itemStack, @Nullable Direction context) {
        super(context);
        this.itemStack = itemStack;
    }

    @Override
    protected boolean evaluateCap(BlockCapabilityCache<IItemHandler, Direction> capabilityCache) {
        var cap = capabilityCache.getCapability();
        if (cap == null) return false;
        return CapabilityHelper.insertItem(cap, itemStack, true).isEmpty();
    }
}
