package moe.wolfgirl.powerfuljs.custom.forge_energy;

import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.ScriptRuntime;
import dev.latvian.mods.rhino.type.JSObjectTypeInfo;
import dev.latvian.mods.rhino.type.JSOptionalParam;
import dev.latvian.mods.rhino.type.RecordTypeInfo;
import dev.latvian.mods.rhino.type.TypeInfo;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.base.info.BlockContext;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.Map;

/**
 * An energy storage that has a constant state. It can receive / produce
 * energy constantly without changing its state.
 */
public class ConstantEnergy implements IEnergyStorage {
    public static final ResourceLocation ID = MCID.create("constant_energy");

    public record Configuration(int maxExtract, int maxReceive) {
        public static final RecordTypeInfo TYPE_INFO = (RecordTypeInfo) TypeInfo.of(Configuration.class);
    }


    public static <O> CapabilityBuilder.CapabilityFactory<O, IEnergyStorage> wraps(Context ctx, Object configuration) {
        Configuration c = (Configuration) Configuration.TYPE_INFO.wrap(ctx, configuration, Configuration.TYPE_INFO);
        return object -> new ConstantEnergy(c.maxExtract, c.maxReceive);
    }

    public static final CapabilityBuilder<BlockContext, IEnergyStorage> BLOCK = CapabilityBuilder.create(
            ID, Capabilities.EnergyStorage.BLOCK,
            Configuration.TYPE_INFO, ConstantEnergy::wraps
    );

    public static final CapabilityBuilder<BlockEntity, IEnergyStorage> BLOCK_ENTITY = CapabilityBuilder.create(
            ID, Capabilities.EnergyStorage.BLOCK,
            Configuration.TYPE_INFO, ConstantEnergy::wraps
    );

    public static final CapabilityBuilder<Entity, IEnergyStorage> ENTITY = CapabilityBuilder.create(
            ID, Capabilities.EnergyStorage.ENTITY,
            Configuration.TYPE_INFO, ConstantEnergy::wraps
    );

    public static final CapabilityBuilder<ItemStack, IEnergyStorage> ITEM = CapabilityBuilder.create(
            ID, Capabilities.EnergyStorage.ITEM,
            Configuration.TYPE_INFO, ConstantEnergy::wraps
    );

    private final int maxExtract;
    private final int maxReceive;

    public ConstantEnergy(int maxExtract, int maxReceive) {
        this.maxExtract = maxExtract;
        this.maxReceive = maxReceive;
    }

    @Override
    public int receiveEnergy(int toReceive, boolean simulate) {
        return Math.min(toReceive, maxReceive);
    }

    @Override
    public int extractEnergy(int toExtract, boolean simulate) {
        return Math.min(toExtract, maxExtract);
    }

    @Override
    public int getEnergyStored() {
        return maxExtract;
    }

    @Override
    public int getMaxEnergyStored() {
        return maxExtract + maxReceive;
    }

    @Override
    public boolean canExtract() {
        return maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return maxReceive > 0;
    }
}
