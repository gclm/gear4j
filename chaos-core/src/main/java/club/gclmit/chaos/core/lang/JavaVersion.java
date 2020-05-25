package club.gclmit.chaos.core.lang;

import club.gclmit.chaos.core.util.NumberUtils;

/**
 * <p>
 * Java 判断枚举
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/21 10:34 上午
 * @version: V1.0
 * @since 1.8
 */
public enum JavaVersion {

    /**
     * JDK 1.1
     */
    JAVA_1_1(1.1f, "1.1"),
    /**
     * JDK 1.2
     */
    JAVA_1_2(1.2f, "1.2"),
    /**
     * JDK 1.3
     */
    JAVA_1_3(1.3f, "1.3"),
    /**
     * JDK 1.4
     */
    JAVA_1_4(1.4f, "1.4"),
    /**
     * JDK 1.5
     */
    JAVA_1_5(1.5f, "1.5"),
    /**
     * JDK 1.6
     */
    JAVA_1_6(1.6f, "1.6"),
    /**
     * JDK 1.7
     */
    JAVA_1_7(1.7f, "1.7"),
    /**
     * JDK 1.8
     */
    JAVA_1_8(1.8f, "1.8"),
    /**
     * JDK 9
     */
    JAVA_9(9.0f, "9"),
    /**
     * JDK 10
     */
    JAVA_10(10.0f, "10"),
    /**
     * JDK 11
     */
    JAVA_11(11.0f, "11"),
    /**
     * JDK 12
     */
    JAVA_12(12.0f, "12"),
    /**
     * JDK 13
     */
    JAVA_13(13.0f, "13"),

    /**
     * JDK
     */
    JAVA_RECENT(maxVersion(), Float.toString(maxVersion()));


    private final float value;
    private final String name;

    private JavaVersion(float value, String name) {
        this.value = value;
        this.name = name;
    }

    public boolean atLeast(JavaVersion requiredVersion) {
        return this.value >= requiredVersion.value;
    }

    public boolean atMost(JavaVersion requiredVersion) {
        return this.value <= requiredVersion.value;
    }

    static JavaVersion getJavaVersion(String nom) {
        return get(nom);
    }

    public static JavaVersion get(String nom) {
        if (nom == null) {
            return null;
        } else if ("1.1".equals(nom)) {
            return JAVA_1_1;
        } else if ("1.2".equals(nom)) {
            return JAVA_1_2;
        } else if ("1.3".equals(nom)) {
            return JAVA_1_3;
        } else if ("1.4".equals(nom)) {
            return JAVA_1_4;
        } else if ("1.5".equals(nom)) {
            return JAVA_1_5;
        } else if ("1.6".equals(nom)) {
            return JAVA_1_6;
        } else if ("1.7".equals(nom)) {
            return JAVA_1_7;
        } else if ("1.8".equals(nom)) {
            return JAVA_1_8;
        } else if ("9".equals(nom)) {
            return JAVA_9;
        } else if ("10".equals(nom)) {
            return JAVA_10;
        } else if ("11".equals(nom)) {
            return JAVA_11;
        } else if ("12".equals(nom)) {
            return JAVA_12;
        } else if ("13".equals(nom)) {
            return JAVA_13;
        } else {
            float v = toFloatVersion(nom);
            if ((double) v - 1.0D < 1.0D) {
                int firstComma = Math.max(nom.indexOf(46), nom.indexOf(44));
                int end = Math.max(nom.length(), nom.indexOf(44, firstComma));
                if (Float.parseFloat(nom.substring(firstComma + 1, end)) > 0.9F) {
                    return JAVA_RECENT;
                }
            } else if (v > 10.0F) {
                return JAVA_RECENT;
            }

            return null;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static float maxVersion() {
        float v = toFloatVersion(System.getProperty("java.specification.version", "99.0"));
        return v > 0.0F ? v : 99.0F;
    }

    private static float toFloatVersion(String value) {
        boolean defaultReturnValue = true;
        if (value.contains(".")) {
            String[] toParse = value.split("\\.");
            return toParse.length >= 2 ? NumberUtils.toFloat(toParse[0] + '.' + toParse[1], -1.0F) : -1.0F;
        } else {
            return NumberUtils.toFloat(value, -1.0F);
        }
    }

    public String generate(JavaVersion version) {
        //        JAVA_0_9(1.5F, "0.9")
        String template = " /**\n" +
                "     * JDK %s\n" +
                "     */\n" +
                "    %s(%sf,\"%s\"),";
        return String.format(template, version.name, version.name(), version.value, version.name);
    }

    public static void main(String[] args) {
        JavaVersion[] versions = values();
        for (JavaVersion version : versions) {
            System.out.println(version.generate(version));
        }
    }
}
