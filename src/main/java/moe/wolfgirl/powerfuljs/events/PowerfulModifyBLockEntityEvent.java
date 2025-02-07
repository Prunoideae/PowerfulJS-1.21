package moe.wolfgirl.powerfuljs.events;

import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.typings.Info;
import moe.wolfgirl.powerfuljs.GameStates;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import moe.wolfgirl.powerfuljs.custom.registries.LogicRegistry;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.List;
import java.util.function.Supplier;

public class PowerfulModifyBLockEntityEvent implements KubeEvent {

    public PowerfulModifyBLockEntityEvent() {
        GameStates.INTERCEPTED_BLOCK_ENTITIES.clear();
        GameStates.FORCED_TICKED_BLOCK_ENTITIES.clear();
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

    @Info("Force a block entity to be ticked or not, returning false will disable its original ticking logic.")
    public void forceTicking(BlockEntityType<?> blockEntityType, boolean ticked) {
        if (GameStates.FORCED_TICKED_BLOCK_ENTITIES.containsKey(blockEntityType)) {
            throw new RuntimeException("Ticking status of %s is already modified!".formatted(blockEntityType));
        }
        GameStates.FORCED_TICKED_BLOCK_ENTITIES.put(blockEntityType, ticked);
    }

    @Info("Makes a machine to record its owner when placed.")
    public void recordOwnerFor(BlockEntityType<?> blockEntityType) {
        GameStates.OWNED_BLOCK_ENTITIES.add(blockEntityType);
    }
}
