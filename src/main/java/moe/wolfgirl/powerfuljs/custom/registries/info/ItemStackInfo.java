package moe.wolfgirl.powerfuljs.custom.registries.info;

import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityValidator;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.BaseCapability;

public class ItemStackInfo extends CapabilityInfo.Info<ItemStack> {

    public ItemStackInfo(BaseCapability<?, ?> capability, CapabilityBuilder.CapabilityFactory<ItemStack, ?> factory) {
        super(capability, factory);
    }

    public ItemStackInfo validate(CapabilityValidator.ItemValidator validator) {
        validators.add(validator);
        return this;
    }
}
