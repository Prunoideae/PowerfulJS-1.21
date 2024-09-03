package moe.wolfgirl.powerfuljs.custom.logic.effects;

import moe.wolfgirl.powerfuljs.custom.Attachments;
import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TickRate extends Effect {
    private final float tickRate;

    public TickRate(float tickRate) {
        this.tickRate = tickRate;
    }

    @Override
    public void apply(boolean condition, ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (condition) {
            blockEntity.setData(Attachments.TICK_SPEED, tickRate);
        }
    }
}
