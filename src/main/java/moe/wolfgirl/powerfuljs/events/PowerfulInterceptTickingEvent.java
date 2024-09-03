package moe.wolfgirl.powerfuljs.events;

import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.typings.Info;
import moe.wolfgirl.powerfuljs.GameStates;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.List;
import java.util.function.Supplier;

public class PowerfulInterceptTickingEvent implements KubeEvent {

    public PowerfulInterceptTickingEvent() {
        GameStates.INTERCEPTED_BLOCK_ENTITIES.clear();
        GameStates.FORCED_TICKED_BLOCK_ENTITIES.clear();
    }

    @Info("Intercept the ticking logic of a block entity. The supplier is important to ensure one ruleset is created for every instance of block entities.")
    public void intercept(BlockEntityType<?> blockEntityType, Supplier<List<Rule>> rules) {
        GameStates.INTERCEPTED_BLOCK_ENTITIES.put(blockEntityType, new Rule.RuleSet(rules));
    }

    @Info("Force a block entity to be ticked or not, returning false will disable its original ticking logic.")
    public void forceTicker(BlockEntityType<?> blockEntityType, boolean ticked) {
        GameStates.FORCED_TICKED_BLOCK_ENTITIES.put(blockEntityType, ticked);
    }
}
