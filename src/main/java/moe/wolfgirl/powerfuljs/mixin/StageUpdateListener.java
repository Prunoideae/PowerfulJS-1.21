package moe.wolfgirl.powerfuljs.mixin;

import dev.latvian.mods.kubejs.stages.Stages;
import moe.wolfgirl.powerfuljs.utils.StageUtils;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Stages.class, remap = false)
public interface StageUpdateListener {

    @Shadow
    Player getPlayer();

    @Inject(method = "add", at = @At("RETURN"))
    private void onAdded(String stage, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            StageUtils.initializePlayer(getPlayer());
        }
    }

    @Inject(method = "remove", at = @At("RETURN"))
    private void onRemoved(String stage, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            StageUtils.initializePlayer(getPlayer());
        }
    }
}
