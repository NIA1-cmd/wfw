package com.oncloudsoft.sdk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.adapter.SelectDialogAdapter;
import com.oncloudsoft.sdk.app.ActivityHelper;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.entity.select_dialog.Option;
import com.oncloudsoft.sdk.entity.select_dialog.PaiMaiSelectData;
import com.oncloudsoft.sdk.okhttprequest.HttpParams;
import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;
import com.oncloudsoft.sdk.utils.AnimationUtil;
import com.oncloudsoft.sdk.utils.TextUtils;
import com.oncloudsoft.sdk.yunxin.uikit.common.ToastHelper;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.C;

import java.util.List;


/**
 * 作者 黄继栋
 * 创建时间 2019/1/27 16:32
 * 描述  全局的dialog统一风格
 */

public class DialogActivity extends BaseActivity {
    private RelativeLayout rlParent;
    private static OnEditListener mOnEditListener;
    private static OnNormalDialogClick mOnNormalDialogClick;
    private static OnTextDialogClick onTextDialogClick;
    public static DialogActivity dialogActivity;

    public interface DialogType {
        int DIALOG_DEFULT = 0; //默认的
        int DIALOG_EDITE = 2; //编辑框
        int DIALOG_NORMAL = 3;//弹出一个普通的 Dialog
        int DIALOG_CASEGUIDEONLYHOUSE = 4;//显示办案指引中唯一住房指引
        int DIALOG_CASEGUIDERENT = 5;//显示办案指引中租赁住房 Dialog
        int DIALOG_SELECT = 6;//显示办案指引中拍卖选择 Dialog
        int DIALOG_CHECKVERSION = 8;//有新版本提醒
        int DIALOG_SHOWLOADING = 9;//显示正在请求中提示
        int DIALOG_TEXT = 11;//显示一个纯文本
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getIntent().getBooleanExtra(Global.SHOW_TYPE, false)) {//不需要背景颜色透明即可
            setTheme(R.style.transparent_dialogs);
            setStatusBarLightcolor(DialogActivity.this, R.color.transparent);

        } else {//蓝色背景
            setStatusBarLightcolor(DialogActivity.this, R.color.translucent);

            setTheme(R.style.transparent_dialog);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        rlParent = findViewById(R.id.rl_parent);

        dialogActivity = this;
        init();

    }

    private void init() {

        switch (getIntent().getIntExtra(Global.TYPE, 0)) {
            case DialogType.DIALOG_DEFULT://默认的
                show03("弹出错误的对话框");
                break;
            case DialogType.DIALOG_EDITE://编辑框
                show02(getIntent().getStringExtra(Global.TITLE));
                break;
            case DialogType.DIALOG_NORMAL://弹出一个普通的 Dialog
                show03(getIntent().getStringExtra(Global.TITLE));
                break;
            case DialogType.DIALOG_CASEGUIDEONLYHOUSE://显示办案指引中唯一住房指引
                show04();
                break;
            case DialogType.DIALOG_CASEGUIDERENT://显示办案指引中租赁住房 Dialog
                show05();
                break;
            case DialogType.DIALOG_SELECT://显示办案指引中拍卖选择 Dialog
                initData(getIntent().getStringExtra(Global.ID));
                break;
            case DialogType.DIALOG_SHOWLOADING://正在请求中的弹框
                show09(getIntent().getStringExtra(Global.NAME));
                break;

            case DialogType.DIALOG_TEXT://显示一个纯文本
                show11(getIntent().getStringExtra(Global.TITLE), getIntent().getStringExtra(Global.CONTENT));
                break;
            default:
                show03("弹出错误的对话框");
                break;
        }
    }

    private void show09(String title) {
        View view = LayoutInflater.from(this).inflate(R.layout.request_dialog, null);
        ImageView imageView = view.findViewById(R.id.iv_loading);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(title);
        imageView.startAnimation(AnimationUtil.RotateAnimation());

        rlParent.addView(view);
        rlParent.setBackgroundColor(getResources().getColor(R.color.transparent));
    }


    private void initData(String id) {
        MyOkhttpClient.getOkhttpInstance().sentGet(DialogActivity.this, Global.URL_PAIMAI, HttpParams.HttpParams().add("id", id).build(), new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) {

                ActivityHelper.getInstance().getUiThread(DialogActivity.this, new Runnable() {
                    @Override
                    public void run() {
                        if (json == null || json.equals("{}")) {
                            ToastHelper.showToast(DialogActivity.this, "暂无信息!");
                            finish();
                        } else {
                            try {
                                show06(json);
                            } catch (Exception e) {
                                e.printStackTrace();
                                finish();
                                ToastHelper.showToast(DialogActivity.this, "暂无信息!");
                            }
                        }
                    }
                });
            }

            @Override
            public boolean onRequestFaild(String mes) {
                return false;
            }
        }, true);
    }


    private void show03(String title) {

        View view = LayoutInflater.from(this).inflate(R.layout.normal_dialog, null);
        rlParent.addView(view);
        final TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvConfrim = view.findViewById(R.id.tv_confirm);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        tvTitle.setText(title);
        tvConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if (mOnNormalDialogClick != null) {
                    mOnNormalDialogClick.OnConfirm();

                }
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if (mOnNormalDialogClick != null) {
                    mOnNormalDialogClick.OnCancel();
                }
            }
        });

    }

    private void show11(String title, String buttonStr) {

        View view = LayoutInflater.from(this).inflate(R.layout.normal_dialog, null);
        rlParent.addView(view);
        final TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvConfrim = view.findViewById(R.id.tv_confirm);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        View line = view.findViewById(R.id.ic_line);
        tvTitle.setText("\u3000\u3000"+title);
        tvTitle.setPadding(20, 0, 20, 0);
        tvConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if (onTextDialogClick != null) {
                    onTextDialogClick.onClick();

                }
            }
        });
        tvCancel.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
        tvConfrim.setText(buttonStr);

    }

    private void show05() {
        String guidecase_renttitle = "经审查发现有下列情形之一的，一般可认定租赁合同签订与抵押查封前";
        String guidecase_rentcontent = "1.租赁合同的当事人在抵押、查封前已就相应租赁关系提取诉讼或仲裁的；\n" + "2.租赁合同的当事人在抵押、查封前已办理租赁合同公证的；\n" + "3.租赁合同当事人已在抵押、查封前缴纳相应的租金税；\n" + "4.租赁合同当事人已在在案涉房屋所在物业公司办理租赁登记；\n" + "5.租赁合同当事人已向抵押权人声明过租赁情况；\n" + "6.有其他确切证据证明租赁合同签订于抵押、查封前的。";

        String guidecase_housetitle = "经审查发现有下列情形之一的，一般可认定案外人在抵押查封前已经占有且至今占有房屋";
        String guidecase_housecontent = "1.案外人在抵押、查封前已经在且至今仍在案涉房屋内生产经营的；\n" + "2.案外人在抵押、查封前已经领取以涉案房屋作为居住地的营业执照且至为未变更住所地的；\n" + "3.案外人在抵押、查封前已经由其且至今仍由其支付案涉房屋水电，物业管理等费用；\n" + "4.案外人在抵押、查封前已经对案涉房屋根据租赁用途进行装修的；\n" + "5.案外人提供其他确切证据证明其已在抵押、查封前直接占有案涉房屋的。";

        View view = LayoutInflater.from(this).inflate(R.layout.caseguide_rent_dialog_dialog, null);
        rlParent.addView(view);

        TextView tvBack = view.findViewById(R.id.tv_back);
        TextView tvRent = view.findViewById(R.id.tv_rent);
        TextView tvHouse = view.findViewById(R.id.tv_house);
        tvHouse.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvRent.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GuideHandCasesActivity.start(DialogActivity.this, getResources().getString(R.string.guide_handcase), guidecase_renttitle, guidecase_rentcontent);

            }
        });
        tvHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuideHandCasesActivity.start(DialogActivity.this, getResources().getString(R.string.guide_handcase), guidecase_housetitle, guidecase_housecontent);

            }
        });

    }

    private void show02(String mTitle) {

        View view = LayoutInflater.from(this).inflate(R.layout.edit_dialog, null);
        rlParent.addView(view);


        EditText mEtInput = view.findViewById(R.id.et_input);
        TextView title = view.findViewById(R.id.tv_title);
        TextView confrim = view.findViewById(R.id.tv_confirm);
        TextView cancel = view.findViewById(R.id.tv_cancel);
        title.setText(mTitle);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEtInput.getText().toString().trim();

                if (text.equals("")) {//没有输入内容
                    ActivityHelper.getInstance().showToast(DialogActivity.this, mTitle + "不能为空");
                } else {
                    if (mOnEditListener != null) {
                        mOnEditListener.onConfirm(DialogActivity.this, text);
                    }
                }


            }
        });

    }

    private void show04() {
        View view = LayoutInflater.from(this).inflate(R.layout.caseguide_onlyhouse_dialog, null);
        rlParent.addView(view);
        TextView tvBack = view.findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void show06(String content) throws Exception {

        View view = LayoutInflater.from(this).inflate(R.layout.select_dialog, null);
        rlParent.addView(view);
        PaiMaiSelectData paiMarData = JSONObject.parseObject(content, PaiMaiSelectData.class);
        String result = paiMarData.getResult();
        List<Option> options = paiMarData.getOption();
        if (result != null) {
            for (int i = 0; i < options.size(); i++) {
                String value = options.get(i).getValue();
                if (value.equals(result)) {
                    Option option = options.get(i);
                    option.setCheck(true);
                    options.clear();
                    options.add(option);
                }
            }
        }
        TextView tvContent = view.findViewById(R.id.tv_content);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new SelectDialogAdapter(getApplication(), options));
        if (paiMarData.getMsg() != null) {
            tvContent.setVisibility(View.VISIBLE);
            String msg = TextUtils.ToDBC(paiMarData.getMsg());
            tvContent.setText(msg);
        }
    }

    /**
     * 弹出编辑框
     *
     * @param activity       上下 文
     * @param title          标题
     * @param onEditListener 监听
     */
    public static void showEditDialog(Activity activity, String title, OnEditListener onEditListener) {
        mOnEditListener = onEditListener;
        Intent intent = new Intent();
        intent.putExtra(Global.TITLE, title);
        startDialog(activity, intent, DialogType.DIALOG_EDITE);
    }

    interface OnEditListener {
        void onConfirm(Activity activity, String text);
    }

    /**
     * 显示办案指引中唯一住房指引
     *
     * @param activity 上下文
     */
    public static void showCaseGuideOnlyHouseDialog(Activity activity) {
        startDialog(activity, new Intent(), DialogType.DIALOG_CASEGUIDEONLYHOUSE);
    }

    /**
     * 显示办案指引中租赁住房 Dialog
     *
     * @param activity 上下文
     */
    public static void showCaseGuideRentDialog(Activity activity) {

        startDialog(activity, new Intent(), DialogType.DIALOG_CASEGUIDERENT);
    }


    /**
     * 显示一个默认的 dialog
     *
     * @param activity          上下文
     * @param titleStr          标题
     * @param normalDialogClick 监听
     */
    public static void showNormalDialog(Activity activity, String titleStr, final OnNormalDialogClick normalDialogClick) {
        mOnNormalDialogClick = normalDialogClick;
        Intent intent = new Intent();
        intent.putExtra(Global.TITLE, titleStr);
        startDialog(activity, intent, DialogType.DIALOG_NORMAL);

    }

    /**
     * x显示一个
     * @param activity
     * @param id
     * @param normalDialogClick
     */
    public static void showSelectDialog(Activity activity, String id, final OnNormalDialogClick normalDialogClick) {
        mOnNormalDialogClick = normalDialogClick;
        Intent intent = new Intent();
        intent.putExtra(Global.ID, id);
        startDialog(activity, intent, DialogType.DIALOG_SELECT);

    }


    /**
     * 显示一个纯文本的dialog
     *
     * @param activity
     * @param title
     * @param buttonStr          按钮的文字
     * @param onTextDialogClick1
     */
    public static void showTextDialog(Activity activity, String title, String buttonStr, final OnTextDialogClick onTextDialogClick1) {
        onTextDialogClick = onTextDialogClick1;
        Intent intent = new Intent();
        intent.putExtra(Global.TITLE, title);
        intent.putExtra(Global.CONTENT, buttonStr);
        startDialog(activity, intent, DialogType.DIALOG_TEXT);

    }


    /**
     * 显示一个正在请求中的弹框
     *
     * @param requestText 请求中的提示文字
     */
    public static void showRequestDialog(Context context, String requestText) {
        Intent intent = new Intent();
        intent.putExtra(Global.NAME, requestText);
        intent.putExtra(Global.SHOW_TYPE, true);//确定不需要蓝色的背景
        intent.putExtra(Global.PRESSED_TYPE, false);//返回按钮不需要关闭页面
        startDialog(context, intent, DialogType.DIALOG_SHOWLOADING);

    }

    /**
     * 启动 dialog
     *
     * @param intent
     * @param dialogType
     */
    private static void startDialog(Context context, Intent intent, int dialogType) {

        intent.putExtra(Global.TYPE, dialogType);
        intent.setClass(context, DialogActivity.class);

        context.startActivity(intent);

    }


    public interface OnNormalDialogClick {
        void OnConfirm();

        void OnCancel();
    }

    public interface OnTextDialogClick {
        void onClick();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rlParent.removeAllViews();

    }

    public static void finishDialog() {
        if (dialogActivity != null && !dialogActivity.isFinishing()) {
            dialogActivity.finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && !onBackFinish()) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 当用户摁返回键是否需要关闭页面
     *
     * @return 默认 为 true
     */
    private boolean onBackFinish() {
        return getIntent().getBooleanExtra(Global.PRESSED_TYPE, true);

    }

}
