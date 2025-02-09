package moe.wolfgirl.powerfuljs.mixin.minecraft;

import dev.latvian.mods.kubejs.util.Cast;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SpawnerBlockEntity.class)
public class SpawnerMixin implements ProgressProvider {
    @Shadow
    @Final
    private BaseSpawner spawner;

    @Unique
    private BlockEntity pjs$self() {
        return Cast.to(this);
    }

    @Override
    public int pjs$getProgress() {
        return 0;
    }

    @Override
    public int pjs$getMaxProgress() {
        return spawner.spawnDelay;
    }

    @Override
    public void pjs$setProgress(int progress) {
        spawner.spawnDelay = Math.max(0, pjs$getMaxProgress() - progress);
    }

    @Override
    public boolean pjs$running() {
        BlockEntity self = pjs$self();
        Level level = self.getLevel();
        if (level == null) return false;
        BlockPos pos = self.getBlockPos();
        return level.hasNearbyAlivePlayer(
                pos.getX(), pos.getY(), pos.getZ(), 16
        );
    }

    @Override
    public void pjs$addProgress(int progress) {
        spawner.spawnDelay = Math.max(0, spawner.spawnDelay - progress);
    }


    @Override
    public void pjs$clearProgress() {
        Level level = pjs$self().getLevel();
        if (level == null) return;
        spawner.spawnDelay = spawner.minSpawnDelay + level.random.nextInt(spawner.maxSpawnDelay);
    }
}
