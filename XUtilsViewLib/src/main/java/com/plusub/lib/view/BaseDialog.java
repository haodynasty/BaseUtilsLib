package com.plusub.lib.view;

import com.plusub.lib.view.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 自定义对话框
 * @author blakequ Blakequ@gmail.com
 *
 */
public class BaseDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Context mContext;// 上下文
	private LinearLayout mLayoutRoot;// 总体根布局
	private LinearLayout mLayoutTop;// 头部根布局
	private LinearLayout mLayoutTitle;// 标题根布局
	private TextView mHtvTitle;// 标题
	private View mViewTitleLine;// 标题分割线
	private LinearLayout mLayoutContent;// 内容根布局
	private TextView mHtvMessage;// 内容
	private LinearLayout mLayoutBottom;// 底部根布局
	private Button mBtnButton1;// 底部按钮1
	private Button mBtnButton2;// 底部按钮2
	private Button mBtnButton3;// 底部按钮3

	private static BaseDialog mBaseDialog;// 当前的对话框
	private OnClickListener mOnClickListener1;// 按钮1的单击监听事件
	private OnClickListener mOnClickListener2;// 按钮2的单击监听事件
	private OnClickListener mOnClickListener3;// 按钮3的单击监听事件

	public BaseDialog(Context context) {
		super(context, R.style.Theme_Light_FullScreenDialogAct);
		mContext = context;
		setContentView(R.layout.common_dialog_generic);
		initViews();
		initEvents();
		setCancelable(true);
		setCanceledOnTouchOutside(true);
	}

	private void initViews() {
		mLayoutRoot = (LinearLayout) findViewById(R.id.dialog_generic_layout_root);
		mLayoutTop = (LinearLayout) findViewById(R.id.dialog_generic_layout_top);
		mLayoutTitle = (LinearLayout) findViewById(R.id.dialog_generic_layout_title);
		mHtvTitle = (TextView) findViewById(R.id.dialog_generic_htv_title);
		mViewTitleLine = findViewById(R.id.dialog_generic_view_titleline);
		mLayoutContent = (LinearLayout) findViewById(R.id.dialog_generic_layout_content);
		mHtvMessage = (TextView) findViewById(R.id.dialog_generic_htv_message);
		mLayoutBottom = (LinearLayout) findViewById(R.id.dialog_generic_layout_bottom);
		mBtnButton1 = (Button) findViewById(R.id.dialog_generic_btn_button1);
		mBtnButton2 = (Button) findViewById(R.id.dialog_generic_btn_button2);
		mBtnButton3 = (Button) findViewById(R.id.dialog_generic_btn_button3);
		mLayoutRoot.setVisibility(View.VISIBLE);
		setTitleLineVisibility(View.VISIBLE);

	}
	
	private void initEvents() {
		mBtnButton1.setOnClickListener(this);
		mBtnButton2.setOnClickListener(this);
		mBtnButton3.setOnClickListener(this);
	}

	/**
	 * 填充新布局到内容布局
	 * 
	 * @param resource
	 */
	public void setDialogContentView(int resource) {
		View v = LayoutInflater.from(mContext).inflate(resource, null);
		if (mLayoutContent.getChildCount() > 0) {
			mLayoutContent.removeAllViews();
		}
		mLayoutContent.addView(v);
	}

	/**
	 * 填充新布局到内容布局
	 * 
	 * @param resource
	 * @param params
	 */
	public void setDialogContentView(int resource,
			LinearLayout.LayoutParams params) {
		View v = LayoutInflater.from(mContext).inflate(resource, null);
		if (mLayoutContent.getChildCount() > 0) {
			mLayoutContent.removeAllViews();
		}
		mLayoutContent.addView(v, params);
	}

	/**
	 * 
	 * <p>Title: getDialog
	 * <p>Description: 只有标题和内容的dialog
	 * @param context
	 * @param title
	 * @param message
	 * @return
	 */
	public static BaseDialog getDialog(Context context, CharSequence title,
			CharSequence message) {
		return getDialog(context, title, message, null, null, null, null, null,
				null);
	}

	/**
	 * 
	 * <p>Title: getDialog
	 * <p>Description: 有标题内容和一个按钮的dialog
	 * @param context
	 * @param title
	 * @param message
	 * @param button1
	 * @param listener1
	 * @return
	 */
	public static BaseDialog getDialog(Context context, CharSequence title,
			CharSequence message, CharSequence button1,
			DialogInterface.OnClickListener listener1) {
		return getDialog(context, title, message, button1, listener1, null,
				null, null, null);
	}

	/**
	 * 
	 * <p>Title: getDialog
	 * <p>Description: 有标题内容和两个按钮的dialog，第二个按钮字体有颜色
	 * @param context
	 * @param title
	 * @param message
	 * @param button1
	 * @param listener1
	 * @param button2
	 * @param listener2
	 * @return
	 */
	public static BaseDialog getDialog(Context context, CharSequence title,
			CharSequence message, CharSequence button1,
			DialogInterface.OnClickListener listener1, CharSequence button2,
			DialogInterface.OnClickListener listener2) {
		return getDialog(context, title, message, button1, listener1, button2,
				listener2, null, null);
	}

	/**
	 * 
	 * <p>Title: getDialog
	 * <p>Description: 有标题内容和三个按钮的dialog，第三个按钮字体有颜色
	 * @param context
	 * @param title
	 * @param message
	 * @param button1
	 * @param listener1
	 * @param button2
	 * @param listener2
	 * @param button3
	 * @param listener3
	 * @return
	 */
	public static BaseDialog getDialog(Context context, CharSequence title,
			CharSequence message, CharSequence button1,
			DialogInterface.OnClickListener listener1, CharSequence button2,
			DialogInterface.OnClickListener listener2, CharSequence button3,
			DialogInterface.OnClickListener listener3) {
		mBaseDialog = new BaseDialog(context);
		if (mBaseDialog.titleAndMessageIsExist(title, message)) {
			mBaseDialog.setTitle(title);
			mBaseDialog.setMessage(message);
		}
		if (mBaseDialog.buttonIsExist(button1, listener1, button2, listener2,
				button3, listener3)) {
			mBaseDialog.setButton1(button1, listener1);
			mBaseDialog.setButton2(button2, listener2);
			mBaseDialog.setButton3(button3, listener3);
			if (button1 != null && button2 == null && button3 == null) {
				mBaseDialog.setButton1Background(R.drawable.btn_dialog_one);
			}
		}
		mBaseDialog.setCancelable(true);
		mBaseDialog.setCanceledOnTouchOutside(true);
		return mBaseDialog;
	}

	private boolean titleAndMessageIsExist(CharSequence title,
			CharSequence message) {
		if (title == null && message == null) {
			mLayoutTop.setVisibility(View.GONE);
			return false;
		} else {
			mLayoutTop.setVisibility(View.VISIBLE);
			return true;
		}
	}

	/**
	 * 设置title的标题，如果text为空则隐藏标题
	 */
	public void setTitle(CharSequence text) {
		if (text != null) {
			mLayoutTitle.setVisibility(View.VISIBLE);
			mHtvTitle.setText(text);
		} else {
			mLayoutTitle.setVisibility(View.GONE);
		}
	}

	/**
	 * 
	 * <p>Title: setMessage
	 * <p>Description: 设置内容，如果text为空则隐藏内容
	 * @param text
	 */
	public void setMessage(CharSequence text) {
		if (text != null) {
			mLayoutContent.setVisibility(View.VISIBLE);
			mHtvMessage.setText(text);
		} else {
			mLayoutContent.setVisibility(View.GONE);
		}
	}

	/**
	 * 
	 * <p>Title: buttonIsExist
	 * <p>Description: 
	 * @param button1
	 * @param listener1
	 * @param button2
	 * @param listener2
	 * @param button3
	 * @param listener3
	 * @return
	 */
	private boolean buttonIsExist(CharSequence button1,
			DialogInterface.OnClickListener listener1, CharSequence button2,
			DialogInterface.OnClickListener listener2, CharSequence button3,
			DialogInterface.OnClickListener listener3) {
		if ((button1 != null && listener1 != null)
				|| (button2 != null && listener2 != null)
				|| (button3 != null && listener3 != null)) {
			mLayoutBottom.setVisibility(View.VISIBLE);
			return true;
		} else {
			mLayoutBottom.setVisibility(View.GONE);
			return false;
		}
	}

	/**
	 * <p>Title: setButton1
	 * <p>Description: 设置第一个按钮的内容和监听器，如果text或listener为空则隐藏按钮
	 * @param text
	 * @param listener
	 */
	public void setButton1(CharSequence text,
			DialogInterface.OnClickListener listener) {
		if (text != null && listener != null) {
			mLayoutBottom.setVisibility(View.VISIBLE);
			mBtnButton1.setVisibility(View.VISIBLE);
			mBtnButton1.setText(text);
			mOnClickListener1 = listener;
		} else {
			mBtnButton1.setVisibility(View.GONE);
		}
	}

	/**
	 * 
	 * <p>Title: setButton2
	 * <p>Description: 设置第2个按钮的内容和监听器，如果text或listener为空则隐藏按钮
	 * @param text
	 * @param listener
	 */
	public void setButton2(CharSequence text,
			DialogInterface.OnClickListener listener) {
		if (text != null && listener != null) {
			mLayoutBottom.setVisibility(View.VISIBLE);
			mBtnButton2.setVisibility(View.VISIBLE);
			mBtnButton2.setText(text);
			mOnClickListener2 = listener;
		} else {
			mBtnButton2.setVisibility(View.GONE);
		}
	}

	/**
	 * 
	 * <p>Title: setButton3
	 * <p>Description: 设置第3个按钮的内容和监听器，如果text或listener为空则隐藏按钮
	 * @param text
	 * @param listener
	 */
	public void setButton3(CharSequence text,
			DialogInterface.OnClickListener listener) {
		if (text != null && listener != null) {
			mLayoutBottom.setVisibility(View.VISIBLE);
			mBtnButton3.setVisibility(View.VISIBLE);
			mBtnButton3.setText(text);
			mOnClickListener3 = listener;
		} else {
			mBtnButton3.setVisibility(View.GONE);
		}
	}

	/**
	 * 
	 * <p>Title: setButton1Background
	 * <p>Description: 设置第一个按钮背景
	 * @param id
	 */
	public void setButton1Background(int id) {
		mBtnButton1.setBackgroundResource(id);
	}

	/**
	 * 
	 * <p>Title: setButton2Background
	 * <p>Description: 设置第二个按钮背景
	 * @param id
	 */
	public void setButton2Background(int id) {
		mBtnButton2.setBackgroundResource(id);
	}

	/**
	 * 
	 * <p>Title: setButton3Background
	 * <p>Description: 设置第三个按钮背景
	 * @param id
	 */
	public void setButton3Background(int id) {
		mBtnButton3.setBackgroundResource(id);
	}
	
	/**
	 *  设置通用颜色（标题，线条）
	 *  <p>注意：如果要改变按钮点击时按下的背景颜色，直接在color.xml中覆盖dialog_btn_normal和dialog_btn_press字段即可</p>
	 * <p>Title: setColor
	 * <p>Description:
	 * @param colorRes 资源颜色id
	 */
	public void setColorCommon(int colorRes){
		int res = mContext.getResources().getColor(colorRes);
		mHtvTitle.setTextColor(res);
		mViewTitleLine.setBackgroundColor(res);
		mBtnButton2.setTextColor(res);
	}

	/**
	 * 设置标题线面的线条是否可见
	 * <p>Title: setTitleLineVisibility
	 * <p>Description: 
	 * @param visibility
	 */
	public void setTitleLineVisibility(int visibility) {
		mViewTitleLine.setVisibility(visibility);
	}
	
	/**
	 * 设置标题文字颜色
	 * <p>Title: setTitleTextColor
	 * <p>Description: 
	 * @param resId
	 */
	public void setTitleTextColor(int resId){
		mHtvTitle.setTextColor(resId);
	}
	
	/**
	 * 设置标题文字颜色
	 * <p>Title: setTitleTextColor
	 * <p>Description: 
	 * @param colorState
	 */
	public void setTitleTextColor(ColorStateList colorState){
		mHtvTitle.setTextColor(colorState);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.dialog_generic_btn_button1) {
			if (mOnClickListener1 != null) {
				mOnClickListener1.onClick(mBaseDialog, 0);
			}
		}else if( id == R.id.dialog_generic_btn_button2){
			if (mOnClickListener2 != null) {
				mOnClickListener2.onClick(mBaseDialog, 1);
			}
		}else if(id == R.id.dialog_generic_btn_button3){
			if (mOnClickListener3 != null) {
				mOnClickListener3.onClick(mBaseDialog, 2);
			}
		}
	}
}
