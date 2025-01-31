package moe.wolfgirl.powerfuljs.serde;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record ChunkDataStorage(Map<BlockPos, CompoundTag> data) {
    private static final Codec<Pair<BlockPos, CompoundTag>> DATA_CODEC = RecordCodecBuilder.create(
            data -> data.group(
                    BlockPos.CODEC.fieldOf("pos").forGetter(Pair::getFirst),
                    CompoundTag.CODEC.fieldOf("data").forGetter(Pair::getSecond)
            ).apply(data, Pair::new)
    );

    public static final Codec<ChunkDataStorage> CODEC = DATA_CODEC.listOf()
            .xmap(ChunkDataStorage::to, ChunkDataStorage::from);

    public static ChunkDataStorage newEmpty() {
        return new ChunkDataStorage(new HashMap<>());
    }


    private static ChunkDataStorage to(List<Pair<BlockPos, CompoundTag>> dataList) {
        Map<BlockPos, CompoundTag> data = new HashMap<>();
        for (Pair<BlockPos, CompoundTag> pair : dataList) {
            if (pair.getSecond().isEmpty()) continue;
            data.put(pair.getFirst(), pair.getSecond());
        }
        return new ChunkDataStorage(data);
    }

    private List<Pair<BlockPos, CompoundTag>> from() {
        List<Pair<BlockPos, CompoundTag>> dataList = new ArrayList<>();
        for (Map.Entry<BlockPos, CompoundTag> entry : data.entrySet()) {
            BlockPos blockPos = entry.getKey();
            CompoundTag compoundTag = entry.getValue();
            dataList.add(new Pair<>(blockPos, compoundTag));
        }
        return dataList;
    }
}
