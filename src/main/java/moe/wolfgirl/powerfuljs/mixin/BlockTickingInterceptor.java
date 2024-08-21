package moe.wolfgirl.powerfuljs.mixin;

import moe.wolfgirl.powerfuljs.GameStates;
import moe.wolfgirl.powerfuljs.custom.Attachments;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
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
            instance.ticker = GameStates.INTERCEPTED_BLOCK_ENTITIES.get(blockEntity.getType()).createTicker(value);
        } else {
            instance.ticker = value;
        }
        /*
        if (blockEntity instanceof FurnaceBlockEntity furnaceBlock) {
            FluidStack lava = new FluidStack(Fluids.LAVA, 1);
            instance.ticker = (level, pos, state, be) -> {
                // TODO: Adapt the ticker to allow JS-defined logic
                IFluidHandler handler = level.getCapability(Capabilities.FluidHandler.BLOCK, pos, state, furnaceBlock, null);
                if (handler != null && state.getValue(BlockStateProperties.LIT)) {
                    if (handler.drain(lava, IFluidHandler.FluidAction.SIMULATE).isEmpty()) {
                        return;
                    } else {
                        handler.drain(1, IFluidHandler.FluidAction.EXECUTE);
                    }
                }

                if (!blockEntity.hasData(Attachments.DISABLED)) value.tick(level, pos, state, be);
            };
        } else {
            instance.ticker = value;
        }*/
    }
}
