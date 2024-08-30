package moe.wolfgirl.powerfuljs.custom.fluid_item;

import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.*;
import moe.wolfgirl.powerfuljs.custom.DataComponents;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.fluid.storage.FixedFluidTank;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class FixedItemFluidTank extends FixedFluidTank implements IFluidHandlerItem {

    public record Configuration(int capacity, int maxExtract, int maxReceive, FluidIngredient validator,
                                ItemTransformRule changeItemWhen) {
        public static final RecordTypeInfo TYPE_INFO = (RecordTypeInfo) TypeInfo.of(Configuration.class);
    }

    public static final CapabilityBuilder<ItemStack, IFluidHandlerItem> ITEM = CapabilityBuilder.create(
            ID, Capabilities.FluidHandler.ITEM,
            Configuration.TYPE_INFO, FixedItemFluidTank::wrapsItem
    );

    public static CapabilityBuilder.CapabilityFactory<ItemStack, IFluidHandlerItem> wrapsItem(Context ctx, Object configuration) {
        Configuration c = (Configuration) Configuration.TYPE_INFO.wrap(ctx, configuration, Configuration.TYPE_INFO);

        ItemStack onEmptyTransform;
        ItemTransformRule.FullRule[] onFullTransform;
        if (c.changeItemWhen != null) {
            onEmptyTransform = c.changeItemWhen.empty;
            onFullTransform = c.changeItemWhen.full;
        } else {
            onFullTransform = null;
            onEmptyTransform = null;
        }

        return object -> new FixedItemFluidTank(c.maxExtract, c.maxReceive, c.capacity, c.validator, onEmptyTransform, onFullTransform, object);
    }

    public record ItemTransformRule(ItemStack empty, FullRule[] full) {
        public static final RecordTypeInfo TYPE_INFO = (RecordTypeInfo) TypeInfo.of(ItemTransformRule.class);

        public record FullRule(FluidIngredient fluid, ItemStack item) {
        }
    }

    private ItemStack parent;
    private final ItemStack onEmptyTransform;
    private final ItemTransformRule.FullRule[] onFullTransform;

    protected FixedItemFluidTank(int maxExtract, int maxReceive, int capacity,
                                 Predicate<FluidStack> validator,
                                 ItemStack onEmptyTransform, ItemTransformRule.FullRule[] onFullTransform,
                                 ItemStack parent) {
        super(maxExtract, maxReceive, capacity, validator);
        this.parent = parent;
        this.onEmptyTransform = onEmptyTransform;
        this.onFullTransform = onFullTransform;
    }

    @Override
    protected FluidStack getFluidData() {
        return parent.getOrDefault(DataComponents.FLUID, FluidStack.EMPTY);
    }

    @Override
    protected void setFluidData(FluidStack fluidStack) {
        parent.set(DataComponents.FLUID, fluidStack);
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, @NotNull FluidAction action) {
        FluidStack drained = super.drain(maxDrain, action);
        if (onEmptyTransform != null && getFluidData().isEmpty()) {
            if (onEmptyTransform.isEmpty()) this.parent.shrink(1);
            else this.parent = onEmptyTransform.copy();
        }
        return drained;
    }

    @Override
    public int fill(@NotNull FluidStack resource, @NotNull FluidAction action) {
        int filled = super.fill(resource, action);
        var fluid = getFluid();
        if (onFullTransform != null && fluid.getAmount() >= getCapacity()) {
            for (ItemTransformRule.FullRule fullRule : onFullTransform) {
                if (fullRule.fluid.test(fluid)) {
                    parent = fullRule.item.copy();
                    break;
                }
            }
        }
        return filled;
    }

    @Override
    public @NotNull ItemStack getContainer() {
        return parent;
    }
}
