package com.oncloudsoft.sdk.activity.message_search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.utils.DateUtil;
import com.oncloudsoft.sdk.utils.OnSingleClickListener;
import com.oncloudsoft.sdk.view.BaseTitleView;
import com.oncloudsoft.sdk.view.calendarview.bean.DateBean;
import com.oncloudsoft.sdk.view.calendarview.listener.OnPagerChangeListener;
import com.oncloudsoft.sdk.view.calendarview.listener.OnSingleChooseListener;
import com.oncloudsoft.sdk.view.calendarview.weiget.CalendarView;

import java.util.ArrayList;

/**
 * 作者 黄继栋
 * 创建时间 2019/1/29 15:13
 * 描述 历史消息搜索 ------按日期搜索
 */

public class SearchMessageDateActivity extends BaseActivity {
    private static String listDate = "listDate";
    private TextView title;
    private CalendarView calendar;
    private BaseTitleView btTitle;
    private ArrayList<String> disEnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchmessage_date);
        title = findViewById(R.id.tv_calendar_title);
        calendar = findViewById(R.id.calendar);
        btTitle = findViewById(R.id.bt_title);

        btTitle.setOnLeftImageClickListener(new BaseTitleView.onLeftImageClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        findViewById(R.id.iv_lastMonth).setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick() {
                calendar.lastMonth();

            }
        });
        findViewById(R.id.iv_nextMonth).setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick() {
                calendar.nextMonth();

            }
        });

        String startTime = getIntent().getStringExtra(Global.START);// "2018.1.1";
        String endTime = getIntent().getStringExtra(Global.END);//"2018.12.31";
        disEnable = getIntent().getStringArrayListExtra(listDate);//没有聊天的天数
        String[] split = endTime.split("\\.");
        int[] cDate = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            cDate[i] = Integer.parseInt(split[i]);
        }

        //不在范围内的也被加进去了

        calendar.setStartEndDate(startTime, endTime)
                .setDisableDate(disEnable)
                .setInitDate(cDate[0] + "." + cDate[1])
                .setSingleDate(cDate[0] + "." + cDate[1] + "." + cDate[2])
                .init();


        title.setText(cDate[0] + "年" + cDate[1] + "月");
//        chooseDate.setText("当前选中的日期：" + cDate[0] + "年" + cDate[1] + "月" + cDate[2] + "日");

        calendar.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0] + "年" + date[1] + "月");
            }
        });

        calendar.setOnSingleChooseListener(new OnSingleChooseListener() {
            @Override
            public void onSingleChoose(View view, DateBean date) {
                if (date.getType() == 1) {
                    String tempDate = date.getSolar()[0] + "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日";

                    title.setText(tempDate);


                    String startStr = DateUtil.getLongTime(tempDate + " 00:00:00", DateUtil.DateFormat.YearmonthdayHourminutesseconds_02) + "";
                    String endStr = DateUtil.getLongTime(tempDate + " 23:59:59", DateUtil.DateFormat.YearmonthdayHourminutesseconds_02) + "";
                    SearchDetaleActivity.startSearchDetale(SearchMessageDateActivity.this, getIntent().getStringExtra(Global.CASEID), "", "", "", tempDate, startStr, endStr, getIntent().getStringExtra(Global.ID));


//                    chooseDate.setText("当前选中的日期：" + date.getSolar()[0] + "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日");
                }
            }
        });


    }

    /**
     * @param activity
     * @param startDateStr
     * @param endDateStr
     * @param dateList     没有聊天的天数
     */
    public static void start(Activity activity, String caseId, String startDateStr, String endDateStr, ArrayList<String> dateList, String sessionId) {
        Intent intent = new Intent();
        intent.setClass(activity, SearchMessageDateActivity.class);
        intent.putExtra(Global.START, startDateStr);
        intent.putExtra(Global.END, endDateStr);
        intent.putExtra(Global.ID, sessionId);
        intent.putExtra(Global.CASEID, caseId);
        intent.putStringArrayListExtra(listDate, dateList);
        activity.startActivity(intent);


    }


}
