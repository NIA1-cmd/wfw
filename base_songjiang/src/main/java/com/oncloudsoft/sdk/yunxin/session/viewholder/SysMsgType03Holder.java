package com.oncloudsoft.sdk.yunxin.session.viewholder;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.utils.TextUtils;
import com.oncloudsoft.sdk.yunxin.session.extension.SysMsgType03Attachment;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;

/**
 * 系统消息的解析类  普通布局 标题内容
 */
public class SysMsgType03Holder extends CustomBaseHolder<SysMsgType03Attachment>{
    private LinearLayout llParent;

    private TextView tvTitle;
    private TextView mesContetn;

    public SysMsgType03Holder(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {


        return R.layout.sysmsg_itemtype03;

    }

    @Override
    protected void inflateContentView() {
        llParent = findViewById(R.id.packet_ll);
        tvTitle = new TextView(context);
        mesContetn = new TextView(context);

    }

    @Override
    protected void bindContentView() {


    }

    @Override
    protected void bindCustomContentView(SysMsgType03Attachment sysMsgType03Attachment) {
        llParent.removeAllViews();
        if (isReceivedMessage()) {//左边
            tvTitle.setTextColor(context.getResources().getColor(R.color.textcolor_01));
            mesContetn.setTextColor(context.getResources().getColor(R.color.textcolor_02));
        } else {
            tvTitle.setTextColor(context.getResources().getColor(R.color.white));
            mesContetn.setTextColor(context.getResources().getColor(R.color.gray9));
        }
        Float dimension = context.getResources().getDimension(R.dimen.max_text_bubble_width);

        mesContetn.setMaxWidth(dimension.intValue());

        llParent.addView(tvTitle);
        llParent.addView(mesContetn);



        tvTitle.setText(sysMsgType03Attachment.getMsgTitle());
        mesContetn.setText(TextUtils.ToDBC(sysMsgType03Attachment.getMsgData().replaceAll(" ", "")));

    }

}
