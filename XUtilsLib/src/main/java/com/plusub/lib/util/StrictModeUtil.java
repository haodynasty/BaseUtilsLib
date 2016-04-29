package com.plusub.lib.util;

import android.os.Build;
import android.os.StrictMode;

import com.plusub.lib.BaseApplication;

/**
 * Copyright (C) quhao All Rights Reserved <blakequ@gmail.com>
 * <p/>
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * author  : quhao <blakequ@gmail.com>
 * date     : 2016/4/21 9:37
 * last modify author :
 * version : 1.0
 * description:严格模式，检测内存、硬盘等敏感操作，线程监控出现问题会出对话框提示
 */
public class StrictModeUtil {
    //默认开启
    private static boolean isShow = true;

    /**
     * 开启内存和VM严格模式
     */
    public static void init() {
        if (isShow && BaseApplication.DEBUG_MODE && Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            try{
                //线程监控，弹框penaltyDialog
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                        .detectAll()
                        .penaltyLog()
//                        .penaltyDialog()
                        .build());

                //VM监控
                StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                        .detectAll()
                        .penaltyLog()
                        .build());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭内存严格模式
     */
    public static void permitStrict(){
        if (isShow && BaseApplication.DEBUG_MODE && Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            try{
                //线程监控，会弹框
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                        .permitAll()
                        .build());

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
