package com.oncloudsoft.sdk.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
    public static String jsonGetString(String json, String key) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.optString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static int jsonGetIntger(String json, String key) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.optInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
