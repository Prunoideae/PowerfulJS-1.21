package moe.wolfgirl.powerfuljs.custom.logic.rules.machine;

import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TickRateRule extends Rule {
    private static final Map<Integer, Integer> RATE_INDEXES = new HashMap<>();
    private final int rate;
    private final int offset;

    public TickRateRule(int rate) {
        this.rate = rate;
        int offset = rate == 1 ? 0 : RATE_INDEXES.getOrDefault(rate, 0) + 1 % rate;
        RATE_INDEXES.put(rate, offset);
        this.offset = offset;
    }

    @Override
    public boolean evaluate(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        return level.getGameTime() % rate == this.offset;
    }
}
