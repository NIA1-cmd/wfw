package com.oncloudsoft.sdk.yunxin.session.viewholder;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.BankDetaleActivity;
import com.oncloudsoft.sdk.utils.JsonUtil;
import com.oncloudsoft.sdk.yunxin.session.extension.CustomAttachmentType;
import com.oncloudsoft.sdk.yunxin.session.extension.SysMsgType02Attachment;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;

/**
 * 系统消息的解析类  普通布局 标题内容-查看
 */
public class SysMsgType02Holder extends CustomBaseHolder<SysMsgType02Attachment> {


    private LinearLayout parent;
    private TextView tvTitle;
    private TextView mesContetn;
    private TextView look;

    private View line;

    public SysMsgType02Holder(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {

        return R.layout.sysmsg_itemtype02;


    }

    @Override
    protected void inflateContentView() {
        parent = findViewById(R.id.packet_ll);
        tvTitle = new TextView(context);
        mesContetn = new TextView(context);
        look = new TextView(context);
        look.setText("查看");

        line = new View(context);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400, 2);
        params.setMargins(0, 10, 0, 10);
        line.setLayoutParams(params);
    }



    @Override
    protected void bindCustomContentView(SysMsgType02Attachment sysMsgType02Attachment) {
        parent.removeAllViews();
        if (isReceivedMessage()) {//左边
            tvTitle.setTextColor(context.getResources().getColor(R.color.textcolor_01));
            mesContetn.setTextColor(context.getResources().getColor(R.color.textcolor_02));
            look.setTextColor(context.getResources().getColor(R.color.maincolor_01));
            line.setBackgroundColor(context.getResources().getColor(R.color.orthercolor_03));
        } else {
            tvTitle.setTextColor(context.getResources().getColor(R.color.white));
            mesContetn.setTextColor(context.getResources().getColor(R.color.gray9));
            look.setTextColor(context.getResources().getColor(R.color.pink));
            line.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lookParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvTitle.setLayoutParams(layoutParams);
        mesContetn.setLayoutParams(layoutParams);

        lookParams.gravity = Gravity.CENTER_HORIZONTAL;
        look.setLayoutParams(lookParams);


        parent.addView(tvTitle);
        parent.addView(mesContetn);
        parent.addView(line);
        parent.addView(look);


        View.OnClickListener onClickListener = null;
        final SysMsgType02Attachment sysMsgAttachment = (SysMsgType02Attachment) message.getAttachment();
        if (sysMsgAttachment == null) {
            return;
        }
        String itemTitle = sysMsgAttachment.getMsgTitle();


        String data = sysMsgAttachment.getMsgData();
        String contentStr = JsonUtil.jsonGetString(data, "content");
        switch (sysMsgAttachment.getMsgType()) {
            case CustomAttachmentType.sys_bank://系统消息 中总对总银行反馈
                String id = JsonUtil.jsonGetString(data, "id");
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BankDetaleActivity.start(context, id, itemTitle);
                    }
                };


                break;
            case CustomAttachmentType.sys_car://中总对总车辆反馈
                String ida = JsonUtil.jsonGetString(data, "id");
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BankDetaleActivity.start(context, ida, itemTitle);

                    }
                };

                break;


        }

        look.setOnClickListener(onClickListener);
        tvTitle.setText(itemTitle);
        mesContetn.setText(contentStr);

    }


}
