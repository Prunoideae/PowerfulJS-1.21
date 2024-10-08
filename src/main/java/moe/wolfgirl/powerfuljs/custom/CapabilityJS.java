package moe.wolfgirl.powerfuljs.custom;

import moe.wolfgirl.powerfuljs.utils.MCID;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Since we don't know what capabilities will be available at anytime we
 * want to query it, it would be a better idea to explicitly declare
 * capabilities for each type.
 */
public class CapabilityJS<B extends BaseCapability<?, ?>> {
    public static final CapabilityJS<BlockCapability<?, ?>> BLOCK = new CapabilityJS<>(BlockCapability.class);
    public static final CapabilityJS<ItemCapability<?, ?>> ITEM = new CapabilityJS<>(ItemCapability.class);
    public static final CapabilityJS<EntityCapability<?, ?>> ENTITY = new CapabilityJS<>(EntityCapability.class);

    private final Map<ResourceLocation, B> registry = new HashMap<>();
    private final Class<?> baseClass;

    public CapabilityJS(Class<?> baseClass) {
        this.baseClass = baseClass;
    }

    public void register(B capability) {
        registry.put(capability.name(), capability);
    }

    @SuppressWarnings("unchecked")
    public B wrap(Object o) {
        if (baseClass.isInstance(o)) return (B) o;

        ResourceLocation rl = MCID.of(o, "neoforge");
        B cap = registry.get(rl);
        if (cap == null) throw new IllegalArgumentException("Could not found capability with key %s!".formatted(rl));
        return cap;
    }

    public Stream<ResourceLocation> getKeys() {
        return registry.keySet().stream();
    }

    public Stream<B> getCapabilities() {
        return registry.values().stream();
    }

    public static void init() {
        for (BlockCapability<?, ?> blockCapability : BlockCapability.getAll()) {
            BLOCK.register(blockCapability);
        }

        for (ItemCapability<?, ?> itemCapability : ItemCapability.getAll()) {
            ITEM.register(itemCapability);
        }

        for (EntityCapability<?, ?> entityCapability : EntityCapability.getAll()) {
            ENTITY.register(entityCapability);
        }
    }
}
