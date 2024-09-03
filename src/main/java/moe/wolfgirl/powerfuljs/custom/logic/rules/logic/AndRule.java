package moe.wolfgirl.powerfuljs.custom.logic.rules.logic;

import dev.latvian.mods.rhino.util.HideFromJS;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Shortcutting Rule that will return on first false, otherwise true.
 */
public class AndRule extends Rule {

    @HideFromJS
    public final List<Rule> rules;

    public AndRule(List<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        for (Rule rule : rules) {
            if (!rule.run(level, pos, state, blockEntity)) return false;
        }
        return true;
    }
}
