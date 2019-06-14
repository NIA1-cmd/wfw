package com.oncloudsoft.sdk.utils;


import android.app.Activity;
import android.content.Context;
import android.os.Build;

import com.oncloudsoft.sdk.app.ActivityHelper;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;


public class PermissionUtil {

    private static PermissionUtil permissionUtil = null;

    public static PermissionUtil getInstance() {

        if (permissionUtil == null) {
            permissionUtil = new PermissionUtil();
        }
        return permissionUtil;
    }


    public boolean hasPermission(Context context, String... permissions) {
        return AndPermission.hasPermissions(context, permissions);
    }


    /**
     * 封装
     * @param context
     * @param callBack
     * @param groups
     */
    public void requestPermissions(final Context context, final PermissionCallBack callBack, String[] groups) {
        //这里使用Permission.Group.你申请的权限组名称
        AndPermission.with(context)
                .permission(groups)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        callBack.onGranted(data);
                    }
                }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {

                ActivityHelper.getInstance().showToast((Activity) context, lostPermission(data));

            }
        }).start();


    }


    public interface PermissionCallBack {
        void onGranted(List<String> data);
    }

    private String lostPermission(List<String> data) {
        StringBuilder remindText = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            String name = PermissionName(data.get(i));

            if (i != data.size() - 1) {
                remindText.append(name).append(",");

            } else {
                remindText.append(name);

            }
        }

        return "您缺少" + remindText + "权限";
    }

    private String PermissionName(String permission) {
        String name = "";
        switch (permission) {
            case "android.permission.CAMERA"://相机权限
                name = "相机";
                break;
            case "android.permission.READ_EXTERNAL_STORAGE"://读写权限
                name = "读写储存卡";
                break;
        }
        return name;

    }


    /**
     * 封装请求权限方法
     * @param activity
     * @param linsteren
     * @param permissions
     */
    public void requestSongJiangPermission(Activity activity, final OnPermissionLinsteren linsteren, String[] permissions) {
        if (Build.VERSION.SDK_INT >= 23) {

            //step  1:判断是否拥有权限
            if (PermissionUtil.getInstance().hasPermission(activity, permissions)) {
                linsteren.onTongYi();

            } else {
                //step  2:请求权限
                PermissionUtil.getInstance().requestPermissions(activity, new PermissionUtil.PermissionCallBack() {
                    @Override
                    public void onGranted(List<String> data) {
                        linsteren.onTongYi();
                    }

                }, permissions);
            }


        } else {
            linsteren.onTongYi();
        }
    }

    public interface OnPermissionLinsteren {
        void onTongYi();
    }
}
