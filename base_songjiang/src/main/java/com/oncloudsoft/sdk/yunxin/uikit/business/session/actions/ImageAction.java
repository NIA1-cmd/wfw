package com.oncloudsoft.sdk.yunxin.uikit.business.session.actions;

import com.oncloudsoft.sdk.R;
import com.netease.nimlib.sdk.chatroom.ChatRoomMessageBuilder;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.io.File;

/**
 * Created by hzxuwen on 2015/6/12.
 */
public class ImageAction extends PickImageAction {

    public ImageAction() {
        super(R.drawable.action_pic, R.string.msg_type_photo, true);
    }

    @Override
    protected void onPicked(File file) {
        IMMessage message;
        if (getContainer() != null && getContainer().sessionType == SessionTypeEnum.ChatRoom) {
            message = ChatRoomMessageBuilder.createChatRoomImageMessage(getAccount(), file, file.getName());
        } else {
            message = MessageBuilder.createImageMessage(getAccount(), getSessionType(), file, file.getName());
        }
        sendMessage(message);
    }
}

