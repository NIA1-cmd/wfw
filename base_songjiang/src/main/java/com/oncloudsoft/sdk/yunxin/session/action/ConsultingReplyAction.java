package com.oncloudsoft.sdk.yunxin.session.action;

import com.netease.nimlib.sdk.team.model.Team;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.ConsultingReplyActivity;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.actions.BaseAction;
import com.oncloudsoft.sdk.yunxin.uikit.impl.cache.TeamDataCache;

/**
 * 咨询回复
 */
public class ConsultingReplyAction extends BaseAction {
    /**
     * 构造函数
     */
    public ConsultingReplyAction() {
        super(R.drawable.icon_consulting_reply, R.string.consulting_reply);
    }

    @Override
    public void onClick() {
        Team team = TeamDataCache.getInstance().getTeamById(getContainer().account);
        ConsultingReplyActivity.start(getActivity(),team.getId());
    }
}
