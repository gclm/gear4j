package club.gclmit.chaos.bean.core;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * copy key
 *
 * @author L.cm
 * @author gclm
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class MicaBeanCopierKey {
	private final Class<?> source;
	private final Class<?> target;
	private final boolean useConverter;
	private final boolean nonNull;
}
