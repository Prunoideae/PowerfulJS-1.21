package moe.wolfgirl.powerfuljs.custom.logic.rules;

import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Predicate;

public abstract class AttachmentRule<T> extends Rule {
    private final AttachmentType<T> attachmentType;

    protected AttachmentRule(AttachmentType<T> attachmentType) {
        this.attachmentType = attachmentType;
    }

    protected abstract boolean testAttachment(T data);

    @Override
    public boolean evaluate(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        T data = blockEntity.getData(attachmentType);
        return testAttachment(data);
    }

    public enum CompareAction {
        EQUALS,
        NOT_EQUALS,
        GREATER_THAN,
        LESS_THAN,
        NOT_GREATER_THAN,
        NOT_LESS_THAN;

        public <T extends Comparable<T>> boolean test(T obj1, T obj2) {
            return switch (this) {
                case EQUALS -> obj1.equals(obj2);
                case NOT_EQUALS -> !obj1.equals(obj2);
                case GREATER_THAN -> obj1.compareTo(obj2) > 0;
                case LESS_THAN -> obj1.compareTo(obj2) < 0;
                case NOT_GREATER_THAN -> obj1.compareTo(obj2) <= 0;
                case NOT_LESS_THAN -> obj1.compareTo(obj2) >= 0;
            };
        }
    }

    public static class NativeComparable<T extends Comparable<T>> extends AttachmentRule<T> {
        private final T criteria;
        private final CompareAction action;

        public NativeComparable(AttachmentType<T> attachmentType, T criteria, CompareAction action) {
            super(attachmentType);
            this.criteria = criteria;
            this.action = action;
        }

        @Override
        protected boolean testAttachment(T data) {
            return action.test(data, criteria);
        }
    }

    public static class Custom<T> extends AttachmentRule<T> {
        private final Predicate<T> test;
        private T cachedData;
        private boolean cachedResult;

        public Custom(AttachmentType<T> attachmentType, Predicate<T> test) {
            super(attachmentType);
            this.test = test;
        }

        @Override
        protected boolean testAttachment(T data) {
            if (cachedData == null || !cachedData.equals(data)) {
                cachedData = data;
                cachedResult = test.test(cachedData);
            }
            return cachedResult;
        }
    }

    public static class Present extends Rule {
        private final AttachmentType<?> attachmentType;

        public Present(AttachmentType<?> attachmentType) {
            this.attachmentType = attachmentType;
        }

        @Override
        public boolean evaluate(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
            return blockEntity.hasData(attachmentType);
        }
    }
}
