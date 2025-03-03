package moe.wolfgirl.powerfuljs.events;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface PowerfulEvents {
    EventGroup GROUP = EventGroup.of("PowerfulEvents");

    EventHandler CAPABILITY = GROUP.startup("registerCapabilities", () -> PowerfulRegisterCapabilitiesEvent.class);
    EventHandler MODIFY_BLOCK_ENTITY = GROUP.server("modifyBlockEntity", () -> PowerfulModifyBlockEntityEvent.class);

}
