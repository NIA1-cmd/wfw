/**
  * Copyright 2018 bejson.com 
  */
package com.oncloudsoft.sdk.entity;

import java.io.Serializable;

/**
 * Auto-generated: 2018-12-19 13:21:17
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GroupDetailData implements Serializable {

    private String yx_accid;
    private String fgmc;
    private String fgbs;
    private boolean check;
    private String sortLetters;
    private boolean select;
    public void setYx_accid(String yx_accid) {
         this.yx_accid = yx_accid;
     }
     public String getYx_accid() {
         return yx_accid;
     }

    public void setFgmc(String fgmc) {
         this.fgmc = fgmc;
     }
     public String getFgmc() {
         return fgmc;
     }

    public void setFgbs(String fgbs) {
         this.fgbs = fgbs;
     }
     public String getFgbs() {
         return fgbs;
     }
    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}