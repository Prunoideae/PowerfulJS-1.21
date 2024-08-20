package moe.wolfgirl.powerfuljs.custom.registries.info;

import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityValidator;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.capabilities.BaseCapability;

public class EntityInfo extends CapabilityInfo.Info<Entity> {

    public EntityInfo(BaseCapability<?, ?> capability, CapabilityBuilder.CapabilityFactory<Entity, ?> factory) {
        super(capability, factory);
    }

    public EntityInfo validate(CapabilityValidator.EntityValidator validator) {
        validators.add(validator);
        return this;
    }
}
