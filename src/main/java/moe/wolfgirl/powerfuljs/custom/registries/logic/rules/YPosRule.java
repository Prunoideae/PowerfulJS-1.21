package moe.wolfgirl.powerfuljs.custom.registries.logic.rules;

import moe.wolfgirl.powerfuljs.custom.registries.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class YPosRule extends Rule {
    private final int higherThan;

    public YPosRule(int higherThan) {
        this.higherThan = higherThan;
    }

    @Override
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        return pos.getY() > higherThan;
    }
}
