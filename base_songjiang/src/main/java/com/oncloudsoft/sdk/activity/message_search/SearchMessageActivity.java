package com.oncloudsoft.sdk.activity.message_search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.TeamMemberActivity;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.MemberData;
import com.oncloudsoft.sdk.okhttprequest.HttpParams;
import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;
import com.oncloudsoft.sdk.utils.DateUtil;
import com.oncloudsoft.sdk.utils.OnSingleClickListener;
import com.oncloudsoft.sdk.view.calendarview.utils.CalendarUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 caomingduo
 * 创建时间 2019/1/29 11:16
 * 描述 历史消息查询
 */

public class SearchMessageActivity extends BaseActivity {

    private final int chooseedPeopleRequestCode = 4445;

    private EditText query;
    private TextView tvCancle;
    private TextView tvTeam;
    private TextView tvDate;
    private TextView tvPhotoVideo;

    private String ajbs;
    private String sesstionId;

    private String start;//2018.1.1
    private String end;//2018.12.31"
    private List<String> have = new ArrayList<>();
    private String startDate;
    private String endDate;

    public static void startSearchMessage(Activity activity, String caseId, String sesstionId) {
        Intent intent = new Intent(activity, SearchMessageActivity.class);
        intent.putExtra(Global.CASEID, caseId);
        intent.putExtra(Global.ID, sesstionId);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_message);
        ajbs = getIntent().getStringExtra(Global.CASEID);

        sesstionId = getIntent().getStringExtra(Global.ID);
        findViews();

        setStatusBarLightcolor(SearchMessageActivity.this, R.color.white);

        query.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        query.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                    hideSoftKeyboard();
                    String content = query.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        ActivityHelper.getInstance().showToast(SearchMessageActivity.this, "请输入关键字搜索");

                        return true;
                    }
                    SearchDetaleActivity.startSearchDetale(SearchMessageActivity.this, ajbs, "", "", content, "聊天记录查询", startDate, endDate, sesstionId);

                    query.setText("");
                    return true;
                }
                return false;


            }
        });

        requestDateRange();

    }

    private void findViews() {
        query = findViewById(R.id.query);
        tvCancle = findViewById(R.id.tv_cancel);
        tvTeam = findViewById(R.id.tv_team);
        tvDate = findViewById(R.id.tv_date);
        tvPhotoVideo = findViewById(R.id.tv_photo_video);

        findViewById(R.id.tv_cancel).setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick() {
                finish();
            }
        });
        findViewById(R.id.tv_team).setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick() {
                TeamMemberActivity.start(SearchMessageActivity.this, ajbs, chooseedPeopleRequestCode);

            }
        });

        findViewById(R.id.tv_date).setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick() {
                if (start == null || end == null) {
                    ActivityHelper.getInstance().showToast(SearchMessageActivity.this, "获取时间有误稍后重试");
                    return;
                }
                if (have.size() < 1) {
                    ActivityHelper.getInstance().showToast(SearchMessageActivity.this, "无聊天记录");

                    return;
                }

                ArrayList<String> strings = CalendarUtil.disEnableDate(start, end, have);
                SearchMessageDateActivity.start(SearchMessageActivity.this, ajbs, start, end, strings, sesstionId);
            }
        });

        findViewById(R.id.tv_photo_video).setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick() {
                SearchMessageVideoImageActivity.start(SearchMessageActivity.this, ajbs, startDate, endDate);

            }
        });

        findViewById(R.id.tv_service_documents).setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick() {
                start("文书送达", "3");

            }
        });

        findViewById(R.id.tv_case_guide).setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick() {
                start("办案指引", "5");

            }
        });

        findViewById(R.id.tv_message_notify).setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick() {
            start("消息通知", "2");

            }
        });
        findViewById(R.id.tv_clue_verification).setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick() {
            start("线索核查", "4");

            }
        });
        findViewById(R.id.tv_apply_discipline).setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick() {
            start("申请惩戒", "6");


        }
        });
        findViewById(R.id.tv_all).setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick() {
            start("所有记录", "");

            }
        });


    }

    /**
     * 查询 历史记录的时间范围 以及 有聊天记录的日期
     */
    private void requestDateRange() {
        MyOkhttpClient.getOkhttpInstance().sentGet(SearchMessageActivity.this, Global.URL_HISTORYMESSAGE_DATERANGE, HttpParams.HttpParams().add("ajbs", ajbs).build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) throws JSONException {
                JSONObject jsonObject = new JSONObject(json);
                have.clear();
                startDate = jsonObject.optString("startDate");//开始的时间戳
                endDate = jsonObject.optString("endDate");//开始的时间戳

                start = DateUtil.longToTime(Long.parseLong(startDate), DateUtil.DateFormat.Yearmonthday_03);
                end = DateUtil.longToTime(Long.parseLong(endDate), DateUtil.DateFormat.Yearmonthday_03);
                JSONArray jsonArray = jsonObject.optJSONArray("list");

                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(j);
                    String istime = jsonObject1.getString("istime");
                    istime = istime.replaceAll("-", ".");//将服务器返回的-替换成.
                    istime = CalendarUtil.simplify(istime);//将前面多余的0 去掉
                    have.add(istime);
                }

            }

            @Override
            public boolean onRequestFaild(String mes) {
                return false;
            }
        }, true);

    }




    private void start(String title, String type) {
        SearchDetaleActivity.startSearchDetale(SearchMessageActivity.this, ajbs, type, "", "", title, startDate, endDate, sesstionId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null && resultCode == RESULT_OK) {
            switch (requestCode) {
                case chooseedPeopleRequestCode:
                    MemberData memberData = (MemberData) data.getSerializableExtra("memberData");
                    SearchDetaleActivity.startSearchDetale(SearchMessageActivity.this, ajbs,
                            "", memberData.getAccid(), "", "按群成员查找-" + memberData.getCymc(), startDate, endDate, sesstionId);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
