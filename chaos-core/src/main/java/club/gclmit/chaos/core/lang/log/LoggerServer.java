package club.gclmit.chaos.core.lang.log;

/**
 * <p>
 * log 常见服务
 * </p>
 *
 * @author gclm
 */
public enum LoggerServer {

    // ~ Chaos 组件
    // ===================================================================================================

    /**
     * Chaos
     */
    CHAOS("Chaos"),

    // ~ MVC 架构 Controller
    // ===================================================================================================

    /**
     * Controller
     */
    CONTROLLER("Controller"),

    /**
     * Interceptor
     */
    INTERCEPTOR("Interceptor"),

    /**
     * Filter
     */
    FILTER("Filter"),

    /**
     * Aspect
     */
    ASPECT("Aspect"),

    /**
     * config
     */
    CONFIG("config"),

    // ~ MVC 架构 Model
    // ===================================================================================================

    /**
     * pojo
     */
    POJO("pojo"),

    /**
     * entity
     */
    ENTITY("entity"),

    /**
     * DTO
     */
    DTO("DTO"),

    /**
     * VO
     */
    VO("VO"),

    /**
     * Param
     */
    PARAM("Param"),

    /**
     * Query
     */
    QUERY("Query"),


    // ~ MVC 架构
    // ===================================================================================================

    /**
     * Service
     */
    SERVICE("Service"),

    /**
     * Mapper
     */
    MAPPER("Mapper"),

    /**
     * Util
     */
    UTIL("Util");

    private String key;

    public String getKey() {
        return key;
    }

    private LoggerServer(String key) {
        this.key = key;
    }
}
