package moe.wolfgirl.powerfuljs.network;

import moe.wolfgirl.powerfuljs.PowerfulJS;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = PowerfulJS.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public interface PowerfulJSNet {
    private static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> type(String id) {
        return new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("powerfuljs", id));
    }


    @SubscribeEvent
    static void register(RegisterPayloadHandlersEvent event) {

    }
}
