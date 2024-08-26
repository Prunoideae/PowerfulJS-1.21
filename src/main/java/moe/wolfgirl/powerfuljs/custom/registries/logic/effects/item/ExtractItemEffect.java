package moe.wolfgirl.powerfuljs.custom.registries.logic.effects.item;

import moe.wolfgirl.powerfuljs.utils.CapabilityHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class ExtractItemEffect extends ItemEffect {
    private final ItemStack itemStack;

    public ExtractItemEffect(ItemStack itemStack, @Nullable Direction context) {
        super(context);
        this.itemStack = itemStack;
    }

    @Override
    protected void runEffect(BlockCapabilityCache<IItemHandler, Direction> cache) {
        var cap = cache.getCapability();
        if (cap == null) return;
        CapabilityHelper.extractItem(cap, itemStack, false);
    }
}
