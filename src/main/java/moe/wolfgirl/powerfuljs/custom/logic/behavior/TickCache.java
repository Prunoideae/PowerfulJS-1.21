package moe.wolfgirl.powerfuljs.custom.logic.behavior;

import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * Cache that prevents multiple value retrieval in one tick
 * @param <T> inner type
 */
public class TickCache<T> {
    private long tick = 0;
    private T value;

    private final LongSupplier tickGetter;
    private final Supplier<T> valueGetter;

    public TickCache(LongSupplier tickGetter, Supplier<T> valueGetter) {
        this.tickGetter = tickGetter;
        this.valueGetter = valueGetter;
    }

    public T get() {
        long current = tickGetter.getAsLong();
        if (tick != current) {
            tick = current;
            value = valueGetter.get();
        }
        return value;
    }
}
