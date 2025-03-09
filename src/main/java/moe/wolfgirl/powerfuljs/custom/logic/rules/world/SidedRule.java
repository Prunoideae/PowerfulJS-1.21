package moe.wolfgirl.powerfuljs.custom.logic.rules.world;

import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SidedRule extends Rule {

    private final Side side;

    public SidedRule(Side side) {
        this.side = side;
    }

    @Override
    public boolean evaluate(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        return (side == Side.SERVER) != level.isClientSide;
    }

    @SuppressWarnings("unused")
    public enum Side {
        SERVER,
        CLIENT
    }
}
