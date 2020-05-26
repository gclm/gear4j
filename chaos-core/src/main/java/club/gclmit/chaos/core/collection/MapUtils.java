package club.gclmit.chaos.core.collection;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Map;

/**
 * <p>
 * Map 工具类
 * </p>
 *
 * @author gclm
 */
public class MapUtils {


    public static boolean isEmpty(Map map){
        return map.isEmpty() || 0 == map.size();
    }

    public static boolean isNotEmpty(Map map){
        return false == isEmpty(map);
    }

    // Type safe getters
    //-------------------------------------------------------------------------
    /**
     * Gets from a Map in a null-safe manner.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map, <code>null</code> if null map input
     */
    public static Object getObject(final Map map, final Object key) {
        if (map != null) {
            return map.get(key);
        }
        return null;
    }

    /**
     * Gets a String from a Map in a null-safe manner.
     * <p>
     * The String is obtained via <code>toString</code>.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a String, <code>null</code> if null map input
     */
    public static String getString(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                return answer.toString();
            }
        }
        return null;
    }

    /**
     * Gets a Boolean from a Map in a null-safe manner.
     * <p>
     * If the value is a <code>Boolean</code> it is returned directly.
     * If the value is a <code>String</code> and it equals 'true' ignoring case
     * then <code>true</code> is returned, otherwise <code>false</code>.
     * If the value is a <code>Number</code> an integer zero value returns
     * <code>false</code> and non-zero returns <code>true</code>.
     * Otherwise, <code>null</code> is returned.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a Boolean, <code>null</code> if null map input
     */
    public static Boolean getBoolean(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                if (answer instanceof Boolean) {
                    return (Boolean) answer;

                } else if (answer instanceof String) {
                    return new Boolean((String) answer);

                } else if (answer instanceof Number) {
                    Number n = (Number) answer;
                    return (n.intValue() != 0) ? Boolean.TRUE : Boolean.FALSE;
                }
            }
        }
        return null;
    }

    /**
     * Gets a Number from a Map in a null-safe manner.
     * <p>
     * If the value is a <code>Number</code> it is returned directly.
     * If the value is a <code>String</code> it is converted using
     * {@link NumberFormat#parse(String)} on the system default formatter
     * returning <code>null</code> if the conversion fails.
     * Otherwise, <code>null</code> is returned.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a Number, <code>null</code> if null map input
     */
    public static Number getNumber(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                if (answer instanceof Number) {
                    return (Number) answer;

                } else if (answer instanceof String) {
                    try {
                        String text = (String) answer;
                        return NumberFormat.getInstance().parse(text);

                    } catch (ParseException e) {
                        // failure means null is returned
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets a Byte from a Map in a null-safe manner.
     * <p>
     * The Byte is obtained from the results of {@link #getNumber(Map,Object)}.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a Byte, <code>null</code> if null map input
     */
    public static Byte getByte(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Byte) {
            return (Byte) answer;
        }
        return new Byte(answer.byteValue());
    }

    /**
     * Gets a Short from a Map in a null-safe manner.
     * <p>
     * The Short is obtained from the results of {@link #getNumber(Map,Object)}.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a Short, <code>null</code> if null map input
     */
    public static Short getShort(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Short) {
            return (Short) answer;
        }
        return new Short(answer.shortValue());
    }

    /**
     * Gets a Integer from a Map in a null-safe manner.
     * <p>
     * The Integer is obtained from the results of {@link #getNumber(Map,Object)}.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a Integer, <code>null</code> if null map input
     */
    public static Integer getInteger(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Integer) {
            return (Integer) answer;
        }
        return new Integer(answer.intValue());
    }

    /**
     * Gets a Long from a Map in a null-safe manner.
     * <p>
     * The Long is obtained from the results of {@link #getNumber(Map,Object)}.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a Long, <code>null</code> if null map input
     */
    public static Long getLong(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Long) {
            return (Long) answer;
        }
        return new Long(answer.longValue());
    }

    /**
     * Gets a Float from a Map in a null-safe manner.
     * <p>
     * The Float is obtained from the results of {@link #getNumber(Map,Object)}.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a Float, <code>null</code> if null map input
     */
    public static Float getFloat(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Float) {
            return (Float) answer;
        }
        return new Float(answer.floatValue());
    }

    /**
     * Gets a Double from a Map in a null-safe manner.
     * <p>
     * The Double is obtained from the results of {@link #getNumber(Map,Object)}.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a Double, <code>null</code> if null map input
     */
    public static Double getDouble(final Map map, final Object key) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        } else if (answer instanceof Double) {
            return (Double) answer;
        }
        return new Double(answer.doubleValue());
    }

    /**
     * Gets a Map from a Map in a null-safe manner.
     * <p>
     * If the value returned from the specified map is not a Map then
     * <code>null</code> is returned.
     *
     * @param map  the map to use
     * @param key  the key to look up
     * @return the value in the Map as a Map, <code>null</code> if null map input
     */
    public static Map getMap(final Map map, final Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null && answer instanceof Map) {
                return (Map) answer;
            }
        }
        return null;
    }

}