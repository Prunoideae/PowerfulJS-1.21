package moe.wolfgirl.powerfuljs.custom.logic.rules.world;

import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TimeRule extends Rule {

    private final int startTime;
    private final int endTime;

    public TimeRule(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        long time = level.getDayTime() % 24000;

        if (startTime < endTime) {
            return startTime < time && time < endTime;
        } else {
            return startTime < time || time < endTime;
        }
    }
}
