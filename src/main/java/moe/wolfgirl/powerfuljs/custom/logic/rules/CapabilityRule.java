package moe.wolfgirl.powerfuljs.custom.logic.rules;

import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import org.jetbrains.annotations.Nullable;

public abstract class CapabilityRule<T, C> extends Rule {
    private final BlockCapability<T, C> blockCapability;
    @Nullable
    private final C context;

    private BlockCapabilityCache<T, C> cache;

    protected CapabilityRule(BlockCapability<T, C> blockCapability, @Nullable C context) {
        this.blockCapability = blockCapability;
        this.context = context;
    }

    protected abstract boolean evaluateCap(T capability);

    @Override
    public final boolean evaluate(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (level instanceof ServerLevel serverLevel) {
            if (cache == null) {
                cache = BlockCapabilityCache.create(blockCapability, serverLevel, pos, context);
            }
            return evaluateCap(this.cache.getCapability());
        } else {
            var cap = level.getCapability(blockCapability, pos, context);
            return evaluateCap(cap);
        }
    }
}
