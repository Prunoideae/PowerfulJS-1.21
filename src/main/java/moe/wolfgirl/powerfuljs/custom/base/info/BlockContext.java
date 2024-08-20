package moe.wolfgirl.powerfuljs.custom.base.info;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public record BlockContext(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {

}
