package moe.wolfgirl.powerfuljs.custom.mods.mekanism;

import dev.latvian.mods.kubejs.typings.Info;
import mekanism.api.chemical.ChemicalStack;
import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.effects.chemical.DrainChemicalEffect;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.effects.chemical.FillChemicalEffect;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.effects.heat.AddHeat;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.effects.machine.MekFactoryProgress;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.effects.machine.MekProgress;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.machine.MekAboutToFinishRule;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.machine.MekFactoryAboutToFinishRule;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.machine.MekFactoryRunningRule;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.machine.MekRunningRule;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

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

        public static Effect fillChemical(ChemicalStack chemicalStack, boolean forced, @Nullable Direction direction) {
            return new FillChemicalEffect(chemicalStack, forced, direction);
        }

        public static Effect drainChemical(ChemicalStack chemicalStack, boolean forced, @Nullable Direction direction) {
            return new DrainChemicalEffect(chemicalStack, forced, direction);
        }

        public static Effect addHeat(double heat, @Nullable Direction direction) {
            return new AddHeat(heat, direction);
        }
    }
}
