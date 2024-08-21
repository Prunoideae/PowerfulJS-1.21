package moe.wolfgirl.powerfuljs.custom.registries.logic.rules.logic;

import dev.latvian.mods.rhino.util.HideFromJS;
import moe.wolfgirl.powerfuljs.custom.registries.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class OrRule extends Rule {
    @HideFromJS
    public final List<Rule> rules;

    public OrRule(List<Rule> rules) {
        this.rules = rules;
    }


    @Override
    public boolean evaluate(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        for (Rule rule : rules) {
            if (rule.run(level, pos, state, blockEntity)) return true;
        }
        return false;
    }
}
