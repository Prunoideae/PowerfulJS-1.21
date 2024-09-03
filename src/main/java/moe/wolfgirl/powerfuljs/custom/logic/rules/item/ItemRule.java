package moe.wolfgirl.powerfuljs.custom.logic.rules.item;

import moe.wolfgirl.powerfuljs.custom.logic.rules.CapabilityRule;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public abstract class ItemRule extends CapabilityRule<IItemHandler, Direction> {
    protected ItemRule(@Nullable Direction context) {
        super(Capabilities.ItemHandler.BLOCK, context);
    }


}
