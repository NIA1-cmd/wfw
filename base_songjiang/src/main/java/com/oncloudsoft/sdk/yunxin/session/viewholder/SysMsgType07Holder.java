package com.oncloudsoft.sdk.yunxin.session.viewholder;

import android.content.Intent;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.activity.webview.TestWevActivity;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.utils.JsonUtil;
import com.oncloudsoft.sdk.utils.OnSingleClickListener;
import com.oncloudsoft.sdk.yunxin.session.extension.SysMsgType07Attachment;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;

/**
 * 和解协议
 */
public class SysMsgType07Holder extends CustomBaseHolder<SysMsgType07Attachment> {
    private LinearLayout llParent;
    private LinearLayout llContent;
    private ImageView imageView;
    private TextView textView;
    private LinearLayout.LayoutParams imageViewParams;

    public SysMsgType07Holder(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.sysmsg_itemtype07;

    }

    @Override
    protected void inflateContentView() {

        llParent = findViewById(R.id.ll_parent);
        llContent = findViewById(R.id.ll_content);

        imageView = new ImageView(context);
        imageView.setImageDrawable(((BaseActivity) context).getDrawableById(R.drawable.icon_pdf));
        imageViewParams = new LinearLayout.LayoutParams(80, 80);
        imageViewParams.width = imageViewParams.height = 100;
        imageView.setLayoutParams(imageViewParams);

        textView = new TextView(context);
        textView.setText(((BaseActivity) context).getStringById(R.string.reconciliation_protocol_pdf));
        textView.setTextAppearance(context, R.style.text_style_09);

    }

    @Override
    protected void bindCustomContentView(SysMsgType07Attachment sysMsgType07Attachment) {
        final SysMsgType07Attachment sysMsgAttachment = (SysMsgType07Attachment) message.getAttachment();
        if (sysMsgAttachment == null) {
            return;
        }
        String json = sysMsgAttachment.getMsgData();

        String pdfUrl = JsonUtil.jsonGetString(json, "pdfUrl");
        int id = JsonUtil.jsonGetIntger(json, "id");

        llContent.removeAllViews();
        if (isReceivedMessage()) {//左边

            llContent.addView(textView);
            llContent.addView(imageView);

            llContent.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);//将布局靠右
            imageViewParams.leftMargin = 40;
            imageViewParams.rightMargin = 0;
        } else {

            llContent.addView(imageView);
            llContent.addView(textView);
            imageViewParams.leftMargin = 0;
            imageViewParams.rightMargin = 40;
            llContent.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);//将布局靠左
        }

        llParent.setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick() {
                Intent pdfIntent = new Intent();
                pdfIntent.putExtra(Global.PDF_ID, id + "");
                TestWevActivity.start(context, pdfUrl, "和解详情", pdfIntent);

            }
        });
    }

    @Override
    protected int leftBackground() {
        return R.drawable.nim_message_item_left_white_selector;
    }

    @Override
    protected int rightBackground() {
        return R.drawable.nim_message_item_right_white_selector;
    }
}
