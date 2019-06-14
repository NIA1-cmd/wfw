package com.oncloudsoft.sdk.entity;

import java.io.Serializable;

public class LoginData extends BaseEntity implements Serializable{





    private String fgbs;
    private String fgmc;
    private String fgzw;//法官职务

    private String fyid;//法院id

    private String token;
    private String fymc;//法院名称
    private String yxAccid;
    private String yxToken;
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

    public class Account {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
