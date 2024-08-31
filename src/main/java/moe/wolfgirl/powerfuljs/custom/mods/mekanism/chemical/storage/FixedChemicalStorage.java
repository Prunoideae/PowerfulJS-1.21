package moe.wolfgirl.powerfuljs.custom.mods.mekanism.chemical.storage;

import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.RecordTypeInfo;
import dev.latvian.mods.rhino.type.TypeInfo;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalHandler;
import mekanism.common.capabilities.Capabilities;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.MekAttachments;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.MekDataComponents;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.attachment.AttachmentHolder;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import org.jetbrains.annotations.NotNull;

public abstract class FixedChemicalStorage extends ChemicalHandler {
    public static final ResourceLocation ID = MCID.create("fixed_storage_chemical");

    public record Configuration(long capacity, long maxReceive, long maxExtract, Chemical validChemical) {
        public static final RecordTypeInfo TYPE_INFO = (RecordTypeInfo) TypeInfo.of(Configuration.class);
    }

    public static final CapabilityBuilder<ItemStack, IChemicalHandler> ITEM = CapabilityBuilder.create(
            ID, Capabilities.CHEMICAL.item(),
            Configuration.TYPE_INFO, FixedChemicalStorage::wrapsComponent
    );

    public static final CapabilityBuilder<BlockEntity, IChemicalHandler> BLOCK_ENTITY = CapabilityBuilder.create(
            ID, Capabilities.CHEMICAL.block(),
            Configuration.TYPE_INFO, FixedChemicalStorage::wrapsAttachment
    );

    public static final CapabilityBuilder<Entity, IChemicalHandler> ENTITY = CapabilityBuilder.create(
            ID, Capabilities.CHEMICAL.entity(),
            Configuration.TYPE_INFO, FixedChemicalStorage::wrapsAttachment
    );

    public static <O extends AttachmentHolder> CapabilityBuilder.CapabilityFactory<O, IChemicalHandler> wrapsAttachment(Context ctx, Object configuration) {
        Configuration c = (Configuration) Configuration.TYPE_INFO.wrap(ctx, configuration, Configuration.TYPE_INFO);
        return object -> new Attachment(c.maxReceive, c.maxExtract, c.capacity, c.validChemical, object);
    }

    public static <O extends MutableDataComponentHolder> CapabilityBuilder.CapabilityFactory<O, IChemicalHandler> wrapsComponent(Context ctx, Object configuration) {
        Configuration c = (Configuration) Configuration.TYPE_INFO.wrap(ctx, configuration, Configuration.TYPE_INFO);
        return object -> new Component(c.maxReceive, c.maxExtract, c.capacity, c.validChemical, object);
    }

    private final long maxReceive;
    private final long maxExtract;
    private final long capacity;
    private final Chemical validChemical;

    protected FixedChemicalStorage(long maxReceive, long maxExtract, long capacity, Chemical validChemical) {
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.capacity = capacity;
        this.validChemical = validChemical;
    }

    @Override
    protected long getMaxReceive() {
        return maxReceive;
    }

    @Override
    protected long getMaxExtract() {
        return maxExtract;
    }

    @Override
    public long getChemicalTankCapacity(int tank) {
        return tank == 0 ? capacity : 0;
    }

    @Override
    public boolean isValid(int tank, @NotNull ChemicalStack stack) {
        return validChemical == null || stack.is(validChemical);
    }

    public static class Component extends FixedChemicalStorage {
        private final MutableDataComponentHolder parent;

        protected Component(long maxReceive, long maxExtract, long capacity, Chemical validChemical, MutableDataComponentHolder parent) {
            super(maxReceive, maxExtract, capacity, validChemical);
            this.parent = parent;

        }

        @Override
        protected void setChemicalData(ChemicalStack stack) {
            parent.set(MekDataComponents.CHEMICAL, stack);
        }

        @Override
        protected ChemicalStack getChemicalData() {
            return parent.get(MekDataComponents.CHEMICAL);
        }
    }

    public static class Attachment extends FixedChemicalStorage {
        private final AttachmentHolder parent;

        protected Attachment(long maxReceive, long maxExtract, long capacity, Chemical validChemical, AttachmentHolder parent) {
            super(maxReceive, maxExtract, capacity, validChemical);
            this.parent = parent;
        }

        @Override
        protected void setChemicalData(ChemicalStack stack) {
            parent.setData(MekAttachments.CHEMICAL, stack);
        }

        @Override
        protected ChemicalStack getChemicalData() {
            return parent.getData(MekAttachments.CHEMICAL);
        }

        @Override
        protected void onChanged() {
            if (parent instanceof BlockEntity blockEntity) {
                blockEntity.setChanged();
            }
        }
    }
}
