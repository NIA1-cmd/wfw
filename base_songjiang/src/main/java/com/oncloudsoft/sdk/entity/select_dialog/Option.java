/**
  * Copyright 2019 bejson.com 
  */
package com.oncloudsoft.sdk.entity.select_dialog;

/**
 * Auto-generated: 2019-02-19 10:21:49
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Option {

    private String name;
    private String value;
    private boolean isCheck = false;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setValue(String value) {
         this.value = value;
     }
     public String getValue() {
         return value;
     }

}