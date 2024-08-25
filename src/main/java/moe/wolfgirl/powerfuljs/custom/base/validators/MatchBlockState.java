package moe.wolfgirl.powerfuljs.custom.base.validators;

import dev.latvian.mods.kubejs.block.state.BlockStatePredicate;
import moe.wolfgirl.powerfuljs.custom.base.CapabilityValidator;
import moe.wolfgirl.powerfuljs.custom.base.info.BlockContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public class MatchBlockState implements CapabilityValidator.BlockValidator {
    private final BlockStatePredicate blockStatePredicate;

    public MatchBlockState(BlockStatePredicate blockStatePredicate) {
        this.blockStatePredicate = blockStatePredicate;
    }

    @Override
    public boolean test(BlockContext info, @Nullable Object context) {
        return blockStatePredicate.test(info.state());
    }
}
