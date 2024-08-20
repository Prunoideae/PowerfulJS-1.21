package moe.wolfgirl.powerfuljs.plugins;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.TypeWrapperRegistry;
import moe.wolfgirl.powerfuljs.custom.CapabilityJS;
import moe.wolfgirl.powerfuljs.events.PowerfulEvents;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

public class PowerfulJSPlugin implements KubeJSPlugin {

    @Override
    public void registerTypeWrappers(TypeWrapperRegistry registry) {
        registry.register(BlockCapability.class, CapabilityJS.BLOCK::wrap);
        registry.register(ItemCapability.class, CapabilityJS.ITEM::wrap);
        registry.register(EntityCapability.class, CapabilityJS.ENTITY::wrap);
    }

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(PowerfulEvents.GROUP);
    }
}
