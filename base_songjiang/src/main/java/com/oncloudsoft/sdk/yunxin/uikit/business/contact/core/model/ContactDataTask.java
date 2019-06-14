package com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.model;

import com.oncloudsoft.sdk.app.MyApplication;
import com.oncloudsoft.sdk.db.DBContext;
import com.oncloudsoft.sdk.utils.MyLog;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.item.AbsContactItem;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.item.ContactItem;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.item.ContactItemFilter;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.query.IContactDataProvider;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.query.TextQuery;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通讯录获取数据任务
 * Created by huangjun on 2015/2/10.
 */
public class ContactDataTask {

    private ArrayList<Map<String, String>> list1;
    private ArrayList<Map<String, String>> list2;

    public interface Host {
        public void onData(ContactDataTask task, AbsContactDataList datas, boolean all); // 搜索完成，返回数据给调用方

        public boolean isCancelled(ContactDataTask task); // 判断调用放是否已经取消
    }

    private final IContactDataProvider dataProvider; // 数据源提供者

    private final ContactItemFilter filter; // 项过滤器

    private final TextQuery query; // 要搜索的信息，null为查询所有

    private Host host;
    private DBContext dbContext;

    public ContactDataTask(TextQuery query, IContactDataProvider dataProvider, ContactItemFilter filter) {
        this.query = query;
        this.dataProvider = dataProvider;
        this.filter = filter;
        this.dbContext =  MyApplication.dbContext;
    }

    public final void setHost(Host host) {
        this.host = host;
    }

    protected void onPreProvide(AbsContactDataList datas) {

    }

    public final void run(AbsContactDataList datas) {
        // CANCELLED
        if (isCancelled()) {
            return;
        }

        // PRE PROVIDE
        onPreProvide(datas);

        // CANCELLED
        if (isCancelled()) {
            return;
        }

        // PROVIDE
        List<AbsContactItem> items = dataProvider.provide(query);

        // ADD
        add(datas, items, filter);
        // BUILD
        datas.build();

        // PUBLISH ALL
        publish(datas, true);
    }

    private void publish(AbsContactDataList datas, boolean all) {
        if (host != null) {
            datas.setQuery(query);

            host.onData(this, datas, all);
        }
    }

    private boolean isCancelled() {
        return host != null && host.isCancelled(this);
    }

    private void add(AbsContactDataList datas1, List<AbsContactItem> items, ContactItemFilter filter) {

        //根据最后时间进行排序
        List<String> ids = new ArrayList<>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        for (int i = 0; i < items.size(); i++) {
            Map<String, String> map = new HashMap<String, String>();
            ContactItem contactItem = ((ContactItem) items.get(i));
            String id = contactItem.getContact().getContactId();
            ids.add(id);
            try {
                JSONObject jsonObject = dbContext.queryLastTime("lsatMessage", "*", id);
                if (jsonObject == null || !jsonObject.has("id")) {
                    map.put("id", id);
                    map.put("lastTime", "0");//默认最小值排在最后面
                }
                else {
                    map.put("id", jsonObject.getString("id"));
                    map.put("lastTime", jsonObject.getString("lastTime"));
                }
                list.add(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Collections.sort(list, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                int i = (int) (Long.parseLong(o2.get("lastTime")) - Long.parseLong(o1.get("lastTime")));//降序排列
                MyLog.i("sort="+i);
                return i;

            }
        });

        list1 = new ArrayList<Map<String, String>>();
        list2 = new ArrayList<Map<String, String>>();
        for (int i = 0; i < list.size(); i++) {
            long time = Long.parseLong(list.get(i).get("lastTime"));
            if (time != 0){
                list1.add(list.get(i));
            }else {
                list2.add(list.get(i));
            }
        }
        list1.addAll(list2);
        list.clear();
        list.addAll(list1);

            for (int j = 0; j < list.size(); j++) {
                String listId = list.get(j).get("id");
                for (int k = 0; k < items.size(); k++) {
                    String id = ((ContactItem) items.get(k)).getContact().getContactId();
                    if (id.equals(listId)) {
                        datas1.add(items.get(k));
                        break;
                    }
                }
            }
       /* for (AbsContactItem item : items) {
            if (filter == null || !filter.filter(item)) {
                datas.add(item);
            }
        }*/
        }
    }