package com.plusub.lib.util;

/**
 * Object Utils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-10-24
 */
public class ObjectUtils {

    /**
     * 将整型对象转换为boolean
     * <p>Title: num
     * <p>Description:
     * @param o
     * @return o大于0返回true， 否则返回false
     */
    public static boolean objNumberToBoolean(Object o) {
        int n = 0;
        try {
            n = Integer.parseInt(o.toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将浮点对象转换为boolean
     * <p>Title: objDecimalToBoolean
     * <p>Description:
     * @param o
     * @return o大于0返回true， 否则返回false
     */
    public static boolean objDecimalToBoolean(Object o) {
        double n = 0;
        try {
            n = Double.parseDouble(o.toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        if (n > 0.0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * obj为0（或者""）转换为false, 否则true
     * <p>Title: stringToBoolean
     * <p>Description:
     * @return obj对象为int类型，等于0返回false，否则返回true
     */
    public static boolean objToBoolean(Object obj){
        boolean result = false;
        if (obj != null) {
            try{
                int value = Integer.parseInt(obj.toString().trim());
                if (value == 0) {
                    result = false;
                }else{
                    result = true;
                }
            }catch(NumberFormatException ex){
                ex.printStackTrace();
                result = false;
            }
        }
        return result;
    }

    /**
     * object is null
     * <p>Title: empty
     * <p>Description:
     * @param o
     * @return
     */
    public static boolean isEmpty(Object o) {
        return o == null || "".equals(o.toString().trim())
                || "null".equalsIgnoreCase(o.toString().trim());
    }

    /**
     * compare two object
     * 
     * @param actual
     * @param expected
     * @return <ul>
     *         <li>if both are null, return true</li>
     *         <li>return actual.{@link Object#equals(Object)}</li>
     *         </ul>
     */
    public static boolean isEquals(Object actual, Object expected) {
        return actual == expected || (actual == null ? expected == null : actual.equals(expected));
    }

    /**
     * convert long array to Long array
     * 
     * @param source
     * @return
     */
    public static Long[] transformLongArray(long[] source) {
        Long[] destin = new Long[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i];
        }
        return destin;
    }

    /**
     * convert Long array to long array
     * 
     * @param source
     * @return
     */
    public static long[] transformLongArray(Long[] source) {
        long[] destin = new long[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i];
        }
        return destin;
    }

    /**
     * convert int array to Integer array
     * 
     * @param source
     * @return
     */
    public static Integer[] transformIntArray(int[] source) {
        Integer[] destin = new Integer[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i];
        }
        return destin;
    }

    /**
     * convert Integer array to int array
     * 
     * @param source
     * @return
     */
    public static int[] transformIntArray(Integer[] source) {
        int[] destin = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i];
        }
        return destin;
    }

    /**
     * compare two object
     * <ul>
     * <strong>About result</strong>
     * <li>if v1 > v2, return 1</li>
     * <li>if v1 = v2, return 0</li>
     * <li>if v1 < v2, return -1</li>
     * </ul>
     * <ul>
     * <strong>About rule</strong>
     * <li>if v1 is null, v2 is null, then return 0</li>
     * <li>if v1 is null, v2 is not null, then return -1</li>
     * <li>if v1 is not null, v2 is null, then return 1</li>
     * <li>return v1.{@link Comparable#compareTo(Object)}</li>
     * </ul>
     * 
     * @param v1
     * @param v2
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <V> int compare(V v1, V v2) {
        return v1 == null ? (v2 == null ? 0 : -1) : (v2 == null ? 1 : ((Comparable)v1).compareTo(v2));
    }
}
