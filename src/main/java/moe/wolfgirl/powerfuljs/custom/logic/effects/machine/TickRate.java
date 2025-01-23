package moe.wolfgirl.powerfuljs.custom.logic.effects.machine;

import moe.wolfgirl.powerfuljs.custom.Attachments;
import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import moe.wolfgirl.powerfuljs.serde.TickModifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TickRate extends Effect {
    private final TickModifiers.TickModifier tickRate;

    public TickRate(TickModifiers.TickModifier modifier) {
        this.tickRate = modifier;
    }

    @Override
    public void apply(boolean condition, ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        TickModifiers tickModifiers = blockEntity.getData(Attachments.TICK_SPEED);
        if (condition) {
            if (!tickModifiers.hasModifier(tickRate.id())) {
                blockEntity.setData(Attachments.TICK_SPEED, tickModifiers.withModifier(tickRate));
            }
        } else {
            if (tickModifiers.hasModifier(tickRate.id())) {
                blockEntity.setData(Attachments.TICK_SPEED, tickModifiers.removeModifier(tickRate.id()));
            }
        }
    }
}
