package moe.wolfgirl.powerfuljs.serde;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public record TickModifiers(Map<String, TickModifier> modifiers) {
    public static final TickModifiers EMPTY = new TickModifiers(Map.of());
    public static final Codec<TickModifiers> CODEC = TickModifier.CODEC
            .listOf()
            .xmap(
                    l -> new TickModifiers(l.stream().collect(Collectors.toUnmodifiableMap(
                            modifier -> modifier.id,
                            modifier -> modifier
                    ))),
                    m -> m.modifiers.values().stream().toList()
            );

    public record TickModifier(String id, float amount, Operation operation) {
        private static final Codec<TickModifier> CODEC = RecordCodecBuilder.create(
                modifier -> modifier.group(
                        Codec.STRING.fieldOf("id").forGetter(TickModifier::id),
                        Codec.FLOAT.fieldOf("amount").forGetter(TickModifier::amount),
                        Operation.CODEC.fieldOf("operation").forGetter(TickModifier::operation)
                ).apply(modifier, TickModifier::new)
        );
    }

    public enum Operation {
        ADD_BASE,
        MULTIPLY_BASE,
        ADD_TOTAL,
        MULTIPLY_TOTAL;

        private static final Codec<Operation> CODEC = Codec.STRING.xmap(Operation::valueOf, Operation::name);
    }

    public float getTickSpeed() {
        float addBase = 0;
        float multiplyBase = 1;
        float addTotal = 0;
        float multiplyTotal = 1;

        for (TickModifier modifier : modifiers.values()) {
            switch (modifier.operation) {
                case ADD_BASE -> addBase += modifier.amount;
                case MULTIPLY_BASE -> multiplyBase += modifier.amount;
                case ADD_TOTAL -> addTotal += modifier.amount;
                case MULTIPLY_TOTAL -> multiplyTotal += modifier.amount;
            }
        }

        return Math.max(0, ((1 + addBase) * multiplyBase) * multiplyTotal + addTotal);
    }

    public TickModifiers withModifier(TickModifier modifier) {
        Map<String, TickModifier> newMap = new HashMap<>(modifiers);
        newMap.put(modifier.id, modifier);
        return new TickModifiers(Map.copyOf(newMap));
    }

    public TickModifiers removeModifier(String id) {
        Map<String, TickModifier> newMap = new HashMap<>(modifiers);
        newMap.remove(id);
        return new TickModifiers(Map.copyOf(newMap));
    }

    public boolean hasModifier(String id) {
        return modifiers.containsKey(id);
    }
}
