package com.oncloudsoft.sdk.yunxin.uikit.business.session.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.team.model.Team;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.yunxin.uikit.api.NimUIKit;
import com.oncloudsoft.sdk.yunxin.uikit.common.ToastHelper;

/**
 * Created by zhoujianghua on 2015/9/10.
 */
public class TeamMessageFragment extends MessageFragment {

    private Team team;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public boolean isAllowSendMessage(IMMessage message) {
        if (team == null) {
            team = NimUIKit.getTeamProvider().getTeamById(sessionId);
        }
        if (team == null || !team.isMyTeam()) {
            ToastHelper.showToast(getActivity(), R.string.team_send_message_not_allow);
            return false;
        }


        return super.isAllowSendMessage(message);
    }

    public void setTeam(Team team) {
        this.team = team;
    }





    @Override
    public void onResume() {
        super.onResume();

       /* imageView.setVisibility(View.VISIBLE);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Team team = NimUIKit.getTeamProvider().getTeamById(sessionId);

                TeamMemberActivity.start(getActivity(), TeamExpansionInfo.getExpansionInfo(team.getExtServer()).getAjbs());


            }
        });*/

    }

    //    public void  aaa(){
////        imageView.setVisibility(View.VISIBLE);
////
////        imageView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////            }
////        });
//
//    }

}