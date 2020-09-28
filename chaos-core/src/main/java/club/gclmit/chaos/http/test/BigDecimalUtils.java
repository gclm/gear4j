package club.gclmit.chaos.http.test;

import java.math.BigDecimal;

/**
 * <p>
 * BigDecimal 工具类
 * </p>
 *
 * @author gclm
 * @since 1.5.1
 */
public class BigDecimalUtils {

    private BigDecimalUtils() {
    }

    /**
     * <p>
     * 加法
     * </p>
     *
     * @param v1 第一个数
     * @param v2 第二个数
     * @return java.math.BigDecimal
     * @author gclm
     */
    public static BigDecimal add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    /**
     * <p>
     * 减法
     * </p>
     *
     * @param v1 第一个数
     * @param v2 第二个数
     * @return java.math.BigDecimal
     * @author gclm
     */
    public static BigDecimal sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }

    /**
     * <p>
     * 除法
     * </p>
     *
     * @param v1 第一个数
     * @param v2 第二个数
     * @return java.math.BigDecimal
     * @author gclm
     */
    public static BigDecimal mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    /**
     * <p>
     * 除法
     * </p>
     *
     * @param v1 第一个数
     * @param v2 第二个数
     * @return java.math.BigDecimal
     * @author gclm
     */
    public static BigDecimal div(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        // 2 = 保留小数点后两位   ROUND_HALF_UP = 四舍五入
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);// 应对除不尽的情况
    }
}
