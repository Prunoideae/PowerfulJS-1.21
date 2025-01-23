package moe.wolfgirl.powerfuljs.mixin;

import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerLevel.class)
@RemapPrefixForJS("pjs$")
public abstract class LevelMixin {

    @Unique
    private ServerLevel pjs$self() {
        return (ServerLevel) (Object) this;
    }

    @Unique
    public <T, C> BlockCapabilityCache<T, C> pjs$getCachedCapability(BlockCapability<T, C> blockCapability, BlockPos blockPos, C context) {
        return BlockCapabilityCache.create(blockCapability, pjs$self(), blockPos, context);
    }
}
