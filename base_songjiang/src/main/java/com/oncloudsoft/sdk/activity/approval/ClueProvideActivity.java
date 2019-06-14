package com.oncloudsoft.sdk.activity.approval;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.fragment.approval.BaseTablayoutApprovalActivity;
import com.oncloudsoft.sdk.fragment.approval.ClueProvideFragment;
import com.oncloudsoft.sdk.fragment.base_fragment.BaseFragment;

import java.util.List;

/**
 * 作者 黄继栋
 * 创建时间 2019/1/31 10:54
 * 描述 线索查询列表
 */

public class ClueProvideActivity extends BaseTablayoutApprovalActivity {
    private ClueProvideFragment throughClueProvideFragment = new ClueProvideFragment();//通过  1
    private ClueProvideFragment pendingClueProvideFragment = new ClueProvideFragment();//带审核  0
    private ClueProvideFragment refuseClueProvideFragment = new ClueProvideFragment();//拒绝   2


    @Override
    protected BaseFragment getThroughFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(Global.CASEID, getIntent().getStringExtra(Global.CASEID));
        bundle.putString(Global.TYPE, "1");
        throughClueProvideFragment.setArguments(bundle);
        return throughClueProvideFragment;
    }

    @Override
    protected BaseFragment getPendingFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(Global.CASEID, getIntent().getStringExtra(Global.CASEID));
        bundle.putString(Global.TYPE, "0");
        pendingClueProvideFragment.setArguments(bundle);
        return pendingClueProvideFragment;
    }

    @Override
    protected BaseFragment getRefuseFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(Global.CASEID, getIntent().getStringExtra(Global.CASEID));
        bundle.putString(Global.TYPE, "2");
        refuseClueProvideFragment.setArguments(bundle);
        return refuseClueProvideFragment;
    }

    @Override
    protected List<String> getLabTitle(List<String> titleList) {
        titleList.add(getStringById(R.string.verification_pending));
        titleList.add(getStringById(R.string.verification_passed));
        titleList.add(getStringById(R.string.verification_rejected));
        return titleList;
    }


    public static void start(Context activity, String caseId, String title) {
        Intent intent = new Intent();
        intent.setClass(activity, ClueProvideActivity.class);
        intent.putExtra(Global.TITLE, title);
        intent.putExtra(Global.CASEID, caseId);
        activity.startActivity(intent);

    }
}
