package moe.wolfgirl.powerfuljs.custom.fluid.storage;

import dev.latvian.mods.rhino.Context;
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

import java.util.function.Predicate;

public abstract class FixedProcessingFluidTank extends ProcessingFluidTank {
    public static final ResourceLocation ID = MCID.create("fixed_storage_processing_fluid");

    public record Configuration(int inputCapacity, int outputCapacity, int maxExtract, int maxReceive,
                                FluidIngredient inputValidator, FluidIngredient outputValidator) {
        public static final RecordTypeInfo TYPE_INFO = (RecordTypeInfo) TypeInfo.of(Configuration.class);
    }

    public static final CapabilityBuilder<BlockEntity, IFluidHandler> BLOCK_ENTITY = CapabilityBuilder.create(
            ID, Capabilities.FluidHandler.BLOCK,
            Configuration.TYPE_INFO, FixedProcessingFluidTank::wraps
    );

    public static final CapabilityBuilder<Entity, IFluidHandler> ENTITY = CapabilityBuilder.create(
            ID, Capabilities.FluidHandler.ENTITY,
            Configuration.TYPE_INFO, FixedProcessingFluidTank::wraps
    );

    public static <O extends AttachmentHolder> CapabilityBuilder.CapabilityFactory<O, IFluidHandler> wraps(Context ctx, Object configuration) {
        Configuration c = (Configuration) Configuration.TYPE_INFO.wrap(ctx, configuration, Configuration.TYPE_INFO);
        return object -> new Attachment(c.maxExtract, c.maxReceive, c.inputCapacity, c.outputCapacity, c.inputValidator, c.outputValidator, object);
    }

    private final int maxExtract;
    private final int maxReceive;
    private final int inputCapacity;
    private final int outputCapacity;

    protected FixedProcessingFluidTank(int maxExtract, int maxReceive, int inputCapacity, int outputCapacity, Predicate<FluidStack> inputValidator, Predicate<FluidStack> outputValidator) {
        super(inputValidator, outputValidator);
        this.maxExtract = maxExtract;
        this.maxReceive = maxReceive;
        this.inputCapacity = inputCapacity;
        this.outputCapacity = outputCapacity;
    }

    @Override
    public int getMaxReceive() {
        return maxReceive;
    }

    @Override
    public int getMaxExtract() {
        return maxExtract;
    }

    @Override
    public int getTankCapacity(int tank) {
        return tank == 0 ? inputCapacity : outputCapacity;
    }

    public static class Attachment extends FixedProcessingFluidTank {
        private final AttachmentHolder parent;

        protected Attachment(int maxExtract, int maxReceive, int inputCapacity, int outputCapacity, Predicate<FluidStack> inputValidator, Predicate<FluidStack> outputValidator, AttachmentHolder parent) {
            super(maxExtract, maxReceive, inputCapacity, outputCapacity, inputValidator, outputValidator);
            this.parent = parent;
        }

        @Override
        protected FluidStack getInputFluidData() {
            return parent.getData(Attachments.INPUT_FLUID);
        }

        @Override
        protected FluidStack getOutputFluidData() {
            return parent.getData(Attachments.OUTPUT_FLUID);
        }

        @Override
        protected void setInputFluidData(FluidStack fluidStack) {
            parent.setData(Attachments.INPUT_FLUID, fluidStack);
        }

        @Override
        protected void setOutputFluidData(FluidStack fluidStack) {
            parent.setData(Attachments.OUTPUT_FLUID, fluidStack);
        }

        @Override
        protected void onChanged() {
            if (parent instanceof BlockEntity blockEntity) {
                blockEntity.setChanged();
            }
        }
    }
}
