package com.plusub.lib.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * Copyright (C) BlakeQu All Rights Reserved <blakequ@gmail.com>
 * <p/>
 * Licensed under the blakequ.com License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * author  : quhao <blakequ@gmail.com>
 * date     : 2016/4/26 10:58
 * last modify author :
 * version : 1.0
 * description:用于回收的工具类
 */
public class GarbageUtils {

    /**
     * 获取根视图
     * @param context
     * @return if can not find view ,return null
     */
    public static View getRootView(Activity context)
    {
        if (context == null) return null;
        ViewGroup content = (ViewGroup) context.findViewById(android.R.id.content);
        if (content != null){
            return content.getChildAt(0);
        }
        return null;
    }

    /**
     * 递归释放所有子view涉及的图片，背景，DrawingCache，监听器等等资源,
     * 适用于让Activity成为一个不占资源的空壳，泄露了也不会导致图片资源被持有。
     * <a href=http://stackoverflow.com/questions/9461364/exception-in-unbinddrawables>stackover link<a/>
     * <a href=http://mp.weixin.qq.com/s?__biz=MzAwNDY1ODY2OQ==&mid=400656149&idx=1&sn=122b4f4965fafebf78ec0b4fce2ef62a&3rd=MzA3MDU4NTYzMw==&scene=6#rd>weixin link<a/>
     * @param view the root view of the layout
     */
    public static void unBindDrawables(View view) {
        if (view != null) {
            try {
                Drawable drawable = view.getBackground();
                if (drawable != null) {
                    drawable.setCallback(null);
                }
                if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    int viewGroupChildCount = viewGroup.getChildCount();
                    for (int j = 0; j < viewGroupChildCount; j++) {
                        unBindDrawables(viewGroup.getChildAt(j));
                    }
                    viewGroup.removeAllViews();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * Remove an onclick listener
     * @param view the root view of the layout
     */
    public static void unBindListener(View view) {
        if (view != null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                    if (view.hasOnClickListeners()) {
                        view.setOnClickListener(null);
                    }
                }else {
                    view.setOnClickListener(null);
                }

                if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    int viewGroupChildCount = viewGroup.getChildCount();
                    for (int i = 0; i < viewGroupChildCount; i++) {
                        unBindListener(viewGroup.getChildAt(i));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 清除画布，<a href=http://blog.csdn.net/yanzi1225627/article/details/8236309>info link<a/> <br>
     * @param mCanvas 画布，清除后会设置mCanvas为空
     */
    public static void cleanCanvas(Canvas mCanvas){
        if (mCanvas != null) {
            Paint paint = new Paint();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            mCanvas.drawPaint(paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            mCanvas = null;
        }
    }

    /**
     * 回收一个未被回收的Bitmap
     * @param bitmap
     */
    public static void doRecycledBitmap(Bitmap bitmap) {
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
}
