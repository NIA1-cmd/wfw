package com.oncloudsoft.sdk.yunxin.session.viewholder;

import com.oncloudsoft.sdk.yunxin.session.extension.SysMsgAttachment;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.viewholder.MsgViewHolderBase;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;

/**
 * 文件描述：
 * 作者：黄继栋
 * 创建时间：2019/5/17
 */
public abstract class CustomBaseHolder<T extends SysMsgAttachment> extends MsgViewHolderBase {
    public T t;
    public CustomBaseHolder(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected void bindContentView() {

        SysMsgAttachment sysMsgAttachment = (SysMsgAttachment) message.getAttachment();
        if (sysMsgAttachment != null) {
            t= (T) sysMsgAttachment;
            bindCustomContentView(t);


        }

    }

    protected abstract void bindCustomContentView(T t);


}
