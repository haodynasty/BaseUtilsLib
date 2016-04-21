package com.plusub.lib.util;

import java.text.DecimalFormat;

import android.content.Context;

/**
 * SizeUtils
 * 
 * <ul>
 * <strong>size transform</strong>
 * <li>{@link SizeUtils#byteToString(long size)}</li>
 * <li>{@link SizeUtils#getPercentString(long, long)}</li>
 * </ul>
 * @author blakequ Blakequ@gmail.com
 *
 */
public class SizeUtils {

	static final DecimalFormat DOUBLE_DECIMAL_FORMAT = new DecimalFormat("0.##");
    /** gb to byte **/
    public static final long GB_2_BYTE = 1024 * 1024 * 1024;
    /** mb to byte **/
    public static final long MB_2_BYTE = 1024 * 1024;
    /** kb to byte **/
    public static final long KB_2_BYTE = 1024;
    
    /**
     * 将字节转换为KB或MB
     * @param size
     * @return
     */
    public static CharSequence byteToString(long size) {
        if (size <= 0) {
            return "0M";
        }

        if (size >= MB_2_BYTE) {
            return new StringBuilder(16).append(DOUBLE_DECIMAL_FORMAT.format((double)size / MB_2_BYTE)).append("M");
        } else if (size >= KB_2_BYTE) {
            return new StringBuilder(16).append(DOUBLE_DECIMAL_FORMAT.format((double)size / KB_2_BYTE)).append("K");
        } else {
            return size + "B";
        }
    }

    /**
     * 百分比转换
     * <p>Title: getPercentString
     * <p>Description: 
     * @param progress
     * @param max
     * @return
     */
    public static String getPercentString(long progress, long max) {
        int rate = 0;
        if (progress <= 0 || max <= 0) {
            rate = 0;
        } else if (progress > max) {
            rate = 100;
        } else {
            rate = (int)((double)progress / max * 100);
        }
        return new StringBuilder(16).append(rate).append("%").toString();
    }
}
