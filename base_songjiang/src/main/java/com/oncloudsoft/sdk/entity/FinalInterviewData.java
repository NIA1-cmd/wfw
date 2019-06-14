package com.oncloudsoft.sdk.entity;

/**
 * 作者 Mingduo Cao
 * 创建时间 2019/6/7 12:38
 * <p>
 * 描述
 */

public class FinalInterviewData  {
    private boolean isCheck = false;
    private String type;
    private String time;
    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
