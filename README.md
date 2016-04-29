# BaseUtilsLib
the new frame for android

建议使用的开发类
---------

## EventBus
[EventBus-Github](https://github.com/greenrobot/EventBus)

## RxJava
[RxJava-Github](https://github.com/ReactiveX/RxJava)

## PhotoView
浏览打开图片组件
[PhotoView-Github](https://github.com/bm-x/PhotoView)

内存优化
http://androidperformance.com/2015/07/20/Android-Performance-Memory-Google.html

包结构变更-名字等

网络请求

图片请求

json

内存清理,使用严苛模式（StrictMode）

使用内存检测LeakCanary

# 需要引用的第三方包
```
    //图形处理
    compile 'com.nineoldandroids:library:2.4.0'
    //基础库
    compile 'com.android.support:appcompat-v7:22.2.0'
    //EventBus
    compile 'org.greenrobot:eventbus:3.0.0'
    //LeakCanary
    debugCompile 'com.squareup.leakcanary:leakcanary-android:1.4-beta2'
    releaseCompile 'com.squareup.leakcanary:leakcanary-android-no-op:1.4-beta2'
    testCompile 'com.squareup.leakcanary:leakcanary-android-no-op:1.4-beta2'
```

混淆
```
#eventbus------------------
#http://greenrobot.org/eventbus/documentation/
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
-keepclassmembers class ** {
    public void onEvent*(**);
    void onEvent*(**);
}

#retrofit------------------
#http://square.github.io/retrofit/
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#fresco------------------
# http://frescolib.org/docs/index.html
# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip
# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}
# Keep native methods
-keepclassmembers class * {
    native <methods>;
}
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
```