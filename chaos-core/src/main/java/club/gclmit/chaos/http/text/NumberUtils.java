package club.gclmit.chaos.http.text;

import club.gclmit.chaos.http.lang.Assert;
import club.gclmit.chaos.http.lang.ConvertHandler;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 数字类型工具类
 *
 * @author gclm
 * @since 2020/9/27 17:20
 * @since 1.8
 */
public class NumberUtils extends org.springframework.util.NumberUtils {

    // round
    // -------------------------------------------------------------------------------------------

    /**
     * 保留固定位数小数<br>
     * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
     * 例如保留2位小数：123.456789 =》 123.46
     *
     * @param v     值
     * @param scale 保留小数位数
     * @return 新值
     */
    public static BigDecimal round(double v, int scale) {
        return round(v, scale, RoundingMode.HALF_UP);
    }

    /**
     * 保留固定位数小数<br>
     * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
     * 例如保留2位小数：123.456789 =》 123.46
     *
     * @param v     值
     * @param scale 保留小数位数
     * @return 新值
     */
    public static String roundStr(double v, int scale) {
        return round(v, scale).toString();
    }

    /**
     * 保留固定位数小数<br>
     * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
     * 例如保留2位小数：123.456789 =》 123.46
     *
     * @param numberStr 数字值的字符串表现形式
     * @param scale     保留小数位数
     * @return 新值
     */
    public static BigDecimal round(String numberStr, int scale) {
        return round(numberStr, scale, RoundingMode.HALF_UP);
    }

    /**
     * 保留固定位数小数<br>
     * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
     * 例如保留2位小数：123.456789 =》 123.46
     *
     * @param number 数字值
     * @param scale  保留小数位数
     * @return 新值
     * @since 4.1.0
     */
    public static BigDecimal round(BigDecimal number, int scale) {
        return round(number, scale, RoundingMode.HALF_UP);
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param v            值
     * @param scale        保留小数位数
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 新值
     */
    public static BigDecimal round(double v, int scale, RoundingMode roundingMode) {
        return round(Double.toString(v), scale, roundingMode);
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param v            值
     * @param scale        保留小数位数
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 新值
     * @since 3.2.2
     */
    public static String roundStr(double v, int scale, RoundingMode roundingMode) {
        return round(v, scale, roundingMode).toString();
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param numberStr    数字值的字符串表现形式
     * @param scale        保留小数位数，如果传入小于0，则默认0
     * @param roundingMode 保留小数的模式 {@link RoundingMode}，如果传入null则默认四舍五入
     * @return 新值
     */
    public static BigDecimal round(String numberStr, int scale, RoundingMode roundingMode) {
        Assert.notBlank(numberStr,"numberStr 不能为空");
        if (scale < 0) {
            scale = 0;
        }
        return round(ConvertHandler.toBigDecimal(numberStr), scale, roundingMode);
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param number       数字值
     * @param scale        保留小数位数，如果传入小于0，则默认0
     * @param roundingMode 保留小数的模式 {@link RoundingMode}，如果传入null则默认四舍五入
     * @return 新值
     */
    public static BigDecimal round(BigDecimal number, int scale, RoundingMode roundingMode) {
        if (null == number) {
            number = BigDecimal.ZERO;
        }
        if (scale < 0) {
            scale = 0;
        }
        if (null == roundingMode) {
            roundingMode = RoundingMode.HALF_UP;
        }

        return number.setScale(scale, roundingMode);
    }

}

