package com.wuadam.awesomewebview.objects;

import androidx.annotation.StringRes;

import java.io.Serializable;

public class CustomMenu implements Serializable {
    private int titleRes;
    private String code;

    public CustomMenu(@StringRes int titleRes, String code) {
        this.titleRes = titleRes;
        this.code = code;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public String getCode() {
        return code;
    }
}
