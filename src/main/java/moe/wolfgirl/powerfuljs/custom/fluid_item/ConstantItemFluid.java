package moe.wolfgirl.powerfuljs.custom.fluid_item;

import dev.latvian.mods.kubejs.fluid.FluidWrapper;
import dev.latvian.mods.kubejs.util.RegistryAccessContainer;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.ScriptRuntime;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.fluid.ConstantFluid;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ConstantItemFluid extends ConstantFluid implements IFluidHandlerItem {

    public static CapabilityBuilder.CapabilityFactory<ItemStack, IFluidHandlerItem> wrapsItem(Context ctx, Map<String, Object> configuration) {
        var content = FluidWrapper.wrap(RegistryAccessContainer.of(ctx), configuration.get("content"));
        var maxReceive = ScriptRuntime.toInt32(ctx, configuration.get("maxReceive"));
        return object -> new ConstantItemFluid(content, maxReceive, object);
    }

    public static final CapabilityBuilder<ItemStack, IFluidHandlerItem> ITEM = CapabilityBuilder.create(
            ID, Capabilities.FluidHandler.ITEM,
            TYPE_INFO, ConstantItemFluid::wrapsItem
    );

    private final ItemStack parent;

    public ConstantItemFluid(FluidStack content, int maxReceive, ItemStack parent) {
        super(content, maxReceive);
        this.parent = parent;
    }

    @Override
    public @NotNull ItemStack getContainer() {
        return parent; // ?????
    }
}
