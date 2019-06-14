package com.oncloudsoft.sdk.yunxin.session.action;

import com.netease.nimlib.sdk.team.model.Team;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.event.SongJiangEventData;
import com.oncloudsoft.sdk.utils.PopupWindowUtils;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.actions.BaseAction;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.activity.TeamMessageActivity;
import com.oncloudsoft.sdk.yunxin.uikit.impl.cache.TeamDataCache;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * 节点采集
 */
public class NodeAcquisitionAction extends BaseAction {
    /**
     * 构造函数
     */
    public NodeAcquisitionAction() {
        super(R.drawable.action_nodeacquisition, R.string.message_node_acquisition);
    }

    @Override
    public void onClick() {

        SongJiangEventData songJiangEventData = new SongJiangEventData();

        Team team = TeamDataCache.getInstance().getTeamById(getContainer().account);
        List<String> list = new ArrayList<>();
        list.add("财产调查");
        list.add("终本约谈");
        songJiangEventData.setType(2);
        songJiangEventData.setTeamId(team.getId());

        PopupWindowUtils.getInstance().showSelectPopuWindow(TeamMessageActivity.teamMessageActivity, whitch -> {
            songJiangEventData.setPostion(whitch);
            EventBus.getDefault().postSticky(songJiangEventData);
            ActivityHelper.getInstance().finishAll();
        }, list);

    }

}
