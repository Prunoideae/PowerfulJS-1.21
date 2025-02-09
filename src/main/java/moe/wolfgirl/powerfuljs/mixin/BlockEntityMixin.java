package moe.wolfgirl.powerfuljs.mixin;

import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.util.RemapForJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import moe.wolfgirl.powerfuljs.GameStates;
import moe.wolfgirl.powerfuljs.custom.Attachments;
import moe.wolfgirl.powerfuljs.serde.SpeedModifiers;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.UUID;

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
    public void pjs$addTickModifier(SpeedModifiers.SpeedModifier multiplier) {
        SpeedModifiers speedModifiers = pjs$self().getData(Attachments.TICK_SPEED);
        if (!speedModifiers.hasModifier(multiplier.id())) {
            pjs$self().setData(Attachments.TICK_SPEED, speedModifiers.withModifier(multiplier));
        }
    }

    @Unique
    public void pjs$removeTickModifier(String id) {
        SpeedModifiers speedModifiers = pjs$self().getData(Attachments.TICK_SPEED);
        if (speedModifiers.hasModifier(id)) {
            pjs$self().setData(Attachments.TICK_SPEED, speedModifiers.removeModifier(id));
        }
    }

    @Unique
    public SpeedModifiers pjs$getTickModifiers() {
        return pjs$self().getData(Attachments.TICK_SPEED);
    }

    @Unique
    public float pjs$getTickingSpeed() {
        return pjs$self().getData(Attachments.TICK_SPEED).getTickSpeed();
    }

    @Unique
    @Nullable
    public UUID pjs$getOwnerUUID() {
        return pjs$self().getExistingData(Attachments.OWNER).orElse(null);
    }

    @Unique
    @Nullable
    public Player pjs$getOwner() {
        UUID ownerUuid = pjs$getOwnerUUID();
        if (ownerUuid == null) return null;
        Level level = pjs$self().getLevel();
        if (level == null) return null;
        return level.getPlayerByUUID(ownerUuid);
    }
}
