package com.oncloudsoft.sdk.entity;

import java.io.Serializable;

public class MemberData extends BaseEntity implements Serializable {

    private String cyjs;//成员角色 0 法官 1 申请人 2 被执行人 3 代理人(申) 4 代理人(被)
    private String cymc;//成员名称
    private String ajbs;//案件标识
    private String accid;//accid
    private String cybs;
    private String sortLetters;
    private String jsmc;

    public String getJsmc() {
        return jsmc;
    }

    public void setJsmc(String jsmc) {
        this.jsmc = jsmc;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getCybs() {
        return cybs;
    }

    public void setCybs(String cybs) {
        this.cybs = cybs;
    }

    public String getCyjs() {
        return cyjs;
    }

    public void setCyjs(String cyjs) {
        this.cyjs = cyjs;
    }

    public String getCymc() {
        return cymc;
    }

    public void setCymc(String cymc) {
        this.cymc = cymc;
    }

    public String getAjbs() {
        return ajbs;
    }

    public void setAjbs(String ajbs) {
        this.ajbs = ajbs;
    }

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

}
