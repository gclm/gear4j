package club.gclmit.gear4j.cos.provider;

import club.gclmit.gear4j.core.exception.Gear4jException;
import club.gclmit.gear4j.cos.domain.CosProvider;
import club.gclmit.gear4j.cos.domain.CosProviderType;
import club.gclmit.gear4j.cos.provider.impl.*;

/**
 * 基于构造器模式构建对象
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/28 13:38
 * @since jdk11
 */
public class CosClientBuilder {

    public static CosProvider.CosProviderBuilder provider() {
        return new CosProvider.CosProviderBuilder();
    }

    public static CosClient getClient(CosProvider provider) {
        if (CosProviderType.ALIYUN.getCode().equals(provider.getProvider())) {
            return new AliyunCosClient(provider);
        } else if (CosProviderType.GO_FASTDFS.getCode().equals(provider.getProvider())) {
            return new GoFastdfsCosClient(provider);
        } else if (CosProviderType.HUAWEI.getCode().equals(provider.getProvider())) {
            return new HuaweiCosClient(provider);
        } else if (CosProviderType.QCLOUD.getCode().equals(provider.getProvider())) {
            return new QcloudCosClient(provider);
        } else if (CosProviderType.QINIU.getCode().equals(provider.getProvider())) {
            return new QiniuCosClient(provider);
        } else if (CosProviderType.UCLOUD.getCode().equals(provider.getProvider())) {
            return new UcloudCosClient(provider);
        } else if (CosProviderType.UPYUN.getCode().equals(provider.getProvider())) {
            return new UpyunCosClient(provider);
        } else {
            throw new Gear4jException("gear4j-cos: Provider参数异常,请仔细检查配置参数");
        }
    }
}
