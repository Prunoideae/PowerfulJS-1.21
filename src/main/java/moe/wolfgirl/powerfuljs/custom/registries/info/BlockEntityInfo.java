package moe.wolfgirl.powerfuljs.custom.registries.info;

import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.BaseCapability;

public class BlockEntityInfo extends BlockInfo {

    public BlockEntityInfo(BaseCapability<?, ?> capability, CapabilityBuilder.CapabilityFactory<BlockEntity, ?> factory) {
        super(capability, info -> factory.getCapability(info.blockEntity()));
        validators.add(((info, context) -> info.blockEntity() != null));
    }
}
