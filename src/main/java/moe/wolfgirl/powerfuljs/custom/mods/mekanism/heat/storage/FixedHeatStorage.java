package moe.wolfgirl.powerfuljs.custom.mods.mekanism.heat.storage;

import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.RecordTypeInfo;
import dev.latvian.mods.rhino.type.TypeInfo;
import mekanism.api.heat.HeatAPI;
import mekanism.api.heat.IHeatHandler;
import mekanism.common.capabilities.Capabilities;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.MekAttachments;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

public class FixedHeatStorage extends HeatHandler {
    public static final ResourceLocation ID = MCID.create("fixed_storage_heat");

    public record Configuration(double insulation, double conduction, double capacity, double initialTemperature) {
        public static final RecordTypeInfo TYPE_INFO = (RecordTypeInfo) TypeInfo.of(Configuration.class);
    }

    public static final CapabilityBuilder<BlockEntity, IHeatHandler> BLOCK_ENTITY = CapabilityBuilder.create(
            ID, Capabilities.HEAT,
            Configuration.TYPE_INFO, FixedHeatStorage::wraps
    );

    public static CapabilityBuilder.CapabilityFactory<BlockEntity, IHeatHandler> wraps(Context ctx, Object configuration) {
        Configuration c = (Configuration) Configuration.TYPE_INFO.wrap(ctx, configuration, Configuration.TYPE_INFO);
        return object -> new FixedHeatStorage(
                c.insulation,
                c.conduction,
                c.capacity,
                c.initialTemperature,
                object
        );
    }

    private final double insulation;
    private final double conduction;
    private final double capacity;
    private final double initialTemperature;
    private final BlockEntity parent;

    public FixedHeatStorage(double insulation, double conduction, double capacity, double initialTemperature, BlockEntity parent) {
        this.insulation = insulation;
        this.conduction = conduction;
        this.capacity = capacity;
        this.initialTemperature = initialTemperature == 0 ? HeatAPI.AMBIENT_TEMP : initialTemperature;
        this.parent = parent;
    }


    @Override
    protected void setHeatData(double heatData) {
        parent.setData(MekAttachments.HEAT, heatData);
    }

    @Override
    protected double getHeatData() {
        return parent.getData(MekAttachments.HEAT);
    }

    @Override
    protected double getInitialTemperature() {
        return initialTemperature;
    }

    @Override
    protected double getInsulation() {
        return insulation;
    }

    @Override
    protected double getConduction() {
        return conduction;
    }

    @Override
    protected double getCapacity() {
        return capacity;
    }

    @Override
    protected void onChanged(double transferred) {
        parent.setChanged();
    }
}
