package moe.wolfgirl.powerfuljs.custom.logic.rules.world;

import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class InBiomeRule extends Rule {
    private final TagKey<Biome> biomeTag;

    public InBiomeRule(TagKey<Biome> biomeTag) {
        this.biomeTag = biomeTag;
    }

    @Override
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        return level.getBiome(pos).is(biomeTag);
    }
}
