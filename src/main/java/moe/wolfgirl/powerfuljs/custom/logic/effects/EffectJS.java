package moe.wolfgirl.powerfuljs.custom.logic.effects;


import dev.latvian.mods.kubejs.level.LevelBlock;
import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EffectJS extends Effect {
    private final Apply apply;

    public EffectJS(Apply apply) {
        this.apply = apply;
    }

    @FunctionalInterface
    public interface Apply {
        void apply(LevelBlock block);
    }

    @Override
    public void apply(boolean condition, Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (condition) this.apply.apply(level.kjs$getBlock(blockEntity));
    }
}
