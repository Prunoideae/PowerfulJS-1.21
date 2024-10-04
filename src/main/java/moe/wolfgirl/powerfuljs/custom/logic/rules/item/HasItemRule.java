package moe.wolfgirl.powerfuljs.custom.logic.rules.item;

import moe.wolfgirl.powerfuljs.utils.CapabilityUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class HasItemRule extends ItemRule {
    private final Ingredient item;
    private final int count;

    public HasItemRule(Ingredient item, int count, @Nullable Direction context) {
        super(context);
        this.item = item;
        this.count = count;
    }

    @Override
    protected boolean evaluateCap(BlockCapabilityCache<IItemHandler, Direction> capabilityCache) {
        var cap = capabilityCache.getCapability();
        if (cap == null) return false;

        return CapabilityUtils.hasItem(cap, item, count);
    }
}
