package club.gclmit.chaos.core.util;

import lombok.experimental.UtilityClass;

/**
 * <p>
 *  UUID 生成器
 * </p>
 *
 * @author gclm
 */
@UtilityClass
public class UUIDUtils {

    private final static byte[] DIGITS = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z'
    };

    /**
     *  构建 uuid 因子
     *
     * @author gclm
     * @param: val
     * @param: buf
     * @param: offset
     * @param: len
     */
    private static void buildUUIDFactor(long val, byte[] buf, int offset, int len) {
        int charPos = offset + len;
        int radix = 1 << 4;
        int mask = radix - 1;
        do {
            buf[--charPos] = DIGITS[((int) val) & mask];
            val >>>= 4;
        } while (charPos > offset);
    }

    /**
     *  无符号uuid
     *
     * @author gclm
     * @return: java.lang.String
     */
    public static String getSimpleUUID(){
        return StringUtils.replace(getUUID(),"-","");
    }

    /**
     *  uuid
     *
     * @author gclm
     * @return: java.lang.String
     */
    public static String getUUID() {
        long lsb = RandomUtils.randomLong();
        long msb = RandomUtils.randomLong();
        byte[] buf = new byte[36];
        buf[8] = '-';
        buf[13] = '-';
        buf[18] = '-';
        buf[23] = '-';

        buildUUIDFactor(lsb, buf, 24, 12);
        buildUUIDFactor(lsb >>> 48, buf, 19, 4);
        buildUUIDFactor(msb, buf, 14, 4);
        buildUUIDFactor(msb >>> 16, buf, 9, 4);
        buildUUIDFactor(msb >>> 32, buf, 0, 8);
        return new String(buf, CharsetUtils.CHARSET_UTF_8);
    }
}
