package moe.wolfgirl.powerfuljs.custom.logic.effects;

import dev.latvian.mods.kubejs.level.BlockContainerJS;
import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EffectJS extends Effect {
    private final Apply apply;

    public EffectJS(Apply apply) {
        this.apply = apply;
    }

    @FunctionalInterface
    public interface Apply {
        void apply(BlockContainerJS block);
    }

    @Override
    public void apply(boolean condition, ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (condition) this.apply.apply(new BlockContainerJS(blockEntity));
    }
}
