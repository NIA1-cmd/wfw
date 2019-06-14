package com.oncloudsoft.sdk.yunxin.session;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.oncloudsoft.sdk.yunxin.uikit.api.model.location.LocationProvider;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.dialog.EasyAlertDialog;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.log.LogUtil;
import com.oncloudsoft.sdk.yunxin.location.activity.LocationAmapActivity;
import com.oncloudsoft.sdk.yunxin.location.activity.LocationExtras;
import com.oncloudsoft.sdk.yunxin.location.activity.NavigationAmapActivity;
import com.oncloudsoft.sdk.yunxin.location.helper.NimLocationManager;


/**
 * Created by zhoujianghua on 2015/8/11.
 */
public class NimDemoLocationProvider implements LocationProvider {
    @Override
    public void requestLocation(final Context context, Callback callback) {
        if (!NimLocationManager.isLocationEnable(context)) {
            final EasyAlertDialog alertDialog = new EasyAlertDialog(context);
            alertDialog.setMessage("位置服务未开启");
            alertDialog.addNegativeButton("取消", EasyAlertDialog.NO_TEXT_COLOR, EasyAlertDialog.NO_TEXT_SIZE,
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
            alertDialog.addPositiveButton("设置", EasyAlertDialog.NO_TEXT_COLOR, EasyAlertDialog.NO_TEXT_SIZE,
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            try {
                                context.startActivity(intent);
                            } catch (Exception e) {
                                LogUtil.e("LOC", "start ACTION_LOCATION_SOURCE_SETTINGS error");
                            }

                        }
                    });
            alertDialog.show();
            return;
        }

        LocationAmapActivity.start(context, callback);
    }

    @Override
    public void openMap(Context context, double longitude, double latitude, String address) {
        Intent intent = new Intent(context, NavigationAmapActivity.class);
        intent.putExtra(LocationExtras.LONGITUDE, longitude);
        intent.putExtra(LocationExtras.LATITUDE, latitude);
        intent.putExtra(LocationExtras.ADDRESS, address);
        context.startActivity(intent);
    }
}
