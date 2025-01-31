package moe.wolfgirl.powerfuljs.mixin;

import moe.wolfgirl.powerfuljs.custom.Attachments;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Level.class)
public abstract class LevelMixin {

    @Unique
    private Level pjs$self() {
        return (Level) (Object) this;
    }

    @Unique
    public <T, C> BlockCapabilityCache<T, C> kjs$getCachedCapability(BlockCapability<T, C> blockCapability, BlockPos blockPos, C context) {
        if (pjs$self() instanceof ServerLevel serverLevel) {
            return BlockCapabilityCache.create(blockCapability, serverLevel, blockPos, context);
        } else {
            return null;
        }
    }

    @Unique
    public CompoundTag kjs$getBlockData(BlockPos blockPos) {
        return pjs$self().getChunkAt(blockPos)
                .getData(Attachments.CHUNK_DATA)
                .data()
                .getOrDefault(blockPos, new CompoundTag());
    }

    @Unique
    public void kjs$setBlockData(BlockPos blockPos, CompoundTag compoundTag) {
        LevelChunk levelChunk = pjs$self().getChunkAt(blockPos);
        var data = levelChunk.getData(Attachments.CHUNK_DATA);

        if (compoundTag.isEmpty()) {
            data.data().remove(blockPos);
        } else {
            data.data().put(blockPos, compoundTag);
        }
        levelChunk.setData(Attachments.CHUNK_DATA, data);
    }
}
