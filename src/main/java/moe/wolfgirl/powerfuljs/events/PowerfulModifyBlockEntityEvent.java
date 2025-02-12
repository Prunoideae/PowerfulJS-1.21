package moe.wolfgirl.powerfuljs.events;

import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.typings.Info;
import moe.wolfgirl.powerfuljs.GameStates;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import moe.wolfgirl.powerfuljs.custom.registries.LogicRegistry;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.List;
import java.util.function.Supplier;

public class PowerfulModifyBlockEntityEvent implements KubeEvent {

    public PowerfulModifyBlockEntityEvent() {
        GameStates.INTERCEPTED_BLOCK_ENTITIES.clear();
        GameStates.TICK_MODIFIED_BLOCK_ENTITIES.clear();
        GameStates.OWNED_BLOCK_ENTITIES.clear();
    }

    public LogicRegistry.Rules getRules() {
        return LogicRegistry.Rules.INSTANCE;
    }

    public LogicRegistry.Effects getEffects() {
        return LogicRegistry.Effects.INSTANCE;
    }

    @Info("Intercept the ticking logic of a block entity. The supplier is important to ensure one ruleset is created for every instance of block entities.")
    public void modifyLogic(BlockEntityType<?> blockEntityType, Supplier<List<Rule>> rules) {
        GameStates.OWNED_BLOCK_ENTITIES.add(blockEntityType);
        GameStates.INTERCEPTED_BLOCK_ENTITIES.put(blockEntityType, new Rule.RuleSet(rules));
    }

    @Info("Modify the base tick speed of the block entity, this will affect tick speed modifiers in a multiplicative way. Speed <= 0 will disable the ticking logic, > 0 will make even non-tick-able BEs tick.")
    public void modifyTickSpeed(BlockEntityType<?> blockEntityType, float tickSpeed) {
        if (GameStates.TICK_MODIFIED_BLOCK_ENTITIES.containsKey(blockEntityType)) {
            throw new RuntimeException("Ticking status of %s is already modified!".formatted(blockEntityType));
        }
        GameStates.TICK_MODIFIED_BLOCK_ENTITIES.put(blockEntityType, tickSpeed);
    }

    @Info("Makes a machine to record its owner when placed.")
    public void recordOwnerFor(BlockEntityType<?> blockEntityType) {
        GameStates.OWNED_BLOCK_ENTITIES.add(blockEntityType);
    }
}
