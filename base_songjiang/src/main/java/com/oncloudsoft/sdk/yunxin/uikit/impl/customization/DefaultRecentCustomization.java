package com.oncloudsoft.sdk.yunxin.uikit.impl.customization;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.NotificationAttachment;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.oncloudsoft.sdk.yunxin.session.extension.DefaultCustomAttachment;
import com.oncloudsoft.sdk.yunxin.session.extension.StickerAttachment;
import com.oncloudsoft.sdk.yunxin.session.extension.SysMsgAttachment;
import com.oncloudsoft.sdk.yunxin.uikit.api.model.recent.RecentCustomization;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.helper.TeamNotificationHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by huangjun on 2017/9/29.
 */

public class DefaultRecentCustomization extends RecentCustomization {

    /**
     * 最近联系人列表项文案定制
     *
     * @param recent 最近联系人
     * @return 默认文案
     */
    public String getDefaultDigest(RecentContact recent) {
        switch (recent.getMsgType()) {
            case text:
                return recent.getContent();
            case image:
                return "[图片]";
            case video:
                return "[视频]";
            case audio:
                return "[语音消息]";
            case location:
                return "[位置]";
            case file:
                return "[文件]";
            case tip:
                List<String> uuids = new ArrayList<>();
                uuids.add(recent.getRecentMessageId());
                List<IMMessage> messages = NIMClient.getService(MsgService.class).queryMessageListByUuidBlock(uuids);
                if (messages != null && messages.size() > 0) {
                    return messages.get(0).getContent();
                }
                return "[通知提醒]";
            case notification:
                return TeamNotificationHelper.getTeamNotificationText(recent.getContactId(),
                        recent.getFromAccount(),
                        (NotificationAttachment) recent.getAttachment());
            case robot:
                return "[机器人消息]";

            case custom:
                MsgAttachment attachment = recent.getAttachment();

                if (attachment instanceof StickerAttachment) {
                    return "[贴图表情]";

                } else if (attachment instanceof SysMsgAttachment) {
                    SysMsgAttachment sysMsgAttachment = (SysMsgAttachment) attachment;
                    return "["+sysMsgAttachment.getMsgTitle()+"]";
                } else if (attachment instanceof DefaultCustomAttachment) {
                    return "[不能解析] ";

                } else {
                    return "[自定义未知消息类型] ";

                }
            default:
                return "[未知消息类型] ";
        }
    }
    public String getTitle(IMMessage imMessage) {
        switch (imMessage.getMsgType()) {
            case text:
                return imMessage.getContent();
            case image:
                return "[图片]";
            case video:
                return "[视频]";
            case audio:
                return "[语音消息]";
            case location:
                return "[位置]";
            case file:
                return "[文件]";
            case tip:
                List<String> uuids = new ArrayList<>();
                uuids.add(imMessage.getUuid());
                List<IMMessage> messages = NIMClient.getService(MsgService.class).queryMessageListByUuidBlock(uuids);
                if (messages != null && messages.size() > 0) {
                    return messages.get(0).getContent();
                }
                return "[通知提醒]";
            case notification:
                return TeamNotificationHelper.getTeamNotificationText(imMessage.getSessionId(), imMessage.getFromAccount(), (NotificationAttachment) imMessage.getAttachment());
            case robot:
                return "[机器人消息]";

            case custom:
                MsgAttachment attachment = imMessage.getAttachment();

                if (attachment instanceof StickerAttachment) {
                    return "[贴图表情]";

                }
                else if (attachment instanceof SysMsgAttachment) {
                    SysMsgAttachment sysMsgAttachment = (SysMsgAttachment) attachment;
                    return "[" + sysMsgAttachment.getMsgTitle() + "]";
                }
                else if (attachment instanceof DefaultCustomAttachment) {
                    return "[不能解析] ";

                }
                else {
                    return "[自定义未知消息类型] ";

                }
            default:
                return "[未知消息类型] ";
        }

    }
}
