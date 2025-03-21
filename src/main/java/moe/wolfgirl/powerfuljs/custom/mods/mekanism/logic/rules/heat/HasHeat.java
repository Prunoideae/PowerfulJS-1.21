package moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.heat;

import mekanism.api.heat.IHeatHandler;
import mekanism.common.capabilities.Capabilities;
import moe.wolfgirl.powerfuljs.custom.logic.rules.CapabilityRule;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import org.jetbrains.annotations.Nullable;

public class HasHeat extends CapabilityRule<IHeatHandler, @Nullable Direction> {
    private final double temperature;

    public HasHeat(double temperature, @Nullable Direction context) {
        super(Capabilities.HEAT, context);
        this.temperature = temperature;
    }

    @Override
    protected boolean evaluateCap(IHeatHandler cap) {
        if (cap == null) return false;
        return cap.getTotalTemperature() >= temperature;
    }
}
