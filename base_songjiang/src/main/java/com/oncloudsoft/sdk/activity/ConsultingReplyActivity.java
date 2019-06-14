package com.oncloudsoft.sdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.BaseTabData;
import com.oncloudsoft.sdk.fragment.ConsultingReplyFragment;
import com.oncloudsoft.sdk.fragment.approval.BaseTablayoutActivity;

import java.util.List;

/**
 * 作者 Mingduo Cao
 * 创建时间 2019/6/7 11:22
 * <p>
 * 描述 咨询回复
 */

public class ConsultingReplyActivity extends BaseTablayoutActivity{

    private String caseId;

    public static void start(Activity activity, String caseId) {
        Intent intent = new Intent();
        intent.setClass(activity, ConsultingReplyActivity.class);
        intent.putExtra(Global.CASEID, caseId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        caseId = getIntent().getStringExtra(Global.CASEID);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected String getTitleText() {
        return resources.getString(R.string.consulting_reply);
    }

    @Override
    protected List<BaseTabData> getBaseTabData(List<BaseTabData> list) {
        ConsultingReplyFragment consultingReplyFragment = new ConsultingReplyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Global.CASEID, caseId);
        bundle.putInt(Global.TYPE, 0);
        consultingReplyFragment.setArguments(bundle);
        BaseTabData baseinfoTab = new BaseTabData();
        baseinfoTab.setBaseFragment(consultingReplyFragment);
        baseinfoTab.setFragmentTitle(resources.getString(R.string.replied));


        ConsultingReplyFragment consultingReplyFragment1 = new ConsultingReplyFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString(Global.CASEID, caseId);
        bundle1.putInt(Global.TYPE, 1);
        consultingReplyFragment1.setArguments(bundle1);
        BaseTabData baseinfoTab1 = new BaseTabData();
        baseinfoTab1.setBaseFragment(consultingReplyFragment1);
        baseinfoTab1.setFragmentTitle(resources.getString(R.string.pending_reply));

        list.add(baseinfoTab);
        list.add(baseinfoTab1);

        return list;
    }
}
