package moe.wolfgirl.powerfuljs.custom.forge_energy;

import dev.latvian.mods.rhino.BaseFunction;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.ScriptRuntime;
import dev.latvian.mods.rhino.type.JSObjectTypeInfo;
import dev.latvian.mods.rhino.type.JSOptionalParam;
import dev.latvian.mods.rhino.type.TypeInfo;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.base.info.BlockContext;
import moe.wolfgirl.powerfuljs.utils.MCID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.Map;

/**
 * An energy storage that delegates all the calls to JS. Note that this is very
 * performance intensive considering how slow JS is.
 */
@SuppressWarnings("unchecked")
public class Delegated implements IEnergyStorage {
    public static final ResourceLocation ID = MCID.create("delegated_fe");

    public static final CapabilityBuilder<BlockContext, IEnergyStorage> BLOCK = CapabilityBuilder.create(
            ID, Capabilities.EnergyStorage.BLOCK,
            createTypeInfo(BlockContext.class), Delegated::wraps
    );

    public static final CapabilityBuilder<Entity, IEnergyStorage> ENTITY = CapabilityBuilder.create(
            ID, Capabilities.EnergyStorage.ENTITY,
            createTypeInfo(Entity.class), Delegated::wraps
    );

    public static final CapabilityBuilder<ItemStack, IEnergyStorage> ITEM = CapabilityBuilder.create(
            ID, Capabilities.EnergyStorage.ITEM,
            createTypeInfo(ItemStack.class), Delegated::wraps
    );

    @FunctionalInterface
    public interface EnergyIO<O> {
        TypeInfo TYPE_INFO = TypeInfo.of(EnergyIO.class);

        int transfer(O context, int amount, boolean simulate);
    }

    @FunctionalInterface
    public interface EnergyCheck<O> {
        TypeInfo TYPE_INFO = TypeInfo.of(EnergyTest.class);

        int check(O context);
    }

    @FunctionalInterface
    public interface EnergyTest<O> {
        TypeInfo TYPE_INFO = TypeInfo.of(EnergyTest.class);

        boolean test(O context);
    }

    public static TypeInfo createTypeInfo(Class<?> contextType) {
        var ioType = TypeInfo.of(EnergyIO.class)
                .withParams(TypeInfo.of(contextType))
                .or(TypeInfo.INT);
        var checkType = TypeInfo.of(EnergyCheck.class)
                .withParams(TypeInfo.of(contextType))
                .or(TypeInfo.INT);
        var testType = TypeInfo.of(EnergyTest.class)
                .withParams(TypeInfo.of(contextType))
                .or(TypeInfo.BOOLEAN);

        return JSObjectTypeInfo.of(
                new JSOptionalParam("receiveEnergy", ioType, true),
                new JSOptionalParam("extractEnergy", ioType, true),
                new JSOptionalParam("getEnergyStored", checkType, true),
                new JSOptionalParam("getMaxEnergyStored", checkType, true),
                new JSOptionalParam("canExtract", testType, true),
                new JSOptionalParam("canReceive", testType, true)
        );
    }

    public static <O> CapabilityBuilder.CapabilityFactory<O, IEnergyStorage> wraps(Context ctx, Map<String, Object> configuration) {
        var v1 = createIO(ctx, configuration.get("receiveEnergy"));
        var v2 = createIO(ctx, configuration.get("extractEnergy"));
        var v3 = createCheck(ctx, configuration.get("getEnergyStored"));
        var v4 = createCheck(ctx, configuration.get("getMaxEnergyStored"));
        var v5 = createTest(ctx, configuration.get("canExtract"));
        var v6 = createTest(ctx, configuration.get("canReceive"));
        return object -> new Delegated(object, v1, v2, v3, v4, v5, v6);
    }

    private static EnergyIO<Object> createIO(Context ctx, Object value) {
        return switch (value) {
            case BaseFunction func -> (EnergyIO<Object>) ctx.createInterfaceAdapter(EnergyIO.TYPE_INFO, func);
            case null -> (context, amount, simulate) -> 0;
            default -> {
                var amount = ScriptRuntime.toInt32(ctx, value);
                yield (c, a, s) -> Math.min(amount, a);
            }
        };
    }

    private static EnergyCheck<Object> createCheck(Context ctx, Object value) {
        return switch (value) {
            case BaseFunction func -> (EnergyCheck<Object>) ctx.createInterfaceAdapter(EnergyCheck.TYPE_INFO, func);
            case null -> (context) -> 0;
            default -> {
                var amount = ScriptRuntime.toInt32(ctx, value);
                yield (c) -> amount;
            }
        };
    }

    private static EnergyTest<Object> createTest(Context ctx, Object value) {
        return switch (value) {
            case BaseFunction func -> (EnergyTest<Object>) ctx.createInterfaceAdapter(EnergyTest.TYPE_INFO, func);
            case null -> (context) -> true;
            default -> {
                var result = ScriptRuntime.toBoolean(ctx, value);
                yield (c) -> result;
            }
        };
    }

    protected Delegated(Object parent,
                        EnergyIO<Object> receiveEnergy, EnergyIO<Object> extractEnergy,
                        EnergyCheck<Object> getEnergyStored, EnergyCheck<Object> getMaxEnergyStored,
                        EnergyTest<Object> canExtract, EnergyTest<Object> canReceive
    ) {
        this.parent = parent;
        this.receiveEnergy = receiveEnergy;
        this.extractEnergy = extractEnergy;
        this.getEnergyStored = getEnergyStored;
        this.getMaxEnergyStored = getMaxEnergyStored;
        this.canExtract = canExtract;
        this.canReceive = canReceive;
    }

    private final Object parent;
    private final EnergyIO<Object> receiveEnergy;
    private final EnergyIO<Object> extractEnergy;
    private final EnergyCheck<Object> getEnergyStored;
    private final EnergyCheck<Object> getMaxEnergyStored;
    private final EnergyTest<Object> canExtract;
    private final EnergyTest<Object> canReceive;

    @Override
    public int receiveEnergy(int toReceive, boolean simulate) {
        if (!canReceive() || toReceive <= 0) return 0;
        return receiveEnergy.transfer(parent, toReceive, simulate);
    }

    @Override
    public int extractEnergy(int toExtract, boolean simulate) {
        if (!canExtract() || toExtract <= 0) return 0;
        return extractEnergy.transfer(parent, toExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return getEnergyStored.check(parent);
    }

    @Override
    public int getMaxEnergyStored() {
        return getMaxEnergyStored.check(parent);
    }

    @Override
    public boolean canExtract() {
        return canExtract.test(parent);
    }

    @Override
    public boolean canReceive() {
        return canReceive.test(parent);
    }
}
