package moe.wolfgirl.powerfuljs.mixin;

import dev.latvian.mods.kubejs.player.StageChangedEvent;
import dev.latvian.mods.kubejs.stages.Stages;
import moe.wolfgirl.powerfuljs.utils.StageUtils;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = StageChangedEvent.class, remap = false)
public class StageUpdateListener {

    @Inject(method = "<init>", at = @At("CTOR_HEAD"))
    private void onCreated(Player player, Stages stages, String stage, CallbackInfo ci) {
        StageUtils.initializePlayer(player);
    }
}
