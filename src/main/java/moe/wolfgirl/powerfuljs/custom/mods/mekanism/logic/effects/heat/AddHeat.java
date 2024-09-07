package moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.effects.heat;

import mekanism.api.heat.IHeatHandler;
import mekanism.common.capabilities.Capabilities;
import moe.wolfgirl.powerfuljs.custom.logic.effects.CapabilityEffect;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import org.jetbrains.annotations.Nullable;


public class AddHeat extends CapabilityEffect<IHeatHandler, @Nullable Direction> {
    private final double heatAdded;

    public AddHeat(double heatAdded, @Nullable Direction context) {
        super(Capabilities.HEAT, context);
        this.heatAdded = heatAdded;
    }

    @Override
    protected void runEffect(BlockCapabilityCache<IHeatHandler, @Nullable Direction> cache) {
        IHeatHandler handler = cache.getCapability();
        if (handler == null) return;
        handler.handleHeat(heatAdded);
    }
}
