package com.oncloudsoft.sdk.utils;

import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.oncloudsoft.sdk.entity.Media;
import com.oncloudsoft.sdk.entity.QueryImgVideoData;
import com.oncloudsoft.sdk.entity.SearchHistoryMessageData;
import com.oncloudsoft.sdk.yunxin.uikit.impl.customization.DefaultRecentCustomization;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/25.
 */

public class JSONParser {
    public static JSONArray parserRecentContact(/*List<*/RecentContact/*>*/ recentContacts) throws Exception {
        JSONArray jsonArray = new JSONArray();
        /*for (int i = 0; i < recentContacts.size(); i++) {
            RecentContact recentContact = recentContacts.get(i);*/
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", recentContacts.getContactId());
        jsonObject.put("lastTime", recentContacts.getTime());//发送时间
        jsonObject.put("sendType", recentContacts.getSessionType());//群聊还是单聊 Team为群聊
        jsonObject.put("lastMessage", recentContacts.getContent());
        jsonObject.put("lastMessageType", recentContacts.getMsgType());//发送的消息内容 图片,text,视频
        jsonObject.put("unreadCount", recentContacts.getUnreadCount());//未读消息数量
        jsonObject.put("lastName", recentContacts.getFromNick());//未读发送人名称
        jsonObject.put("excludeNumber", 0);//未读发送人名称
        jsonArray.put(jsonObject);
        //}
        return jsonArray;
    }
    public static JSONArray parserRecentContact(IMMessage imMessage,int unreadCount,int excludeNumber) throws Exception {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", imMessage.getSessionId());
        jsonObject.put("lastTime", imMessage.getTime());//发送时间
        jsonObject.put("sendType", imMessage.getSessionType());//群聊还是单聊 Team为群聊
        jsonObject.put("lastMessage", new DefaultRecentCustomization().getTitle(imMessage));
        jsonObject.put("lastMessageType", imMessage.getMsgType());//发送的消息内容 图片,text,视频
        jsonObject.put("unreadCount", unreadCount);//未读消息数量
        jsonObject.put("lastName", imMessage.getFromNick());//未读发送人名称
        jsonObject.put("excludeNumber", excludeNumber);//未读发送人名称
        jsonArray.put(jsonObject);
        return jsonArray;
    }

    public static JSONObject parserContactHolder(JSONArray jsonArray) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", jsonArray.get(1).toString());
        jsonObject.put("lastTime", jsonArray.get(2));
        jsonObject.put("sendType", jsonArray.get(3).toString());
        jsonObject.put("lastMessage", jsonArray.get(4).toString());
        jsonObject.put("lastMessageType", jsonArray.get(5).toString());
        jsonObject.put("unreadCount", jsonArray.get(6).toString());
        jsonObject.put("lastName", jsonArray.get(7).toString());
        return jsonObject;
    }
    public static List<QueryImgVideoData> parserSearchMessage(List<SearchHistoryMessageData> searchHistoryMessageDataList) throws Exception {
        List<QueryImgVideoData> list = new ArrayList<QueryImgVideoData>();

        Map<String,List<SearchHistoryMessageData>> map = new HashMap<String,List<SearchHistoryMessageData>>();
        for (int i = 0;i<searchHistoryMessageDataList.size();i++){
            SearchHistoryMessageData searchHistoryMessageData = searchHistoryMessageDataList.get(i);
            String time = searchHistoryMessageData.getMsgTimestamp();
            String yearMonth = DateUtil.longToTime(Long.parseLong(time),4);
            if (map.get(yearMonth)==null){
                List<SearchHistoryMessageData> list1 = new ArrayList<SearchHistoryMessageData>();
                map.put(yearMonth,list1);
            }
            map.get(yearMonth).add(searchHistoryMessageData);
        }
        for(Map.Entry<String, List<SearchHistoryMessageData>> vo : map.entrySet()){
            QueryImgVideoData queryImgVideoData = new QueryImgVideoData();
            queryImgVideoData.setTime(vo.getKey());

            List<Media> list1 = new ArrayList<Media>();
            List<SearchHistoryMessageData> searchHistoryMessageDataList1 = vo.getValue();
            for (int i = 0;i<searchHistoryMessageDataList1.size();i++){
                Media media = new Media();
                String attach = searchHistoryMessageDataList1.get(i).getAttach();
                JSONObject jsonObject = new JSONObject(attach);
                attach = jsonObject.getString("url");
                String msgType = searchHistoryMessageDataList1.get(i).getMsgType();
                if (msgType.equals("PICTURE")){
                    attach = attach/*+".jpg"*/;
                }
                media.setUrl(attach);
                media.setType(msgType);
                list1.add(media);
            }
            queryImgVideoData.setList(list1);
            list.add(queryImgVideoData);
        }
        return list;
    }
}
