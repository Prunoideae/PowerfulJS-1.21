package moe.wolfgirl.powerfuljs.utils;

import dev.latvian.mods.kubejs.stages.Stages;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class StageUtils {

    public static class Cache {
        public final Set<String> stages;
        private boolean invalidated = false;

        public Cache(Set<String> stages) {
            this.stages = stages;
        }

        private void invalidate() {
            this.invalidated = true;
        }

        public boolean invalidated() {
            return this.invalidated;
        }
    }

    private static final Map<UUID, Cache> stageCache = new HashMap<>();

    public static Cache get(UUID uuid) {
        return stageCache.computeIfAbsent(uuid, u -> new Cache(Set.of()));
    }

    public static void initializePlayer(Player player) {
        Stages stages = player.kjs$getStages();
        stageCache.computeIfPresent(player.getUUID(), (uuid, cache) -> {
            cache.invalidate();
            return new Cache(new HashSet<>(stages.getAll()));
        });
    }
}
