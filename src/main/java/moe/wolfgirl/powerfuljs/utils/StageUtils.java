package moe.wolfgirl.powerfuljs.utils;

import dev.latvian.mods.kubejs.stages.Stages;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class StageUtils {

    public static class Cache {
        public static final Cache EMPTY = new Cache(Set.of());

        static {
            EMPTY.invalidate();
        }

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

    private static final Map<String, Cache> stageCache = new HashMap<>();

    public static Cache get(UUID uuid) {
        String uuidString = uuid.toString();
        if (stageCache.containsKey(uuidString)) return stageCache.get(uuidString);
        return Cache.EMPTY;
    }

    public static void initializePlayer(Player player) {
        Stages stages = player.kjs$getStages();
        String uuidString = player.getUUID().toString();
        if (stageCache.containsKey(uuidString)) stageCache.get(uuidString).invalidate();
        stageCache.put(uuidString, new Cache(new HashSet<>(stages.getAll())));
    }
}
