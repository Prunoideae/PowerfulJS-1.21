package moe.wolfgirl.powerfuljs.custom.fluid_item;

import com.google.common.base.Predicates;
import dev.latvian.mods.kubejs.fluid.FluidWrapper;
import dev.latvian.mods.kubejs.util.RegistryAccessContainer;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.ScriptRuntime;
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

import java.util.Map;
import java.util.function.Predicate;

public class FixedItemFluidTank extends FixedFluidTank implements IFluidHandlerItem {
    public static final TypeInfo TYPE_INFO = JSObjectTypeInfo.of(
            new JSOptionalParam("capacity", TypeInfo.INT),
            new JSOptionalParam("maxExtract", TypeInfo.INT, true),
            new JSOptionalParam("maxReceive", TypeInfo.INT, true),
            new JSOptionalParam("validator", FluidWrapper.INGREDIENT_TYPE_INFO, true),
            new JSOptionalParam("changeItemWhen", ItemTransformRule.TYPE_INFO, true)
    );

    public static CapabilityBuilder.CapabilityFactory<ItemStack, IFluidHandlerItem> wrapsItem(Context ctx, Map<String, Object> configuration) {
        var capacity = ScriptRuntime.toInt32(ctx, configuration.get("capacity"));
        var maxExtract = ScriptRuntime.toInt32(ctx, configuration.get("maxExtract"));
        var maxReceive = ScriptRuntime.toInt32(ctx, configuration.get("maxReceive"));
        Predicate<FluidStack> validator = configuration.containsKey("validator") ?
                FluidWrapper.wrapIngredient(RegistryAccessContainer.of(ctx), configuration.get("validator")) :
                Predicates.alwaysTrue();

        ItemStack onEmptyTransform;
        ItemTransformRule.FullRule[] onFullTransform;
        if (configuration.containsKey("changeItemWhen")) {
            ItemTransformRule ruleSet = (ItemTransformRule) ItemTransformRule.TYPE_INFO.wrap(ctx, configuration.get("changeItemWhen"), ItemTransformRule.TYPE_INFO);
            onEmptyTransform = ruleSet.empty;
            onFullTransform = ruleSet.full;
        } else {
            onFullTransform = null;
            onEmptyTransform = null;
        }

        return object -> new FixedItemFluidTank(maxExtract, maxReceive, capacity, validator, onEmptyTransform, onFullTransform, object);
    }

    public record ItemTransformRule(ItemStack empty, FullRule[] full) {
        public static final RecordTypeInfo TYPE_INFO = (RecordTypeInfo) TypeInfo.of(ItemTransformRule.class);

        public record FullRule(FluidIngredient fluid, ItemStack item) {
        }
    }

    public static final CapabilityBuilder<ItemStack, IFluidHandlerItem> ITEM = CapabilityBuilder.create(
            ID, Capabilities.FluidHandler.ITEM,
            TYPE_INFO, FixedItemFluidTank::wrapsItem
    );

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
