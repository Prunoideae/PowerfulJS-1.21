package moe.wolfgirl.powerfuljs.custom.logic;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class Effect {
    public abstract void apply(boolean condition, Level level, BlockPos pos, BlockState state, BlockEntity blockEntity);
}
