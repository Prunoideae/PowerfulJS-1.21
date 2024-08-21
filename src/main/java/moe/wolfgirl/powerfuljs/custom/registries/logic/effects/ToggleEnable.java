package moe.wolfgirl.powerfuljs.custom.registries.logic.effects;

import moe.wolfgirl.powerfuljs.custom.Attachments;
import moe.wolfgirl.powerfuljs.custom.registries.logic.Effect;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Unit;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ToggleEnable extends Effect {
    @Override
    public void apply(boolean condition, Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (condition) blockEntity.removeData(Attachments.DISABLED);
        else blockEntity.setData(Attachments.DISABLED, Unit.INSTANCE);
    }
}
