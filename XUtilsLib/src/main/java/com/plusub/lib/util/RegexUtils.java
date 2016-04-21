package com.plusub.lib.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @author blakequ Blakequ@gmail.com
 *
 */
public class RegexUtils {

	/**
	 * 车牌号码Pattern
	 */
	public static final Pattern PLATE_NUMBER_PATTERN = Pattern
			.compile("^[\u0391-\uFFE5]{1}[a-zA-Z0-9]{6}$");

	/**
	 * 证件号码Pattern
	 */
	public static final Pattern ID_CODE_PATTERN = Pattern
			.compile("^[a-zA-Z0-9]+$");

	/**
	 * 编码Pattern
	 */
	public static final Pattern CODE_PATTERN = Pattern
			.compile("^[a-zA-Z0-9]+$");

	/**
	 * 固定电话编码Pattern
	 */
	public static final Pattern PHONE_NUMBER_PATTERN = Pattern
			.compile("0\\d{2,3}-[0-9]+");

	/**
	 * 邮政编码Pattern
	 */
	public static final Pattern POST_CODE_PATTERN = Pattern.compile("\\d{6}");

	/**
	 * 面积Pattern
	 */
	public static final Pattern AREA_PATTERN = Pattern.compile("\\d*.?\\d*");

	/**
	 * 手机号码Pattern
	 */
	public static final Pattern MOBILE_NUMBER_PATTERN = Pattern
			.compile("\\d{11}");

	/**
	 * 银行帐号Pattern
	 */
	public static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern
			.compile("\\d{16,21}");
	
	/**
	 * 数字+字母
	 */
	public static final Pattern NUMBER_AND_LETTER = Pattern.compile("[0-9A-Za-z]");
	
	/**
	 * email
	 */
	public static final Pattern EMAIL = Pattern.compile("\\w[\\w.-]*@[\\w.]+\\.\\w+");

	/**
	 * 检查数字+字母
	 * <p>Title: checkNumberAndLetter
	 * <p>Description: 
	 * @param str
	 * @return
	 */
	public static boolean checkNumberAndLetter(String str){
		Matcher matcher = NUMBER_AND_LETTER.matcher(str);
		return matcher.matches();
	}
	
	/**
	 * 数字
	 * <p>Title: isNumeric
	 * <p>Description: 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {   
        Pattern pattern = Pattern.compile("[0-9]*");   
        Matcher isNum = pattern.matcher(str);   
        return isNum.matches();
    }
	
	/**
	 * 车牌号码是否正确
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isPlateNumber(String s) {
		Matcher m = PLATE_NUMBER_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * 证件号码是否正确
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isIDCode(String s) {
		Matcher m = ID_CODE_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * 编码是否正确
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isCode(String s) {
		Matcher m = CODE_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * 固话编码是否正确
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isPhoneNumber(String s) {
		Matcher m = PHONE_NUMBER_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * 邮政编码是否正确
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isPostCode(String s) {
		Matcher m = POST_CODE_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * 面积是否正确
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isArea(String s) {
		Matcher m = AREA_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * 手机号码否正确(是否为11位数字)
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isMobileNumberSimple(String s) {
		Matcher m = MOBILE_NUMBER_PATTERN.matcher(s);
		return m.matches();
	}
	
	/**
	 * 严格条件验证，是否为手机号码pattern="^[1][0-9][0-9]{9}$";
	 * <p>Title: isMobileNumber2
	 * <p>Description: 
	 * @param phoneNum
	 * @return
	 */
	public static boolean isMobileNumber(String phoneNum) {
		String pattern="^[1][0-9][0-9]{9}$";
		Pattern r=Pattern.compile(pattern);
		Matcher m=r.matcher(phoneNum);
		
		if (m.find()) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 严格座机号码验证
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhoneNumber2(String str) {
		Pattern p1 = null, p2 = null, p3 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
		p3 = Pattern.compile("^[0][1-9]{2,3}[0-9]{5,10}$"); // 验证带区号的(不带横-)
		if (str.length() > 9) {
			if (str.contains("-")) {
				m = p1.matcher(str);
				b = m.matches();
			}else{
				m = p3.matcher(str);
				b = m.matches();
			}
		} else {
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}

	/**
	 * 银行账号否正确
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isAccountNumber(String s) {
		Matcher m = ACCOUNT_NUMBER_PATTERN.matcher(s);
		return m.matches();
	}
	
	/**
	 * 是否为邮箱
	 * <p>Title: isEmail
	 * <p>Description: 
	 * @param s
	 * @return
	 */
	public static boolean isEmail(String s){
		Matcher m = EMAIL.matcher(s);
		return m.matches();
	}

}
