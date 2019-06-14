package com.oncloudsoft.sdk.yunxin.uikit.common.adapter;

public interface IScrollStateListener {

    /**
     * move to scrap heap
     */
    public void reclaim();


    /**
     * on idle
     */
    public void onImmutable();
}
