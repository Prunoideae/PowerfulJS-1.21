package moe.wolfgirl.powerfuljs.custom.forge_energy.storage;

import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.ScriptRuntime;
import dev.latvian.mods.rhino.type.JSObjectTypeInfo;
import dev.latvian.mods.rhino.type.JSOptionalParam;
import dev.latvian.mods.rhino.type.RecordTypeInfo;
import dev.latvian.mods.rhino.type.TypeInfo;
import moe.wolfgirl.powerfuljs.custom.Attachments;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.DataComponents;
import moe.wolfgirl.powerfuljs.custom.fluid.storage.FixedFluidTank;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.attachment.AttachmentHolder;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import java.util.Map;

/**
 * A simple energy storage with bounds of I/O and capacity fixed.
 */
public abstract class FixedEnergyStorage extends BaseEnergyStorage {
    public static final ResourceLocation ID = MCID.create("fixed_storage_fe");

    public record Configuration(int capacity, int maxExtract, int maxReceive) {
        public static final RecordTypeInfo TYPE_INFO = (RecordTypeInfo) TypeInfo.of(Configuration.class);
    }


    public static final CapabilityBuilder<BlockEntity, IEnergyStorage> BLOCK_ENTITY = CapabilityBuilder.create(
            ID, Capabilities.EnergyStorage.BLOCK,
            Configuration.TYPE_INFO, FixedEnergyStorage::wrapsAttachment
    );

    public static final CapabilityBuilder<Entity, IEnergyStorage> ENTITY = CapabilityBuilder.create(
            ID, Capabilities.EnergyStorage.ENTITY,
            Configuration.TYPE_INFO, FixedEnergyStorage::wrapsAttachment
    );

    public static final CapabilityBuilder<ItemStack, IEnergyStorage> ITEM = CapabilityBuilder.create(
            ID, Capabilities.EnergyStorage.ITEM,
            Configuration.TYPE_INFO, FixedEnergyStorage::wrapsComponent
    );

    public static <O extends AttachmentHolder> CapabilityBuilder.CapabilityFactory<O, IEnergyStorage> wrapsAttachment(Context ctx, Object configuration) {
        Configuration c = (Configuration) Configuration.TYPE_INFO.wrap(ctx, configuration, Configuration.TYPE_INFO);
        return object -> new Attachment(c.capacity, c.maxReceive, c.maxExtract, object);
    }

    public static <O extends MutableDataComponentHolder> CapabilityBuilder.CapabilityFactory<O, IEnergyStorage> wrapsComponent(Context ctx, Object configuration) {
        Configuration c = (Configuration) Configuration.TYPE_INFO.wrap(ctx, configuration, Configuration.TYPE_INFO);
        return object -> new Component(c.capacity, c.maxReceive, c.maxExtract, object);
    }

    private final int capacity;
    private final int maxReceive;
    private final int maxExtract;

    protected FixedEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    @Override
    public int getMaxEnergyStored() {
        return capacity;
    }

    @Override
    public int getMaxExtract() {
        return maxExtract;
    }

    @Override
    public int getMaxReceive() {
        return maxReceive;
    }

    public static class Component extends FixedEnergyStorage {
        private final MutableDataComponentHolder parent;

        protected Component(int capacity, int maxReceive, int maxExtract, MutableDataComponentHolder parent) {
            super(capacity, maxReceive, maxExtract);
            this.parent = parent;
        }

        @Override
        protected void setEnergyData(int energy) {
            parent.set(DataComponents.FORGE_ENERGY, energy);
        }

        @Override
        protected int getEnergyData() {
            return parent.getOrDefault(DataComponents.FORGE_ENERGY, 0);
        }
    }

    public static class Attachment extends FixedEnergyStorage {
        private final AttachmentHolder parent;

        protected Attachment(int capacity, int maxReceive, int maxExtract, AttachmentHolder parent) {
            super(capacity, maxReceive, maxExtract);
            this.parent = parent;
        }

        @Override
        protected void setEnergyData(int energy) {
            parent.setData(Attachments.FORGE_ENERGY, energy);
        }

        @Override
        protected int getEnergyData() {
            return parent.getData(Attachments.FORGE_ENERGY);
        }

        @Override
        protected void onChanged() {
            if (parent instanceof BlockEntity blockEntity) {
                blockEntity.setChanged();
            }
        }
    }
}
