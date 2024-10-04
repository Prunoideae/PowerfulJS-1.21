package moe.wolfgirl.powerfuljs;

import moe.wolfgirl.powerfuljs.custom.Attachments;
import moe.wolfgirl.powerfuljs.utils.StageUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber
public class GameEvents {

    @SubscribeEvent
    public static void assignOwner(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof Player player) {
            BlockEntity blockEntity = event.getLevel().getBlockEntity(event.getPos());
            if (blockEntity == null || !GameStates.INTERCEPTED_BLOCK_ENTITIES.containsKey(blockEntity.getType())) {
                return;
            }
            blockEntity.setData(Attachments.OWNER, player.getUUID());
        }
    }

    @SubscribeEvent
    public static void updatePlayerStages(PlayerEvent.PlayerLoggedInEvent event) {
        StageUtils.initializePlayer(event.getEntity());
    }
}
