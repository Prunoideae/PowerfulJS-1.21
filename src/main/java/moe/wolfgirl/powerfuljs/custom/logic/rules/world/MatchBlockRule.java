package moe.wolfgirl.powerfuljs.custom.logic.rules.world;

import dev.latvian.mods.kubejs.block.state.BlockStatePredicate;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MatchBlockRule extends Rule {
    private final BlockStatePredicate matcher;

    public MatchBlockRule(BlockStatePredicate matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean evaluate(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        return matcher.test(state);
    }
}
