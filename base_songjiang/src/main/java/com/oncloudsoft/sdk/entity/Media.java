package com.oncloudsoft.sdk.entity;

import java.io.Serializable;

/**
 * 作者 黄继栋
 * 创建时间 2019/2/21 17:45
 * 描述 多媒体图片视屏对象
 */

public class Media extends BaseEntity implements Serializable{
    private String url;
    private String type;//PICTURE   VIDEO
    private boolean isCheck = false;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
