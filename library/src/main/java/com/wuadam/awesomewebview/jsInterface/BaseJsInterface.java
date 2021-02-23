package com.wuadam.awesomewebview.jsInterface;

import android.webkit.JavascriptInterface;

/**
 * @Description: Base class for JS interface
 * @Author: zongheng.wu
 * @Date: 2/23/21 4:10 PM
 */
public abstract class BaseJsInterface {

    public BaseJsInterface() {
    }

    @JavascriptInterface
    public String getSimpleName() {
        return getClass().getSimpleName();
    }
}
