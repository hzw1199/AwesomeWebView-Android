package com.wuadam.awesomewebview.sample;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.wuadam.awesomewebview.jsInterface.BaseJsInterface;

/**
 * @Description: demo for JS interface showing toast
 * @Author: zongheng.wu
 * @Date: 2/23/21 5:03 PM
 */
public class ToastJsInterface extends BaseJsInterface {

    @JavascriptInterface
    public void showToast(String content) {
        Toast.makeText(MyApplication.context, content, Toast.LENGTH_SHORT).show();
    }
}
