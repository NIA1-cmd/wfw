package com.oncloudsoft.sdk.yunxin.uikit.business.chatroom.viewholder;

import com.oncloudsoft.sdk.R;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;

/**
 * Created by huangjun on 2016/12/27.
 */
public class ChatRoomMsgViewHolderUnknown extends ChatRoomMsgViewHolderBase {

    public ChatRoomMsgViewHolderUnknown(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_unknown;
    }

    @Override
    protected boolean isShowHeadImage() {
        if (message.getSessionType() == SessionTypeEnum.ChatRoom) {
            return false;
        }
        return true;
    }

    @Override
    protected void inflateContentView() {
    }

    @Override
    protected void bindContentView() {
    }
}
