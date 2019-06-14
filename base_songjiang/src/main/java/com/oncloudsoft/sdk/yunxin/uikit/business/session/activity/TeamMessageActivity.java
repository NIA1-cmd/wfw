package com.oncloudsoft.sdk.yunxin.uikit.business.session.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.constant.TeamTypeEnum;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.SongJiangSDk;
import com.oncloudsoft.sdk.activity.CaseInfoActivity;
import com.oncloudsoft.sdk.activity.TeamMemberActivity;
import com.oncloudsoft.sdk.activity.approval.ClueProvideActivity;
import com.oncloudsoft.sdk.activity.message_search.SearchMessageActivity;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.app.MyApplication;
import com.oncloudsoft.sdk.event.TeamEvent;
import com.oncloudsoft.sdk.utils.OnSingleClickListener;
import com.oncloudsoft.sdk.view.BaseTitleView;
import com.oncloudsoft.sdk.yunxin.uikit.api.NimUIKit;
import com.oncloudsoft.sdk.yunxin.uikit.api.model.SimpleCallback;
import com.oncloudsoft.sdk.yunxin.uikit.api.model.contact.ContactChangedObserver;
import com.oncloudsoft.sdk.yunxin.uikit.api.model.session.SessionCustomization;
import com.oncloudsoft.sdk.yunxin.uikit.api.model.team.TeamDataChangedObserver;
import com.oncloudsoft.sdk.yunxin.uikit.api.model.team.TeamMemberDataChangedObserver;
import com.oncloudsoft.sdk.yunxin.uikit.business.custom.TeamExpansionInfo;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.constant.Extras;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.constant.RequestCode;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.fragment.MessageFragment;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.fragment.TeamMessageFragment;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.helper.VideoMessageHelper;
import com.oncloudsoft.sdk.yunxin.uikit.common.ToastHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 群聊界面
 * <p/>
 * Created by huangjun on 2015/3/5.
 */
public class TeamMessageActivity extends BaseMessageActivity {
    private static SongJiangSDk.onSongJiangOprationListener oprationListener;

    private BaseTitleView btTitle;
    private FrameLayout ivTeammember;
    private TextView invalidTeamText;
    private RelativeLayout invalidTeamTip;
    private View redMessage;
    private ImageView ivBaseifno;
    public Team team;


    private TeamMessageFragment fragment;

    private Class<? extends Activity> backToClass;
    //    private DBContext dbContext;
    private List<String> list = new ArrayList<String>();
    private boolean b;

    public static TeamMessageActivity teamMessageActivity;


    public static void setOpenListener(SongJiangSDk.onSongJiangOprationListener listener) {
        oprationListener = listener;
    }

    public static void start(Context context, String tid, SessionCustomization customization, Class<? extends Activity> backToClass, IMMessage anchor) {


        Intent intent = new Intent();
        intent.putExtra(Extras.EXTRA_ACCOUNT, tid);
        intent.putExtra(Extras.EXTRA_CUSTOMIZATION, customization);
        intent.putExtra(Extras.EXTRA_BACK_TO_CLASS, backToClass);
        if (anchor != null) {
            intent.putExtra(Extras.EXTRA_ANCHOR, anchor);
        }
        intent.setClass(context, TeamMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    protected void initView() {
        teamMessageActivity = this;
        btTitle = findViewById(R.id.bt_title);
        ivTeammember = findViewById(R.id.iv_teammember);
        invalidTeamText = findViewById(R.id.invalid_team_text);
        invalidTeamTip = findViewById(R.id.invalid_team_tip);
        redMessage = findViewById(R.id.red_message);
        ivBaseifno = findViewById(R.id.iv_baseifno);
        btTitle.setOnLeftImageClickListener(new BaseTitleView.onLeftImageClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    private String caseId;
    private TeamExpansionInfo.TeamExpansionData expansionInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backToClass = (Class<? extends Activity>) getIntent().getSerializableExtra(Extras.EXTRA_BACK_TO_CLASS);
        initTeamMember();
//        initSQLite();
        registerTeamUpdateObserver(true);
        //注册EventBus
        EventBus.getDefault().register(this);
        initView();
        Team team = NimUIKit.getTeamProvider().getTeamById(sessionId);
        if (team == null) {
            if (oprationListener != null) {
                oprationListener.onException("群组打开异常");
            }
            finish();
        } else {
            if (!team.isMyTeam()) {
                if (oprationListener != null) {
                    oprationListener.onException("已退出该群聊");
                }
                finish();
            } else {
                expansionInfo = TeamExpansionInfo.getExpansionInfo(team.getExtServer());
                caseId = expansionInfo.getAjbs();
                ivTeammember.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    protected void onSingleClick() {
                        TeamMemberActivity.start(TeamMessageActivity.this, caseId, 200);//群成员
                    }
                });
                findViewById(R.id.iv_search).setOnClickListener(new OnSingleClickListener() {
                    @Override
                    protected void onSingleClick() {
                        SearchMessageActivity.startSearchMessage(TeamMessageActivity.this, caseId, team.getId());
                    }
                });
                ivBaseifno.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    protected void onSingleClick() {
                        String ajlb = expansionInfo.getAjlb();
                        CaseInfoActivity.start(TeamMessageActivity.this, caseId, "0", ajlb);//基本信息
                    }
                });
                if (oprationListener != null) {
                    oprationListener.onException("打开成功");
                }
            }
        }
    }


    private void sq() {
        try {
            b = MyApplication.dbContext.queryIsUnread("*", "lastMessage", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (b) {
            redMessage.setVisibility(View.VISIBLE);
        } else {
            redMessage.setVisibility(View.GONE);
        }

    }

    private void initTeamMember() {
        NIMClient.getService(TeamService.class).queryMemberList(sessionId).setCallback(new RequestCallbackWrapper<List<TeamMember>>() {
            @Override
            public void onResult(int code, final List<TeamMember> members, Throwable exception) {
                if (members == null) {
                    return;
                }
                for (int i = 0; i < members.size(); i++) {
                    list.add(members.get(i).getAccount());
                }
                sq();
            }
        });

        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(new Observer<StatusCode>() {
            @Override
            public void onEvent(StatusCode status) {
                if (status == StatusCode.KICKOUT || status == StatusCode.KICK_BY_OTHER_CLIENT) {//被踢出或者其他客户端登录之后
                    showToast("您已在其它地方登录");
                    ActivityHelper.getInstance().UserExit();

                }
            }
        }, true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onTeamEvent(TeamEvent event) {
        if (list != null && list.size() > 1) {
            if (list.contains(event.getContactId()) == true) {
                sq();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        registerTeamUpdateObserver(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestTeamInfo();
    }

    /**
     * 请求群基本信息
     */
    private void requestTeamInfo() {
        // 请求群基本信息
        Team t = NimUIKit.getTeamProvider().getTeamById(sessionId);
        if (t != null) {
            updateTeamInfo(t);
        } else {
            NimUIKit.getTeamProvider().fetchTeamById(sessionId, new SimpleCallback<Team>() {
                @Override
                public void onResult(boolean success, Team result, int code) {
                    if (success && result != null) {
                        updateTeamInfo(result);
                    } else {
                        onRequestTeamInfoFailed();
                    }
                }
            });
        }
    }

    private void onRequestTeamInfoFailed() {
        ToastHelper.showToast(TeamMessageActivity.this, "获取群组信息失败!");

        finish();
    }

    /**
     * R
     * 更新群信息
     *
     * @param d
     */
    private void updateTeamInfo(final Team d) {
        if (d == null) {
            return;
        }
        team = d;
        fragment.setTeam(team);
        //setTitle(team == null ? sessionId : team.getName() + "(" + team.getMemberCount() + "人)");
        btTitle.setTitleText(team == null ? sessionId : team.getName() + "(" + (team.getMemberCount() - 1) + ")");
        //        tv_title.setText(team == null ? sessionId : team.getName());
        invalidTeamText.setText(team.getType() == TeamTypeEnum.Normal ? R.string.normal_team_invalid_tip : R.string.team_invalid_tip);
        invalidTeamTip.setVisibility(team.isMyTeam() ? View.GONE : View.VISIBLE);
        MyApplication.setTeamExpansionData(TeamExpansionInfo.getExpansionInfo(d.getExtServer()));
    }

    /**
     * 注册群信息更新监听
     *
     * @param register
     */
    private void registerTeamUpdateObserver(boolean register) {
        NimUIKit.getTeamChangedObservable().registerTeamDataChangedObserver(teamDataChangedObserver, register);
        NimUIKit.getTeamChangedObservable().registerTeamMemberDataChangedObserver(teamMemberDataChangedObserver, register);
        NimUIKit.getContactChangedObservable().registerObserver(friendDataChangedObserver, register);
        //        NIMClient.getService(EventSubscribeService.class).publishEvent(new Event());
    }

    /**
     * 群资料变动通知和移除群的通知（包括自己退群和群被解散）
     */
    TeamDataChangedObserver teamDataChangedObserver = new TeamDataChangedObserver() {
        @Override
        public void onUpdateTeams(List<Team> teams) {
            if (team == null) {
                return;
            }
            for (Team t : teams) {
                if (t.getId().equals(team.getId())) {
                    updateTeamInfo(t);
                    break;
                }
            }
        }

        @Override
        public void onRemoveTeam(Team team) {
            if (team == null) {
                return;
            }
            if (team.getId().equals(TeamMessageActivity.this.team.getId())) {
                updateTeamInfo(team);
            }
        }
    };

    /**
     * 群成员资料变动通知和移除群成员通知
     */
    TeamMemberDataChangedObserver teamMemberDataChangedObserver = new TeamMemberDataChangedObserver() {

        @Override
        public void onUpdateTeamMember(List<TeamMember> members) {
            fragment.refreshMessageList();
        }

        @Override
        public void onRemoveTeamMember(List<TeamMember> member) {
        }
    };

    ContactChangedObserver friendDataChangedObserver = new ContactChangedObserver() {
        @Override
        public void onAddedOrUpdatedFriends(List<String> accounts) {
            fragment.refreshMessageList();
        }

        @Override
        public void onDeletedFriends(List<String> accounts) {
            fragment.refreshMessageList();
        }

        @Override
        public void onAddUserToBlackList(List<String> account) {
            fragment.refreshMessageList();
        }

        @Override
        public void onRemoveUserFromBlackList(List<String> account) {
            fragment.refreshMessageList();
        }
    };

    @Override
    protected MessageFragment fragment() {
        // 添加fragment
        Bundle arguments = getIntent().getExtras();
        arguments.putSerializable(Extras.EXTRA_TYPE, SessionTypeEnum.Team);
        fragment = new TeamMessageFragment();
        fragment.setArguments(arguments);
        fragment.setContainerId(R.id.message_fragment_container);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.nim_team_message_activity;
    }

    @Override
    protected void initToolBar() {
       /* TeamToolBarOptions options = new TeamToolBarOptions();
        options.titleString = "群聊";
        setToolBar(R.id.toolbar, options);*/

    }

    @Override
    protected boolean enableSensor() {
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (backToClass != null) {
            Intent intent = new Intent();
            intent.setClass(this, backToClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }

    private VideoMessageHelper videoHelper() {
        if (videoMessageHelper == null) {
            videoMessageHelper = new VideoMessageHelper(TeamMessageActivity.this, new VideoMessageHelper.VideoMessageHelperListener() {

                @Override
                public void onVideoPicked(File file, String md5, int type) {


                    MediaPlayer mediaPlayer = getVideoMediaPlayer(file);
                    long duration = mediaPlayer == null ? 0 : mediaPlayer.getDuration();
                    int height = mediaPlayer == null ? 0 : mediaPlayer.getVideoHeight();
                    int width = mediaPlayer == null ? 0 : mediaPlayer.getVideoWidth();
                    IMMessage message = MessageBuilder.createVideoMessage(fragment.sessionId, fragment.sessionType, file, duration, width, height, md5);

                    fragment.sendMessage(message);
                }
            });
        }
        return videoMessageHelper;
    }

    /**
     * 获取视频mediaPlayer
     *
     * @param file 视频文件
     * @return mediaPlayer
     */
    private MediaPlayer getVideoMediaPlayer(File file) {
        try {
            return MediaPlayer.create(TeamMessageActivity.this, Uri.fromFile(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        switch (requestCode) {
            case RequestCode.GET_LOCAL_VIDEO://本地选择视频文件
                videoHelper().onGetLocalVideoResult(data);
                break;
            case RequestCode.CAPTURE_VIDEO://拍摄视频
                videoHelper().onCaptureVideoResult(data);
                break;
            case RequestCode.PICK_IMAGE://拍照回来
                String photoPath = data.getStringExtra(Extras.EXTRA_FILE_PATH);


                File imageFile = new File(photoPath);
                IMMessage message = MessageBuilder.createImageMessage(fragment.sessionId, fragment.sessionType, imageFile, imageFile.getName());

                fragment.sendMessage(message);

                break;


        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    protected transient VideoMessageHelper videoMessageHelper;

}
