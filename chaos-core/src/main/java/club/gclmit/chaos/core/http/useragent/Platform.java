package club.gclmit.chaos.core.http.useragent;

import cn.hutool.core.collection.CollUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 平台对象
 *
 * @author gclm
 * @author looly
 */
public class Platform extends UserAgentInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 未知
     */
    public static final Platform PLATFORM_UNKNOWN = new Platform(NAME_UNKNOWN, null);

    /**
     * Iphone
     */
    public static final Platform IPHONE = new Platform("iPhone", "iphone");
    /**
     * ipod
     */
    public static final Platform IPOD = new Platform("iPod", "ipod");
    /**
     * ipad
     */
    public static final Platform IPAD = new Platform("iPad", "ipad");

    /**
     * android
     */
    public static final Platform ANDROID = new Platform("Android", "android");
    /**
     * android
     */
    public static final Platform GOOGLE_TV = new Platform("GoogleTV", "googletv");

    /**
     * Windows Phone
     */
    public static final Platform WINDOWS_PHONE = new Platform("Windows Phone", "windows (ce|phone|mobile)( os)?");

    /**
     * 支持的移动平台类型
     */
    public static final List<Platform> MOBILE_PLATFORMS = CollUtil.newArrayList(
            WINDOWS_PHONE,
            IPAD,
            IPOD,
            IPHONE,
            ANDROID,
            GOOGLE_TV,
            new Platform("htcFlyer", "htc_flyer"),
            new Platform("Symbian", "symbian(os)?"),
            new Platform("Blackberry", "blackberry")
    );

    /**
     * 支持的桌面平台类型
     */
    public static final List<Platform> DESKTOP_PLATFORMS = CollUtil.newArrayList(
            new Platform("Windows", "windows"),
            new Platform("Mac", "(macintosh|darwin)"),
            new Platform("Linux", "linux"),
            new Platform("Wii", "wii"),
            new Platform("Playstation", "playstation"),
            new Platform("Java", "java")
    );

    /**
     * 支持的平台类型
     */
    public static final List<Platform> PLATFORMS;

    static {
        PLATFORMS = new ArrayList<>(13);
        PLATFORMS.addAll(MOBILE_PLATFORMS);
        PLATFORMS.addAll(DESKTOP_PLATFORMS);
    }

    /**
     * 构造
     *
     * @param name  平台名称
     * @param regex 关键字或表达式
     */
    public Platform(String name, String regex) {
        super(name, regex);
    }

    /**
     * 是否为移动平台
     *
     * @return 是否为移动平台
     */
    public boolean isMobile() {
        return MOBILE_PLATFORMS.contains(this);
    }

    /**
     * 是否为Iphone或者iPod设备
     *
     * @return 是否为Iphone或者iPod设备
     */
    public boolean isIPhoneOrIPod() {
        return this.equals(IPHONE) || this.equals(IPOD);
    }

    /**
     * 是否为Iphone或者iPod设备
     *
     * @return 是否为Iphone或者iPod设备
     */
    public boolean isIPad() {
        return this.equals(IPAD);
    }

    /**
     * 是否为IOS平台，包括IPhone、IPod、IPad
     *
     * @return 是否为IOS平台，包括IPhone、IPod、IPad
     */
    public boolean isIos() {
        return isIPhoneOrIPod() || isIPad();
    }

    /**
     * 是否为Android平台，包括Android和Google TV
     *
     * @return 是否为Android平台，包括Android和Google TV
     */
    public boolean isAndroid() {
        return this.equals(ANDROID) || this.equals(GOOGLE_TV);
    }

}
