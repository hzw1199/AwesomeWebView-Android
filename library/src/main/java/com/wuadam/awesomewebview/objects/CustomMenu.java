package com.wuadam.awesomewebview.objects;

import java.io.Serializable;

public class CustomMenu implements Serializable {
    private String title;
    private String code;

    public CustomMenu(String title, String code) {
        this.title = title;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }
}
