package moe.wolfgirl.powerfuljs.custom.logic.rules;

import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class Chanced extends Rule {
    private final double chance;

    public Chanced(double chance) {
        this.chance = chance;
    }

    @Override
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        return level.random.nextDouble() <= chance;
    }
}
