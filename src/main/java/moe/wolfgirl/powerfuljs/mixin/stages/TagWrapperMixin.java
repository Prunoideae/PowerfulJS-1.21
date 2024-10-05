package moe.wolfgirl.powerfuljs.mixin.stages;

import dev.latvian.mods.kubejs.stages.TagWrapperStages;
import moe.wolfgirl.powerfuljs.utils.StageUtils;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TagWrapperStages.class, remap = false)
public abstract class TagWrapperMixin {

    @Shadow
    public abstract Player getPlayer();

    @Inject(method = "clear", at = @At("RETURN"))
    private void onClear(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) StageUtils.initializePlayer(getPlayer());
    }
}
