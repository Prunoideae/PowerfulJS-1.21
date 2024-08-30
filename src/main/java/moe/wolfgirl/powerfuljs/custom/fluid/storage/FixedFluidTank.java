package moe.wolfgirl.powerfuljs.custom.fluid.storage;

import com.google.common.base.Predicates;
import dev.latvian.mods.kubejs.fluid.FluidWrapper;
import dev.latvian.mods.kubejs.util.RegistryAccessContainer;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.ScriptRuntime;
import dev.latvian.mods.rhino.type.JSObjectTypeInfo;
import dev.latvian.mods.rhino.type.JSOptionalParam;
import dev.latvian.mods.rhino.type.RecordTypeInfo;
import dev.latvian.mods.rhino.type.TypeInfo;
import moe.wolfgirl.powerfuljs.custom.Attachments;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.attachment.AttachmentHolder;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import java.util.Map;
import java.util.function.Predicate;

public abstract class FixedFluidTank extends BaseFluidTank {
    public static final ResourceLocation ID = MCID.create("fixed_storage_fluid");

    public record Configuration(int capacity, int maxExtract, int maxReceive, FluidIngredient validator) {
        public static final RecordTypeInfo TYPE_INFO = (RecordTypeInfo) TypeInfo.of(Configuration.class);
    }
    
    public static final CapabilityBuilder<BlockEntity, IFluidHandler> BLOCK_ENTITY = CapabilityBuilder.create(
            ID, Capabilities.FluidHandler.BLOCK,
            Configuration.TYPE_INFO, FixedFluidTank::wraps
    );

    public static final CapabilityBuilder<Entity, IFluidHandler> ENTITY = CapabilityBuilder.create(
            ID, Capabilities.FluidHandler.ENTITY,
            Configuration.TYPE_INFO, FixedFluidTank::wraps
    );

    public static <O extends AttachmentHolder> CapabilityBuilder.CapabilityFactory<O, IFluidHandler> wraps(Context ctx, Object configuration) {
        Configuration c = (Configuration) Configuration.TYPE_INFO.wrap(ctx, configuration, Configuration.TYPE_INFO);
        return object -> new Attachment(c.maxExtract, c.maxReceive, c.capacity, c.validator, object);
    }

    private final int maxExtract;
    private final int maxReceive;
    private final int capacity;


    protected FixedFluidTank(int maxExtract, int maxReceive, int capacity, Predicate<FluidStack> validator) {
        super(validator);
        this.maxExtract = maxExtract;
        this.maxReceive = maxReceive;
        this.capacity = capacity;
    }

    @Override
    protected int getMaxExtract() {
        return maxExtract;
    }

    @Override
    protected int getMaxReceive() {
        return maxReceive;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    public static class Attachment extends FixedFluidTank {
        private final AttachmentHolder parent;

        protected Attachment(int maxExtract, int maxReceive, int capacity, Predicate<FluidStack> validator, AttachmentHolder parent) {
            super(maxExtract, maxReceive, capacity, validator);
            this.parent = parent;
        }

        @Override
        protected FluidStack getFluidData() {
            return parent.getData(Attachments.FLUID);
        }

        @Override
        protected void setFluidData(FluidStack fluidStack) {
            parent.setData(Attachments.FLUID, fluidStack);
        }

        @Override
        protected void onChanged() {
            if (parent instanceof BlockEntity blockEntity) {
                blockEntity.setChanged();
            }
        }
    }
}
