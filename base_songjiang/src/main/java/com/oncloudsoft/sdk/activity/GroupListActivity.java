package com.oncloudsoft.sdk.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.adapter.GroupAdapter;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.GroupDetailData;
import com.oncloudsoft.sdk.okhttprequest.HttpParams;
import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;
import com.oncloudsoft.sdk.utils.MyLog;
import com.oncloudsoft.sdk.utils.SharedPreferencesUtils;
import com.oncloudsoft.sdk.view.BaseTitleView;
import com.oncloudsoft.sdk.widget.SideBar;

import java.util.ArrayList;
import java.util.List;


/**
 * 群成员添加界面
 * Created by Administrator on 2018/12/19.
 */

public class GroupListActivity extends BaseActivity implements GroupAdapter.ChangeState {
    private List<GroupDetailData> list = new ArrayList<GroupDetailData>();
    private ListView listView;
    private SideBar sideBar;
    private TextView dialog;
    private EditText query;
    private ImageView clearSearch;
    private BaseTitleView my_title;
    private GroupAdapter adapter;
    private String member = "";
    private List<GroupDetailData> cb;
    private String name;
    private String ajbs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        findViews();
        ajbs = getIntent().getStringExtra(Global.CASEID);
        setStatusBarLightcolor(GroupListActivity.this, R.color.white);
        initView();
        initData();
    }

    private void findViews() {

        listView = findViewById(R.id.listView);
        sideBar = findViewById(R.id.sidrbar);
        dialog = findViewById(R.id.dialog);
        query = findViewById(R.id.query);
        clearSearch = findViewById(R.id.search_clear);
        my_title = findViewById(R.id.my_title);

    }

    private void initView() {
        my_title.setOnLeftImageClickListener(new BaseTitleView.onLeftImageClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        my_title.setOnRightTextClickListener(new BaseTitleView.onRightTextClickListener() {
            @Override
            public void onClick() {
                if (member != null && !member.equals("")) {
                    member = member.substring(1, member.length());
                    addMember();
                }
                else {
                    showToast("请至少选择一人");
                }
            }
        });
        sideBar.setTextView(dialog);
        adapter = new GroupAdapter(list, this);
        adapter.setListener(this);
        listView.setAdapter(adapter);

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
    }


    private void initData() {
        MyOkhttpClient.getOkhttpInstance().sentGet(GroupListActivity.this, Global.URL_JUDGE_LIST,
                HttpParams.HttpParams().add("fyid", (String) SharedPreferencesUtils.getParam(Global.FYID, "")).add("ajbs", ajbs).build(), new MyOkhttpClient.MyOkhttpCallBack() {
                    @Override
                    public void onRequestSccess(String json) {
                        list.clear();
                        cb = JSON.parseArray(json, GroupDetailData.class);
                        list.addAll(cb);
                        ActivityHelper.getInstance().getUiThread(GroupListActivity.this, new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public boolean onRequestFaild(String mes) {
                        return false;
                    }
                }, true);
    }

    private void addMember() {

        MyOkhttpClient.getOkhttpInstance().sentGet(GroupListActivity.this, Global.URL_JUDGE_ADD, HttpParams.HttpParams().add("ajbs", ajbs).add("fgbs", member).build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) {
                ActivityHelper.getInstance().getUiThread(GroupListActivity.this, new Runnable() {
                    @Override
                    public void run() {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }

            @Override
            public boolean onRequestFaild(String mes) {
                return false;
            }
        }, true);
    }


    @Override
    public void getMember(String member) {
        this.member = member;
    }


    public void filterContactList(String filterStr) {
        List<GroupDetailData> filterDateList = new ArrayList<GroupDetailData>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = list;
        } else {

            filterDateList.clear();
            for (GroupDetailData sortModel : list) {
                name = sortModel.getFgmc();
                if (name.indexOf(filterStr.toString()) != -1) {
                    filterDateList.add(sortModel);
                }
            }
        }
        updateDataByFilter(filterDateList);
    }

    public void updateDataByFilter(List<GroupDetailData> filterDateList) {
        if (adapter != null) {
            adapter.updateListView(filterDateList);
        }
    }
}
