package moe.wolfgirl.powerfuljs;

import moe.wolfgirl.powerfuljs.custom.Attachments;
import moe.wolfgirl.powerfuljs.custom.DataComponents;
import moe.wolfgirl.powerfuljs.custom.Registries;
import moe.wolfgirl.powerfuljs.events.PowerfulEvents;
import moe.wolfgirl.powerfuljs.events.PowerfulRegisterCapabilitiesEvent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onRegister(RegisterEvent event) {
        event.register(NeoForgeRegistries.ATTACHMENT_TYPES.key(), Attachments::init);
        event.register(BuiltInRegistries.DATA_COMPONENT_TYPE.key(), DataComponents::init);
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        PowerfulRegisterCapabilitiesEvent eventPJS = new PowerfulRegisterCapabilitiesEvent();
        PowerfulEvents.CAPABILITY.post(eventPJS);
        eventPJS.register(event);
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent ignore) {
        Registries.init();
    }
}
