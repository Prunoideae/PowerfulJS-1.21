package moe.wolfgirl.powerfuljs.custom.logic.rules.machine;

import moe.wolfgirl.powerfuljs.custom.Attachments;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import moe.wolfgirl.powerfuljs.utils.StageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class StageRule extends Rule {
    private final String stage;
    private StageUtils.Cache cache = null;
    private boolean present = false;

    public StageRule(String stage) {
        this.stage = stage;
    }

    @Override
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (cache == null || cache.invalidated()) {
            UUID uuid = blockEntity.getData(Attachments.OWNER);
            cache = StageUtils.get(uuid);
            present = cache.stages.contains(stage);
        }
        return present;
    }
}
