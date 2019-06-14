package com.oncloudsoft.sdk.yunxin.uikit.business.session.viewholder;

import android.app.Activity;

import com.netease.nimlib.sdk.msg.attachment.ImageAttachment;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.BigImagActivity;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.activity.WatchMessagePictureActivity;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;

/**
 * Created by zhoujianghua on 2015/8/4.
 */
public class MsgViewHolderPicture extends MsgViewHolderThumbBase {
private boolean isChart=true;//默认是在群聊中用的PictyreHolder
    public MsgViewHolderPicture(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_picture;
    }

    @Override
    protected void onItemClick() {
        if (isChart){
            WatchMessagePictureActivity.start(context, message);

        }else {
            ImageAttachment attachment = (ImageAttachment) message.getAttachment();
            BigImagActivity.startBigImg((Activity) context,attachment.getUrl());
        }


    }

    @Override
    protected String thumbFromSourceFile(String path) {
        return path;
    }

    @Override
    protected void isChart(boolean isChart) {
       this. isChart=false;
    }

    @Override
    protected int leftBackground() {

        return 0;
    }

    @Override
    protected int rightBackground() {
       return  0;
    }
}
