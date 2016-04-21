package com.plusub.lib.util;

/**
 * @author blakequ Blakequ@gmail.com
 *
 */
public class CommException  extends Exception {
	private int statusCode = -1;
	private static final long serialVersionUID = 1L;
	
	public CommException(String msg) {
        super(msg);
    }

    public CommException(Exception cause) {
        super(cause);
    }

    public CommException(String msg, int statusCode) {
        super(msg);
        this.statusCode = statusCode;

    }

    public CommException(String msg, Exception cause) {
        super(msg, cause);
    }

    public CommException(String msg, Exception cause, int statusCode) {
        super(msg, cause);
        this.statusCode = statusCode;

    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
