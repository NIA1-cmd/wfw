package com.oncloudsoft.sdk.yunxin.session.viewholder;

import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.utils.ExcludeUtils;
import com.oncloudsoft.sdk.yunxin.session.extension.CustomAttachmentType;
import com.oncloudsoft.sdk.yunxin.session.extension.SysMsgTypeCustomTipsAttachment;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;

/**
 * 文件描述：自定义提示消息类的holder    目前用作所有的需要验证权限的消息类型
 * 作者：黄继栋
 * 创建时间：2019/4/13
 */
public class SysMsgTypeCustomTipsHolder extends CustomBaseHolder<SysMsgTypeCustomTipsAttachment> {
    private TextView notificationTextView;

    public SysMsgTypeCustomTipsHolder(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_notification;
    }

    @Override
    protected void inflateContentView() {
        notificationTextView = view.findViewById(R.id.message_item_notification_label);

    }

    @Override
    protected void bindCustomContentView(SysMsgTypeCustomTipsAttachment sysMsgTypeCustomTipsAttachment) {
        JSONArray msgAuth = sysMsgTypeCustomTipsAttachment.getMsgAuth();


        ViewGroup.MarginLayoutParams mp; //item的宽高

        if (ExcludeUtils.getExclude(msgAuth)) {
            mp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            switch (sysMsgTypeCustomTipsAttachment.getMsgType()) {
                case CustomAttachmentType.sys_tip://系统提示类消息
                    notificationTextView.setText(sysMsgTypeCustomTipsAttachment.getMsgData());
                    break;
                case CustomAttachmentType.sys_applypeople_enterchat://申请人进群通知

                    break;
                case CustomAttachmentType.sys_final_publicity://终本公示消息
                    break;
            }
        } else {//没有权限
            mp = new ViewGroup.MarginLayoutParams(0, 0);//不再占位置
        }

        mp.setMargins(0, 0, 0, 0);//分别是margin_top那四个属性
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mp);
        mRlParentBaseItemLayout.setLayoutParams(lp);


    }


    @Override
    protected boolean isMiddleItem() {
        return true;
    }
}
