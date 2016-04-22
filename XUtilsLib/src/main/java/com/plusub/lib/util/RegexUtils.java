package com.plusub.lib.util;

import com.plusub.lib.util.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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


	/**
	 * 不为空时，验证str是否为正确的身份证格式
	 *
	 * @param str
	 * @return
	 */
	public static boolean isIdentityCard(String str) {
		boolean flag = true;
		if (!StringUtils.isEmpty(str)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			/*
			 * { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
			 * 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
			 * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",
			 * 42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
			 * 51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",
			 * 63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外" }
			 */
			String provinces = "11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91";
			//最简单的匹配：^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X|x)$ //分6位+4位年+2位月+2位日+3位数字+最后一位数字或字母x，X
			Pattern pattern = Pattern.compile("^[1-9]\\d{14}");//第一位为数字1-9，后面14位为数字
			Matcher matcher = pattern.matcher(str);
			Pattern pattern2 = Pattern.compile("^[1-9](\\d{16})([0-9]|x|X)$");//第一位为1-9，中间16位为数字，最后一位为数字或x,X
			Matcher matcher2 = pattern2.matcher(str);
			Pattern pattern3 = Pattern.compile("[\\d,x,X]$");//最后一位为数字或x,X
			Matcher matcher3 = pattern3.matcher(str);
			// 粗略判断
			if (!matcher.find() && !matcher2.find()) {
				Logger.e("RegexUtils", "身份证号必须为15或18位数字（最后一位可以为x或X）");
				flag = false;
			} else {
				// 判断出生地
				if (provinces.indexOf(str.substring(0, 2)) == -1) {
					Logger.e("RegexUtils", "身份证号前两位不正确！");
					flag = false;
				}

				// 判断出生日期
				if (str.length() == 15) {
					String birth = "19" + str.substring(6, 8) + "-"
							+ str.substring(8, 10) + "-"
							+ str.substring(10, 12);
					try {
						Date birthday = sdf.parse(birth);
						if (!sdf.format(birthday).equals(birth)) {
							Logger.e("RegexUtils", "出生日期非法！");
							flag = false;
						}
						if (birthday.after(new Date())) {
							Logger.e("RegexUtils", "出生日期不能在今天之后！");
							flag = false;
						}
					} catch (ParseException e) {
						Logger.e("RegexUtils", "出生日期非法！");
						flag = false;
					}
				} else if (str.length() == 18) {
					String birth = str.substring(6, 10) + "-"
							+ str.substring(10, 12) + "-"
							+ str.substring(12, 14);
					try {
						Date birthday = sdf.parse(birth);
						if (!sdf.format(birthday).equals(birth)) {
							Logger.e("RegexUtils", "出生日期非法！");
							flag = false;
						}
						if (birthday.after(new Date())) {
							Logger.e("RegexUtils", "出生日期不能在今天之后！");
							flag = false;
						}
					} catch (ParseException e) {
						Logger.e("RegexUtils", "出生日期非法！");
						flag = false;
					}

					if (!matcher3.find()) {
						Logger.e("RegexUtils", "身份证号最后一位只能为数字或字母x,X！");
						flag = false;
					}
				} else {
					Logger.e("RegexUtils", "身份证号位数不正确，请确认！");
					flag = false;
				}
			}
		}
		return flag;
	}
}
