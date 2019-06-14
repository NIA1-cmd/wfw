package com.oncloudsoft.sdk.fragment.approval;

import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.BaseTabData;
import com.oncloudsoft.sdk.fragment.base_fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 黄继栋
 * 创建时间 2019/1/31 10:09
 * 描述  统一的审批界面  审批
 */

public abstract class BaseTablayoutApprovalActivity extends BaseTablayoutActivity {


    protected abstract BaseFragment getThroughFragment();

    protected abstract BaseFragment getPendingFragment();

    protected abstract BaseFragment getRefuseFragment();


    protected abstract List<String> getLabTitle(List<String> titleList);


    @Override
    protected List<BaseTabData> getBaseTabData(List<BaseTabData> list) {
        List<String> titleList = new ArrayList<>();
        titleList.clear();
        getLabTitle(titleList);


        //待审批
        BaseTabData pendingBaseTabData = new BaseTabData();
        pendingBaseTabData.setBaseFragment(getPendingFragment());
        pendingBaseTabData.setFragmentTitle(titleList.get(0));

        //已通过
        BaseTabData throughBaseTabData = new BaseTabData();
        throughBaseTabData.setBaseFragment(getThroughFragment());
        throughBaseTabData.setFragmentTitle(titleList.get(1));

        // 已拒绝
        BaseTabData refuseBaseTabData = new BaseTabData();
        refuseBaseTabData.setBaseFragment(getRefuseFragment());
        refuseBaseTabData.setFragmentTitle(titleList.get(2));


        list.add(pendingBaseTabData);
        list.add(throughBaseTabData);
        list.add(refuseBaseTabData);

        return list;
    }

    @Override
    protected String getTitleText() {
        return getIntent().getStringExtra(Global.TITLE);
    }

}
