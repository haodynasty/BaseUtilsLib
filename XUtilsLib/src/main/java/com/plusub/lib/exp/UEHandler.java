/*
 * FileName: UEHandler.java
 * Copyright (C) 2014 Plusub Tech. Co. Ltd. All Rights Reserved <admin@plusub.com>
 * 
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : quhao <blakequ@gmail.com>
 * date     : 2014-7-22 下午4:57:19
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.exp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

import com.plusub.lib.BaseApplication;
import com.plusub.lib.util.FileUtils;
import com.plusub.lib.util.LogUtils;
import com.plusub.lib.util.TimeUtils;

/**
 *  全局异常处理
 * @author blakequ Blakequ@gmail.com
 *
 */
public class UEHandler implements Thread.UncaughtExceptionHandler{

	private String savePath;
	private BaseApplication app;
	
	public UEHandler(String savePath, BaseApplication app){
		this.savePath = savePath;
		this.app = app;
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		// fetch Excpetion Info
		long threadId = thread.getId();
		String phoneInfo = "";
		try {
			phoneInfo = dumpPhoneInfo();
		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuilder info = new StringBuilder("------------"+TimeUtils.getDateCNNotSecond()+"-------------\n");
		info.append(phoneInfo);
		ByteArrayOutputStream baos = null;
		PrintStream printStream = null;
		try {
			baos = new ByteArrayOutputStream();
			printStream = new PrintStream(baos);
			ex.printStackTrace(printStream);
			byte[] data = baos.toByteArray();
			String str = new String(data);
			info.append(str);
			data = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (printStream != null) {
					printStream.close();
				}
				if (baos != null) {
					baos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			FileUtils.write(savePath, info.toString(), true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		LogUtils.e("UEHandler", "[UEHandler] Thread Name:"+thread.getName()+" Thread Id:"+threadId+" exception:"+info);
		app.doUncatchException(savePath, threadId, info.toString());
	}
	
	/**
	 * 保存手机信息
	 * <p>Title: dumpPhoneInfo
	 * <p>Description: 
	 * @param pw
	 * @throws NameNotFoundException
	 */
	@SuppressLint("NewApi")
	private String dumpPhoneInfo()
            throws NameNotFoundException {
		StringBuilder sb = new StringBuilder("\n");
        // 应用的版本名称和版本号
        PackageManager pm = app.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(app.getPackageName(),
                PackageManager.GET_ACTIVITIES);
        sb.append("App Version: ");
        sb.append(pi.versionName);
        sb.append('_');
        sb.append(pi.versionCode);
        sb.append("\n");

        // android版本号
        sb.append("OS Version: ");
        sb.append(Build.VERSION.RELEASE);
        sb.append("_");
        sb.append(Build.VERSION.SDK_INT);
        sb.append("\n");

        // 手机制造商
        sb.append("Vendor: ");
        sb.append(Build.MANUFACTURER);
        sb.append("\n");

        // 手机型号
        sb.append("Model: ");
        sb.append(Build.MODEL);
        sb.append("\n");

        // cpu架构
        sb.append("CPU ABI: ");
        sb.append(Build.CPU_ABI);
        sb.append("\n");
        return sb.toString();
    }
	
}
