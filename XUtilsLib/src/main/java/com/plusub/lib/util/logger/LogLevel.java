package com.plusub.lib.util.logger;

/**
 * @author blakequ
 */
public enum LogLevel {

	/**Prints all logs*/
	FULL(0),
	/**print all log but not include info*/
	VERBOSE(1),
	/**any print warning and error*/
	WARNING(2),
	/**only print error*/
	ERROR(3),
	/**not print any log*/
	NONE(4);

	public int type;
	LogLevel(int type){
		this.type = type;
	}
}
