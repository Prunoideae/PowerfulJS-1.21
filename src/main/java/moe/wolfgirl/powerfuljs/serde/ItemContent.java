package moe.wolfgirl.powerfuljs.serde;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public record ItemContent(List<ItemStack> items) {
    public static final Supplier<ItemContent> EMPTY = () -> new ItemContent(NonNullList.create());
    public static final Codec<ItemContent> CODEC = Slot.CODEC
            .listOf()
            .xmap(ItemContent::fromSlots, ItemContent::asSlots);
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemContent> STREAM_CODEC = ItemStack.OPTIONAL_STREAM_CODEC
            .apply(ByteBufCodecs.list())
            .map(ItemContent::new, content -> content.items);

    record Slot(int index, ItemStack content) {
        public static final Codec<Slot> CODEC = RecordCodecBuilder.create(
                slot -> slot.group(
                                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("slot").forGetter(Slot::index),
                                ItemStack.CODEC.fieldOf("item").forGetter(Slot::content)
                        )
                        .apply(slot, Slot::new)
        );
    }

    public ItemStack getStackInSlot(int slot) {
        return slot < items.size() ? items.get(slot) : ItemStack.EMPTY;
    }

    public ItemContent withStackInSlot(int slot, ItemStack itemStack) {
        NonNullList<ItemStack> newContent = NonNullList.withSize(Math.max(slot + 1, items.size()), ItemStack.EMPTY);
        for (int i = 0; i < items.size(); i++) {
            newContent.set(i, (i == slot ? itemStack : items.get(i)).copy());
        }
        return new ItemContent(newContent);
    }

    private static ItemContent fromSlots(List<Slot> slots) {
        var maxIndex = slots.stream().mapToInt(Slot::index).max();
        if (maxIndex.isEmpty()) return EMPTY.get();

        ItemContent itemContent = new ItemContent(NonNullList.withSize(maxIndex.getAsInt() + 1, ItemStack.EMPTY));
        for (Slot slot : slots) {
            itemContent.items.set(slot.index, slot.content);
        }

        return itemContent;
    }

    private List<Slot> asSlots() {
        List<Slot> slots = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            var content = items.get(i);
            if (!content.isEmpty()) {
                slots.add(new Slot(i, content));
            }
        }

        return slots;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof ItemContent(List<ItemStack> items1)) {
            if (items1 != null && items1.size() == items.size()) {
                for (int i = 0; i < items.size(); i++) {
                    if (!ItemStack.isSameItemSameComponents(items.get(i), items1.get(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
