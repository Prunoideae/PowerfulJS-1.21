package moe.wolfgirl.powerfuljs.mixin.mekanism;

import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import mekanism.api.chemical.ChemicalStack;
import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.effects.chemical.DrainChemicalEffect;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.effects.chemical.FillChemicalEffect;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.effects.heat.AddHeat;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.effects.machine.MekProgress;
import moe.wolfgirl.powerfuljs.custom.registries.LogicRegistry;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = LogicRegistry.Effects.class, remap = false)
@RemapPrefixForJS("pjs$")
public abstract class ExtendEffects {

    @Unique
    public Effect pjs$addProgressMek(int ticks) {
        return new MekProgress(ticks);
    }

    @Unique
    public Effect pjs$fillChemicalMek(ChemicalStack chemicalStack, boolean forced, @Nullable Direction direction) {
        return new FillChemicalEffect(chemicalStack, forced, direction);
    }

    @Unique
    public Effect pjs$drainChemicalMek(ChemicalStack chemicalStack, boolean forced, @Nullable Direction direction) {
        return new DrainChemicalEffect(chemicalStack, forced, direction);
    }

    @Unique
    public Effect pjs$addHeatMek(double heat, @Nullable Direction direction) {
        return new AddHeat(heat, direction);
    }
}
