package moe.wolfgirl.powerfuljs.custom.base.validators;

import moe.wolfgirl.powerfuljs.custom.base.CapabilityValidator;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class MatchDirection<T> implements CapabilityValidator<T> {
    private final EnumSet<Direction> allowed;
    private final boolean allowsNull;

    public MatchDirection(Direction[] allowed) {
        List<Direction> nonNulls = new ArrayList<>();
        boolean allowsNull = false;
        for (Direction direction : allowed) {
            if (direction != null) nonNulls.add(direction);
            else allowsNull = true;
        }

        this.allowed = EnumSet.copyOf(nonNulls);
        this.allowsNull = allowsNull;
    }

    @Override
    public boolean test(T info, @Nullable Object context) {
        if (allowsNull && context == null) return true;
        return context instanceof Direction direction && (allowed.contains(direction));
    }
}
