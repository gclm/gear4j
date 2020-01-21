package club.gclmit.chaos.security.core.properties;

/**
 * <p>
 *  SocialProperties 配置类
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/6 4:13 下午
 * @version: V1.0
 * @since 1.8
 */
public class SocialProperties {

    /**
     *  社交登陆前缀
     */
    private String filterProcessesUrl = "/auth";

    /**
     *  Spring Social 数据表前缀
     */
    private String tablePrefix = "chaos_";

    private QQProperties qq = new QQProperties();

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }
}
