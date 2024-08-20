package moe.wolfgirl.powerfuljs.custom.fluid.storage;

import com.google.common.base.Predicates;
import dev.latvian.mods.kubejs.fluid.FluidWrapper;
import dev.latvian.mods.kubejs.util.RegistryAccessContainer;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.ScriptRuntime;
import dev.latvian.mods.rhino.type.JSObjectTypeInfo;
import dev.latvian.mods.rhino.type.JSOptionalParam;
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

import java.util.Map;
import java.util.function.Predicate;

public abstract class FixedFluidTank extends BaseFluidTank {
    public static final ResourceLocation ID = MCID.create("fixed_storage_fluid");
    public static final TypeInfo TYPE_INFO = JSObjectTypeInfo.of(
            new JSOptionalParam("capacity", TypeInfo.INT),
            new JSOptionalParam("maxExtract", TypeInfo.INT, true),
            new JSOptionalParam("maxReceive", TypeInfo.INT, true),
            new JSOptionalParam("validator", FluidWrapper.INGREDIENT_TYPE_INFO, true)
    );

    public static final CapabilityBuilder<BlockEntity, IFluidHandler> BLOCK_ENTITY = CapabilityBuilder.create(
            ID, Capabilities.FluidHandler.BLOCK,
            TYPE_INFO, FixedFluidTank::wraps
    );

    public static final CapabilityBuilder<Entity, IFluidHandler> ENTITY = CapabilityBuilder.create(
            ID, Capabilities.FluidHandler.ENTITY,
            TYPE_INFO, FixedFluidTank::wraps
    );

    public static <O extends AttachmentHolder> CapabilityBuilder.CapabilityFactory<O, IFluidHandler> wraps(Context ctx, Map<String, Object> configuration) {
        var capacity = ScriptRuntime.toInt32(ctx, configuration.get("capacity"));
        var maxExtract = ScriptRuntime.toInt32(ctx, configuration.get("maxExtract"));
        var maxReceive = ScriptRuntime.toInt32(ctx, configuration.get("maxReceive"));
        Predicate<FluidStack> validator = configuration.containsKey("validator") ?
                FluidWrapper.wrapIngredient(RegistryAccessContainer.of(ctx), configuration.get("validator")) :
                Predicates.alwaysTrue();
        return object -> new Attachment(maxExtract, maxReceive, capacity, validator, object);
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
