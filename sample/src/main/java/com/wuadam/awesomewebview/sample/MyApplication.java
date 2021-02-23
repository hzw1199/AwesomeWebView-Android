package com.wuadam.awesomewebview.sample;

import android.app.Application;
import android.content.Context;
import android.webkit.CookieManager;

/**
 * Created by wuzongheng on 2018/3/11.
 */

public class MyApplication extends Application{
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        CookieManager.getInstance().setAcceptCookie(true);
    }
}
