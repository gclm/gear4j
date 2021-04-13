package club.gclmit.chaos.core.function;

import club.gclmit.chaos.core.utils.ExceptionUtils;
import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 *
 * 解决 Lambda 受检异常处理
 *
 * <p>
 *  https://segmentfault.com/a/1190000007832130
 * <p>
 *
 * @author gclm
 */
@UtilityClass
public class Attempt {

    /**
     * 构造受检的 function
     *
     * @param function CheckedFunction
     * @param <T>      泛型
     * @param <R>      泛型
     * @return Function
     */
    public static <T, R> Function<T, R> function(CheckedFunction<T, R> function) {
        Objects.requireNonNull(function);
        return t -> {
            try {
                return function.apply(t);
            } catch (Throwable e) {
                throw ExceptionUtils.wrapUnchecked(e);
            }
        };
    }

    /**
     * 构造受检的 supplier
     *
     * @param consumer CheckedConsumer
     * @param <T>      泛型
     * @return Consumer
     */
    public static <T> Consumer<T> consumer(CheckedConsumer<T> consumer) {
        Objects.requireNonNull(consumer);
        return t -> {
            try {
                consumer.accept(t);
            } catch (Throwable e) {
                throw ExceptionUtils.wrapUnchecked(e);
            }
        };
    }

    /**
     * 构造受检的 supplier
     *
     * @param supplier CheckedSupplier
     * @param <T>      泛型
     * @return Supplier
     */
    public static <T> Supplier<T> supplier(CheckedSupplier<T> supplier) {
        Objects.requireNonNull(supplier);
        return () -> {
            try {
                return supplier.get();
            } catch (Throwable e) {
                throw ExceptionUtils.wrapUnchecked(e);
            }
        };
    }

    /**
     * 构造受检的 predicate
     *
     * @param predicate CheckedPredicate
     * @param <T>       泛型
     * @return Supplier
     */
    public static <T> Predicate<T> predicate(CheckedPredicate<T> predicate) {
        Objects.requireNonNull(predicate);
        return (t) -> {
            try {
                return predicate.test(t);
            } catch (Throwable e) {
                throw ExceptionUtils.wrapUnchecked(e);
            }
        };
    }

    /**
     * 构造受检的 runnable
     *
     * @param runnable CheckedRunnable
     * @return Runnable
     */
    public static Runnable runnable(CheckedRunnable runnable) {
        Objects.requireNonNull(runnable);
        return () -> {
            try {
                runnable.run();
            } catch (Throwable e) {
                throw ExceptionUtils.wrapUnchecked(e);
            }
        };
    }

    /**
     * 构造受检的 callable
     *
     * @param callable CheckedCallable
     * @param <T>      泛型
     * @return Callable
     */
    public static <T> Callable<T> callable(CheckedCallable<T> callable) {
        Objects.requireNonNull(callable);
        return () -> {
            try {
                return callable.call();
            } catch (Throwable e) {
                throw ExceptionUtils.wrapUnchecked(e);
            }
        };
    }

    /**
     * 构造受检的 comparator
     *
     * @param comparator CheckedComparator
     * @param <T>        泛型
     * @return Comparator
     */
    public static <T> Comparator<T> comparator(CheckedComparator<T> comparator) {
        Objects.requireNonNull(comparator);
        return (T o1, T o2) -> {
            try {
                return comparator.compare(o1, o2);
            } catch (Throwable e) {
                throw ExceptionUtils.wrapUnchecked(e);
            }
        };
    }

}
