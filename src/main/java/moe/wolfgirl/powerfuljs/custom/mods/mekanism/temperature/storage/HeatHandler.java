package moe.wolfgirl.powerfuljs.custom.mods.mekanism.temperature.storage;

import mekanism.api.heat.HeatAPI;
import mekanism.api.heat.IHeatHandler;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.heat.CachedAmbientTemperature;
import mekanism.common.util.EnumUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

public abstract class HeatHandler implements IHeatHandler {
    private final EnumMap<Direction, BlockCapabilityCache<IHeatHandler, @Nullable Direction>> heatHandlers = new EnumMap<>(Direction.class);
    private CachedAmbientTemperature ambientTemperature = null;

    @Override
    public final int getHeatCapacitorCount() {
        return 1;
    }

    @Override
    public final double getInverseConduction(int capacitor) {
        return getConduction();
    }

    @Override
    public final double getHeatCapacity(int capacitor) {
        return getCapacity();
    }

    @Override
    public final double getTemperature(int capacitor) {
        double heat = getHeatData();
        if (heat == -1) return getInitialTemperature();
        return getHeatData() / getCapacity();
    }

    @Override
    public void handleHeat(int capacitor, double transfer) {
        if (Math.abs(transfer) <= HeatAPI.EPSILON) return;
        double currentHeat = getHeatData();
        if (currentHeat == -1) currentHeat = getCapacity() * getInitialTemperature();
        currentHeat += transfer;
        setHeatData(currentHeat);
        onChanged(transfer);
    }

    public double simulateEnvironment(ServerLevel level, BlockPos thisPos) {
        double environmentTransfer = 0;
        double capacity = getCapacity();

        double temperature = getTemperature(0);
        if (heatHandlers.isEmpty()) { // Initialize surrounding heat handlers, note that
            for (Direction direction : EnumUtils.DIRECTIONS) {
                heatHandlers.put(direction, BlockCapabilityCache.create(
                        Capabilities.HEAT, level,
                        thisPos.relative(direction), direction.getOpposite()
                ));
            }
        }
        if (ambientTemperature == null) {
            ambientTemperature = new CachedAmbientTemperature(() -> level, () -> thisPos);
        }

        for (Direction direction : EnumUtils.DIRECTIONS) {
            IHeatHandler sink = heatHandlers.get(direction).getCapability();
            double heatTransferred;
            if (sink != null) {
                // Transfer heat to the sink
                double conduction = getConduction() + sink.getTotalInverseConduction();
                double tempDiff = (temperature - sink.getTotalTemperature()) / conduction;
                if (tempDiff < HeatAPI.EPSILON) continue;
                heatTransferred = tempDiff * capacity;
                sink.handleHeat(heatTransferred);
            } else {
                // Dissipate to environment
                double airConduction = HeatAPI.AIR_INVERSE_COEFFICIENT + getInsulation() + getConduction();
                double tempDiff = (temperature - getAmbientTemperature(direction)) / airConduction;
                heatTransferred = Math.max(0, tempDiff * capacity);
            }
            this.handleHeat(-heatTransferred);
            environmentTransfer += heatTransferred;
        }
        return environmentTransfer;
    }

    private double getAmbientTemperature(Direction direction) {
        if (this.ambientTemperature == null) return HeatAPI.AMBIENT_TEMP;
        return ambientTemperature.getTemperature(direction);
    }

    protected abstract void setHeatData(double heatData);

    protected abstract double getHeatData();

    protected abstract double getInitialTemperature();

    protected abstract double getInsulation();

    protected abstract double getConduction();

    protected abstract double getCapacity();

    protected abstract void onChanged(double transferred);
}
