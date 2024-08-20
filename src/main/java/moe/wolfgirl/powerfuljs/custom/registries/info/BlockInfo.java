package moe.wolfgirl.powerfuljs.custom.registries.info;

import dev.latvian.mods.kubejs.block.state.BlockStatePredicate;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityValidator;
import moe.wolfgirl.powerfuljs.custom.base.info.BlockContext;
import moe.wolfgirl.powerfuljs.custom.base.validators.MatchBlockState;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.capabilities.IBlockCapabilityProvider;

public class BlockInfo extends CapabilityInfo<BlockContext> {
    public BlockInfo(BaseCapability<?, ?> capability, CapabilityBuilder.CapabilityFactory<BlockContext, ?> factory) {
        super(capability, factory);
    }

    public IBlockCapabilityProvider<?, ?> build() {
        return (level, pos, state, blockEntity, context) -> {
            var info = new BlockContext(level, pos, state, blockEntity);
            for (CapabilityValidator<BlockContext> validator : validators) {
                if (!validator.test(info, context)) {
                    return null;
                }
            }
            return factory.getCapability(info);
        };
    }

    public BlockInfo validate(CapabilityValidator.BlockValidator validator) {
        validators.add(validator);
        return this;
    }

    public BlockInfo matchState(BlockStatePredicate predicate) {
        return validate(new MatchBlockState(predicate));
    }
}
