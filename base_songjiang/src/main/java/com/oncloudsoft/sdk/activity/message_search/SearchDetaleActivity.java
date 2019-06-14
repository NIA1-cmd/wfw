package com.oncloudsoft.sdk.activity.message_search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.SearchHistoryMessageData;
import com.oncloudsoft.sdk.okhttprequest.HttpParams;
import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;
import com.oncloudsoft.sdk.utils.MyLog;
import com.oncloudsoft.sdk.view.BaseTitleView;
import com.oncloudsoft.sdk.yunxin.session.extension.AttachParserUtils;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.module.Container;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.module.ModuleProxy;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.module.list.MsgAdapter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 caomingduo
 * 创建时间 2019/1/29 14:55
 * 描述  聊天记录搜索结果页面
 */

public class SearchDetaleActivity extends BaseActivity {


    private BaseTitleView btTitle;
    private ImageView ivEmpty;
    private TextView tvEmpty;
    private RelativeLayout rlEmpty;
    private RecyclerView rvBase;
    private List<IMMessage> itemList = new ArrayList<>();

    private MsgAdapter adapter;

    private String title, sesstionId, accid, caseId, caseStartTime, caseEndTime, messageType, content;
    private Container container;

    public static void startSearchDetale(Activity activity, String caseId, String messageType, String accid, String content, String title, String startTime, String endTime, String sesstionId) {
        Intent intent = new Intent(activity, SearchDetaleActivity.class);
        intent.putExtra(Global.CASEID, caseId);
        intent.putExtra(Global.ACCID, accid);
        intent.putExtra(Global.ID, sesstionId);
        intent.putExtra(Global.CONTENT, content);
        intent.putExtra(Global.MESSAGE_TYPE, messageType);
        intent.putExtra(Global.TITLE, title);
        intent.putExtra(Global.START, startTime);
        intent.putExtra(Global.END, endTime);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_baselayout);
        initViews();
        init();
        query();
    }

    private void initViews() {


        btTitle = findViewById(R.id.bt_title);
        ivEmpty = findViewById(R.id.iv_empty);
        tvEmpty = findViewById(R.id.tv_empty);
        rlEmpty = findViewById(R.id.rl_empty);
        rvBase = findViewById(R.id.rv_base);
    }

    private void init() {
        title = getIntent().getStringExtra(Global.TITLE);
        btTitle.setVisibility(View.VISIBLE);
        btTitle.setOnLeftImageClickListener(new BaseTitleView.onLeftImageClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        btTitle.setTitleText(title);
        accid = getIntent().getStringExtra(Global.ACCID);
        caseId = getIntent().getStringExtra(Global.CASEID);
        caseStartTime = getIntent().getStringExtra(Global.START);
        caseEndTime = getIntent().getStringExtra(Global.END);
        messageType = getIntent().getStringExtra(Global.MESSAGE_TYPE);
        content = getIntent().getStringExtra(Global.CONTENT);
        sesstionId = getIntent().getStringExtra(Global.ID);
        rvBase.setLayoutManager(new LinearLayoutManager(SearchDetaleActivity.this, LinearLayoutManager.VERTICAL, false));
        container = new Container(SearchDetaleActivity.this, "", SessionTypeEnum.Team, new ModuleProxy() {
            @Override
            public boolean sendMessage(IMMessage msg) {
                return false;
            }

            @Override
            public void onInputPanelExpand() {

            }

            @Override
            public void shouldCollapseInputPanel() {

            }

            @Override
            public boolean isLongClickEnabled() {
                return false;
            }

            @Override
            public void onItemFooterClick(IMMessage message) {

            }
        });

        /*adapter = new MsgAdapter(rvBase, itemList, container,true);
        rvBase.setAdapter(adapter);
        rvBase.scrollToPosition(adapter.getItemCount()-1);*/

    }

    /**
     * 查询
     */
    private void query() {
        HttpParams params = HttpParams.HttpParams();
        if (!content.equals("")) {
            params.add("contain", content);
        }
        if (!accid.equals("")) {//按照人查询
            params.add("accid", accid);
        }
        if (!messageType.equals("")) {// 查询系统消息
            params.add("type", messageType);

        }
        params.add("startDate", caseStartTime);
        params.add("endDate", caseEndTime);
        params.add("ajbs", caseId);
        MyOkhttpClient.getOkhttpInstance().sentGet(SearchDetaleActivity.this, Global.URL_HISTORYMESSAGE, params.build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) throws JSONException {
                handler(json);
            }

            @Override
            public boolean onRequestFaild(String mes) {
                return false;
            }
        }, true);

    }

    private void handler(String json) {

        itemList.clear();
        List<SearchHistoryMessageData> searchHistoryMessageData = JSON.parseArray(json, SearchHistoryMessageData.class);


        for (int i = 0; i < searchHistoryMessageData.size(); i++) {
            SearchHistoryMessageData searchHistoryMessageData1 = searchHistoryMessageData.get(i);
            if (!searchHistoryMessageData1.getMsgType().equals("NOTIFICATION") && !searchHistoryMessageData1.getMsgType().equals("TIPS")) {
                // TODO: 2019/3/18   前端主动过滤目前不能解析的notifycation类消息
                String contetn = searchHistoryMessageData1.getMsgType().equals("TEXT") ? searchHistoryMessageData1.getBody() : searchHistoryMessageData1.getAttach();
                MsgTypeEnum type;
                switch (searchHistoryMessageData1.getMsgType()) {
                    case "PICTURE":
                        type = MsgTypeEnum.image;
                        break;
                    case "VIDEO":
                        type = MsgTypeEnum.video;
                        break;
                    case "TEXT":
                        type = MsgTypeEnum.text;
                        break;
                    case "CUSTOM":
                        type = MsgTypeEnum.custom;
                        break;

                    case "NOTIFICATION":
                        type = MsgTypeEnum.notification;
                        break;

                    case "TIPS":
                        type = MsgTypeEnum.tip;
                        break;

                    case "AUDIO":
                        type = MsgTypeEnum.audio;
                        break;
                    case "LOCATION":
                        type = MsgTypeEnum.location;
                        break;

                    case "ROBOT":
                        type = MsgTypeEnum.robot;
                        break;

                    case "FILE":
                        type = MsgTypeEnum.file;
                        break;

                    case "AVCHAT":
                        type = MsgTypeEnum.avchat;
                        break;
                    default:
                        type = MsgTypeEnum.undef;

                        break;
                }

                IMMessage imMessage1 = AttachParserUtils.getImMessage(searchHistoryMessageData1.getFromNick(), searchHistoryMessageData1.getFromAccount(), type, contetn, Long.parseLong(searchHistoryMessageData1.getMsgTimestamp()), sesstionId);
                itemList.add(imMessage1);


            } else {
                //防止 项目初期的一些没有定义类型的 消息
                MyLog.d("iiii", searchHistoryMessageData1.getMsgType() + "");
            }
        }

        ActivityHelper.getInstance().getUiThread(SearchDetaleActivity.this, new Runnable() {
            @Override
            public void run() {
                if (itemList.size() < 1) {
                    rlEmpty.setVisibility(View.VISIBLE);
                    tvEmpty.setText("暂无相关聊天记录");
                }
                adapter = new MsgAdapter(rvBase, itemList, container, true);
                adapter.updateShowTimeItem1(itemList, false, true);
                rvBase.setAdapter(adapter);
                rvBase.scrollToPosition(adapter.getItemCount() - 1);//直接定位到最后一条数据
            }
        });
    }
}
