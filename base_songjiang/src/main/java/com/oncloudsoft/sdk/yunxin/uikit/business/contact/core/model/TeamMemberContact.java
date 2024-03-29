package com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.model;

import com.netease.nimlib.sdk.team.model.TeamMember;
import com.oncloudsoft.sdk.yunxin.uikit.business.team.helper.TeamHelper;

/**
 * Created by huangjun on 2015/5/5.
 */
public class TeamMemberContact extends AbsContact {

    private TeamMember teamMember;

    public TeamMemberContact(TeamMember teamMember) {
        this.teamMember = teamMember;
    }

    @Override
    public String getContactId() {
        return teamMember.getAccount();
    }

    @Override
    public int getContactType() {
        return Type.TeamMember;
    }

    @Override
    public String getDisplayName() {
        return TeamHelper.getTeamMemberDisplayName(teamMember.getTid(), teamMember.getAccount());
    }
}
