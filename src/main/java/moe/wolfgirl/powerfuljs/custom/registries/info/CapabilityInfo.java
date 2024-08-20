package moe.wolfgirl.powerfuljs.custom.registries.info;

import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityValidator;
import moe.wolfgirl.powerfuljs.custom.base.validators.MatchDirection;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class CapabilityInfo<I> {
    public final BaseCapability<?, ?> capability;
    protected final CapabilityBuilder.CapabilityFactory<I, ?> factory;
    protected final List<CapabilityValidator<I>> validators = new ArrayList<>();

    public CapabilityInfo(BaseCapability<?, ?> capability, CapabilityBuilder.CapabilityFactory<I, ?> factory) {
        this.capability = capability;
        this.factory = factory;
    }

    protected static class Info<I> extends CapabilityInfo<I> {
        public Info(BaseCapability<?, ?> capability, CapabilityBuilder.CapabilityFactory<I, ?> factory) {
            super(capability, factory);
        }

        public ICapabilityProvider<I, ?, ?> build() {
            return (object, context) -> {
                for (CapabilityValidator<I> validator : validators) {
                    if (!validator.test(object, context)) {
                        return null;
                    }
                }
                return factory.getCapability(object);
            };
        }
    }

    public CapabilityInfo<I> matchDirection(Direction... directions) {
        validators.add(new MatchDirection<>(directions));
        return this;
    }
}
