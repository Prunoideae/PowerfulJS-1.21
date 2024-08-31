package moe.wolfgirl.powerfuljs.custom.mods.mekanism.chemical;

import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.RecordTypeInfo;
import dev.latvian.mods.rhino.type.TypeInfo;
import mekanism.api.Action;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalHandler;
import mekanism.common.capabilities.Capabilities;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.base.info.BlockContext;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

/**
 * A chemical handler that holds a constant amount of chemical.
 */
public class ConstantChemical implements IChemicalHandler {
    public static final ResourceLocation ID = MCID.create("constant_chemical");

    public record Configuration(ChemicalStack stack) {
        public static final RecordTypeInfo TYPE_INFO = (RecordTypeInfo) TypeInfo.of(Configuration.class);
    }

    public static final CapabilityBuilder<ItemStack, IChemicalHandler> ITEM = CapabilityBuilder.create(
            ID, Capabilities.CHEMICAL.item(),
            Configuration.TYPE_INFO, ConstantChemical::wraps
    );

    public static final CapabilityBuilder<BlockEntity, IChemicalHandler> BLOCK_ENTITY = CapabilityBuilder.create(
            ID, Capabilities.CHEMICAL.block(),
            Configuration.TYPE_INFO, ConstantChemical::wraps
    );

    public static final CapabilityBuilder<BlockContext, IChemicalHandler> BLOCK = CapabilityBuilder.create(
            ID, Capabilities.CHEMICAL.block(),
            Configuration.TYPE_INFO, ConstantChemical::wraps
    );

    public static final CapabilityBuilder<Entity, IChemicalHandler> ENTITY = CapabilityBuilder.create(
            ID, Capabilities.CHEMICAL.entity(),
            Configuration.TYPE_INFO, ConstantChemical::wraps
    );

    public static <O> CapabilityBuilder.CapabilityFactory<O, IChemicalHandler> wraps(Context ctx, Object configuration) {
        Configuration c = (Configuration) Configuration.TYPE_INFO.wrap(ctx, configuration, Configuration.TYPE_INFO);
        return object -> new ConstantChemical(c.stack);
    }

    private final ChemicalStack chemical;

    public ConstantChemical(ChemicalStack chemical) {
        this.chemical = chemical;
    }

    @Override
    public int getChemicalTanks() {
        return 1;
    }

    @Override
    public @NotNull ChemicalStack getChemicalInTank(int tank) {
        return chemical.copy();
    }

    @Override
    public void setChemicalInTank(int tank, @NotNull ChemicalStack stack) {
    }

    @Override
    public long getChemicalTankCapacity(int tank) {
        return chemical.getAmount();
    }

    @Override
    public boolean isValid(int tank, @NotNull ChemicalStack stack) {
        return ChemicalStack.isSameChemical(stack, chemical);
    }

    @Override
    public @NotNull ChemicalStack insertChemical(int tank, @NotNull ChemicalStack stack, @NotNull Action action) {
        return stack;
    }

    @Override
    public @NotNull ChemicalStack extractChemical(int tank, long amount, @NotNull Action action) {
        return chemical.copyWithAmount(Math.min(chemical.getAmount(), amount));
    }
}
