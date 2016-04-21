/*
 * FileName: BaseMethod.java
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
 * date     : 2014-11-17 下午5:41:50
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.view;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.plusub.lib.view.R;
import com.plusub.lib.util.LogUtils;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * 
 * @ClassName: ViewInjectUtils
 * @Description: TODO 侵入式View的调用工具类
 * @author qh@plusub.com
 * @date 2014-12-1 下午12:01:28
 * @version v1.0
 */
public class ViewInjectUtils {
	
	private static RotateLoadingDialog mLoadingDialog = null;
	
	
	/**
	 * 含有标题、内容、图标、两个按钮的对话框
	 * <p>Title: showAlertDialog
	 * <p>Description: 
	 * @param title
	 * @param message
	 * @param icon
	 * @param positiveText
	 * @param onPositiveClickListener
	 * @param negativeText
	 * @param onNegativeClickListener
	 * @return
	 */
	public static BaseDialog showAlertDialog(Context context, String title, String message,
			int icon, String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		BaseDialog mDialog = BaseDialog.getDialog(
				context, 
				title, 
				message, 
				positiveText, 
				onPositiveClickListener, 
				negativeText,
				onNegativeClickListener);
		mDialog.show();
		return mDialog;
	}
	
	/**
	 * 显示Crouton风格提示
	 * <p>Title: showCrouton
	 * <p>Description: 
	 * @param text
	 * @param viewGroupResId(0 is default) The resource id of the <b>ViewGroup<b> that this Crouton should be added to
	 * 此组件id必须是ViewGroup的，如LinearLayout等
	 */
	@SuppressLint("ResourceAsColor")
	public static void showCrouton(Activity context, String text, int viewGroupResId){
		//setting configuration of show time or Animation
		Configuration croutonConfiguration = new Configuration.Builder()
			.setDuration(2000)
			.build();
		//setting style
		Style croutonStyle = new Style.Builder()
			.setBackgroundColor(R.color.blue_light)
			.setConfiguration(croutonConfiguration)
			.setHeight(100)
			.build();
		if (viewGroupResId <= 0) {
			Crouton.makeText(context, text, croutonStyle).show();
		}else{
			Crouton.makeText(context, text, croutonStyle, viewGroupResId).show();
		}
	}
	
	/**
	 * 显示自定义Toast提示(来自res)
	 * <p>Title: showCustomToast
	 * <p>Description: 
	 * @param resId
	 */
	public static void showCustomToast(Context context, int resId) {
		View toastRoot = LayoutInflater.from(context).inflate(
				R.layout.common_toast, null);
		((TextView) toastRoot.findViewById(R.id.common_toast_text))
				.setText(context.getString(resId));
		Toast toast = new Toast(context);
//		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}
	
	/**
	 * 显示自定义Toast提示
	 * <p>Title: showCustomToast
	 * <p>Description: 
	 * @param text
	 */
	public static void showCustomToast(Context context, String text) {
		View toastRoot = LayoutInflater.from(context).inflate(
				R.layout.common_toast, null);
		((TextView) toastRoot.findViewById(R.id.common_toast_text))
				.setText(text);
		Toast toast = new Toast(context);
//		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}
	
	/**
	 * show loading dialog
	 * <p>Title: showLoadingDialog
	 * <p>Description: 
	 * @param text
	 */
	public static void showLoadingDialog(Context context, String text) {
		if (context instanceof Application) {
			LogUtils.e("ViewInjectUtils", "传入的参数context不能是Application, 它无法attach to view");
			return;
		}
		if (mLoadingDialog != null) {
			dismissLoadingDialog();
			mLoadingDialog = null;
		}
		mLoadingDialog = new RotateLoadingDialog(context, text);
		mLoadingDialog.show();
	}
	
	/**
	 * 无法自动隐藏的dialog
	 * <p>Title: showLoadingDialogNotCancel
	 * <p>Description: 
	 * @param text
	 */
	public static void showLoadingDialogNotCancel(Context context, String text) {
		if (context instanceof Application) {
			LogUtils.e("ViewInjectUtils", "传入的参数context不能是Application, 它无法attach to view");
			return;
		}
		if (mLoadingDialog != null) {
			dismissLoadingDialog();
			mLoadingDialog = null;
		}
		mLoadingDialog = new RotateLoadingDialog(context, text);
		mLoadingDialog.setCancelable(false);
		mLoadingDialog.setCanceledOnTouchOutside(false);
		mLoadingDialog.show();
	}

	/**
	 * dismiss dialog
	 * <p>Title: dismissLoadingDialog
	 * <p>Description:
	 */
	public static void dismissLoadingDialog() {
		if (mLoadingDialog != null  && mLoadingDialog.isShowing()) {
			try {
				mLoadingDialog.dismiss();
				mLoadingDialog = null;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * 显示网络设置
	 * <p>
	 * Title: showNetSettingDialog
	 * <p>
	 * Description:
	 * 
	 * @param context
	 */
	public static void showNetSettingDialog(final Context context) {
		BaseDialog mDialogAttention = BaseDialog.getDialog(context, context.getString(R.string.net_not_avail), 
				context.getString(R.string.net_setting), 
				context.getString(R.string.cancel), 
				new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}, 
				context.getString(R.string.confirm),
				new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						context.startActivity(new Intent(
								Settings.ACTION_WIRELESS_SETTINGS));
						dialog.dismiss();
					}
				});
		mDialogAttention.show();
	}
	
}
