package com.plusub.lib.example.api.http.subscribers;

/**
 * 当处理完毕调用
 * @param <T>
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);

    void onError();
}
