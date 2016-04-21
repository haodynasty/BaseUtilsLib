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
package com.plusub.lib.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Parcelable;
import android.view.View;

/**
 * 
 * @ClassName: CaptureUtils
 * @Description: TODO 系统界面截图工具类
 * @author qh@plusub.com
 * @date 2014-12-1 下午10:02:51
 * @version v1.0
 */
public class CaptureUtils {
    /**
     * 截图
     * 
     * @param v
     *            需要进行截图的控件
     * @return 该控件截图的Bitmap对象。
     */
    public static Bitmap captureView(View v) {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        return v.getDrawingCache();
    }

    /**
     * 创建快捷方式
     * 
     * @param cxt
     *            Context
     * @param icon
     *            快捷方式图标
     * @param title
     *            快捷方式标题
     * @param cls
     *            要启动的类
     */
    public void createDeskShortCut(Context cxt, int icon,
            String title, Class<?> cls) {
        // 创建快捷方式的Intent
        Intent shortcutIntent = new Intent(
                "com.android.launcher.action.INSTALL_SHORTCUT");
        // 不允许重复创建
        shortcutIntent.putExtra("duplicate", false);
        // 需要现实的名称
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        // 快捷图片
        Parcelable ico = Intent.ShortcutIconResource.fromContext(
                cxt.getApplicationContext(), icon);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                ico);
        Intent intent = new Intent(cxt, cls);
        // 下面两个属性是为了当应用程序卸载时桌面上的快捷方式会删除
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        // 点击快捷图片，运行的程序主入口
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        // 发送广播。OK
        cxt.sendBroadcast(shortcutIntent);
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     * 
     * @param aty
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity aty) {
        View view = aty.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = DensityUtils.getScreenW(aty);
        int height = DensityUtils.getScreenH(aty);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     * 
     * @param aty
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity aty) {
        View view = aty.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        aty.getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = DensityUtils.getScreenW(aty);
        int height = DensityUtils.getScreenH(aty);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width,
                height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }
}
