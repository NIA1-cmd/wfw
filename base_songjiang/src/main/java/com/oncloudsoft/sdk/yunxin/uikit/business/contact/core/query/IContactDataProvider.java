package com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.query;

import com.oncloudsoft.sdk.yunxin.uikit.business.contact.core.item.AbsContactItem;

import java.util.List;

/**
 * 通讯录数据源提供者接口
 * Created by huangjun on 2015/4/2.
 */
public interface IContactDataProvider {
    public List<AbsContactItem> provide(TextQuery query);
}
