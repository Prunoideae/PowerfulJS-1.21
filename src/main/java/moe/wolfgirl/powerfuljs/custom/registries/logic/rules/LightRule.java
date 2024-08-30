package moe.wolfgirl.powerfuljs.custom.registries.logic.rules;

import moe.wolfgirl.powerfuljs.custom.registries.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LightRule extends Rule {
    private final int brighterThan;

    public LightRule(int brighterThan) {
        this.brighterThan = brighterThan;
    }

    @Override
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        return level.getBrightness(LightLayer.BLOCK, pos) > brighterThan;
    }
}
