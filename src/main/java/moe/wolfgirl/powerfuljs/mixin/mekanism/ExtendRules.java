package moe.wolfgirl.powerfuljs.mixin.mekanism;

import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import mekanism.api.chemical.ChemicalStack;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.chemical.CanExtractChemical;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.chemical.CanInsertChemical;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.chemical.HasChemical;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.heat.HasHeat;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.machine.MekAboutToFinishRule;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.machine.MekFactoryAboutToFinishRule;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.machine.MekFactoryRunningRule;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.machine.MekRunningRule;
import moe.wolfgirl.powerfuljs.custom.registries.LogicRegistry;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = LogicRegistry.Rules.class, remap = false)
@RemapPrefixForJS("pjs$")
public abstract class ExtendRules {

    @Unique
    public Rule pjs$machineRunningMek() {
        return new MekRunningRule();
    }

    @Unique
    @Info("Test if the machine is **exactly** one tick away from producing results. Must be run after any tick modification effect.")
    public Rule pjs$machineAboutToFinishMek() {
        return new MekAboutToFinishRule(1);
    }

    @Unique
    public Rule pjs$factoryRunningMek() {
        return new MekFactoryRunningRule();
    }

    @Unique
    @Info("Test if the machine is **exactly** one tick away from producing results. Must be run after any tick modification effect.")
    public Rule pjs$factoryAboutToFinishMek() {
        return new MekFactoryAboutToFinishRule(1);
    }

    @Unique
    public Rule pjs$hasHeatMek(double heat, @Nullable Direction direction) {
        return new HasHeat(heat, direction);
    }

    @Unique
    public Rule pjs$hasChemicalMek(ChemicalStack chemicalStack, @Nullable Direction direction) {
        return new HasChemical(chemicalStack, direction);
    }

    @Unique
    public Rule pjs$canDrainChemicalMek(ChemicalStack chemicalStack, @Nullable Direction direction) {
        return new CanExtractChemical(chemicalStack, direction);
    }

    @Unique
    public Rule pjs$canFillChemicalMek(ChemicalStack chemicalStack, @Nullable Direction direction) {
        return new CanInsertChemical(chemicalStack, direction);
    }
}