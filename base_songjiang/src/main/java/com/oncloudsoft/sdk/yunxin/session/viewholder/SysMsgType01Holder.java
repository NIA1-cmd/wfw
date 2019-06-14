package com.oncloudsoft.sdk.yunxin.session.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.DialogActivity;
import com.oncloudsoft.sdk.activity.webview.LongImageWebActivity;
import com.oncloudsoft.sdk.activity.webview.TestWevActivity;
import com.oncloudsoft.sdk.utils.JsonUtil;
import com.oncloudsoft.sdk.yunxin.session.extension.CustomAttachmentType;
import com.oncloudsoft.sdk.yunxin.session.extension.SysMsgType01Attachment;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.string.StringUtil;

/**
 * 系统消息的解析类  普通布局 标题-查看
 */
public class SysMsgType01Holder extends CustomBaseHolder<SysMsgType01Attachment>  {
    private LinearLayout parent;
    private TextView tvTitle;
    private TextView look;
    private String title;
    private String data;

    private View line;

    public SysMsgType01Holder(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    LinearLayout.LayoutParams textLayoutParams;
    LinearLayout.LayoutParams lookLayoutParams;

    @Override
    protected int getContentResId() {
        return R.layout.sysmsg_itemtype01;

    }

    @Override
    protected void inflateContentView() {

        parent = findViewById(R.id.packet_ll);

        tvTitle = new TextView(context);
        look = new TextView(context);
        look.setText("查看");
        line = new View(context);

        textLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lookLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(2, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(20, 0, 20, 0);
        line.setLayoutParams(params);
    }

    @Override
    protected void bindCustomContentView(SysMsgType01Attachment sysMsgType01Attachment) {
        parent.removeAllViews();
        if (isReceivedMessage()) {//左边
            tvTitle.setTextColor(context.getResources().getColor(R.color.textcolor_01));
            look.setTextColor(context.getResources().getColor(R.color.maincolor_01));
            line.setBackgroundColor(context.getResources().getColor(R.color.orthercolor_03));
            parent.addView(tvTitle);
            parent.addView(line);
            parent.addView(look);
            textLayoutParams.setMargins(20, 0, 0, 0);
            tvTitle.setLayoutParams(textLayoutParams);


            lookLayoutParams.setMargins(0, 0, 20, 0);
            look.setLayoutParams(lookLayoutParams);

        } else {
            tvTitle.setTextColor(context.getResources().getColor(R.color.white));
            look.setTextColor(context.getResources().getColor(R.color.pink));
            line.setBackgroundColor(context.getResources().getColor(R.color.white));


            lookLayoutParams.setMargins(20, 0, 0, 0);
            look.setLayoutParams(lookLayoutParams);


            textLayoutParams.setMargins(0, 0, 20, 0);
            tvTitle.setLayoutParams(textLayoutParams);


            parent.addView(look);
            parent.addView(line);
            parent.addView(tvTitle);
        }


        View.OnClickListener onClickListener = null;
        final SysMsgType01Attachment sysMsgAttachment = (SysMsgType01Attachment) message.getAttachment();
        if (sysMsgAttachment == null) {
            return;
        }
        data = sysMsgAttachment.getMsgData();
        //id = message.getSessionId();
        title = sysMsgAttachment.getMsgTitle();
        if (title == null) {
            title = "";
        }
        if (title.length() > 12) {//长度超过12位的，内容需要折行
            try {
                title = StringUtil.getStringByEnter(20, title);
            } catch (Exception e) {
                title = "异常";
                e.printStackTrace();
            }
        }
        switch (sysMsgAttachment.getMsgType()) {
            case CustomAttachmentType.sys_onlyhouse://系统消息 中被执行人唯一住房，如何执行
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogActivity.showCaseGuideOnlyHouseDialog((Activity)context);
                    }
                };


                break;
            case CustomAttachmentType.sys_lease://系统消息 中被执行人房产处于租赁状态, 如何执行?
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogActivity.showCaseGuideRentDialog((Activity)context);

                    }
                };

                break;
            case CustomAttachmentType.sys_executive_notification://执行通知书
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent pdfIntent=new Intent();
                        TestWevActivity.start(context, JsonUtil.jsonGetString(sysMsgAttachment.getMsgData(), "url"), title,pdfIntent);
                    }
                };
                break;
            case CustomAttachmentType.sys_process_notification://金钱给付类执行案件流程告知书
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LongImageWebActivity.start(context, JsonUtil.jsonGetString(sysMsgAttachment.getMsgData(), "url"), title);
                    }
                };
                break;
            case CustomAttachmentType.sys_property_notification://	财产报告令
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TestWevActivity.start(context, JsonUtil.jsonGetString(sysMsgAttachment.getMsgData(), "url"), title,new Intent());
                    }
                };
                break;
            case CustomAttachmentType.sys_message_09://	通知申请人拍卖平台选择
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogActivity.showSelectDialog((Activity) context, JsonUtil.jsonGetString(data, "id"), new DialogActivity.OnNormalDialogClick() {
                            @Override
                            public void OnConfirm() {

                            }

                            @Override
                            public void OnCancel() {

                            }
                        });

                    }
                };
                break;
        }

        look.setOnClickListener(onClickListener);
        tvTitle.setText(title);

    }


}
