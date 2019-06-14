package com.oncloudsoft.sdk.okhttprequest;

import java.util.HashMap;

public class HttpParams {
private static HttpParams httpParams=null;
    private static HashMap<String,String> map=new HashMap<>();



    public static HttpParams HttpParams() {
        map.clear();
       if (httpParams==null){
           httpParams=new HttpParams();
       }
       return httpParams;
    }

    public HttpParams add(String key, String param){
        map.put(key,param);
        return this;
    }
    public HashMap<String,String> build(){

        return map;

    }
    public void clear(){
        map.clear();
    }
}
