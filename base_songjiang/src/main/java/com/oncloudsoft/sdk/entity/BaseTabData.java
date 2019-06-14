package com.oncloudsoft.sdk.entity;

import com.oncloudsoft.sdk.fragment.base_fragment.BaseFragment;

/**
 * 文件描述：公共的 tab viewpager 数据
 * 作者：黄继栋
 * 创建时间：2019/5/17
 */
public class BaseTabData {
    private BaseFragment baseFragment;
    private String fragmentTitle;

    public BaseFragment getBaseFragment() {
        return baseFragment;
    }

    public void setBaseFragment(BaseFragment baseFragment) {
        this.baseFragment = baseFragment;
    }

    public String getFragmentTitle() {
        return fragmentTitle;
    }

    public void setFragmentTitle(String fragmentTitle) {
        this.fragmentTitle = fragmentTitle;
    }
}
