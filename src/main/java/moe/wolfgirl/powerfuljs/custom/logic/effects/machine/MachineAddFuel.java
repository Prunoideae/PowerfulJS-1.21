package moe.wolfgirl.powerfuljs.custom.logic.effects.machine;

import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.FuelProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MachineAddFuel extends Effect {
    private final int ticks;

    public MachineAddFuel(int ticks) {
        this.ticks = ticks;
    }

    @Override
    public void apply(boolean condition, Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (condition && blockEntity instanceof FuelProvider provider) {
            provider.pjs$setFuel(provider.pjs$getFuel() + ticks);
        }
    }
}
