package com.hqj.utils;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class BeanCopy<S, T> {
        private final S source;
        private final T target;

        private BeanCopy(S source, T target) {
            this.source = source;
            this.target = target;
        }

        public static <S, T> BeanCopy<S, T> of(S source, T target) {
            Objects.requireNonNull(source, "source is null");
            Objects.requireNonNull(target, "target is null");
            return new BeanCopy(source, target);
        }

        public final BeanCopy<S, T> copy(BiConsumer<? super S, ? super T> action) {
            Objects.requireNonNull(action, "action is null");
            Optional.ofNullable(this.source).ifPresent((s) -> {
                action.accept(s, this.target);
            });
            return this;
        }

        public final BeanCopy<S, T> set(Consumer<? super T> action) {
            Objects.requireNonNull(action, "action is null");
            Optional.ofNullable(this.source).ifPresent((s) -> {
                action.accept(this.target);
            });
            return this;
        }

        public final T get() {
            return Optional.ofNullable(this.source).map((s) -> {
                return this.target;
            }).orElse((T)null);
        }
}
