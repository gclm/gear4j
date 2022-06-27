package club.gclmit.gear4j.storage.model.properties;

import java.util.List;

/**
 * 云存储客户端配置信息封装
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/27 13:19
 * @since jdk11
 */
public class StorageProperties {

    private static final long serialVersionUID = 1L;

    private List<ProviderProperties> providers;

    public List<ProviderProperties> getProviders() {
        return providers;
    }

    public void setProviders(List<ProviderProperties> providers) {
        this.providers = providers;
    }
}
