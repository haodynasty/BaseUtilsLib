package com.plusub.lib.util;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * 轮询和定时任务使用AlarmManager
 * Created by PLUSUB on 2015/10/10.
 */
public class PollingUtils {
    /**
     * 开启循环轮询服务,取消使用{@link #stopPollingTask(Context, Class, String)}
     * @param context 上下文
     * @param seconds 循环间隔秒数
     * @param cls 要调用的class可以是Service,BroadcastReceiver和Activity
     * @param action class的启动action
     */
    public static void startPollingTask(Context context, int seconds, Class<?> cls,String action) {
        //获取AlarmManager系统服务
        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);

        //包装需要执行的Intent
        PendingIntent pendingIntent = getPendingIntent(context, cls, action);
        if (pendingIntent == null) return;

        //触发服务的起始时间
        long triggerAtTime = SystemClock.elapsedRealtime();

        //使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service
        manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime,
                seconds * 1000, pendingIntent);
    }

    /**
     * 停止循环轮询服务
     * @param context 上下文
     * @param cls 要调用的class可以是Service,BroadcastReceiver和Activity
     * @param action class的启动action
     */
    public static void stopPollingTask(Context context, Class<?> cls,String action) {
        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);

        //包装需要执行的Intent
        PendingIntent pendingIntent = getPendingIntent(context, cls, action);
        if (pendingIntent == null) return;

        //取消正在执行的任务
        manager.cancel(pendingIntent);
    }


    /**
     * 启动定时任务,取消使用{@link #stopPollingTask(Context, Class, String)}
     * @param context
     * @param noticeTime 要启动的精确时间
     * @param cls
     * @param action
     */
    public static void startTimeTask(Context context, long noticeTime, Class<?> cls,String action){
        //获取AlarmManager系统服务
        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);

        //包装需要执行的Intent
        PendingIntent pendingIntent = getPendingIntent(context, cls, action);
        if (pendingIntent == null) return;

        manager.set(AlarmManager.RTC, noticeTime, pendingIntent);
    }


    private static PendingIntent getPendingIntent(Context context, Class<?> cls,String action){
        Intent intent = new Intent(context, cls);
        if (android.os.Build.VERSION.SDK_INT >= 12) {
            intent.setFlags(32);//Intent.FLAG_INCLUDE_STOPPED_PACKAGES
        }
        intent.setAction(action);
        PendingIntent pendingIntent = null;
        if (Service.class.isAssignableFrom(cls)){
            pendingIntent = PendingIntent.getService(context, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }else if(BroadcastReceiver.class.isAssignableFrom(cls)){
            pendingIntent = PendingIntent.getBroadcast(context, 0,
                    intent, 0);
        }else if(Activity.class.isAssignableFrom(cls)){
            pendingIntent = PendingIntent.getActivity(context, 0,
                    intent, 0);
        }
        return pendingIntent;
    }
}
