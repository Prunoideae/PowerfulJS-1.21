package moe.wolfgirl.powerfuljs.custom.mods.mekanism.logic.rules.chemical;

import mekanism.api.chemical.IChemicalHandler;
import mekanism.common.capabilities.Capabilities;
import moe.wolfgirl.powerfuljs.custom.logic.rules.CapabilityRule;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public abstract class ChemicalCapability extends CapabilityRule<IChemicalHandler, @Nullable Direction> {
    protected ChemicalCapability(@Nullable Direction context) {
        super(Capabilities.CHEMICAL.block(), context);
    }
}
