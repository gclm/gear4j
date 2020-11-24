package club.gclmit.chaos.core.function;

import java.io.Serializable;

/**
 * 受检的 runnable
 *
 * @author L.cm
 */
@FunctionalInterface
public interface CheckedRunnable extends Serializable {

	/**
	 * Run this runnable.
	 *
	 * @throws Throwable CheckedException
	 */
	void run() throws Throwable;

}
