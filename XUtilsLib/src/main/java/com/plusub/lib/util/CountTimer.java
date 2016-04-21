/*
 * FileName: CountTimer.java
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
 * date     : 2015-4-30 上午10:50:54
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.util;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;


/**
 * 可以设置监听器{@link #setOnTimerListener(OnTimerListener)}, 当完成或间隔都会回调接口方法
 * <br>Schedule a countdown until a time in the future, with
 * regular notifications on intervals along the way.
 *
 * The calls to {@link #onTick(long)} are synchronized to this object so that
 * one call to {@link #onTick(long)} won't ever occur before the previous
 * callback is complete.  This is only relevant when the implementation of
 * {@link #onTick(long)} takes an amount of time to execute that is significant
 * compared to the countdown interval.
 */
public class CountTimer {

	private OnTimerListener timerListener;
	 /**
     * Millis since epoch when alarm should stop.
     */
    private final long mMillisInFuture;

    /**
     * The interval in millis that the user receives callbacks
     */
    private final long mCountdownInterval;

    private long mStopTimeInFuture;
    
    private static final int MSG = 1;
	
	/**
	 * 
	 * <p>Title: 
	 * <p>Description: 
	 * @param millisInFuture  倒计时毫秒数 The number of millis in the future from the call to {@link CountTimer#start()} until the countdown is done and {@link #onFinish()} is called.
	 * @param countDownInterval 间隔毫秒数 The interval along the way to receive {@link #onTick(long)} callbacks.
	 */
	public CountTimer(long millisInFuture, long countDownInterval) {
		mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
	}

	public void setOnTimerListener(OnTimerListener timerListener){
		this.timerListener = timerListener;
	}
	
	
	/**
     * Cancel the countdown.
     */
    public final void cancel() {
        mHandler.removeMessages(MSG);
    }

    /**
     * Start the countdown.
     */
    public synchronized final CountTimer start() {
        if (mMillisInFuture <= 0) {
        	if (timerListener != null) {
    			timerListener.onFinish();
    		}
            return this;
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }



    // handles counting down
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (CountTimer.this) {
                final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();

                if (millisLeft <= 0) {
                	if (timerListener != null) {
            			timerListener.onFinish();
            		}
                } else if (millisLeft < mCountdownInterval) {
                    // no tick, just delay until done
                    sendMessageDelayed(obtainMessage(MSG), millisLeft);
                } else {
                    long lastTickStart = SystemClock.elapsedRealtime();
                    if (timerListener != null) {
            			timerListener.onTick(millisLeft);
            		}

                    // take into account user's onTick taking time to execute
                    long delay = lastTickStart + mCountdownInterval - SystemClock.elapsedRealtime();

                    // special case: user's onTick took more than interval to
                    // complete, skip to next interval
                    while (delay < 0) delay += mCountdownInterval;

                    sendMessageDelayed(obtainMessage(MSG), delay);
                }
            }
        }
    };


	public interface OnTimerListener{
		/**
		 * 倒计时完成调用
		 * <p>Title: onFinish
		 * <p>Description:
		 */
		void onFinish();
		/**
		 * 每次间隔时间时调用
		 * <p>Title: onTick
		 * <p>Description: 
		 * @param millisUntilFinished 还剩余时间
		 */
		void onTick(long millisUntilFinished);
	}
}
