//package com.oncloudsoft.yunxin.session.extension;
//
//import com.alibaba.fastjson.JSONObject;
//
//public class MyCustomAttachment extends CustomAttachment {
//    public MyCustomAttachment() {
//        super(CustomAttachmentType.custom);
//    }
//
//    private String content;//  消息文本内容
//    private int drawable;// 消息图标
//
//    private static final String CONTENT = "content";
//    private static final String DRAWABLE = "drawable";
//
//
//
//    @Override
//    protected void parseData(JSONObject data) {
//        content = data.getString(CONTENT);
//        drawable = data.getIntValue(DRAWABLE);
//    }
//
//    @Override
//    protected JSONObject packData() {
//        JSONObject data = new JSONObject();
//        data.put(CONTENT, content);
//        data.put(DRAWABLE, drawable);
//        return data;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public int getDrawable() {
//        return drawable;
//    }
//
//    public void setDrawable(int drawable) {
//        this.drawable = drawable;
//    }
//}
