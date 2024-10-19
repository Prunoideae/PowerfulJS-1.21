package moe.wolfgirl.powerfuljs.custom.logic.behavior;

import java.util.function.Supplier;

public class IdentityCache<C, T> {
    private C condition;
    private T value;

    private final Supplier<C> conditionGetter;
    private final Supplier<T> valueGetter;

    public IdentityCache(Supplier<C> conditionGetter, Supplier<T> valueGetter) {
        this.conditionGetter = conditionGetter;
        this.valueGetter = valueGetter;
    }

    public T get() {
        C current = conditionGetter.get();
        if (condition != current) {
            condition = current;
            value = valueGetter.get();
        }
        return value;
    }
}
