package moe.wolfgirl.powerfuljs.mixin;

import moe.wolfgirl.powerfuljs.GameStates;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.chunk.LevelChunk;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Allows the injection of custom logic, or even pausing the ticking for any
 * block entity.
 */
@Mixin(LevelChunk.BoundTickingBlockEntity.class)
public abstract class BlockTickingInterceptor {

    @Shadow
    @Final
    public BlockEntity blockEntity;

    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/chunk/LevelChunk$BoundTickingBlockEntity;ticker:Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", opcode = Opcodes.PUTFIELD))
    private <T extends BlockEntity> void wrapTicker(LevelChunk.BoundTickingBlockEntity<T> instance, BlockEntityTicker<T> value) {
        if (GameStates.INTERCEPTED_BLOCK_ENTITIES.containsKey(blockEntity.getType())) {
            instance.ticker = GameStates.INTERCEPTED_BLOCK_ENTITIES
                    .get(blockEntity.getType())
                    .createTicker(value);
        } else {
            instance.ticker = value;
        }
        /*
        Every(20tick).then(
            Or(
                Property(LIT=false),
                CanDrain(LAVA, 1).then(Drain(LAVA, 1))
            ).apply(ToggleEnable())
        )
        */
    }
}
