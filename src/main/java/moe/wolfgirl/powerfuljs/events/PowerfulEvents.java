package moe.wolfgirl.powerfuljs.events;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface PowerfulEvents {
    EventGroup GROUP = EventGroup.of("PowerfulEvents");

    EventHandler CAPABILITY = GROUP.startup("registerCapabilities", () -> PowerfulRegisterCapabilitiesEvent.class);
    EventHandler INTERCEPT_TICKING = GROUP.server("registerInterceptors", () -> PowerfulInterceptTickingEvent.class);
}
