package moe.wolfgirl.powerfuljs.custom.logic.effects.machine;

import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FurnaceFuel extends Effect {
    private final int ticks;

    public FurnaceFuel(int ticks) {
        if (ticks <= 0) throw new RuntimeException("Must have fuel tick > 0!");
        this.ticks = ticks;
    }

    @Override
    public void apply(boolean condition, ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (condition && blockEntity instanceof AbstractFurnaceBlockEntity furnaceBlockEntity) {
            if (furnaceBlockEntity.litTime == 0) level.setBlock(pos, state.setValue(AbstractFurnaceBlock.LIT, true), 3);
            furnaceBlockEntity.litTime += ticks;
            if (furnaceBlockEntity.litTime == 1) furnaceBlockEntity.litTime++;
            if (furnaceBlockEntity.litDuration == 0) furnaceBlockEntity.litDuration = furnaceBlockEntity.litTime;
        }
    }
}
