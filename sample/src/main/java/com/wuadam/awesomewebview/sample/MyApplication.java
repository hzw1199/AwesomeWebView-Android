package com.wuadam.awesomewebview.sample;

import android.app.Application;
import android.webkit.CookieManager;

/**
 * Created by wuzongheng on 2018/3/11.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        CookieManager.getInstance().setAcceptCookie(true);
    }
}
