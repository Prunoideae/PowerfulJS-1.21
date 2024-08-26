package moe.wolfgirl.powerfuljs.custom.registries.logic.effects.item;

import moe.wolfgirl.powerfuljs.custom.registries.logic.effects.CapabilityEffect;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public abstract class ItemEffect extends CapabilityEffect<IItemHandler, Direction> {
    protected ItemEffect(@Nullable Direction context) {
        super(Capabilities.ItemHandler.BLOCK, context);
    }
}
