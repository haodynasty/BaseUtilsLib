package com.plusub.lib.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.plusub.lib.BaseApplication;
import com.plusub.lib.annotate.AnnotateUtil;
import com.plusub.lib.util.GarbageUtils;
import com.plusub.lib.util.logger.LogLevel;
import com.plusub.lib.util.logger.Logger;

/**
 * 基本Framgent类
 */
public abstract class BaseFragment extends Fragment  implements OnClickListener, BaseTask {
	protected View mBaseView;

	/**
	 * 必须实现，需要载入root视图
	 * <p>Title: inflaterView
	 * <p>Description: 
	 * @param inflater
	 * @param container
	 * @param bundle
	 * @return
	 */
	protected abstract View inflaterView(LayoutInflater inflater,
            ViewGroup container, Bundle bundle);

	/**
     * initialization widget, you should look like parentView.findviewbyid(id);
     * call method
     * 
     * @param parentView
     */
    protected abstract void initView(View parentView);
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflaterView(inflater, container, savedInstanceState);
		this.mBaseView = view;
        AnnotateUtil.initBindView(this, view);
        initView(view);
        return view;
	}
	

	/**
	 * Look for a child view with the given id. If this view has the given id, return this view.
	 * <p>Title: findViewById
	 * <p>Description: 
	 * @param id  The id to search for.
	 * @return The view that has the given id in the hierarchy or null
	 */
	public View findViewById(int id){
		if (mBaseView != null) {
			return this.mBaseView.findViewById(id);
		}
		return null;
	}
	
	public Context getApplicationContext(){
		if (getActivity() != null) {
			return getActivity().getApplicationContext();
		}else{
			return BaseApplication.getInstance();
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		BaseApplication.totalList.add(this);
		if (BaseApplication.DEBUG_MODE) {
			Logger.init(getClass().getSimpleName()).setLogLevel(LogLevel.FULL).hideThreadInfo();
		} else {
			Logger.init(getClass().getSimpleName()).setLogLevel(LogLevel.NONE).hideThreadInfo();
		}
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mBaseView != null){
			GarbageUtils.unBindDrawables(mBaseView);
			GarbageUtils.unBindListener(mBaseView);
		}
		BaseApplication.totalList.remove(this);
		BaseApplication.getRefWatcher(getActivity()).watch(this);
	}
	
	/** 通过Class跳转界面 **/
	protected void startActivity(Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), cls);
		startActivity(intent);
	}
	
}
