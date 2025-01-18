package moe.wolfgirl.powerfuljs.mixin;

import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import moe.wolfgirl.powerfuljs.custom.Attachments;
import net.minecraft.util.Unit;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockEntity.class)
@RemapPrefixForJS("pjs$")
public class BlockEntityMixin {

    @Unique
    private BlockEntity pjs$self() {
        return (BlockEntity) (Object) this;
    }

    @Unique
    public void pjs$setTickingEnabled(boolean enabled) {
        if (enabled) {
            pjs$self().removeData(Attachments.DISABLED);
        } else {
            pjs$self().setData(Attachments.DISABLED, Unit.INSTANCE);
        }
    }

    @Unique
    public boolean pjs$isTickingEnabled() {
        return !pjs$self().hasData(Attachments.DISABLED);
    }

    @Unique
    public void pjs$setTickingSpeed(float multiplier) {
        if (multiplier < 0) throw new RuntimeException("Ticking speed multiplier must not less than 0!");
        pjs$self().setData(Attachments.TICK_SPEED, multiplier);
    }

    @Unique
    public float pjs$getTickingSpeed() {
        return pjs$self().getData(Attachments.TICK_SPEED);
    }
}
