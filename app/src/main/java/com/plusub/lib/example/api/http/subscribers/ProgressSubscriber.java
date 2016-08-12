package com.plusub.lib.example.api.http.subscribers;

import android.content.Context;

import com.plusub.lib.example.R;
import com.plusub.lib.example.api.ApiException;
import com.plusub.lib.example.api.http.progress.ProgressCancelListener;
import com.plusub.lib.example.api.http.progress.ProgressDialogHandler;
import com.plusub.lib.util.ToastUtils;
import com.plusub.lib.util.logger.Logger;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * @param <T>
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressDialogHandler mProgressDialogHandler;
    private Context context;
    private boolean isShowProgress = false;

    public ProgressSubscriber(Context context, SubscriberOnNextListener mSubscriberOnNextListener){
        this(context, mSubscriberOnNextListener, true, true);
    }

    public ProgressSubscriber(Context context, SubscriberOnNextListener mSubscriberOnNextListener, boolean isShowProgress){
        this(context, mSubscriberOnNextListener, isShowProgress, true);
    }

    public ProgressSubscriber(Context context, SubscriberOnNextListener mSubscriberOnNextListener, boolean isShowProgress, boolean cancelable) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        this.isShowProgress = isShowProgress;
        if (isShowProgress){
            mProgressDialogHandler = new ProgressDialogHandler(context, this, cancelable);
        }
    }

    private void showProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * 订阅开始时调用,执行在subscribeOn的线程（一般不是主线程，所以必须用handler处理消息）
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        Logger.e("ProgressSubscriber", "exception:" + e);
//        FileUtils.write(MainApplication.errorLogPath, "exception:"+e.toString()+"\r\n", true);
        if (e instanceof SocketTimeoutException) {
            ToastUtils.show(context, context.getString(R.string.net_error));
        } else if (e instanceof ConnectException) {
            ToastUtils.show(context, context.getString(R.string.net_error));
        } else if (e instanceof UnknownHostException) {
            ToastUtils.show(context, context.getString(R.string.net_error));
        }else if(e instanceof ApiException){
            int errorCode = ((ApiException) e).getErrorCode();
            if (errorCode == ApiException.SESSION_OUT_TIME){
                //重新登录处理
            }
            ToastUtils.show(context, e.getMessage());
        }else{
            ToastUtils.show(context, context.getString(R.string.error_unkown));
        }
        dismissProgressDialog();
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onError();
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}