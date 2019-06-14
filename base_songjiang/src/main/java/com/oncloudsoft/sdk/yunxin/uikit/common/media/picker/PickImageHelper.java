package com.oncloudsoft.sdk.yunxin.uikit.common.media.picker;

import android.app.Activity;
import android.content.Context;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.yunxin.uikit.common.media.picker.activity.PickImageActivity;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.storage.StorageType;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.storage.StorageUtil;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.string.StringUtil;
import com.oncloudsoft.sdk.utils.PopupWindowUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangjun on 2015/9/22.
 */
public class PickImageHelper {

    public static class PickImageOption {
        /**
         * 图片选择器标题
         */
        public int titleResId = R.string.choose;

        /**
         * 是否多选
         */
        public boolean multiSelect = true;

        /**
         * 最多选多少张图（多选时有效）
         */
        public int multiSelectMaxCount = 9;

        /**
         * 是否进行图片裁剪(图片选择模式：false / 图片裁剪模式：true)
         */
        public boolean crop = false;

        /**
         * 图片裁剪的宽度（裁剪模式时有效）
         */
        public int cropOutputImageWidth = 720;

        /**
         * 图片裁剪的高度（裁剪模式时有效）
         */
        public int cropOutputImageHeight = 720;

        /**
         * 图片选择保存路径
         */
        public String outputPath = StorageUtil.getWritePath(StringUtil.get32UUID() + ".jpg", StorageType.TYPE_TEMP);
    }

    /**
     * 打开图片选择器
     *
     * @param context
     * @param requestCode
     * @param option
     * @param type        0/-1  :代表 是需要用户去选择的    1 ：代表 选择拍照    2：代表 本地选择拍照
     */
    public static void pickImage(final Context context, final int requestCode, final PickImageOption option, int type) {
        if (type == 1) {
            int from = PickImageActivity.FROM_CAMERA;
            if (!option.crop) {
                PickImageActivity.start((Activity) context, requestCode, from, option.outputPath, option.multiSelect, 1,
                        true, false, 0, 0);
            } else {
                PickImageActivity.start((Activity) context, requestCode, from, option.outputPath, false, 1,
                        false, true, option.cropOutputImageWidth, option.cropOutputImageHeight);
            }
        } else if (type == 2) {

        } else {
            if (context == null) {
                return;
            }
            List<String> list = new ArrayList<>();
            list.add(context.getString(R.string.input_panel_take));
            list.add(context.getString(R.string.choose_from_photo_album));

            PopupWindowUtils.getInstance().showSelectPopuWindow((Activity) context, whitch -> {
                switch (whitch) {
                    case 0://拍摄
                        int from = PickImageActivity.FROM_CAMERA;
                        if (!option.crop) {
                            PickImageActivity.start((Activity) context, requestCode, from, option.outputPath, option.multiSelect, 1,
                                    true, false, 0, 0);
                        } else {
                            PickImageActivity.start((Activity) context, requestCode, from, option.outputPath, false, 1,
                                    false, true, option.cropOutputImageWidth, option.cropOutputImageHeight);
                        }
                        break;
                    case 1://从相册
                        int fromLocal = PickImageActivity.FROM_LOCAL;
                        if (!option.crop) {
                            PickImageActivity.start((Activity) context, requestCode, fromLocal, option.outputPath, option.multiSelect,
                                    option.multiSelectMaxCount, true, false, 0, 0);
                        } else {
                            PickImageActivity.start((Activity) context, requestCode, fromLocal, option.outputPath, false, 1,
                                    false, true, option.cropOutputImageWidth, option.cropOutputImageHeight);
                        }

                        break;
                }
            }, list);
        }


    }
}
