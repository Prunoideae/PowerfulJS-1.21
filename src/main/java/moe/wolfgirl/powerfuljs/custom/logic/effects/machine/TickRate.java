package moe.wolfgirl.powerfuljs.custom.logic.effects.machine;

import moe.wolfgirl.powerfuljs.custom.Attachments;
import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import moe.wolfgirl.powerfuljs.serde.SpeedModifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TickRate extends Effect {
    private final SpeedModifiers.SpeedModifier tickRate;

    public TickRate(SpeedModifiers.SpeedModifier modifier) {
        this.tickRate = modifier;
    }

    @Override
    public void apply(boolean condition, Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        SpeedModifiers speedModifiers = blockEntity.getData(Attachments.TICK_SPEED);
        if (condition) {
            if (!speedModifiers.hasModifier(tickRate.id())) {
                blockEntity.setData(Attachments.TICK_SPEED, speedModifiers.withModifier(tickRate));
            }
        } else {
            if (speedModifiers.hasModifier(tickRate.id())) {
                blockEntity.setData(Attachments.TICK_SPEED, speedModifiers.removeModifier(tickRate.id()));
            }
        }
    }
}
