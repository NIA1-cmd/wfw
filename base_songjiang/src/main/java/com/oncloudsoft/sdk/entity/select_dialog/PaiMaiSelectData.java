/**
  * Copyright 2019 bejson.com 
  */
package com.oncloudsoft.sdk.entity.select_dialog;
import java.util.List;

/**
 * Auto-generated: 2019-02-19 10:21:49
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class PaiMaiSelectData {

    private String msg;
    private List<Option> option;
    private String result;
    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }

    public void setOption(List<Option> option) {
         this.option = option;
     }
     public List<Option> getOption() {
         return option;
     }

    public void setResult(String result) {
         this.result = result;
     }
     public String getResult() {
         return result;
     }

}