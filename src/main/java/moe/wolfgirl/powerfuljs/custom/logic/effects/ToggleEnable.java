package moe.wolfgirl.powerfuljs.custom.logic.effects;

import moe.wolfgirl.powerfuljs.custom.Attachments;
import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ToggleEnable extends Effect {
    @Override
    public void apply(boolean condition, ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (condition && blockEntity.hasData(Attachments.DISABLED)) blockEntity.removeData(Attachments.DISABLED);
        else blockEntity.setData(Attachments.DISABLED, Unit.INSTANCE);
    }
}
