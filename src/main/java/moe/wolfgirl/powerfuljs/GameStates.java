package moe.wolfgirl.powerfuljs;


import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameStates {
    public static Set<Block> REGISTERED_BLOCKS = new HashSet<>();
    public static Map<BlockEntityType<?>, Rule.RuleSet> INTERCEPTED_BLOCK_ENTITIES = new HashMap<>();
    public static Map<BlockEntityType<?>, Boolean> FORCED_TICKED_BLOCK_ENTITIES = new HashMap<>();
}
