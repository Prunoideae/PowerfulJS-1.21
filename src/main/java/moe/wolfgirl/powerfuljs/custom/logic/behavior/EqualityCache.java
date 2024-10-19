package moe.wolfgirl.powerfuljs.custom.logic.behavior;

import com.google.common.base.Objects;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class EqualityCache<C, T> {
    private C condition;
    private T value;

    private final Supplier<C> conditionGetter;
    private final UnaryOperator<C> conditionTransformer;
    private final UnaryOperator<T> valueGetter;

    public EqualityCache(Supplier<C> conditionGetter, UnaryOperator<C> conditionTransformer, UnaryOperator<T> valueGetter) {
        this.conditionGetter = conditionGetter;
        this.conditionTransformer = conditionTransformer;
        this.valueGetter = valueGetter;
    }


    public T get() {
        C current = conditionGetter.get();
        if (!Objects.equal(condition, current)) {
            condition = conditionTransformer.apply(condition);
            value = valueGetter.apply(value);
        }

        return value;
    }
}
