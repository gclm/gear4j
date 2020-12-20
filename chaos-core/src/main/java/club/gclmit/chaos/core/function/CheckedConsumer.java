package club.gclmit.chaos.core.function;

import org.springframework.lang.Nullable;
import java.io.Serializable;

/**
 * 受检的 Consumer
 *
 * @author L.cm
 */
@FunctionalInterface
public interface CheckedConsumer<T> extends Serializable {

	/**
	 * Run the Consumer
	 *
	 * @param t T
	 * @throws Throwable UncheckedException
	 */
	@Nullable
	void accept(@Nullable T t) throws Throwable;

}
