package moe.wolfgirl.powerfuljs.mixin.create;

import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MechanicalMixerBlockEntity.class)
public class MechanicalMixerMixin implements ProgressProvider {
    @Shadow
    public boolean running;

    @Shadow
    public int processingTicks;

    @Override
    public int pjs$getProgress() {
        return 0;
    }

    @Override
    public int pjs$getMaxProgress() {
        return processingTicks;
    }

    @Override
    public void pjs$setProgress(int progress) {
        processingTicks = Math.max(1, processingTicks - progress);
    }

    @Override
    public boolean pjs$running() {
        return running;
    }
}
