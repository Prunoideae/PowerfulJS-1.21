package moe.wolfgirl.powerfuljs.custom.mods.create.logic;

import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import moe.wolfgirl.powerfuljs.custom.mods.create.KineticModifier;
import moe.wolfgirl.powerfuljs.serde.SpeedModifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModifyCapacityEffect extends Effect {
    private final SpeedModifiers.SpeedModifier modifier;

    public ModifyCapacityEffect(SpeedModifiers.SpeedModifier modifier) {
        this.modifier = modifier;
    }

    @Override
    public void apply(boolean condition, Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (blockEntity instanceof KineticModifier kineticModifier) {
            if (condition) {
                kineticModifier.pjs$addCapacityModifier(modifier);
            } else {
                kineticModifier.pjs$removeCapacityModifier(modifier.id());
            }
        }
    }
}
