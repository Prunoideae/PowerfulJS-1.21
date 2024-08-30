package moe.wolfgirl.powerfuljs.custom.fluid;

import dev.latvian.mods.kubejs.fluid.FluidWrapper;
import dev.latvian.mods.kubejs.util.RegistryAccessContainer;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * A fluid handler with a constant fluid stack supply.
 * <p>
 * E.g. extracting little amount of water from mud
 */
public class ConstantFluid implements IFluidHandler {
    public static final ResourceLocation ID = MCID.create("constant_fluid");

    public record Configuration(FluidStack content, int maxReceive) {
        public static final RecordTypeInfo TYPE_INFO = (RecordTypeInfo) TypeInfo.of(Configuration.class);
    }

    public static <O> CapabilityBuilder.CapabilityFactory<O, IFluidHandler> wraps(Context ctx, Object configuration) {
        Configuration c = (Configuration) Configuration.TYPE_INFO.wrap(ctx, configuration, Configuration.TYPE_INFO);
        return object -> new ConstantFluid(c.content, c.maxReceive);
    }

    public static final CapabilityBuilder<BlockContext, IFluidHandler> BLOCK = CapabilityBuilder.create(
            ID, Capabilities.FluidHandler.BLOCK,
            Configuration.TYPE_INFO, ConstantFluid::wraps
    );

    public static final CapabilityBuilder<BlockEntity, IFluidHandler> BLOCK_ENTITY = CapabilityBuilder.create(
            ID, Capabilities.FluidHandler.BLOCK,
            Configuration.TYPE_INFO, ConstantFluid::wraps
    );

    public static final CapabilityBuilder<Entity, IFluidHandler> ENTITY = CapabilityBuilder.create(
            ID, Capabilities.FluidHandler.ENTITY,
            Configuration.TYPE_INFO, ConstantFluid::wraps
    );


    private final FluidStack content;
    private final int capacity;
    private final int maxReceive;

    public ConstantFluid(FluidStack content, int maxReceive) {
        if (content.isEmpty()) throw new IllegalArgumentException("Content can't be empty.");
        this.content = content;
        this.capacity = content.getAmount() + maxReceive;
        this.maxReceive = maxReceive;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return content.copy();
    }

    @Override
    public int getTankCapacity(int tank) {
        return capacity;
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return FluidStack.isSameFluidSameComponents(content, stack);
    }

    @Override
    public int fill(@NotNull FluidStack resource, @NotNull FluidAction action) {
        if (resource.isEmpty()) return 0;
        return Math.min(maxReceive, resource.getAmount());
    }

    @Override
    public @NotNull FluidStack drain(@NotNull FluidStack resource, @NotNull FluidAction action) {
        if (resource.isEmpty()) return FluidStack.EMPTY;
        return content.copyWithAmount(Math.min(resource.getAmount(), content.getAmount()));
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, @NotNull FluidAction action) {
        if (maxDrain <= 0) return FluidStack.EMPTY;
        return content.copyWithAmount(Math.min(content.getAmount(), maxDrain));
    }
}
