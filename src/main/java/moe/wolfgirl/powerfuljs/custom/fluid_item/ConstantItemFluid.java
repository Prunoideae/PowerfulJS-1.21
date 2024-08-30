package moe.wolfgirl.powerfuljs.custom.fluid_item;

import dev.latvian.mods.rhino.Context;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.fluid.ConstantFluid;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;

public class ConstantItemFluid extends ConstantFluid implements IFluidHandlerItem {

    public static CapabilityBuilder.CapabilityFactory<ItemStack, IFluidHandlerItem> wrapsItem(Context ctx, Object configuration) {
        Configuration c = (Configuration) Configuration.TYPE_INFO.wrap(ctx, configuration, Configuration.TYPE_INFO);
        return object -> new ConstantItemFluid(c.content(), c.maxReceive(), object);
    }

    public static final CapabilityBuilder<ItemStack, IFluidHandlerItem> ITEM = CapabilityBuilder.create(
            ID, Capabilities.FluidHandler.ITEM,
            Configuration.TYPE_INFO, ConstantItemFluid::wrapsItem
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
