package moe.wolfgirl.powerfuljs.custom.logic.effects;

import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class CompositeEffect extends Effect {
    private final List<Rule> compositeEffects;

    public CompositeEffect(List<Rule> compositeEffects) {
        this.compositeEffects = compositeEffects;
    }


    @Override
    public void apply(boolean condition, ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (!condition) return;
        for (Rule compositeEffect : compositeEffects) {
            compositeEffect.run(level, pos, state, blockEntity);
        }
    }
}
