package com.plusub.lib.constant;

/**
 * 系统错误码描述
 * @author blakequ Blakequ@gmail.com
 *
 */
public class ErrorCode {
	//默认，没有错误
	private String codeDescripte;
	private int errorCode;
	
	public ErrorCode(int errorCode, String codeDescripte){
		this.errorCode = errorCode;
		this.codeDescripte = codeDescripte;
	}
	
	public String getCodeDescripte() {
		return codeDescripte;
	}
	public void setCodeDescripte(String codeDescripte) {
		this.codeDescripte = codeDescripte;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}


	/**
	 * 默认值，没有错误
	 */
	public static final int DEFAULT_VALUE = 1000;
	
	//参数错误
	public static final int PARA_EXCEPTION = 100;//参数错误
	public static final int SERVER_NO_DATA = 101;//服务器没有更多数据
	
	//解析错误
	public static final int PARSER_JSON_EXCEPTION = 200;
	public static final int PARSER_CLASS_NOT_FOUND = 201;
	public static final int PARSER_SERVER_MEDIA_LOAD_FAIL = 202;//媒体数据加载失败
	
	//net error
	public static final int NET_LINK_EXCEPTION = 300;
	/**session out of time*/
	public static final int SESSION_OUT_OF_TIME = 302; //session失效
	
	//其他错误
	public static final int OTHER_DEFAULT_EXCEPTION = 400;
	public static final int OTHER_TASK_NOT_FOUND = 401;
	
	
}
