package moe.wolfgirl.powerfuljs.serde;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public record SpeedModifiers(Map<String, SpeedModifier> modifiers) {
    public static final SpeedModifiers EMPTY = new SpeedModifiers(Map.of());
    public static final Codec<SpeedModifiers> CODEC = SpeedModifier.CODEC
            .listOf()
            .xmap(
                    l -> new SpeedModifiers(l.stream().collect(Collectors.toUnmodifiableMap(
                            modifier -> modifier.id,
                            modifier -> modifier
                    ))),
                    m -> m.modifiers.values().stream().toList()
            );

    public record SpeedModifier(String id, float amount, Operation operation) {
        private static final Codec<SpeedModifier> CODEC = RecordCodecBuilder.create(
                modifier -> modifier.group(
                        Codec.STRING.fieldOf("id").forGetter(SpeedModifier::id),
                        Codec.FLOAT.fieldOf("amount").forGetter(SpeedModifier::amount),
                        Operation.CODEC.fieldOf("operation").forGetter(SpeedModifier::operation)
                ).apply(modifier, SpeedModifier::new)
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

        for (SpeedModifier modifier : modifiers.values()) {
            switch (modifier.operation) {
                case ADD_BASE -> addBase += modifier.amount;
                case MULTIPLY_BASE -> multiplyBase += modifier.amount;
                case ADD_TOTAL -> addTotal += modifier.amount;
                case MULTIPLY_TOTAL -> multiplyTotal += modifier.amount;
            }
        }

        return Math.max(0, ((1 + addBase) * multiplyBase) * multiplyTotal + addTotal);
    }

    public SpeedModifiers withModifier(SpeedModifier modifier) {
        Map<String, SpeedModifier> newMap = new HashMap<>(modifiers);
        newMap.put(modifier.id, modifier);
        return new SpeedModifiers(Map.copyOf(newMap));
    }

    public SpeedModifiers removeModifier(String id) {
        Map<String, SpeedModifier> newMap = new HashMap<>(modifiers);
        newMap.remove(id);
        return new SpeedModifiers(Map.copyOf(newMap));
    }

    public boolean hasModifier(String id) {
        return modifiers.containsKey(id);
    }
}
