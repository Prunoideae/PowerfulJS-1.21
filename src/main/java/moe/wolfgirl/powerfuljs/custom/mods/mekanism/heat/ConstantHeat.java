package moe.wolfgirl.powerfuljs.custom.mods.mekanism.heat;

import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.RecordTypeInfo;
import dev.latvian.mods.rhino.type.TypeInfo;
import mekanism.api.heat.IHeatHandler;
import mekanism.common.capabilities.Capabilities;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.base.info.BlockContext;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ConstantHeat implements IHeatHandler {
    public static final ResourceLocation ID = MCID.create("constant_heat");

    public record Configuration(double temperature, double conductivity, double capacity) {
        public static final RecordTypeInfo TYPE_INFO = (RecordTypeInfo) TypeInfo.of(Configuration.class);
    }

    public static final CapabilityBuilder<BlockContext, IHeatHandler> BLOCK = CapabilityBuilder.create(
            ID, Capabilities.HEAT,
            Configuration.TYPE_INFO, ConstantHeat::wraps
    );

    public static final CapabilityBuilder<BlockEntity, IHeatHandler> BLOCK_ENTITY = CapabilityBuilder.create(
            ID, Capabilities.HEAT,
            Configuration.TYPE_INFO, ConstantHeat::wraps
    );

    public static <O> CapabilityBuilder.CapabilityFactory<O, IHeatHandler> wraps(Context ctx, Object configuration) {
        Configuration c = (Configuration) Configuration.TYPE_INFO.wrap(ctx, configuration, Configuration.TYPE_INFO);
        return object -> new ConstantHeat(c.temperature, c.conductivity, c.capacity);
    }

    private final double temperature;
    private final double conductivity;
    private final double capacity;

    public ConstantHeat(double temperature, double conductivity, double capacity) {
        this.temperature = temperature;
        this.conductivity = conductivity;
        this.capacity = capacity;
    }

    @Override
    public int getHeatCapacitorCount() {
        return 1;
    }


    @Override
    public double getTemperature(int capacitor) {
        return temperature;
    }

    @Override
    public double getInverseConduction(int capacitor) {
        return conductivity;
    }

    @Override
    public double getHeatCapacity(int capacitor) {
        return capacity;
    }

    @Override
    public void handleHeat(int capacitor, double transfer) {

    }
}
