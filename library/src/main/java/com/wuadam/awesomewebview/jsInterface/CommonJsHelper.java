package com.wuadam.awesomewebview.jsInterface;

import android.text.TextUtils;
import android.util.Pair;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: JS interface handler
 * @Author: zongheng.wu
 * @Date: 2/23/21 3:36 PM
 */
public class CommonJsHelper<T extends BaseJsInterface> {
    private static CommonJsHelper commonJsHelper;

    public static CommonJsHelper getInstance() {
        if (commonJsHelper == null) {
            synchronized (CommonJsHelper.class) {
                if (commonJsHelper == null) {
                    commonJsHelper = new CommonJsHelper();
                }
            }
        }
        return commonJsHelper;
    }

    private CommonJsHelper() {
    }

    private final List<Pair<Class<T>, String>> interfacesInternal = new ArrayList<>(0);

    /**
     * add new JS interface
     * @param ifc
     * @param bridge
     */
    public void addJavascriptInterface(Class<T> ifc, String bridge) {
        interfacesInternal.add(Pair.create(ifc, bridge));
    }

    /**
     * do not call, only for internal
     * @param webView
     */
    public void addJavascriptInterface(WebView webView) {
        for (Pair<Class<T>, String> pair: interfacesInternal) {
            if (pair == null || pair.first == null || TextUtils.isEmpty(pair.second)) {
                continue;
            }
            try {
                webView.addJavascriptInterface(pair.first.newInstance(), pair.second);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
