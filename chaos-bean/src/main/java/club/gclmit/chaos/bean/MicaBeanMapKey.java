package club.gclmit.chaos.bean;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * bean map key，提高性能
 *
 * @author L.cm
 * @author gclm
 */
@EqualsAndHashCode
@RequiredArgsConstructor
public class MicaBeanMapKey {
	private final Class type;
	private final int require;
}
