package moe.wolfgirl.powerfuljs.events;

import dev.latvian.mods.kubejs.event.KubeEvent;
import moe.wolfgirl.powerfuljs.GameStates;
import moe.wolfgirl.powerfuljs.custom.registries.logic.Rule;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class PowerfulInterceptTickingEvent implements KubeEvent {

    public PowerfulInterceptTickingEvent() {
        GameStates.INTERCEPTED_BLOCK_ENTITIES.clear();
    }

    public void intercept(BlockEntityType<?> blockEntityType, Rule... rules) {
        GameStates.INTERCEPTED_BLOCK_ENTITIES.put(blockEntityType, new Rule.RuleSet(rules));
    }
}
