package moe.wolfgirl.powerfuljs.custom.logic.rules.logic;

import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AlwaysRule extends Rule {
    private final boolean state;

    public AlwaysRule(boolean state) {
        this.state = state;
    }

    @Override
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        return this.state;
    }
}
