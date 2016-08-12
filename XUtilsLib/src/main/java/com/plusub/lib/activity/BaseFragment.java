package com.plusub.lib.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.plusub.lib.BaseApplication;
import com.plusub.lib.activity.lifecycle.LifecycleDispatcher;
import com.plusub.lib.annotate.AnnotateUtil;
import com.plusub.lib.util.GarbageUtils;
import com.plusub.lib.util.logger.LogLevel;
import com.plusub.lib.util.logger.Logger;

import java.lang.reflect.Field;

/**
 * 基本Framgent类
 */
public abstract class BaseFragment extends Fragment  implements OnClickListener, BaseTask {
	protected View mBaseView;
	//避免ViewPager在一开始创建,在可见的时候加载，默认开启
	private boolean hasLazyLoad = true;
	protected FragmentActivity mActivity;

	//必须有空的构造函数
	public BaseFragment() {
	}

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
	public void onStart() {
		super.onStart();
		LifecycleDispatcher.get().onFragmentStarted(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		LifecycleDispatcher.get().onFragmentResumed(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		LifecycleDispatcher.get().onFragmentPaused(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		LifecycleDispatcher.get().onFragmentStopped(this);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		LifecycleDispatcher.get().onFragmentSaveInstanceState(this, outState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LifecycleDispatcher.get().onFragmentActivityCreated(this, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view,
							  Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		LifecycleDispatcher.get().onFragmentViewCreated(this, view, savedInstanceState);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		LifecycleDispatcher.get().onFragmentAttach(this, context);
		if (context instanceof FragmentActivity){
			mActivity = (FragmentActivity) context;
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		LifecycleDispatcher.get().onFragmentDetach(this);
		mActivity = null;
		removeContext();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LifecycleDispatcher.get().onFragmentCreated(this, savedInstanceState);
//		BaseApplication.totalList.add(this);
		if (BaseApplication.DEBUG_MODE) {
			Logger.init(getClass().getSimpleName()).setLogLevel(LogLevel.FULL).hideThreadInfo();
		} else {
			Logger.init(getClass().getSimpleName()).setLogLevel(LogLevel.NONE).hideThreadInfo();
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint() && hasLazyLoad) {
			onLazyLoad();
			hasLazyLoad = false;
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		LifecycleDispatcher.get().onFragmentDestroyView(this);
		hasLazyLoad = true;
		//解决嵌套 Fragment 的bug
		removeContext();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		LifecycleDispatcher.get().onFragmentCreateView(this, inflater, container, savedInstanceState);
		View view = inflaterView(inflater, container, savedInstanceState);
		this.mBaseView = view;
		AnnotateUtil.initBindView(this, view);
		initView(view);
		return view;
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LifecycleDispatcher.get().onFragmentDestroyed(this);
		if (mBaseView != null){
			GarbageUtils.unBindDrawables(mBaseView);
			GarbageUtils.unBindListener(mBaseView);
		}
//		BaseApplication.totalList.remove(this);
//		BaseApplication.getRefWatcher(getActivity()).watch(this);
	}

	private void removeContext(){
		try {
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 设置是否懒加载数据，当界面显示的时候才显示加载数据，默认为true开启
	 * @param hasLazyLoad
	 */
	public void setHasLazyLoad(boolean hasLazyLoad) {
		this.hasLazyLoad = hasLazyLoad;
	}

	/**
	 * 懒加载,当界面可见时调用，防止ViewPager重复创建
	 */
	protected void onLazyLoad() {
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

	/**
	 * Return the {@link FragmentActivity} this fragment is currently associated with.
	 * May return {@code null} if the fragment is associated with a {@link Context}
	 * instead.
	 * 注意：如果不是一定需要Activity的话尽量使用{@link #getApplicationContext()}
	 * @return
	 */
	protected FragmentActivity getActivityReference() {
		if (mActivity == null) {
			mActivity = getActivity();
		}
		return mActivity;
	}
	
	/** 通过Class跳转界面 **/
	protected void startActivity(Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), cls);
		startActivity(intent);
	}
	
}
