package moe.wolfgirl.powerfuljs.custom.logic;

import com.google.common.collect.ImmutableList;
import moe.wolfgirl.powerfuljs.custom.Attachments;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.FuelProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.MultiProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.ProgressProvider;
import moe.wolfgirl.powerfuljs.custom.logic.rules.logic.AlwaysRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.logic.AndRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.logic.NotRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.logic.OrRule;
import moe.wolfgirl.powerfuljs.serde.SpeedModifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

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

    public Rule thenRun(Effect... effects) {
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

    public abstract boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity);

    public final boolean run(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        boolean condition = evaluate(level, pos, state, blockEntity);
        for (Effect effect : effects) {
            effect.apply(condition, level, pos, state, blockEntity);
        }
        return condition;
    }

    public record RuleSet(Supplier<List<Rule>> rules) {
        public static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Collection<RuleSet> ruleSets, BlockEntityTicker<T> original) {
            List<Rule> allRules = new ArrayList<>();
            for (RuleSet ruleSet : ruleSets) {
                allRules.addAll(ruleSet.rules.get());
            }
            return new PowerfulJSTicker<>(List.copyOf(allRules), original);
        }
    }

    private static <T extends BlockEntity> void advanceTicks(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, T blockEntity, BlockEntityTicker<T> original, int ticks) {
        if (ticks == 0) return;
        if (ticks >= 1) original.tick(level, pos, state, blockEntity);
        ticks--;
        if (ticks == 0) return;

        for (int i = 0; i < ticks; i++) {
            original.tick(level, pos, state, blockEntity);
        }
    }

    private static class PowerfulJSTicker<T extends BlockEntity> implements BlockEntityTicker<T> {
        private final List<Rule> rules;
        private float ticks = 0;
        private float tickSpeed = 1;
        private SpeedModifiers modifiers = SpeedModifiers.EMPTY;
        private final BlockEntityTicker<T> original;

        private PowerfulJSTicker(List<Rule> rules, BlockEntityTicker<T> original) {
            this.rules = rules;
            this.original = original;
        }

        @Override
        public void tick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull T blockEntity) {
            if (level instanceof ServerLevel serverLevel) {
                for (Rule rule : rules) {
                    rule.run(serverLevel, pos, state, blockEntity);
                }
            }

            if (!blockEntity.hasData(Attachments.DISABLED)) {
                SpeedModifiers speedModifiers = blockEntity.getData(Attachments.TICK_SPEED);
                if (modifiers != speedModifiers) {
                    modifiers = speedModifiers;
                    tickSpeed = modifiers.getTickSpeed();
                }

                ticks += tickSpeed;
                int advancedTicks = (int) Math.floor(ticks);
                advanceTicks(level, pos, state, blockEntity, original, advancedTicks);
                ticks -= advancedTicks;
            }
        }
    }

    public static class PowerfulJSDefaultTicker<T extends BlockEntity> implements BlockEntityTicker<T> {
        private final BlockEntityTicker<T> original;
        private float ticks = 0;
        private float tickSpeed = 1;
        private SpeedModifiers modifiers = SpeedModifiers.EMPTY;

        public PowerfulJSDefaultTicker(BlockEntityTicker<T> original) {
            this.original = original;
        }

        @Override
        public void tick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, T blockEntity) {
            if (!blockEntity.hasData(Attachments.DISABLED)) {
                SpeedModifiers speedModifiers = blockEntity.getData(Attachments.TICK_SPEED);
                if (modifiers != speedModifiers) {
                    modifiers = speedModifiers;
                    tickSpeed = modifiers.getTickSpeed();
                }

                ticks += tickSpeed;
                int advancedTicks = (int) Math.floor(ticks);
                advanceTicks(level, pos, state, blockEntity, original, advancedTicks);
                ticks -= advancedTicks;
            }
        }
    }
}
