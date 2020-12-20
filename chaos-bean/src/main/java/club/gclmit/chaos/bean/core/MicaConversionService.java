package club.gclmit.chaos.bean.core;

import club.gclmit.chaos.bean.convert.EnumToStringConverter;
import club.gclmit.chaos.bean.convert.StringToEnumConverter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.lang.Nullable;

/**
 * 类型 转换 服务，添加了 IEnum 转换
 *
 * @author L.cm
 * @author gclm
 */
public class MicaConversionService extends DefaultFormattingConversionService {
    @Nullable
    private static volatile MicaConversionService SHARED_INSTANCE;

    public MicaConversionService() {
        super();
        super.addConverter(new EnumToStringConverter());
        super.addConverter(new StringToEnumConverter());
    }

    /**
     * Return a shared default application {@code ConversionService} instance, lazily
     * building it once needed.
     * <p>
     * Note: This method actually returns an {@link MicaConversionService}
     * instance. However, the {@code ConversionService} signature has been preserved for
     * binary compatibility.
     *
     * @return the shared {@code MicaConversionService} instance (never{@code null})
     */
    public static GenericConversionService getInstance() {
        MicaConversionService sharedInstance = MicaConversionService.SHARED_INSTANCE;
        if (sharedInstance == null) {
            synchronized (MicaConversionService.class) {
                sharedInstance = MicaConversionService.SHARED_INSTANCE;
                if (sharedInstance == null) {
                    sharedInstance = new MicaConversionService();
                    MicaConversionService.SHARED_INSTANCE = sharedInstance;
                }
            }
        }
        return sharedInstance;
    }

}

