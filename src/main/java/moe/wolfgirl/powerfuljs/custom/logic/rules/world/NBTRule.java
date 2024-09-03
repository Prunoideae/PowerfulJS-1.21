package moe.wolfgirl.powerfuljs.custom.logic.rules.world;

import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NBTRule extends Rule {
    private final CompoundTag compoundTag;

    public NBTRule(CompoundTag compoundTag) {
        this.compoundTag = compoundTag;
    }

    @Override
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        var entityData = blockEntity.saveWithoutMetadata(level.registryAccess());
        return entityData.copy().merge(compoundTag).equals(entityData);
    }
}
