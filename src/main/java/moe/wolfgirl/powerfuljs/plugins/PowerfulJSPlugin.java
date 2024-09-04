package moe.wolfgirl.powerfuljs.plugins;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import dev.latvian.mods.kubejs.script.ScriptManager;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.script.TypeWrapperRegistry;
import moe.wolfgirl.powerfuljs.custom.CapabilityJS;
import moe.wolfgirl.powerfuljs.custom.CapabilityWrapper;
import moe.wolfgirl.powerfuljs.custom.registries.LogicRegistry;
import moe.wolfgirl.powerfuljs.events.PowerfulEvents;
import moe.wolfgirl.powerfuljs.events.PowerfulInterceptTickingEvent;
import moe.wolfgirl.powerfuljs.plugins.mods.Mekanism;
import moe.wolfgirl.powerfuljs.utils.ModUtils;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

public class PowerfulJSPlugin implements KubeJSPlugin {

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(PowerfulEvents.GROUP);
    }

    @Override
    public void registerBindings(BindingRegistry bindings) {
        bindings.add("Capabilities", CapabilityWrapper.class);

        if (bindings.type() != ScriptType.SERVER) return;
        bindings.add("Rules", LogicRegistry.Rules.class);
        bindings.add("Effects", LogicRegistry.Effects.class);
        ModUtils.whenLoaded("mekanism", () -> Mekanism.registerBindings(bindings));
    }

    @Override
    public void afterScriptsLoaded(ScriptManager manager) {
        if (manager.scriptType == ScriptType.SERVER) {
            PowerfulEvents.INTERCEPT_TICKING.post(new PowerfulInterceptTickingEvent());
        }
    }

    @Override
    public void registerTypeWrappers(TypeWrapperRegistry registry) {
        CapabilityJS.init();
        registry.register(BlockCapability.class, CapabilityJS.BLOCK::wrap);
        registry.register(ItemCapability.class, CapabilityJS.ITEM::wrap);
        registry.register(EntityCapability.class, CapabilityJS.ENTITY::wrap);
    }
}
