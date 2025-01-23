package moe.wolfgirl.powerfuljs.mixin;

import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.util.RemapForJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import moe.wolfgirl.powerfuljs.GameStates;
import moe.wolfgirl.powerfuljs.custom.Attachments;
import moe.wolfgirl.powerfuljs.serde.TickModifiers;
import net.minecraft.util.Unit;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockEntity.class)
@RemapPrefixForJS("pjs$")
public abstract class BlockEntityMixin {

    @RemapForJS("setDataRaw")
    @Shadow
    public abstract <T> T setData(AttachmentType<T> type, T data);

    @Unique
    @SuppressWarnings("unchecked")
    public <T> T pjs$setData(Context context, AttachmentType<T> type, Object data) {
        if (GameStates.ATTACHMENT_TYPE_INFO.containsKey(type)) {
            data = context.jsToJava(data, GameStates.ATTACHMENT_TYPE_INFO.get(type));
        }
        return setData(type, (T) data);
    }

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
    public void pjs$addTickModifier(TickModifiers.TickModifier multiplier) {
        TickModifiers tickModifiers = pjs$self().getData(Attachments.TICK_SPEED);
        if (!tickModifiers.hasModifier(multiplier.id())) {
            pjs$self().setData(Attachments.TICK_SPEED, tickModifiers.withModifier(multiplier));
        }
    }

    @Unique
    public void pjs$removeTickModifier(String id) {
        TickModifiers tickModifiers = pjs$self().getData(Attachments.TICK_SPEED);
        if (tickModifiers.hasModifier(id)) {
            pjs$self().setData(Attachments.TICK_SPEED, tickModifiers.removeModifier(id));
        }
    }

    @Unique
    public TickModifiers pjs$getTickModifiers() {
        return pjs$self().getData(Attachments.TICK_SPEED);
    }

    @Unique
    public float pjs$getTickingSpeed() {
        return pjs$self().getData(Attachments.TICK_SPEED).getTickSpeed();
    }
}
