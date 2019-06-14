package com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.provider;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.item.AbsContactItem;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.item.ContactItem;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.item.ItemTypes;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.query.IContactDataProvider;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.query.TextQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ContactDataProvider implements IContactDataProvider {

    private int[] itemTypes;

    public ContactDataProvider(int... itemTypes) {
        this.itemTypes = itemTypes;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<AbsContactItem> provide(TextQuery query) {
        List<AbsContactItem> data = new ArrayList<>();
        for (int itemType : itemTypes) {
            List<AbsContactItem> provide = provide(itemType, null);//不用条件查询  返回所有的群列表
            //目的：群查找不能根据 群id，只有我们手动去筛选出合适的群，添加到 data中
            if (query == null) {
                data.addAll(provide);
            } else {
                List<String> ids = new ArrayList<>();
                JSONArray jsonArray;
                try {
                    jsonArray = new JSONArray(query.text);
                    for (int l = 0; l < jsonArray.length(); l++) {
                        String id = (String) jsonArray.get(l);
                        ids.add(id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int u = 0; u < provide.size(); u++) {
                    boolean isSamme = false;
                    String id = ((ContactItem) provide.get(u)).getContact().getContactId();
                    for (int k = 0; k < ids.size(); k++) {
                        String s1 = ids.get(k);
                        if (id.equals(s1)) {
                            isSamme = true;
                        }
                    }

                    if (isSamme) {//有相同的
                        data.add(provide.get(u));
                    }
                }


            }
        }
        return data;
    }

    private final List<AbsContactItem> provide(int itemType, TextQuery query) {
        switch (itemType) {
            case ItemTypes.FRIEND:
                return UserDataProvider.provide(query);
            case ItemTypes.TEAM:
            case ItemTypes.TEAMS.ADVANCED_TEAM:
            case ItemTypes.TEAMS.NORMAL_TEAM:
                return TeamDataProvider.provide(query, itemType);
            case ItemTypes.MSG:
                return MsgDataProvider.provide(query);
            default:
                return new ArrayList<>();
        }
    }
}
