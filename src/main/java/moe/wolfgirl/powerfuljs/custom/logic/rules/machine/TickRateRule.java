package moe.wolfgirl.powerfuljs.custom.logic.rules.machine;

import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class TickRateRule extends Rule {
    private static final Random RANDOM = new Random();
    private final int rate;
    private final int offset;

    public TickRateRule(int rate) {
        this.rate = rate;
        this.offset = RANDOM.nextInt(rate);
    }

    @Override
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        return level.getGameTime() % rate == this.offset;
    }
}
