package com.oncloudsoft.sdk.yunxin.session.action;

import com.netease.nimlib.sdk.team.model.Team;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.approval.ClueProvideActivity;
import com.oncloudsoft.sdk.event.SongJiangEventData;
import com.oncloudsoft.sdk.yunxin.uikit.business.custom.TeamExpansionInfo;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.actions.BaseAction;
import com.oncloudsoft.sdk.yunxin.uikit.impl.cache.TeamDataCache;

import org.greenrobot.eventbus.EventBus;

import javax.net.ssl.SSLServerSocket;


/**
 * 线索举报
 */
public class CluereportAction extends BaseAction {
    /**
     * 构造函数

     */
    public CluereportAction() {
        super(R.drawable.action_cluereport, R.string.cluereport);
    }

    @Override
    public void onClick() {
        Team team = TeamDataCache.getInstance().getTeamById(getContainer().account);
        String ajbs = TeamExpansionInfo.getExpansionInfo(team.getExtServer()).getAjbs();
        ClueProvideActivity.start(getActivity(),ajbs,"线索举报");
    }
}
