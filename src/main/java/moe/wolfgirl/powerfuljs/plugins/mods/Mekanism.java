package moe.wolfgirl.powerfuljs.plugins.mods;

import dev.latvian.mods.kubejs.script.BindingRegistry;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.MekLogicRegistry;

public class Mekanism {
    public static void registerBindings(BindingRegistry bindings) {
        bindings.add("MekRules", MekLogicRegistry.Rules.class);
        bindings.add("MekEffects", MekLogicRegistry.Effects.class);
    }
}
