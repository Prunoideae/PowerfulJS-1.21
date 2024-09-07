package moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.effects.chemical;

import mekanism.api.chemical.IChemicalHandler;
import mekanism.common.capabilities.Capabilities;
import moe.wolfgirl.powerfuljs.custom.logic.effects.CapabilityEffect;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public abstract class ChemicalEffect extends CapabilityEffect<IChemicalHandler, @Nullable Direction> {
    protected ChemicalEffect(@Nullable Direction context) {
        super(Capabilities.CHEMICAL.block(), context);
    }
}
