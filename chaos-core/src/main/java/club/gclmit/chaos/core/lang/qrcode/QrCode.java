package club.gclmit.chaos.core.lang.qrcode;

import club.gclmit.chaos.core.util.CharsetUtils;

/**
 * 二维码工具类
 *
 * @author gclm
 */
public class QrCode {

    public static final String DEFAULT_CHARACTER_SET = CharsetUtils.UTF_8;

    private QrCode() {
    }

    public static GenerateQrCode generate() {
        return GenerateQrCode.getInstance();
    }

    public static DecodeQrCode decode() {
        return DecodeQrCode.getInstance();
    }
}
