package com.oncloudsoft.sdk.yunxin.uikit.business.session.viewholder;

import com.netease.nimlib.sdk.msg.attachment.VideoAttachment;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.activity.WatchVideoActivity2;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.media.BitmapDecoder;

/**
 * Created by zhoujianghua on 2015/8/5.
 */
public class MsgViewHolderVideo extends MsgViewHolderThumbBase {

    public MsgViewHolderVideo(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_video;
    }

    @Override
    protected void onItemClick() {
        String path = ((VideoAttachment) message.getAttachment()).getPath();
        WatchVideoActivity2.start(context, message);
        //JzvdStd.startFullscreen(context, JzvdStd.class, path, "饺子辛苦了");
    }

    @Override
    protected String thumbFromSourceFile(String path) {
        VideoAttachment attachment = (VideoAttachment) message.getAttachment();
        String thumb = attachment.getThumbPathForSave();
        return BitmapDecoder.extractThumbnail(path, thumb) ? thumb : null;
    }

    @Override
    protected void isChart(boolean isChart) {

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
