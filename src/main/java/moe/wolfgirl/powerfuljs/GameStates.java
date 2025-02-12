package moe.wolfgirl.powerfuljs;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import dev.latvian.mods.rhino.type.TypeInfo;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameStates {
    public static final Map<AttachmentType<?>, TypeInfo> ATTACHMENT_TYPE_INFO = new HashMap<>();

    public static Set<Block> REGISTERED_BLOCKS = new HashSet<>();
    public static Multimap<BlockEntityType<?>, Rule.RuleSet> INTERCEPTED_BLOCK_ENTITIES = ArrayListMultimap.create();
    public static Map<BlockEntityType<?>, Float> TICK_MODIFIED_BLOCK_ENTITIES = new HashMap<>();
    public static Set<BlockEntityType<?>> OWNED_BLOCK_ENTITIES = new HashSet<>();
    public static Difficulty GAME_DIFFICULTY = null;
}
