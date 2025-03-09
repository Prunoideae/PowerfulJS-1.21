package moe.wolfgirl.powerfuljs.custom.logic.rules.logic;

import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NotRule extends Rule {
    private final Rule inner;

    public NotRule(Rule inner) {
        this.inner = inner;
    }

    @Override
    public boolean evaluate(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        return !inner.run(level, pos, state, blockEntity);
    }
}
