package com.oncloudsoft.sdk.yunxin.session.action;

import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.actions.BaseAction;

/**
 * Tip类型消息测试
 * Created by hzxuwen on 2016/3/9.
 */
public class TipAction extends BaseAction {

    public TipAction() {
        super(R.drawable.message_plus_tip_selector, R.string.cancel);
    }

    @Override
    public void onClick() {
        IMMessage msg = MessageBuilder.createTipMessage(getAccount(), getSessionType());
        msg.setContent("提醒消息");

        CustomMessageConfig config = new CustomMessageConfig();
        config.enablePush = false; // 不推送
        msg.setConfig(config);

        sendMessage(msg);
    }
}
