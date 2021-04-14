package club.gclmit.chaos.core.bean.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Bean属性
 *
 * @author L.cm
 * @author gclm
 */
@Getter
@RequiredArgsConstructor
public class BeanProperty {

    private final String name;
    private final Class<?> type;
}
