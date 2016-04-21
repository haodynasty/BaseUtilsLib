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