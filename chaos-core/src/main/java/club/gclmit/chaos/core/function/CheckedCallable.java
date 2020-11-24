package club.gclmit.chaos.core.function;

import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * 受检的 Callable
 *
 * @author L.cm
 */
@FunctionalInterface
public interface CheckedCallable<T> extends Serializable {

	/**
	 * Run this callable.
	 *
	 * @return result
	 * @throws Throwable CheckedException
	 */
	@Nullable
	T call() throws Throwable;
}
