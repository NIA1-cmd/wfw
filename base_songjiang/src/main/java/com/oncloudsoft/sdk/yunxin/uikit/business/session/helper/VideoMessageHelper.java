package com.oncloudsoft.sdk.yunxin.uikit.business.session.helper;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.activity.CaptureVideoActivity;
import com.oncloudsoft.sdk.yunxin.uikit.business.session.constant.RequestCode;
import com.oncloudsoft.sdk.yunxin.uikit.common.ToastHelper;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.C;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.file.AttachmentStore;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.file.FileUtil;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.storage.StorageType;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.storage.StorageUtil;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.string.MD5;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.string.StringUtil;
import com.oncloudsoft.sdk.utils.PermissionUtil;
import com.oncloudsoft.sdk.utils.PopupWindowUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzxuwen on 2015/4/10.
 */
public class VideoMessageHelper {
    private File videoFile;
    private String videoFilePath;

    private Activity activity;
    private VideoMessageHelperListener listener;

    private int localRequestCode;
    private int captureRequestCode;

    public VideoMessageHelper(Activity activity, VideoMessageHelperListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public interface VideoMessageHelperListener {
        void onVideoPicked(File file, String md5, int type);
    }


    /**
     * 显示视频拍摄或从本地相册中选取
     *
     * @param local
     * @param capture
     * @param type    0/-1  :代表 是需要用户去选择的    1 ：代表 选择拍摄    2：代表 本地选择视频
     */
    public void showVideoSource(int local, int capture, int type) {
        this.localRequestCode = local;
        this.captureRequestCode = capture;

        if (type == 1) {
            chooseVideoFromCamera(null);

        } else if (type == 2) {
            chooseVideoFromLocal();

        } else {
            List<String> list = new ArrayList<>();
            list.add("拍摄");
            list.add("本地选择");

            PopupWindowUtils.getInstance().showSelectPopuWindow(activity, whitch -> {
                switch (whitch) {
                    case 0://视屏拍摄
                        chooseVideoFromCamera(null);
                        break;
                    case 1://从相册中选择视频
                        chooseVideoFromLocal();

                        break;
                }
            }, list);
        }


    }

    /**
     * 显示视频拍摄或从本地相册中选取
     */
    public void showVideoSource(Activity activity, int local, int capture, int witch, final CaptureVideoActivity.onHandlerVideoCallBack onHandlerVideoCallBack) {
        this.localRequestCode = local;
        this.captureRequestCode = capture;


        switch (witch) {
            case 0://选择文件
                PermissionUtil.getInstance().requestSongJiangPermission(activity, new PermissionUtil.OnPermissionLinsteren() {
                    @Override
                    public void onTongYi() {
                        chooseVideoFromLocal();

                    }
                }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});

                break;
            case 1://拍摄

                PermissionUtil.getInstance().requestSongJiangPermission(activity, new PermissionUtil.OnPermissionLinsteren() {
                    @Override
                    public void onTongYi() {
                        chooseVideoFromCamera(onHandlerVideoCallBack);

                    }
                }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE});


                break;

        }


    }

    /************************************************* 视频操作S *******************************************/

    /**
     * 拍摄视频
     * from  1:群聊中  2：执行记录中
     */
    protected void chooseVideoFromCamera(CaptureVideoActivity.onHandlerVideoCallBack onHandlerVideoCallBack) {
        if (!StorageUtil.hasEnoughSpaceForWrite(activity,
                StorageType.TYPE_VIDEO, true)) {
            return;
        }
        videoFilePath = StorageUtil.getWritePath(
                activity, StringUtil.get36UUID()
                        + C.FileSuffix.MP4, StorageType.TYPE_TEMP);
        videoFile = new File(videoFilePath);


        if (onHandlerVideoCallBack != null) {//执行记录中拍摄视屏
            CaptureVideoActivity.startFromForensics(activity, videoFilePath, captureRequestCode, 2, "",onHandlerVideoCallBack);

        } else {//群聊中录制视屏
            // 启动视频录制
            CaptureVideoActivity.start(activity, videoFilePath, captureRequestCode);
        }

    }

    /**
     * 从本地相册中选择视频 1:群聊中  2：执行记录中
     */
    protected void chooseVideoFromLocal() {
        if (Build.VERSION.SDK_INT >= 19) {
            chooseVideoFromLocalKitKat();
        } else {
            chooseVideoFromLocalBeforeKitKat();
        }
    }




//    /**
//     * 打开其他程序获取ip地址
//     */
//    private void startOrthergetIP(Activity activity, String action) {
//        Intent intent = new Intent(action);
//        PackageManager manager = activity.getPackageManager();
//        List<ResolveInfo> list = manager.queryIntentActivities(intent, 0);
//        if (list.size() == 0) {
//            DialogActivity.showNormalDialog(activity, "没有找到相关App,是否去下载", new DialogActivity.OnNormalDialogClick() {
//                @Override
//                public void OnConfirm() {
//
//                    Intent intent1 = new Intent();
//                    intent1.setData(Uri.parse(Global.smartconfigUrl));//Url 就是你要打开的网址
//                    intent1.setAction(Intent.ACTION_VIEW);
//                    activity.startActivity(intent1); //启动浏览器
//                }
//
//                @Override
//                public void OnCancel() {
//
//                }
//            });
//        } else if (list.size() == 1) {
//            Iterator<ResolveInfo> iterator = list.iterator();
//            ResolveInfo info = iterator.next();
//            String packageName = info.activityInfo.packageName;
//            String name = info.activityInfo.name;
//            Log.i("sognjiangapp", packageName + "/" + name);
//            intent.setComponent(new ComponentName(packageName, name));
//            activity.startActivityForResult(intent, deviceRequestCode);
//        } else {
//            Log.w("sognjiangapp", "list size > 1");
//        }
//
//    }

    /**
     * API19 之后选择视频
     */
    protected void chooseVideoFromLocalKitKat() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        try {
            activity.startActivityForResult(intent, localRequestCode);
        } catch (ActivityNotFoundException e) {
            ToastHelper.showToast(activity, R.string.gallery_invalid);
        } catch (SecurityException e) {

        }
    }

    /**
     * API19 之前选择视频
     */
    protected void chooseVideoFromLocalBeforeKitKat() {
        Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
        mIntent.setType(C.MimeType.MIME_VIDEO_ALL);
        mIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        try {
            activity.startActivityForResult(mIntent, localRequestCode);
        } catch (ActivityNotFoundException e) {
            ToastHelper.showToast(activity, R.string.gallery_invalid);
        }
    }

    /****************************视频选中后回调操作********************************************/

    /**
     * 获取本地相册视频回调操作
     */
    public void onGetLocalVideoResult(final Intent data) {
        if (data == null) {
            return;
        }

        String filePath = filePathFromIntent(data);
        if (StringUtil.isEmpty(filePath) || !checkVideoFile(filePath)) {
            return;
        }

        String md5 = MD5.getStreamMD5(filePath);
        String filename = md5 + "." + FileUtil.getExtensionName(filePath);
        String md5Path = StorageUtil.getWritePath(filename, StorageType.TYPE_VIDEO);

        if (AttachmentStore.copy(filePath, md5Path) != -1) {
            if (listener != null) {
                listener.onVideoPicked(new File(md5Path), md5, RequestCode.GET_LOCAL_VIDEO);
            }
        } else {
            ToastHelper.showToast(activity, R.string.video_exception);
        }
    }

    public void onDeviceVideoResult(Intent data) {
        if (data == null) {
            return;
        }
        String path = data.getStringExtra("path");
        String md5 = MD5.getStreamMD5(path);

        if (listener != null) {
            listener.onVideoPicked(new File(path), md5, RequestCode.GET_DEVICE_VIDEO);
        }
    }


    /**
     * 拍摄视频后回调操作
     */
    public void onCaptureVideoResult(Intent data) {

        if (videoFile == null || !videoFile.exists()) {
            //activity 可能会销毁重建，所以从这取一下
            String dataFilePath = data.getStringExtra(CaptureVideoActivity.EXTRA_DATA_FILE_NAME);
            if (!TextUtils.isEmpty(dataFilePath)) {
                videoFile = new File(dataFilePath);
            }
        }

        if (videoFile == null || !videoFile.exists()) {
            return;
        }

        //N930拍照取消也产生字节为0的文件
        if (videoFile.length() <= 0) {
            videoFile.delete();
            return;
        }

        String videoPath = videoFile.getPath();
        String md5 = MD5.getStreamMD5(videoPath);
        String md5Path = StorageUtil.getWritePath(md5 + ".mp4", StorageType.TYPE_VIDEO);

        if (AttachmentStore.move(videoPath, md5Path)) {
            if (listener != null) {
                listener.onVideoPicked(new File(md5Path), md5, RequestCode.CAPTURE_VIDEO);
            }
        }
    }

    /**
     * 获取文件路径
     *
     * @param data intent数据
     * @return
     */
    private String filePathFromIntent(Intent data) {
        Uri uri = data.getData();

        try {
            Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
            if (cursor == null) {
                //miui 2.3 有可能为null
                return uri.getPath();
            } else {
                cursor.moveToFirst();
                return cursor.getString(cursor.getColumnIndex("_data")); // 文件路径
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 检查文件
     *
     * @param file 视频文件
     * @return boolean
     */
    private boolean checkVideoFile(String file) {
        if (!AttachmentStore.isFileExist(file)) {
            return false;
        }

        if (new File(file).length() > C.MAX_LOCAL_VIDEO_FILE_SIZE) {
            ToastHelper.showToast(activity, R.string.im_choose_video_file_size_too_large);
            return false;
        }

        if (!StorageUtil.isInvalidVideoFile(file)) {
            ToastHelper.showToast(activity, R.string.im_choose_video);
            return false;
        }
        return true;
    }


}
