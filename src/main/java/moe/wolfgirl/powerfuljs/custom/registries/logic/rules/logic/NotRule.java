package moe.wolfgirl.powerfuljs.custom.registries.logic.rules.logic;

import moe.wolfgirl.powerfuljs.custom.registries.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NotRule extends Rule {
    private final Rule inner;

    public NotRule(Rule inner) {
        this.inner = inner;
    }

    @Override
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        return !inner.evaluate(level, pos, state, blockEntity);
    }
}
