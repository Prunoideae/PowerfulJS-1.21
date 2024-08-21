package moe.wolfgirl.powerfuljs.custom.registries.logic;

import com.google.common.collect.ImmutableList;
import moe.wolfgirl.powerfuljs.custom.Attachments;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.logic.AlwaysRule;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.logic.AndRule;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.logic.NotRule;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.logic.OrRule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public abstract class Rule {
    private final List<Effect> effects = new ArrayList<>();

    public Rule effect(Effect effect) {
        effects.add(effect);
        return this;
    }

    public Rule effects(Effect... effects) {
        this.effects.addAll(List.of(effects));
        return this;
    }

    public Rule negate() {
        return new NotRule(this);
    }

    public Rule and(Rule... rules) {
        List<Rule> flattened = new ArrayList<>();
        flattened.add(this);
        for (Rule rule : rules) {
            if (rule instanceof AndRule andRule) flattened.addAll(andRule.rules);
            else flattened.add(rule);
        }
        return new AndRule(ImmutableList.copyOf(flattened));
    }

    public Rule then(Rule rule) {
        return and(rule);
    }

    public Rule then(Effect... effects) {
        return then(new AlwaysRule(true).effects(effects));
    }

    public Rule or(Rule... rules) {
        List<Rule> flattened = new ArrayList<>();
        flattened.add(this);
        for (Rule rule : rules) {
            if (rule instanceof OrRule orRule) flattened.addAll(orRule.rules);
            else flattened.add(rule);
        }
        return new OrRule(ImmutableList.copyOf(flattened));
    }

    public abstract boolean evaluate(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity);

    public final boolean run(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        boolean condition = evaluate(level, pos, state, blockEntity);
        for (Effect effect : effects) {
            effect.apply(condition, level, pos, state, blockEntity);
        }
        return condition;
    }

    public record RuleSet(Rule[] rules) {
        public <T extends BlockEntity> BlockEntityTicker<T> createTicker(BlockEntityTicker<T> original) {
            return (level, pos, state, be) -> {
                for (Rule rule : rules) {
                    rule.run(level, pos, state, be);
                }
                if (!be.hasData(Attachments.DISABLED)) original.tick(level, pos, state, be);
            };
        }
    }
}
