package com.oncloudsoft.sdk.yunxin.session.viewholder;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.utils.TextUtils;
import com.oncloudsoft.sdk.yunxin.session.extension.SysMsgType04Attachment;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;

/**
 * 系统消息的解析类  普通布局  内容
 */
public class SysMsgType04Holder extends CustomBaseHolder<SysMsgType04Attachment> {
    private LinearLayout parent;
    private TextView tvContent;


    public SysMsgType04Holder(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.sysmsg_itemtype04;


    }

    @Override
    protected void inflateContentView() {
        parent = findViewById(R.id.packet_ll);
        tvContent = new TextView(context);

    }


    @Override
    protected void bindCustomContentView(SysMsgType04Attachment sysMsgType04Attachment) {
        parent.removeAllViews();
        if (isReceivedMessage()) {//左边
            tvContent.setTextColor(context.getResources().getColor(R.color.textcolor_02));
        } else {
            tvContent.setTextColor(context.getResources().getColor(R.color.gray9));
        }
        parent.addView(tvContent);

        tvContent.setText(TextUtils.ToDBC(sysMsgType04Attachment.getMsgData().replaceAll(" ", "")));

    }


}
