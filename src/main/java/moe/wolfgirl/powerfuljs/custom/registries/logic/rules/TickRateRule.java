package moe.wolfgirl.powerfuljs.custom.registries.logic.rules;

import moe.wolfgirl.powerfuljs.custom.registries.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TickRateRule extends Rule {
    private final int rate;

    public TickRateRule(int rate) {
        this.rate = rate;
    }

    @Override
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        return level.getGameTime() % rate == 0;
    }
}
