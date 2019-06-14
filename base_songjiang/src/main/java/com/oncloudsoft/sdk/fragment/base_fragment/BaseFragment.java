package com.oncloudsoft.sdk.fragment.base_fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 所有Fragment的父类
 */
public class BaseFragment extends Fragment {
    private InputMethodManager inputMethodManager;
    private Resources resources;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         resources = getResources();

        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);


    }

    public void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 通过id获取String
     *
     * @param stirngId 改String的id
     * @return 当前id的字符串
     */
    public String getStringById(@StringRes int stirngId) {
        return resources.getString(stirngId);
    }

    /**
     * 通过id获取Drawable
     *
     * @param drawableId 改String的id
     * @return 当前id的Drawable
     */
    public Drawable getDrawableById(@DrawableRes int drawableId) {
        return resources.getDrawable(drawableId);
    }

    /**
     * 通过id获取Color
     *
     * @param colorId 改Color的id
     * @return 当前id的Color
     */
    public int getColorById(@ColorRes int colorId) {

        return ContextCompat.getColor(getActivity(), colorId);

    }
}
