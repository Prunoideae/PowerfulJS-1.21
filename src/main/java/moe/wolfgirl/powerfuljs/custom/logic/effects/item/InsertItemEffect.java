package moe.wolfgirl.powerfuljs.custom.logic.effects.item;

import moe.wolfgirl.powerfuljs.utils.CapabilityUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class InsertItemEffect extends ItemEffect {
    private final ItemStack itemStack;

    public InsertItemEffect(ItemStack itemStack, @Nullable Direction context) {
        super(context);
        this.itemStack = itemStack;
    }

    @Override
    protected void runEffect(IItemHandler cap) {
        if (cap == null) return;
        CapabilityUtils.insertItem(cap, itemStack, false);
    }
}
