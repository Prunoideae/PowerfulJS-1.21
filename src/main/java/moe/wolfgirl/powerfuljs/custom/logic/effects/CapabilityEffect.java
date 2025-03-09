package moe.wolfgirl.powerfuljs.custom.logic.effects;

import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import org.jetbrains.annotations.Nullable;

public abstract class CapabilityEffect<T, C> extends Effect {
    private final BlockCapability<T, C> blockCapability;
    @Nullable
    private final C context;

    private BlockCapabilityCache<T, C> cache;


    protected CapabilityEffect(BlockCapability<T, C> blockCapability, @Nullable C context) {
        this.blockCapability = blockCapability;
        this.context = context;
    }

    protected abstract void runEffect(T cap);

    @Override
    public final void apply(boolean condition, Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (level instanceof ServerLevel serverLevel) {
            if (cache == null) {
                cache = BlockCapabilityCache.create(blockCapability, serverLevel, pos, context);
            }
            if (condition) {
                runEffect(cache.getCapability());
            }
        }
    }
}
