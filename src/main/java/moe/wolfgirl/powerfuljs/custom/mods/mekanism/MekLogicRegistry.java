package moe.wolfgirl.powerfuljs.custom.mods.mekanism;

import dev.latvian.mods.kubejs.typings.Info;
import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.effects.machine.MekFactoryProgress;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.effects.machine.MekProgress;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.machine.MekAboutToFinishRule;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.machine.MekFactoryAboutToFinishRule;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.machine.MekFactoryRunningRule;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.machine.MekRunningRule;

public class MekLogicRegistry {
    public static class Rules {
        public static Rule machineRunning() {
            return new MekRunningRule();
        }

        @Info("Test if the machine is **exactly** one tick away from producing results. Must be run after any tick modification effect.")
        public static Rule machineAboutToFinish() {
            return new MekAboutToFinishRule(1);
        }

        public static Rule factoryRunning() {
            return new MekFactoryRunningRule();
        }

        @Info("Test if the machine is **exactly** one tick away from producing results. Must be run after any tick modification effect.")
        public static Rule factoryAboutToFinish() {
            return new MekFactoryAboutToFinishRule(1);
        }
    }

    public static class Effects {
        public static Effect addProgress(int ticks) {
            return new MekProgress(ticks);
        }

        public static Effect addFactoryProgress(int ticks) {
            return new MekFactoryProgress(ticks);
        }
    }
}
