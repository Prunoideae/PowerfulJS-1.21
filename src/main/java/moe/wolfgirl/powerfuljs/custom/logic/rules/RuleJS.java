package moe.wolfgirl.powerfuljs.custom.logic.rules;

import com.google.common.base.Objects;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;
import java.util.function.Predicate;

public class RuleJS<T> extends Rule {
    private final Function<BlockEntity, T> valueGetter;
    private final Predicate<T> valuePredicate;

    private T value = null;
    private boolean result = false;

    public RuleJS(Function<BlockEntity, T> valueGetter, Predicate<T> valuePredicate) {
        this.valueGetter = valueGetter;
        this.valuePredicate = valuePredicate;
    }


    @Override
    public boolean evaluate(ServerLevel level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        T currentValue = this.valueGetter.apply(blockEntity); // Still not easy to eliminate this
        if (!Objects.equal(currentValue, this.value)) {
            this.value = currentValue;
            this.result = this.valuePredicate.test(this.value);
        }
        return this.result;
    }
}
