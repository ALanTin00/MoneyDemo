package com.alan.handsome.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.alan.handsome.net.ObservableSubscribeHooker;

import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by liufe on 2018/6/26
 * 不要在Application里面存取数据（除了app）
 */
public class App extends Application {
    private static App app;
    public static final String TAG = "====App=====";
    private ActivityManager activityManager;
    private String packageName;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        app = this;
        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        packageName = this.getPackageName();
        //面向切面 处理网络异常,统一丢到onError 自己处理
        RxJavaPlugins.setOnObservableSubscribe((observable, observer) -> new ObservableSubscribeHooker(observer));
    }

    public static App getApp() {
        return app;
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
