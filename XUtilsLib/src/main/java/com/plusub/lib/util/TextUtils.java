package com.plusub.lib.util;


/**
 * 文本处理
 * @author blakequ Blakequ@gmail.com
 *
 */
public class TextUtils {
	
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
	 * @param str
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
	 * 处理空字符串
	 * 
	 * @param str
	 * @return String
	 */
	public static String doEmpty(String str) {
		return doEmpty(str, "");
	}

	/**
	 * 处理空字符串
	 * 
	 * @param str
	 * @param defaultValue
	 * @return String
	 */
	public static String doEmpty(String str, String defaultValue) {
		if (str == null || str.equalsIgnoreCase("null")
				|| str.trim().equals("")) {
			str = defaultValue;
		} else if (str.startsWith("null")) {
			str = str.substring(4, str.length());
		}
		return str.trim();
	}

	public static boolean notEmpty(Object o) {
		return o != null && !"".equals(o.toString().trim())
				&& !"null".equalsIgnoreCase(o.toString().trim());
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
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }
    
    /**
     * Returns the length that the specified CharSequence would have if
     * spaces and control characters were trimmed from the start and end,
     * as by {@link String#trim}.
     */
    public static int getTrimmedLength(CharSequence s) {
        int len = s.length();

        int start = 0;
        while (start < len && s.charAt(start) <= ' ') {
            start++;
        }

        int end = len;
        while (end > start && s.charAt(end - 1) <= ' ') {
            end--;
        }

        return end - start;
    }
    
    /**
     * Returns true if a and b are equal, including if they are both null.
     * <p><i>Note: In platform versions 1.1 and earlier, this method only worked well if
     * both the arguments were instances of String.</i></p>
     * @param a first CharSequence to check
     * @param b second CharSequence to check
     * @return true if a and b are equal
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }
}
