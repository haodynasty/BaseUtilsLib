package com.plusub.lib.util;

import android.app.Activity;
import android.content.Context;

import com.plusub.lib.net.Caller;

/**
 * CacheManager
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-11
 */
public class CacheManager {

    private static CacheUtils httpCache = null;

    private CacheManager() {

    }

    /**
     * get the singleton instance of HttpCache
     * 
     * @param context {@link Activity#getApplicationContext()}
     * @return
     */
    public static CacheUtils getHttpCache(Context context) {
    	return Caller.getRequestCache(context);
    }

}
