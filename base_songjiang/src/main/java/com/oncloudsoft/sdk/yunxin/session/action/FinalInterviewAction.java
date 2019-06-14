package com.oncloudsoft.sdk.yunxin.session.action;

import com.netease.nimlib.sdk.team.model.Team;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.FinalInterviewActivity;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.actions.BaseAction;
import com.oncloudsoft.sdk.yunxin.uikit.impl.cache.TeamDataCache;

/**
 * 终本约谈
 */
public class FinalInterviewAction extends BaseAction {
    /**
     * 构造函数
     */
    public FinalInterviewAction() {
        super(R.drawable.action_finalinterview, R.string.final_interview);
    }

    @Override
    public void onClick() {
        Team team = TeamDataCache.getInstance().getTeamById(getContainer().account);
        FinalInterviewActivity.start(getActivity(),team.getId());
    }
}
