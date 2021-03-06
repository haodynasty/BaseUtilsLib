/*
 * FileName: IActivity.java
 * Copyright (C) 2014 Plusub Tech. Co. Ltd. All Rights Reserved <admin@plusub.com>
 * 
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : service@plusub.com
 * date     : 2014-12-1 上午11:16:49
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.util.bitmap;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;

import com.plusub.lib.other.LruMemoryCache;
import com.plusub.lib.util.SystemUtils;

/**
 * 使用lru算法的Bitmap内存缓存池<br>
 * 
 * <b>创建时间</b> 2014-7-11
 * 
 * @version 1.0
 * @author kymjs(kymjs123@gmail.com)
 */
public final class BitmapMemoryCache {

    private LruMemoryCache<String, Bitmap> cache;

    public BitmapMemoryCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        init(maxMemory / 8);
    }

    /**
     * @param maxSize
     *            使用内存缓存的内存大小，单位：kb
     */
    public BitmapMemoryCache(int maxSize) {
        init(maxSize);
    }

    /**
     * @param maxSize
     *            使用内存缓存的内存大小，单位：kb
     */
    @SuppressLint("NewApi")
    private void init(int maxSize) {
        cache = new LruMemoryCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                super.sizeOf(key, value);
                if (SystemUtils.getSDKVersion() >= 12) {
                    return value.getByteCount() / 1024;
                } else {
                    return value.getRowBytes() * value.getHeight() / 1024;
                }
            }
        };
    }

    public void put(String key, Bitmap bitmap) {
        if (this.get(key) == null) {
            cache.put(key, bitmap);
        }
    }

    public Bitmap get(String key) {
        return cache.get(key);
    }
}
