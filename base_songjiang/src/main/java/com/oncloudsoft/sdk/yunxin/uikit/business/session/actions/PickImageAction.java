package com.oncloudsoft.sdk.yunxin.uikit.business.session.actions;

import android.content.Intent;
import android.text.TextUtils;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.constant.Extras;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.constant.RequestCode;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.helper.SendImageHelper;
import com.oncloudsoft.sdk.yunxin.uikit.common.ToastHelper;
import com.oncloudsoft.sdk.yunxin.uikit.common.media.picker.PickImageHelper;
import com.oncloudsoft.sdk.yunxin.uikit.common.media.picker.activity.PickImageActivity;
import com.oncloudsoft.sdk.yunxin.uikit.common.media.picker.activity.PreviewImageFromCameraActivity;
import com.oncloudsoft.sdk.yunxin.uikit.common.media.picker.activity.PreviewImageFromLocalActivity;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.file.AttachmentStore;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.media.ImageUtil;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.storage.StorageType;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.storage.StorageUtil;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.string.StringUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by zhoujianghua on 2015/7/31.
 */
public abstract class PickImageAction extends BaseAction {

    public static final int PICK_IMAGE_COUNT = 9;
    public static final int PORTRAIT_IMAGE_WIDTH = 720;

    public static final String MIME_JPEG = "image/jpeg";
    public static final String JPG = ".jpg";

    private boolean multiSelect;
    private boolean crop = false;

    protected abstract void onPicked(File file);

    protected PickImageAction(int iconResId, int titleId, boolean multiSelect) {
        super(iconResId, titleId);
        this.multiSelect = multiSelect;
    }

    @Override
    public void onClick() {
        int requestCode = makeRequestCode(RequestCode.PICK_IMAGE);
        showSelector(getTitleId(), requestCode, multiSelect, tempFile());
    }

    private String tempFile() {
        String filename = StringUtil.get32UUID() + JPG;
        return StorageUtil.getWritePath(filename, StorageType.TYPE_TEMP);
    }



    /**
     * 打开图片选择器
     */
    public void showSelector(int titleId, final int requestCode, final boolean multiSelect, final String outPath) {
        PickImageHelper.PickImageOption option = new PickImageHelper.PickImageOption();
        option.titleResId = titleId;
        option.multiSelect = multiSelect;
        option.multiSelectMaxCount = PICK_IMAGE_COUNT;
        option.crop = crop;
        option.cropOutputImageWidth = PORTRAIT_IMAGE_WIDTH;
        option.cropOutputImageHeight = PORTRAIT_IMAGE_WIDTH;
        option.outputPath = outPath;

        PickImageHelper.pickImage(getActivity(), requestCode, option,0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RequestCode.PICK_IMAGE:
                onPickImageActivityResult(requestCode, data);
                break;
            case RequestCode.PREVIEW_IMAGE_FROM_CAMERA:
                onPreviewImageActivityResult(requestCode, data);
                break;
        }
    }

    /**
     * 图片选取回调
     */
    private void onPickImageActivityResult(int requestCode, Intent data) {
        if (data == null) {
            ToastHelper.showToastLong(getActivity(), R.string.picker_image_error);
            return;
        }
        boolean local = data.getBooleanExtra(Extras.EXTRA_FROM_LOCAL, false);
        if (local) {
            // 本地相册
            sendImageAfterSelfImagePicker(data);
        } else {
            // 拍照
            Intent intent = new Intent();
            if (!handleImagePath(intent, data)) {
                return;
            }


            boolean hasPreview = false;

            if (hasPreview) {

                intent.setClass(getActivity(), PreviewImageFromCameraActivity.class);
                getActivity().startActivityForResult(intent, makeRequestCode(RequestCode.PREVIEW_IMAGE_FROM_CAMERA));
            } else {

                String imageFilePathString = intent.getExtras().getString("ImageFilePath");

                String origImageFilePath = intent.getExtras().getString("OrigImageFilePath");
                File imageFile = new File(imageFilePathString);
                ArrayList<String> imageList = new ArrayList<String>();
                ArrayList<String> origImageList = new ArrayList<String>();
                imageList.add(imageFile.getPath());
                origImageList.add(origImageFilePath);
                Intent intenta = PreviewImageFromLocalActivity.initPreviewImageIntent(imageList, origImageList, false);
                sendImageAfterPreviewPhotoActivityResult(intenta);
            }


        }
    }

    /**
     * 是否可以获取图片
     */
    private boolean handleImagePath(Intent intent, Intent data) {
        String photoPath = data.getStringExtra(Extras.EXTRA_FILE_PATH);
        if (TextUtils.isEmpty(photoPath)) {
            ToastHelper.showToast(getActivity(), R.string.picker_image_error);
            return false;
        }

        File imageFile = new File(photoPath);
        intent.putExtra("OrigImageFilePath", photoPath);
        File scaledImageFile = ImageUtil.getScaledImageFileWithMD5(imageFile, MIME_JPEG);

        boolean local = data.getExtras().getBoolean(Extras.EXTRA_FROM_LOCAL, true);
        if (!local) {
            // 删除拍照生成的临时文件
            AttachmentStore.delete(photoPath);
        }

        if (scaledImageFile == null) {
            ToastHelper.showToast(getActivity(), R.string.picker_image_error);
            return false;
        } else {
            ImageUtil.makeThumbnail(scaledImageFile);
        }
        intent.putExtra("ImageFilePath", scaledImageFile.getAbsolutePath());
        return true;
    }

    /**
     * 从预览界面点击发送图片
     */
    private void sendImageAfterPreviewPhotoActivityResult(Intent data) {
        SendImageHelper.sendImageAfterPreviewPhotoActivityResult(data, new SendImageHelper.Callback() {

            @Override
            public void sendImage(File file, boolean isOrig) {
                onPicked(file);
            }
        });
    }

    /**
     * 发送图片
     */
    private void sendImageAfterSelfImagePicker(final Intent data) {
        SendImageHelper.sendImageAfterSelfImagePicker(getActivity(), data, new SendImageHelper.Callback() {

            @Override
            public void sendImage(File file, boolean isOrig) {
                onPicked(file);

            }
        });
    }

    /**
     * 拍摄回调
     */
    private void onPreviewImageActivityResult(int requestCode, Intent data) {
        if (data.getBooleanExtra(PreviewImageFromCameraActivity.RESULT_SEND, false)) {//点击的是送
            sendImageAfterPreviewPhotoActivityResult(data);
        } else if (data.getBooleanExtra(PreviewImageFromCameraActivity.RESULT_RETAKE, false)) {//点击的是重拍
            String filename = StringUtil.get32UUID() + JPG;
            String path = StorageUtil.getWritePath(filename, StorageType.TYPE_TEMP);

            if (requestCode == RequestCode.PREVIEW_IMAGE_FROM_CAMERA) {
                PickImageActivity.start(getActivity(), makeRequestCode(RequestCode.PICK_IMAGE), PickImageActivity.FROM_CAMERA, path);
            }
        }
    }
}
