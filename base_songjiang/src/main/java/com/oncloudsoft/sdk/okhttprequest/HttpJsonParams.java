package com.oncloudsoft.sdk.okhttprequest;


import org.json.JSONException;
import org.json.JSONObject;

public class HttpJsonParams {
    private static HttpJsonParams httpJsonParams=new HttpJsonParams();
    private static JSONObject jsonObject = null;


    public static HttpJsonParams HttpParams() {
        jsonObject = new JSONObject();
        return httpJsonParams;
    }

    public HttpJsonParams add(String key, Object param) {
        try {
            jsonObject.put(key, param);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return this;
    }

    public String build() {
        return String.valueOf(jsonObject);

    }
}
