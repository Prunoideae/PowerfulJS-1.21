package moe.wolfgirl.powerfuljs.custom.logic.rules.world;

import moe.wolfgirl.powerfuljs.GameStates;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DifficultyRule extends Rule {
    private final Difficulty difficulty;

    public DifficultyRule(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        return GameStates.GAME_DIFFICULTY == difficulty;
    }
}
