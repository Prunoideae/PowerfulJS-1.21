package moe.wolfgirl.powerfuljs.mixin;

import moe.wolfgirl.powerfuljs.GameStates;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockTickerInterceptor {

    @Inject(method = "getTicker", cancellable = true, at = @At("RETURN"))
    public <T extends BlockEntity> void forceTicker(Level level, BlockEntityType<T> blockEntityType, CallbackInfoReturnable<BlockEntityTicker<T>> cir) {
        if (level instanceof ServerLevel && GameStates.TICK_MODIFIED_BLOCK_ENTITIES.containsKey(blockEntityType)) {
            if (GameStates.TICK_MODIFIED_BLOCK_ENTITIES.get(blockEntityType) > 0) {
                if (cir.getReturnValue() == null) {
                    // Create an empty ticker here for non-tick-able entities
                    // otherwise just return the original logic
                    cir.setReturnValue((level1, pos, state, blockEntity) -> {
                    });
                }
            } else {
                cir.setReturnValue(null);
            }
        }
    }
}
