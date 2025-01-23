package moe.wolfgirl.powerfuljs;

import moe.wolfgirl.powerfuljs.custom.Attachments;
import moe.wolfgirl.powerfuljs.utils.StageUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber
public class GameEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void assignOwner(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof Player player) {
            BlockEntity blockEntity = event.getLevel().getBlockEntity(event.getPos());
            if (blockEntity == null || !GameStates.INTERCEPTED_BLOCK_ENTITIES.containsKey(blockEntity.getType())) {
                return;
            }
            blockEntity.setData(Attachments.OWNER, player.getUUID());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void assignOwnerMulti(BlockEvent.EntityMultiPlaceEvent event) {
        if (event.getEntity() instanceof Player player) {
            LevelAccessor level = event.getLevel();
            for (BlockSnapshot replacedBlockSnapshot : event.getReplacedBlockSnapshots()) {
                BlockEntity blockEntity = level.getBlockEntity(replacedBlockSnapshot.getPos());
                if (blockEntity != null && GameStates.INTERCEPTED_BLOCK_ENTITIES.containsKey(blockEntity.getType())) {
                    blockEntity.setData(Attachments.OWNER, player.getUUID());
                }
            }
        }
    }

    @SubscribeEvent
    public static void updatePlayerStages(PlayerEvent.PlayerLoggedInEvent event) {
        StageUtils.initializePlayer(event.getEntity());
    }
}
