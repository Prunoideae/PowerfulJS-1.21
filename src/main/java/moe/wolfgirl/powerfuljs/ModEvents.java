package moe.wolfgirl.powerfuljs;

import moe.wolfgirl.powerfuljs.custom.Attachments;
import moe.wolfgirl.powerfuljs.custom.CapabilityJS;
import moe.wolfgirl.powerfuljs.custom.DataComponents;
import moe.wolfgirl.powerfuljs.custom.Registries;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.MekAttachments;
import moe.wolfgirl.powerfuljs.custom.mods.mekanism.MekDataComponents;
import moe.wolfgirl.powerfuljs.events.PowerfulEvents;
import moe.wolfgirl.powerfuljs.events.PowerfulRegisterCapabilitiesEvent;
import moe.wolfgirl.powerfuljs.utils.ModUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.EventPriority;
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
        var attachment = NeoForgeRegistries.ATTACHMENT_TYPES.key();
        var dataComponent = BuiltInRegistries.DATA_COMPONENT_TYPE.key();
        event.register(attachment, Attachments::initAttachments);
        event.register(dataComponent, DataComponents::initComponents);

        ModUtils.whenLoaded("mekanism", () -> {
            event.register(attachment, MekAttachments::initAttachments);
            event.register(dataComponent, MekDataComponents::initComponents);
        });
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        PowerfulRegisterCapabilitiesEvent eventPJS = new PowerfulRegisterCapabilitiesEvent();
        PowerfulEvents.CAPABILITY.post(eventPJS);
        eventPJS.register(event);
        CapabilityJS.init();
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent ignore) {
        Registries.init();
    }
}
