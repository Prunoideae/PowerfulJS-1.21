package moe.wolfgirl.powerfuljs.custom.registries.logic.rules.logic;

import moe.wolfgirl.powerfuljs.custom.registries.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AlwaysRule extends Rule {
    private final boolean state;

    public AlwaysRule(boolean state) {
        this.state = state;
    }

    @Override
    public boolean evaluate(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        return this.state;
    }
}
