package com.oncloudsoft.sdk.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.oncloudsoft.sdk.R;


/**
 */

public class GlidelUtil {


    /**
     * 加载普通图片
     *
     * @param activity  上下文对象
     * @param model     加载url
     * @param imageView 目标图片
     */
    public static void loadImag(Activity activity, @Nullable Object model, ImageView imageView) {
        loadImag(activity, model, -1, -1, imageView);
    }

    /**
     * 加载普通图片
     *
     * @param activity  上下文对象
     * @param model     加载url
     * @param imageView 目标图片
     */
    public static void loadImag(Activity activity, @Nullable Object model, int height, int width, ImageView imageView) {

        RequestOptions requestOptions = getBaseRequestOptions(width, height, null);

        loadBitmap(activity, requestOptions, model, imageView);

    }


    /**
     * 加载圆角图片
     *
     * @param activity       上下文对象
     * @param model          加载地址
     * @param imageView      目标ImageView
     * @param roundingRadius 圆角值大小
     */
    public static void loadRoundedCornerImag(Activity activity, @Nullable Object model, ImageView imageView, int roundingRadius) {
        // 构造 RequestOptions
        RequestOptions requestOptions = getBaseRequestOptions(-1, -1, new RoundedCorners(roundingRadius));


//        RequestOptions placeholder = new RequestOptions().bitmapTransform(new RoundedCorners(roundingRadius)).override(-1, -1).error(R.drawable.app_roundlogo).placeholder(R.drawable.app_roundlogo);

        //加载图片
        loadBitmap(activity, requestOptions, model, imageView);

    }

    /**
     * 最基础的RequestOptions
     * @param width  宽
     * @param height 高
     * @param transformation Transformation
     * @return RequestOptions
     */
    private static RequestOptions getBaseRequestOptions(int width, int height, Transformation<Bitmap> transformation) {
        RequestOptions requestOptions = new RequestOptions();

        if (transformation != null) {

            requestOptions = RequestOptions.bitmapTransform(transformation);
        }

        return requestOptions.override(width, height).error(R.drawable.app_roundlogo).placeholder(R.drawable.app_roundlogo);
    }


    /**
     * 加载图片
     * @param activity  上下文对象
     * @param requestOptions RequestOptions
     * @param model 图片的url
     * @param target 目标imageView
     */
    private static void loadBitmap(Activity activity, RequestOptions requestOptions, @Nullable Object model, @NonNull ImageView target) {
        Glide.with(activity).load(model).apply(requestOptions).into(target);

    }

}
