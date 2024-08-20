package moe.wolfgirl.powerfuljs.mixin;

import moe.wolfgirl.powerfuljs.GameStates;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public class BlockCapabilityInvalidator {

    @Inject(method = "onRemove", at = @At("RETURN"))
    public void autoInvalidate(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston, CallbackInfo ci) {
        if (GameStates.REGISTERED_BLOCKS.contains(state.getBlock())) level.invalidateCapabilities(pos);
    }
}
