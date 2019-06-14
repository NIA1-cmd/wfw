package com.oncloudsoft.sdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.adapter.TeamMemberAdapter2;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.MemberData;
import com.oncloudsoft.sdk.event.TeamEvent;
import com.oncloudsoft.sdk.okhttprequest.HttpParams;
import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;
import com.oncloudsoft.sdk.view.BaseTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TeamMemberActivity extends BaseActivity {
    private TextView tvEmpty;
    private RelativeLayout rlEmpty;
    private ListView listView;
    private List<MemberData> list = new ArrayList<>();
    private TeamMemberAdapter2 teamMemberAdapter;
    private final int CODE = 100;
    private String ajbs;
    BaseTitleView titleView;
    private EditText query;
    private ImageView clearSearch;
    private List<MemberData> cb;
    private String name;

    private int intentType = 0;  //200:群成员列表页面进入         其余的值是选择群成员

    private String memberFilter = "";

    /**
     * @param activity
     * @param caseId
     * @param chooseRequestCode
     * @param memberFilter      成员过滤  0:法官  1:申请人  2:被执行人 3：代理人(申)   4:代理人(被)
     */
    public static void start(Activity activity, String caseId, int chooseRequestCode, String memberFilter) {
        Intent intent = new Intent(activity, TeamMemberActivity.class);
        intent.putExtra(Global.CASEID, caseId);
        intent.putExtra(Global.INTENTTYPE, chooseRequestCode);
        intent.putExtra(Global.TYPE, memberFilter);
        if (chooseRequestCode != 200) {//进入页面选择人
            activity.startActivityForResult(intent, chooseRequestCode);
        }
        else {//从成员列表页面进入的 200
            activity.startActivity(intent);
        }

    }

    public static void start(Activity activity, String caseId, int chooseRequestCode) {
        start(activity, caseId, chooseRequestCode, "");

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teammember);

        setStatusBarLightcolor(TeamMemberActivity.this, R.color.white);
        intentType = getIntent().getIntExtra(Global.INTENTTYPE, 0);
        memberFilter = getIntent().getStringExtra(Global.TYPE);

        intiView();
        initTitle();
        request();
        //注册EventBus
        EventBus.getDefault().register(this);

        titleView.setTitleText(intentType == 200 ? "群成员列表" : "群成员查询");

    }

    private void intiView() {
        tvEmpty = findViewById(R.id.tv_empty);
        rlEmpty = findViewById(R.id.rl_empty);
        titleView = findViewById(R.id.title);
        ajbs = getIntent().getStringExtra(Global.CASEID);
        listView = findViewById(R.id.listView);
        query = findViewById(R.id.query);
        clearSearch = findViewById(R.id.search_clear);
        setEditextCursorColor(query, true);

        teamMemberAdapter = new TeamMemberAdapter2(list, TeamMemberActivity.this);
        listView.setAdapter(teamMemberAdapter);
        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    clearSearch.setVisibility(View.VISIBLE);
                }
                else {
                    clearSearch.setVisibility(View.INVISIBLE);
                }
                filterContactList(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query.getText().clear();
                hideSoftKeyboard();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                MemberData memberData = (MemberData) listView.getItemAtPosition(position);
                String accid = memberData.getAccid();

                if (intentType == 200) {//用于 群成员列表的
                    if (accid != null && !TextUtils.isEmpty(accid)) {
                        UserInfoActivity.startUserInfoActivity(TeamMemberActivity.this, accid, ajbs);

                    }
                    else {
                        ActivityHelper.getInstance().showToast(TeamMemberActivity.this, "没有id,暂时无法跳转");
                    }
                }
                else {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("memberData", (Serializable) memberData);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }


    private void initTitle() {

        titleView.setOnLeftImageClickListener(new BaseTitleView.onLeftImageClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    String cbfg = "";    //成员角色 0 法官 1 申请人 2 被执行人 3 代理人(申) 4 代理人(被)

    private void request() {
        MyOkhttpClient.getOkhttpInstance().sentGet(TeamMemberActivity.this, Global.URL_TEAMMEMBER_LIST, HttpParams.HttpParams().add("ajbs", ajbs).build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) {
                String listArryJson = "";
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    cbfg = jsonObject.optString("cbfg");
                    listArryJson = jsonObject.optString("list");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                list.clear();
                cb = JSON.parseArray(listArryJson, MemberData.class);
                if (TextUtils.isEmpty(memberFilter)) {//没有筛选条件
                    list.addAll(cb);
                }
                else {
                    for (int i = 0; i < cb.size(); i++) {
                        MemberData memberData = cb.get(i);
                        if (memberData.getCyjs().equals(memberFilter)) {
                            list.add(memberData);
                        }
                    }
                }

                ActivityHelper.getInstance().getUiThread(TeamMemberActivity.this, new Runnable() {
                    @Override
                    public void run() {
                        teamMemberAdapter.notifyDataSetChanged();
                        rlEmpty.setVisibility(list.size() < 1 ? View.VISIBLE : View.GONE);
                        listView.setVisibility(list.size() < 1 ? View.GONE : View.VISIBLE);
                        tvEmpty.setText("没有符合条件的成员");
                    }
                });
            }

            @Override
            public boolean onRequestFaild(String mes) {
                return false;
            }
        }, true);
    }

    public void filterContactList(String filterStr) {
        List<MemberData> filterDateList = new ArrayList<MemberData>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = list;
        }
        else {
            filterDateList.clear();
            for (MemberData sortModel : list) {
                name = sortModel.getCymc();
                if (name.indexOf(filterStr.toString()) != -1) {
                    filterDateList.add(sortModel);
                }
            }
        }
        updateDataByFilter(filterDateList);
    }

    public void updateDataByFilter(List<MemberData> filterDateList) {
        if (teamMemberAdapter != null) {
            teamMemberAdapter.updateListView(filterDateList);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                request();
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        teamMemberAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onTeamEvent(TeamEvent event) {
        teamMemberAdapter.notifyDataSetChanged();
    }

}
