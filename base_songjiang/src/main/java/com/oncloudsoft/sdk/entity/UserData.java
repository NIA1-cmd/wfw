package com.oncloudsoft.sdk.entity;

public class UserData extends BaseEntity {


    private String token;//token
    private String yxAccid;//云信id
    private String yxToken;//云信token
    private String fgbs;//法官标识
    private String fgmc;//法官姓名
    private String fgzw;//法官职务
    private String fyid;//法院id
    private String fymc;//法院名称

    public String getFgzw() {
        return fgzw;
    }

    public void setFgzw(String fgzw) {
        this.fgzw = fgzw;
    }

    public String getFyid() {
        return fyid;
    }

    public void setFyid(String fyid) {
        this.fyid = fyid;
    }

    public String getFymc() {
        return fymc;
    }

    public void setFymc(String fymc) {
        this.fymc = fymc;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getYxAccid() {
        return yxAccid;
    }

    public void setYxAccid(String yxAccid) {
        this.yxAccid = yxAccid;
    }

    public String getYxToken() {
        return yxToken;
    }

    public void setYxToken(String yxToken) {
        this.yxToken = yxToken;
    }

    public String getFgbs() {
        return fgbs;
    }

    public void setFgbs(String fgbs) {
        this.fgbs = fgbs;
    }

    public String getFgmc() {
        return fgmc;
    }

    public void setFgmc(String fgmc) {
        this.fgmc = fgmc;
    }
}
