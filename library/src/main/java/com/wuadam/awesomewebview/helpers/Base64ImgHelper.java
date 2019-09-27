package com.wuadam.awesomewebview.helpers;

import android.text.TextUtils;
import android.util.Base64;

public class Base64ImgHelper {
    private String src;

    public Base64ImgHelper(String src) {
        this.src = src;
    }

    public boolean isBase64Img() {
        if (!TextUtils.isEmpty(src) && src.startsWith("data:image")) {
            return true;
        }
        return false;
    }

    public byte[] decode() {
        src = src.substring(src.indexOf(","));
        byte[] imageAsBytes = Base64.decode(src.getBytes(), 0);
        return imageAsBytes;
    }
}
