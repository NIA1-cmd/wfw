package com.oncloudsoft.sdk.yunxin.session.action;

import com.netease.nimlib.sdk.team.model.Team;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.event.SongJiangEventData;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.actions.BaseAction;
import com.oncloudsoft.sdk.yunxin.uikit.impl.cache.TeamDataCache;

import org.greenrobot.eventbus.EventBus;


/**
 * 多媒体采集
 */
public class MediaCollectionAction extends BaseAction {
    /**
     * 构造函数

     */
    public MediaCollectionAction() {
        super(R.drawable.action_mediacollection, R.string.message_media_collection);
    }

    @Override
    public void onClick() {
        Team team = TeamDataCache.getInstance().getTeamById(getContainer().account);
        SongJiangEventData songJiangEventData = new SongJiangEventData();

        songJiangEventData.setType(3);
        songJiangEventData.setTeamId(team.getId());
        EventBus.getDefault().postSticky(songJiangEventData);
        ActivityHelper.getInstance().finishAll();

    }
}
