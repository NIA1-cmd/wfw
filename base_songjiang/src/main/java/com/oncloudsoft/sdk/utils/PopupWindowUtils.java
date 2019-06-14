package com.oncloudsoft.sdk.utils;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.adapter.LabItemAdapter;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.entity.StageData;

import java.util.ArrayList;
import java.util.List;


public class PopupWindowUtils {

    public static PopupWindow popupWindow = null;

    private PopupWindowUtils() {
    }


    public static PopupWindowUtils getInstance() {
        return PopupWindowUtilsHolder.instance;
    }

    /**
     * 静态内部类
     */
    private static class PopupWindowUtilsHolder {
        private static final PopupWindowUtils instance = new PopupWindowUtils();
    }



    public interface OnCustomeDateSelectedListener {
        void onChooseed(List<Long> list);
    }

    public interface OnPopDimissListener {
        void onPopDimiss();
    }

    private boolean isFrist = true;


    private static List<StageData> list = new ArrayList<>();





    public interface onPopupItemListener {
        void OnItemClick(StageData StageData);
    }


    /**
     * 让popup消失
     */
    private static void dimis(PopupWindow popupWindow) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }


    /**
     * 点击确定之后
     */
    private static void onConfrm2(Activity activity, StageData list, onPopupItemListener listener, PopupWindow popupWindow) {
        boolean isCheck = false;
        listener.OnItemClick(list);
        isCheck = true;
        if (!isCheck) {//一个都没选择
            ActivityHelper.getInstance().showToast(activity, "请选择");
        } else {
            dimis(popupWindow);
        }

    }

    /**
     * 获取一个 通用的popuwindow
     *
     * @param customerView
     * @return
     */
    private PopupWindow getPopupWindow(View customerView) {
        PopupWindow popupWindow = new PopupWindow();
        // 设置视图
        popupWindow.setContentView(customerView);
        // 设置弹出窗体的宽和高  将
        popupWindow.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        popupWindow.setFocusable(true);


        // 设置弹出窗体的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        // 设置弹出窗体显示时的动画，从底部向上弹出
        popupWindow.setAnimationStyle(R.style.ActionSheetDialogStyle);


        popupWindow.showAtLocation(customerView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);


        return popupWindow;
    }

    /**
     * 获取一个 通用的popuwindow
     *
     * @param customerView
     * @return
     */
    private PopupWindow getPopupWindows(View customerView, int height, int width, OnPopDimissListener dimissListener, int animationStyle) {
        PopupWindow popupWindow = new PopupWindow();
        // 设置视图
        popupWindow.setContentView(customerView);
        // 设置弹出窗体的宽和高  将
        popupWindow.setHeight(height);
        popupWindow.setWidth(width);

        // 设置弹出窗体可点击
        popupWindow.setFocusable(true);


        // 设置弹出窗体的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        // 设置弹出窗体显示时的动画，从底部向上弹出

        if (0 != animationStyle) {
//            popupWindow.setAnimationStyle(R.style.ActionSheetDialogStyle);
            popupWindow.setAnimationStyle(animationStyle);

        }

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (dimissListener != null) {
                    dimissListener.onPopDimiss();
                }
            }
        });

//        if (0 != gravity) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                popupWindow.showAsDropDown(clickView, 0, 0, gravity);
//            }
//        } else {
//            popupWindow.showAsDropDown(clickView, 0, 0);
//        }


        return popupWindow;
    }


    /**
     * 以前大部分都直接調用這個方法
     *
     * @param customerView
     * @param dimissListener
     * @return
     */
    private PopupWindow getPopupWindows(View customerView, OnPopDimissListener dimissListener) {

        return getPopupWindows(customerView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT, dimissListener, R.style.ActionSheetDialogStyle);
    }



    /**
     * 显示一个选择的popupWindow
     *
     * @param activity
     */
    public void showSelectPopuWindow(Activity activity, final onSelectLinstener linstener, List<String> courtList) {

        LinearLayout rootLayout = getRootLayout(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.recycler_baselayout, null);


        rootLayout.addView(view);
        LinearLayout.LayoutParams recyclerLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(recyclerLayoutParam);
        RecyclerView recyclerView = view.findViewById(R.id.rv_base);
        recyclerView.setLayoutParams(recyclerLayoutParam);

        LabItemAdapter labItemAdapter = new LabItemAdapter(activity, courtList, new LabItemAdapter.OnLabItemClickListener() {
            @Override
            public void onClick(int pos) {
                linstener.OnItemClick(pos);
                popupWindow.dismiss();
            }
        });
        recyclerView.setAdapter(labItemAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));


        LinearLayout.LayoutParams lineLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 20);

        View line = new View(activity);
        line.setBackgroundColor(activity.getResources().getColor(R.color.backgroundcolor_01));
        line.setLayoutParams(lineLayoutParam);

        rootLayout.addView(line);


        TextView textView = new TextView(activity);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150);
        textView.setLayoutParams(layoutParams);
        textView.setBackgroundColor(activity.getResources().getColor(R.color.white));
        textView.setText("取消");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(activity.getResources().getColor(R.color.black));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        rootLayout.addView(textView);


        popupWindow = getPopupWindow(rootLayout);

    }


    public interface onSelectLinstener {
        void OnItemClick(int whitch);
    }

    /**
     * 获取popup 从下面弹出来的父布局
     *
     * @param activity
     * @return
     */
    private LinearLayout getRootLayout(Activity activity) {
        LinearLayout ll_root = new LinearLayout(activity);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll_root.setBackgroundColor(ContextCompat.getColor(activity, R.color.translucent));
        ll_root.setLayoutParams(params);//set child layout
        ll_root.setOrientation(LinearLayout.VERTICAL);//setting oreientation as vertical

        LinearLayout.LayoutParams ortherTextViewlayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
        TextView ortherTextView = new TextView(activity);
        ortherTextView.setLayoutParams(ortherTextViewlayout);
        ll_root.addView(ortherTextView);


        ortherTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        return ll_root;
    }


    /**
     * 获取popup 从下面弹出来的父布局
     *
     * @param activity
     * @return
     */
    private LinearLayout getRootLayouts(Activity activity, View view, int gravity) {
        LinearLayout ll_root = new LinearLayout(activity);
        ll_root.removeAllViews();
        ll_root.addView(view);
        ll_root.setGravity(gravity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll_root.setBackgroundColor(ContextCompat.getColor(activity, R.color.translucent));
        ll_root.setLayoutParams(params);//set child layout
        ll_root.setOrientation(LinearLayout.VERTICAL);//setting oreientation as vertical

        LinearLayout.LayoutParams ortherTextViewlayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
        TextView ortherTextView = new TextView(activity);
        ortherTextView.setLayoutParams(ortherTextViewlayout);
        ll_root.addView(ortherTextView);


        ortherTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        return ll_root;
    }

}
