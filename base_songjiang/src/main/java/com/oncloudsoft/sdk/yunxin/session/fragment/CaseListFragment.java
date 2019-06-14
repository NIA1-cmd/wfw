//package com.oncloudsoft.sdk.yunxin.session.fragment;
//
//import android.content.ContentValues;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//
//import com.alibaba.fastjson.JSON;
//import com.netease.nimlib.sdk.NIMClient;
//import com.netease.nimlib.sdk.Observer;
//import com.netease.nimlib.sdk.RequestCallback;
//import com.netease.nimlib.sdk.auth.LoginInfo;
//import com.netease.nimlib.sdk.msg.MsgServiceObserve;
//import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
//import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
//import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
//import com.netease.nimlib.sdk.msg.model.IMMessage;
//import com.netease.nimlib.sdk.msg.model.RecentContact;
//import com.netease.nimlib.sdk.team.model.Team;
//import com.oncloudsoft.sdk.R;
//import com.oncloudsoft.sdk.app.ActivityHelper;
//import com.oncloudsoft.sdk.app.Global;
//import com.oncloudsoft.sdk.app.MyApplication;
//import com.oncloudsoft.sdk.db.DBContext;
//import com.oncloudsoft.sdk.db.DBData;
//import com.oncloudsoft.sdk.entity.BaseTabData;
//import com.oncloudsoft.sdk.entity.CaseListData;
//import com.oncloudsoft.sdk.event.TeamEvent;
//import com.oncloudsoft.sdk.fragment.base_fragment.BaseTablayoutFragment;
//import com.oncloudsoft.sdk.okhttprequest.HttpParams;
//import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;
//import com.oncloudsoft.sdk.utils.ExcludeUtils;
//import com.oncloudsoft.sdk.utils.JSONParser;
//import com.oncloudsoft.sdk.utils.SharedPreferencesUtils;
//import com.oncloudsoft.sdk.yunxin.session.activity.TeamListFragment;
//import com.oncloudsoft.sdk.yunxin.session.extension.SysMsgTypeCustomTipsAttachment;
//import com.oncloudsoft.sdk.yunxin.uikit.api.NimUIKit;
//import com.oncloudsoft.sdk.yunxin.uikit.api.model.team.TeamDataChangedObserver;
//import com.oncloudsoft.sdk.yunxin.uikit.common.badger.Badger;
//import com.oncloudsoft.sdk.yunxin.uikit.common.ui.drop.DropCover;
//import com.oncloudsoft.sdk.yunxin.uikit.common.ui.drop.DropManager;
//import com.oncloudsoft.sdk.yunxin.uikit.impl.customization.DefaultRecentCustomization;
//
//import org.greenrobot.eventbus.EventBus;
//import org.json.JSONArray;
//import org.json.JSONException;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 文件描述：案件列表fragment
// * 作者：黄继栋
// * 创建时间：2019/5/17
// */
//public class CaseListFragment extends BaseTablayoutFragment {
//    private Map<String, RecentContact> cached; // 暂缓刷上列表的数据（未读数红点拖拽动画运行时用）
//    private ContentValues values;
//    /*
//     * 有新消息的时候会执行此方法
//     * 将新消息存sqlite  展示未读消息
//     * */
//    private String contactId;
//
//    private static DBContext dbContext;
//    private TeamListFragment zaizhiTeamListFragment;
//    private TeamListFragment zhongbenTeamListFragment;
//    private TeamListFragment yijieTeamListFragment;
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        cached = new HashMap<>(3);
//        initSQLite();
//
//        registerObservers(true);
//
//
//    }
//
//
//    @Override
//    protected List<BaseTabData> getBaseTabData(List<BaseTabData> list) {
//
//
//        zaizhiTeamListFragment = new TeamListFragment();
//        BaseTabData zaizhiTab = new BaseTabData();
//        zaizhiTab.setBaseFragment(zaizhiTeamListFragment);
//        zaizhiTab.setFragmentTitle(getStringById(R.string.casetype_zaizhi));
//
//
//        zhongbenTeamListFragment = new TeamListFragment();
//        BaseTabData zhongbenTab = new BaseTabData();
//        zhongbenTab.setBaseFragment(zhongbenTeamListFragment);
//        zhongbenTab.setFragmentTitle(getStringById(R.string.casetype_zhongben));
//
//
//        yijieTeamListFragment = new TeamListFragment();
//        BaseTabData yijieTab = new BaseTabData();
//        yijieTab.setBaseFragment(yijieTeamListFragment);
//        yijieTab.setFragmentTitle(getStringById(R.string.casetype_yijie));
//
//        list.add(zaizhiTab);
//        list.add(zhongbenTab);
//        list.add(yijieTab);
//
//
//        return list;
//    }
//
//    @Override
//    protected void onaddFinished() {
//        String account = (String) SharedPreferencesUtils.getParam(Global.YUNXIN_ACCONT, "");
//        String token = (String) SharedPreferencesUtils.getParam(Global.YUNXIN_PASSWORD, "");
//
//        NimUIKit.login(new LoginInfo(account, token), new RequestCallback<LoginInfo>() {
//            @Override
//            public void onSuccess(LoginInfo param) {
//                requestCaseListData();
//            }
//
//            @Override
//            public void onFailed(int code) {
//                ActivityHelper.getInstance().exitLogOut(getActivity());
//            }
//
//            @Override
//            public void onException(Throwable exception) {
//                ActivityHelper.getInstance().exitLogOut(getActivity());
//
//            }
//        });
//    }
//
//    private void initSQLite() {
//        dbContext = ((MyApplication) getActivity().getApplicationContext()).dbContext;
//    }
//
//    /**
//     * ********************** 收消息，处理状态变化 ************************
//     */
//    private void registerObservers(boolean register) {
//        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
//        service.observeRecentContact(messageObserver, register);
//        service.observeMsgStatus(statusObserver, register);
//        service.observeRecentContactDeleted(deleteObserver, register);
//        service.observeReceiveMessage(incomingMessageObserver, register);
//        registerDropCompletedListener(register);
//        registerDropCompletedListener(register);
//        NimUIKit.getTeamChangedObservable().registerTeamDataChangedObserver(teamDataChangedObserver, register);
//
//        //        registerTeamUpdateObserver(register);
//        //        registerTeamMemberUpdateObserver(register);
//        //        NimUIKit.getContactChangedObservable().registerObserver(friendDataChangedObserver, register);
//        //        if (register) {
//        //            registerUserInfoObserver();
//        //        } else {
//        //            unregisterUserInfoObserver();
//        //        }
//    }
//
//    private void registerDropCompletedListener(boolean register) {
//        if (register) {
//            DropManager.getInstance().addDropCompletedListener(dropCompletedListener);
//        }
//        else {
//            DropManager.getInstance().removeDropCompletedListener(dropCompletedListener);
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        registerObservers(false);
//    }
//
//    TeamDataChangedObserver teamDataChangedObserver = new TeamDataChangedObserver() {
//        @Override
//        public void onUpdateTeams(List<Team> teams) {
//            upLoad();
//        }
//
//        @Override
//        public void onRemoveTeam(Team team) {
//            upLoad();
//        }
//    };
//
//
//    Observer<List<IMMessage>> incomingMessageObserver = new Observer<List<IMMessage>>() {
//        @Override
//        public void onEvent(List<IMMessage> messages) {
//            // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
//            long lastTime = 0;
//            String lastName = "";
//            String lastMessage = "";
//            //消息总数
//            int unreadCount = 0;
//            IMMessage imMessage = null;
//            for (int j = 0; j < messages.size(); j++) {
//                imMessage = messages.get(j);
//                sessionType = imMessage.getSessionType();
//                contactId = imMessage.getSessionId();
//                //群聊类型才会进行筛选
//                if (sessionType != SessionTypeEnum.P2P) {
//                    MsgAttachment msgAttachment = imMessage.getAttachment();
//                    if (msgAttachment instanceof SysMsgTypeCustomTipsAttachment) {
//                        SysMsgTypeCustomTipsAttachment sysMsgAttachment = (SysMsgTypeCustomTipsAttachment) imMessage.getAttachment();
//                        if (sysMsgAttachment != null) {
//                            boolean b;
//                            b = ExcludeUtils.getExclude(sysMsgAttachment.getMsgAuth());
//                            if (b == true) {
//                                lastTime = imMessage.getTime();
//                                lastName = imMessage.getFromNick();
//                                lastName = "".equals(lastName) ? "系统" : lastName;
//                                lastMessage = new DefaultRecentCustomization().getTitle(imMessage);
//                                unreadCount++;
//                            }
//                        }
//                    }
//                    else {
//                        MsgTypeEnum msgTypeEnum= imMessage.getMsgType();
//                        lastTime = imMessage.getTime();
//                        lastName = imMessage.getFromNick();
//                        lastName = "".equals(lastName) ? "系统" : lastName;
//                        lastMessage = new DefaultRecentCustomization().getTitle(imMessage);
//                        if (msgTypeEnum == MsgTypeEnum.tip){
//                            int index = lastMessage.indexOf("撤回了一条消息");
//                            unreadCount = index==-1?unreadCount+1:unreadCount;
//                            if (index != -1){
//                                return;
//                            }
//                        }else{
//                            unreadCount++;
//                        }
//
//                    }
//                }
//            }
//            int unCountNumber = 0;
//            List<DBData> dbData = dbContext.whereQuery("*", "lastMessage", "id", contactId);
//            //数据库不存在这条数据  重新插入  存在则更新
//            if (dbData.size() == 0) {
//                try {
//                    JSONArray jsonArray1 = JSONParser.parserRecentContact(imMessage, unreadCount, 0);
//                    unCountNumber = unreadCount;
//                    dbContext.insertLastMessage("lastMessage", jsonArray1);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            else {
//                int unCount = dbData.get(0).getUnreadCount();
//                if ("".equals(lastName)) {
//                    lastTime = Long.parseLong(dbData.get(0).getLastTime());
//                    lastName = dbData.get(0).getLastName();
//                    lastMessage = dbData.get(0).getLastMessage();
//                }
//                unCountNumber = unreadCount + unCount;
//                updataSql(unCountNumber, lastTime, lastName, lastMessage);
//            }
//            updataList(unCountNumber);
//        }
//    };
//
//    private SessionTypeEnum sessionType;
//
//    private void updataSql(int unreadCount, long lastTime, String lastName, String lastMessage) {
//        values = new ContentValues();
//        values.put("unreadCount", unreadCount + "");
//        values.put("lastTime", lastTime);
//        values.put("lastMessage", lastMessage);
//        values.put("lastName", lastName);
//        values.put("excludeNumber", 0);
//        dbContext.update(values, "lastMessage", contactId);
//    }
//
//    private void updataList(int unreadCount) {
//        if (sessionType == SessionTypeEnum.Team) {
//            upLoad();
//
//        }
//        else if (sessionType == SessionTypeEnum.P2P) {
//            EventBus.getDefault().postSticky(new TeamEvent(contactId, "" + unreadCount));
//        }
//        //更新桌面图标未读数量
//        Badger.setBadger(getActivity(), getTeamUnreadCount());
//    }
//
//    /**
//     * 获取所有群聊中未读消息数量
//     *
//     * @return
//     */
//    public static int getTeamUnreadCount() {
//        int unCount = 0;
//        //查询 条件为群聊的消息
//        List<DBData> lastMessage = dbContext.whereQuery("*", "lastMessage", "sendType", SessionTypeEnum.Team.name());
//        for (int i = 0; i < lastMessage.size(); i++) {
//            DBData dbData = lastMessage.get(i);
//            int s = dbData.getUnreadCount();
//            unCount = unCount + s;
//        }
//        return unCount;
//    }
//
//    Observer<List<RecentContact>> messageObserver = new Observer<List<RecentContact>>() {
//        @Override
//        public void onEvent(List<RecentContact> recentContacts) {
//            String lastMessage = "";
//            long lastTime = 0;
//            String lastName = "";
//            RecentContact recentContact = recentContacts.get(0);
//            int unreadCount1 = recentContact.getUnreadCount();
//            contactId = recentContacts.get(0).getContactId();
//            lastMessage = new DefaultRecentCustomization().getDefaultDigest(recentContacts.get(0));
//            if (unreadCount1 != 0) {
//                MsgTypeEnum msgTypeEnum = recentContact.getMsgType();
//                if (msgTypeEnum == MsgTypeEnum.tip&&recentContact.getSessionType() == SessionTypeEnum.Team){
//                    int index = lastMessage.indexOf("撤回了一条消息");
//                    if (index != -1){
//                        values = new ContentValues();
//                        values.put("lastMessage", lastMessage);
//                        dbContext.update(values, "lastMessage", contactId);
//                        upLoad();
//                    }
//                }
//                return;
//            }
//            sessionType = recentContact.getSessionType();
//            lastTime = recentContacts.get(0).getTime();
//            lastName = recentContacts.get(0).getFromNick();
//            lastName = "".equals(lastName) ? "系统" : lastName;
//            values = new ContentValues();
//            if (recentContact.getSessionType() == SessionTypeEnum.Team) {
//                MsgAttachment msgAttachment = recentContact.getAttachment();
//                if (msgAttachment instanceof SysMsgTypeCustomTipsAttachment) {
//                    values.put("unreadCount", 0);
//                    dbContext.update(values, "lastMessage", contactId);
//                }
//                else {
//                    updataSql(unreadCount1, lastTime, lastName, lastMessage);
//                }
//            }
//            else {
//                updataSql(unreadCount1, lastTime, lastName, lastMessage);
//            }
//            updataList(unreadCount1);
//        }
//    };
//    DropCover.IDropCompletedListener dropCompletedListener = new DropCover.IDropCompletedListener() {
//        @Override
//        public void onCompleted(Object id, boolean explosive) {
//            if (cached != null && !cached.isEmpty()) {
//                // 红点爆裂，已经要清除未读，不需要再刷cached
//                if (explosive) {
//                    if (id instanceof RecentContact) {
//                        RecentContact r = (RecentContact) id;
//                        cached.remove(r.getContactId());
//                    }
//                    else if (id instanceof String && ((String) id).contentEquals("0")) {
//                        cached.clear();
//                    }
//                }
//
//                // 刷cached
//                if (!cached.isEmpty()) {
//                    List<RecentContact> recentContacts = new ArrayList<>(cached.size());
//                    recentContacts.addAll(cached.values());
//                    cached.clear();
//                    //onRecentContactChanged(recentContacts);
//                }
//            }
//        }
//    };
//
//    Observer<IMMessage> statusObserver = new Observer<IMMessage>() {
//        @Override
//        public void onEvent(IMMessage message) {
//            //            int index = getItemIndex(message.getUuid());
//            //            if (index >= 0 && index < items.size()) {
//            //                RecentContact item = items.get(index);
//            //                item.setMsgStatus(message.getStatus());
//            //                refreshViewHolderByIndex(index);
//            //            }
//        }
//    };
//
//    Observer<RecentContact> deleteObserver = new Observer<RecentContact>() {
//        @Override
//        public void onEvent(RecentContact recentContact) {
//            //            if (recentContact != null) {
//            //                for (RecentContact item : items) {
//            //                    if (TextUtils.equals(item.getContactId(), recentContact.getContactId())
//            //                            && item.getSessionType() == recentContact.getSessionType()) {
//            //                        items.remove(item);
//            //                        refreshMessages(true);
//            //                        break;
//            //                    }
//            //                }
//            //            } else {
//            //                items.clear();
//            //                refreshMessages(true);
//            //            }
//        }
//    };
//
//    public void upLoad() {
//
//        zaizhiTeamListFragment.upload();
//        zhongbenTeamListFragment.upload();
//        yijieTeamListFragment.upload();
//
//    }
//
//
//    private void requestCaseListData() {
//        MyOkhttpClient.getOkhttpInstance().sentGet(getActivity(), Global.URL_CASE_LIST, HttpParams.HttpParams().build(), new MyOkhttpClient.MyOkhttpCallBack() {
//            @Override
//            public void onRequestSccess(String json) throws JSONException {
//                CaseListData caseListData = JSON.parseObject(json, CaseListData.class);
//                List<String> zz = caseListData.getZz();//在执
//                List<String> wj = caseListData.getWj();//完结 终本
//                List<String> yj = caseListData.getYj();//已结
//
//                zaizhiTeamListFragment.setdatas(zz,0);
//                zhongbenTeamListFragment.setdatas(wj,1);
//                yijieTeamListFragment.setdatas(yj,2);
//                upLoad();
//
//            }
//
//            @Override
//            public boolean onRequestFaild(String mes) {
//                return false;
//            }
//        }, true);
//
//    }
//    public int getIndex(){
//        return index;
//    }
//    //更新搜索的数据
//    public void setSearchData(List<String> keyWord){
//        switch (getIndex()){
//            case 0:
//                zaizhiTeamListFragment.upDataSearch(keyWord);
//                break;
//            case 1:
//                zhongbenTeamListFragment.upDataSearch(keyWord);
//                break;
//            case 2:
//                yijieTeamListFragment.upDataSearch(keyWord);
//                break;
//        }
//    }
//    //更新群聊列表
//    public void queryData(String keyWord){
//        switch (getIndex()){
//            case 0:
//                zaizhiTeamListFragment.queryCase(keyWord);
//                break;
//            case 1:
//                zhongbenTeamListFragment.queryCase(keyWord);
//                break;
//            case 2:
//                yijieTeamListFragment.queryCase(keyWord);
//                break;
//        }
//    }
//
//}
