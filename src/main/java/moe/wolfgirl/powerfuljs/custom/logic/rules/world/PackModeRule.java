package moe.wolfgirl.powerfuljs.custom.logic.rules.world;

import dev.latvian.mods.kubejs.CommonProperties;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PackModeRule extends Rule {
    private final String packMode;

    public PackModeRule(String packMode) {
        this.packMode = packMode;
    }

    @Override
    public boolean evaluate(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        return CommonProperties.get().packMode.equals(packMode);
    }
}
