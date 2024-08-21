package moe.wolfgirl.powerfuljs.custom.registries;

import dev.latvian.mods.kubejs.block.state.BlockStatePredicate;
import moe.wolfgirl.powerfuljs.custom.registries.logic.Effect;
import moe.wolfgirl.powerfuljs.custom.registries.logic.Rule;
import moe.wolfgirl.powerfuljs.custom.registries.logic.effects.ToggleEnable;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.MatchBlockRule;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.logic.AlwaysRule;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.logic.AndRule;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.logic.OrRule;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

public class LogicRegistry {
    public static class Rules {
        public static Rule always(boolean state) {
            return new AlwaysRule(state);
        }

        public static Rule and(Rule... rules) {
            return new AndRule(List.of(rules));
        }

        public static Rule or(Rule... rules) {
            return new OrRule(List.of(rules));
        }

        public static Rule matchBlock(BlockStatePredicate blockState) {
            return new MatchBlockRule(blockState);
        }

        /* Fluid handling */
        public static Rule hasFluid() {
            throw new NotImplementedException();
        }

        public static Rule canExtractFluid() {
            throw new NotImplementedException();
        }

        public static Rule canInsertFluid() {
            throw new NotImplementedException();
        }

        /* Energy handling */

        /* Item handling */
    }

    public static class Effects {
        public static Effect toggleEnable() {
            return new ToggleEnable();
        }
    }
}
