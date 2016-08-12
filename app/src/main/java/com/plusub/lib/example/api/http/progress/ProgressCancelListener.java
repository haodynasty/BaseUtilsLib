package com.plusub.lib.example.api.http.progress;

/**
 * 取消请求监听器
 */
public interface ProgressCancelListener {
    /**
     * 当取消时调用
     */
    void onCancelProgress();
}
