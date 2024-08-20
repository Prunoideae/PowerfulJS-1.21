package moe.wolfgirl.powerfuljs.custom.base.validators;

import moe.wolfgirl.powerfuljs.custom.base.CapabilityValidator;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class MatchDirection<T> implements CapabilityValidator<T> {
    private final EnumSet<Direction> allowed;

    public MatchDirection(Direction[] allowed) {
        this.allowed = EnumSet.copyOf(List.of(allowed));
    }

    @Override
    public boolean test(T info, @Nullable Object context) {
        return context instanceof Direction direction && allowed.contains(direction);
    }
}
