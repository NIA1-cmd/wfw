package com.oncloudsoft.sdk.yunxin.session.activity;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.app.MyApplication;
import com.oncloudsoft.sdk.db.DBContext;
import com.oncloudsoft.sdk.db.DBData;
import com.oncloudsoft.sdk.event.TeamEvent;
import com.oncloudsoft.sdk.fragment.base_fragment.BaseFragment;
import com.oncloudsoft.sdk.utils.JSONParser;
import com.oncloudsoft.sdk.utils.MyLog;
import com.oncloudsoft.sdk.yunxin.session.SessionHelper;
import com.oncloudsoft.sdk.yunxin.uikit.api.NimUIKit;
import com.oncloudsoft.sdk.yunxin.uikit.api.model.team.TeamDataChangedObserver;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.item.AbsContactItem;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.item.ContactItem;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.item.ItemTypes;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.model.ContactDataAdapter;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.provider.ContactDataProvider;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.query.IContactDataProvider;
import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.viewholder.ContactHolder;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.drop.DropCover;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.drop.DropManager;
import com.oncloudsoft.sdk.yunxin.uikit.impl.customization.DefaultRecentCustomization;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 群列表(通讯录)
 * <p/>
 * Created by huangjun on 2015/4/21.
 */
public class TeamListFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ContactDataAdapter adapter;
//    private List<RecentContact> items;
    private ListView lvContacts;
    private RelativeLayout rlEmpty;
    private TextView tvEmpty;
    private Map<String, RecentContact> cached; // 暂缓刷上列表的数据（未读数红点拖拽动画运行时用）
    private DBContext dbContext;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initMessageList();
        //registerObservers(true);
        registerDropCompletedListener(true);


    }

    private void initSQLite() {
        this.dbContext = MyApplication.dbContext;
    }

    private void initMessageList() {
        cached = new HashMap<>(3);
    }

    private void registerDropCompletedListener(boolean register) {
        if (register) {
            DropManager.getInstance().addDropCompletedListener(dropCompletedListener);
        }
        else {
            DropManager.getInstance().removeDropCompletedListener(dropCompletedListener);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //registerObservers(false);

    }

    /**
     * ********************** 收消息，处理状态变化 ************************
     */
    private void registerObservers(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeRecentContact(messageObserver, register);
        service.observeMsgStatus(statusObserver, register);
        service.observeRecentContactDeleted(deleteObserver, register);
        service.observeReceiveMessage(incomingMessageObserver, register);

    }

    Observer<List<IMMessage>> incomingMessageObserver = new Observer<List<IMMessage>>() {
        @Override
        public void onEvent(List<IMMessage> messages) {
            // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。\
            MyLog.i("info11111","..............immessage="+messages.size());
            int unreadCount = messages.size();//消息总数
            int excludeNumber = 0;//消息中被剔除的消息数量
            IMMessage imMessage = null;
            for (int j = 0; j < messages.size(); j++) {
                imMessage = messages.get(j);
                contactId = imMessage.getSessionId();
                lastTime = imMessage.getTime() + "";
                lastName = imMessage.getFromNick();
                if (lastName.equals("")) {
                    lastName = "系统";
                }
                sessionType = imMessage.getSessionType();
                lastMessage = new DefaultRecentCustomization().getTitle(imMessage);
            }
            int unCountNumber = 0;
            List<DBData> dbData = dbContext.whereQuery("*", "lastMessage", "id", contactId);
            if (dbData.size() == 0) {//数据库不存在这条数据  重新插入  存在则更新
                try {
                    JSONArray jsonArray1 = JSONParser.parserRecentContact(imMessage, unreadCount, excludeNumber);
                    unCountNumber = unreadCount - excludeNumber;
                    dbContext.insertLastMessage("lastMessage", jsonArray1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                int unCount = dbData.get(0).getUnreadCount();
                unCountNumber = unreadCount + unCount;
                updataSql(unreadCount + unCount, 0);
            }
            updataList(unCountNumber);
        }
    };

    private void updataSql(int unreadCount, int excludeNumber) {
        values = new ContentValues();
        values.put("unreadCount", unreadCount + "");
        values.put("lastTime", lastTime);
        values.put("lastMessage", lastMessage);
        values.put("lastName", lastName);
        values.put("excludeNumber", excludeNumber);
        dbContext.update(values, "lastMessage", contactId);
    }

    private void updataList(int unreadCount) {
        if (sessionType == SessionTypeEnum.Team) {
            upDate();

        }
        else if (sessionType == SessionTypeEnum.P2P) {
            EventBus.getDefault().postSticky(new TeamEvent(contactId, "" + unreadCount));
        }
    }

    /*
     * 有新消息的时候会执行此方法
     * 将新消息存sqlite  展示未读消息
     * */
    private ContentValues values;
    private String contactId;
    private String lastMessage, lastTime, lastName;
    private SessionTypeEnum sessionType;
    Observer<List<RecentContact>> messageObserver = new Observer<List<RecentContact>>() {
        @Override
        public void onEvent(List<RecentContact> recentContacts) {
            lastName = "";
            RecentContact recentContact = recentContacts.get(0);
            int unreadCount = recentContact.getUnreadCount();

            contactId = recentContacts.get(0).getContactId();
            lastMessage = new DefaultRecentCustomization().getDefaultDigest(recentContacts.get(0));
            lastTime = recentContacts.get(0).getTime() + "";
            lastName = recentContacts.get(0).getFromNick();
            if (lastName.equals("")) {
                lastName = "系统";
            }
            sessionType = recentContacts.get(0).getSessionType();

            List<DBData> dbData = dbContext.whereQuery("*", "lastMessage","id", recentContacts.get(0).getContactId());
            if (dbData.size() == 0) {
                try {
                    JSONArray jsonArray1 = JSONParser.parserRecentContact(recentContact);
                    dbContext.insertLastMessage("lastMessage", jsonArray1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                updataSql(unreadCount, 0);
            }

            if (sessionType == SessionTypeEnum.Team) {

                upDate();

            } else if (sessionType == SessionTypeEnum.P2P) {
                EventBus.getDefault().postSticky(new TeamEvent(contactId, "" + unreadCount));
            }
            updataList(unreadCount);
        }
    };
    DropCover.IDropCompletedListener dropCompletedListener = new DropCover.IDropCompletedListener() {
        @Override
        public void onCompleted(Object id, boolean explosive) {
            if (cached != null && !cached.isEmpty()) {
                // 红点爆裂，已经要清除未读，不需要再刷cached
                if (explosive) {
                    if (id instanceof RecentContact) {
                        RecentContact r = (RecentContact) id;
                        cached.remove(r.getContactId());
                    }
                    else if (id instanceof String && ((String) id).contentEquals("0")) {
                        cached.clear();
                    }
                }

                // 刷cached
                if (!cached.isEmpty()) {
                    List<RecentContact> recentContacts = new ArrayList<>(cached.size());
                    recentContacts.addAll(cached.values());
                    cached.clear();
                    //onRecentContactChanged(recentContacts);
                }
            }
        }
    };

    Observer<IMMessage> statusObserver = new Observer<IMMessage>() {
        @Override
        public void onEvent(IMMessage message) {

        }
    };

    Observer<RecentContact> deleteObserver = new Observer<RecentContact>() {
        @Override
        public void onEvent(RecentContact recentContact) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.group_list_activity, container, false);
        findViews(view);
        setParames();
        // load data
        initSQLite();
        registerTeamUpdateObserver(true);
        //注册EventBus
        EventBus.getDefault().register(this);
        return view;
    }


    /**
     * 准备数据源，以及初始化控件的一些属性，创建adapter
     */
    private void setParames() {
        IContactDataProvider dataProvider = new ContactDataProvider(ItemTypes.TEAMS.ADVANCED_TEAM);
        adapter = new ContactDataAdapter(getActivity(), null, dataProvider) {
            @Override
            protected List<AbsContactItem> onNonDataItems() {
                return null;
            }

            @Override
            protected void onPreReady() {
            }

            @Override
            protected void onPostLoad(boolean empty, String queryText, boolean all) {
            }
        };
        //adapter.addViewHolder(ItemTypes.LABEL, LabelHolder.class);
        adapter.addViewHolder(ItemTypes.TEAM, ContactHolder.class);
        lvContacts.setAdapter(adapter);
        lvContacts.setOnItemClickListener(this);
        lvContacts.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //                showKeyboard(false);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

    }

    /**
     * 初始化控件
     */
    private void findViews(View view) {
        lvContacts = view.findViewById(R.id.group_list);
        rlEmpty = view.findViewById(R.id.rl_empty);
        tvEmpty = view.findViewById(R.id.tv_empty);
        tvEmpty.setText("暂无相关案件");

    }

    @Override
    public void onDestroyView() {
        registerTeamUpdateObserver(false);
        registerDropCompletedListener(false);
        super.onDestroyView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AbsContactItem item = (AbsContactItem) adapter.getItem(position);
        switch (item.getItemType()) {
            case ItemTypes.TEAM:
                SessionHelper.startTeamSession(getActivity(), ((ContactItem) item).getContact().getContactId());
                break;
        }
    }

    private void registerTeamUpdateObserver(boolean register) {
        NimUIKit.getTeamChangedObservable().registerTeamDataChangedObserver(teamDataChangedObserver, register);
    }

    TeamDataChangedObserver teamDataChangedObserver = new TeamDataChangedObserver() {
        @Override
        public void onUpdateTeams(List<Team> teams) {
            upDate();
        }

        @Override
        public void onRemoveTeam(Team team) {
            upDate();
        }
    };

    /**
     * 加载数据
     */
    public void upDate() {
        adapter.load(true);
        NIMClient.getService(TeamService.class).queryTeamList().setCallback(new RequestCallback<List<Team>>() {
            @Override
            public void onSuccess(List<Team> teams) {
                if (teams.size()>0){
                    rlEmpty.setVisibility(View.GONE);
                    lvContacts.setVisibility(View.VISIBLE);
                }else {
                    rlEmpty.setVisibility(View.VISIBLE);
                    lvContacts.setVisibility(View.GONE);
                }
                // 获取成功，teams为加入的所有群组
            }
            @Override
            public void onFailed(int i) {
                // 获取失败，具体错误码见i参数
            }

            @Override
            public void onException(Throwable throwable) {
                // 获取异常
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onTeamEvent(String s) {
        upDate();
    }

}
