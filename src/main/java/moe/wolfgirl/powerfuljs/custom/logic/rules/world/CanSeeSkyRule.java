package moe.wolfgirl.powerfuljs.custom.logic.rules.world;

import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CanSeeSkyRule extends Rule {
    @Override
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        return level.canSeeSky(pos);
    }
}
