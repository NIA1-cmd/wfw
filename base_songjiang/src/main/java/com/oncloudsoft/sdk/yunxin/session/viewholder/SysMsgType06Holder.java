package com.oncloudsoft.sdk.yunxin.session.viewholder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.CaseAgentDetailActivity;
import com.oncloudsoft.sdk.activity.ClueVerificationDetailActivity;
import com.oncloudsoft.sdk.activity.LargeImageActivity;
import com.oncloudsoft.sdk.activity.NetCheckControlNotifycationActivity;
import com.oncloudsoft.sdk.activity.PunishmentDetailActivity;
import com.oncloudsoft.sdk.entity.approval.ClueProvideData;
import com.oncloudsoft.sdk.utils.GlidelUtil;
import com.oncloudsoft.sdk.utils.JsonUtil;
import com.oncloudsoft.sdk.utils.OnSingleClickListener;
import com.oncloudsoft.sdk.yunxin.session.extension.CustomAttachmentType;
import com.oncloudsoft.sdk.yunxin.session.extension.SysMsgType06Attachment;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;

import java.util.List;

/**
 * 系统消息的解析类  标题 内容 查看
 */
public class SysMsgType06Holder extends CustomBaseHolder<SysMsgType06Attachment> {

    private LinearLayout llParent;
    private TextView tvTitle;

//    private LinearLayout llApply;
//    private TextView tvApply;


    //类型第一种
    private LinearLayout llType;
    private TextView tvType;
    private TextView tvTypes;


    //类型第二种
    private LinearLayout llType02;
    private TextView tvTypes02;
    private TextView tvType02;


    //    private LinearLayout llProperty;
    private LinearLayout llImages;
    private TextView tvVerificationResult;
    private ImageView ivFrist;
    private ImageView ivSecond;
    private ImageView ivThird;
    private ImageView ivFourth;
    private LinearLayout llVerificationResult;
    private TextView tvContetn;
    private TextView tvRemark;

    public SysMsgType06Holder(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.sysmsg_itemtype06;

    }

    @Override
    protected void inflateContentView() {

        llParent = findViewById(R.id.ll_parent);
        tvTitle = findViewById(R.id.tv_title);
        tvType = findViewById(R.id.tv_type);
        tvType02 = findViewById(R.id.tv_type02);
        llType = findViewById(R.id.ll_type);
        llType02 = findViewById(R.id.ll_type02);
        llImages = findViewById(R.id.ll_images);
        tvVerificationResult = findViewById(R.id.tv_verification_result);
        ivFrist = findViewById(R.id.iv_frist);
        ivSecond = findViewById(R.id.iv_second);
        ivThird = findViewById(R.id.iv_third);
        ivFourth = findViewById(R.id.iv_fourth);
        llVerificationResult = findViewById(R.id.ll_verification_result);
        tvContetn = findViewById(R.id.tv_contetn);
        tvRemark = findViewById(R.id.tv_remark);
        tvTypes = findViewById(R.id.tv_types);
        tvTypes02 = findViewById(R.id.tv_types02);

    }


    @Override
    protected int leftBackground() {
        return R.drawable.nim_message_item_left_blue_selector;
    }

    @Override
    protected int rightBackground() {
        return R.drawable.nim_message_item_right_blue_selector;
    }



    @Override
    protected void bindCustomContentView(SysMsgType06Attachment sysMsgType06Attachment) {

        llType02.setVisibility(View.GONE);
        tvRemark.setVisibility(View.GONE);
        llVerificationResult.setVisibility(View.GONE);
        llType.setVisibility(View.GONE);
        llImages.setVisibility(View.GONE);

        OnSingleClickListener listener = null;
        String data = sysMsgType06Attachment.getMsgData();
        String msgTitle = sysMsgType06Attachment.getMsgTitle();
        tvTitle.setText(msgTitle);
        String applyState = "";

        switch (sysMsgType06Attachment.getMsgType()) {
            case CustomAttachmentType.sys_clueprovide://线索登记消息
                llType02.setVisibility(View.VISIBLE);
                llType.setVisibility(View.VISIBLE);

                tvTypes.setText("财产类型");
                String cclx = JsonUtil.jsonGetString(data, "cclx");
                tvType.setText(ClueProvideData.getClueProvideName(cclx));

                String syqr = JsonUtil.jsonGetString(data, "syqr");
                tvType02.setText(syqr);
                tvTypes02.setText("所有权人");
                if (android.text.TextUtils.isEmpty(syqr)) {
                    llType02.setVisibility(View.GONE);
                } else {
                    llType02.setVisibility(View.VISIBLE);
                }


                applyState = "查看";
                String state = "";
                int shzt = JsonUtil.jsonGetIntger(data, "shzt");
                if (shzt != 0) {//代表已经被核查过了
                    llVerificationResult.setVisibility(View.VISIBLE);
                    switch (shzt) {//0 待审核 1 通过 2 拒绝
                        case 1:
                            state = "属实";
                            break;
                        case 2:
                            state = "不实";
                            break;
                    }
                    tvVerificationResult.setText(state);


                    List<String> strings = JSON.parseArray(JsonUtil.jsonGetString(data, "shwjList"), String.class);
                    if (strings.size() > 0) {
                        llImages.setVisibility(View.VISIBLE);
                    }

                    switch (strings.size()) {

                        case 1:
                            loadImg(strings.get(0), ivFrist);
                            break;
                        case 2:

                            loadImg(strings.get(0), ivFrist);
                            loadImg(strings.get(1), ivSecond);

                            break;
                        case 3:
                            loadImg(strings.get(0), ivFrist);
                            loadImg(strings.get(1), ivSecond);
                            loadImg(strings.get(2), ivThird);
                            loadImg(strings.get(2), ivThird);
                            break;
                        case 4:
                            loadImg(strings.get(0), ivFrist);
                            loadImg(strings.get(1), ivSecond);
                            loadImg(strings.get(2), ivThird);
                            loadImg(strings.get(3), ivFourth);
                            break;
                        default:
                            break;
                    }


                } else {
                    llVerificationResult.setVisibility(View.GONE);
                }

                listener = new OnSingleClickListener() {
                    @Override
                    protected void onSingleClick() {
                        ClueVerificationDetailActivity.startVerification(context, JsonUtil.jsonGetIntger(data, "id") + "");

                    }
                };

                break;
            case CustomAttachmentType.sys_punishment_lostletter://惩戒消息

                llType.setVisibility(View.VISIBLE);
                tvTypes.setText("被申请人");
                tvType.setText(JsonUtil.jsonGetString(data, "bsqr"));
                String sqzt = JsonUtil.jsonGetString(data, "shzt");
                String url = JsonUtil.jsonGetString(data, "url");
                int id = JsonUtil.jsonGetIntger(data, "id");


                if (sqzt != null) {
                    if (sqzt.equals("1")) {//通过
                        applyState = "申请已通过";
                        listener = new OnSingleClickListener() {
                            @Override
                            protected void onSingleClick() {
                                PunishmentDetailActivity.start(context, id + "");

                            }
                        };
                    } else if (sqzt.equals("2")) {//拒绝
                        applyState = "申请已驳回";
                        listener = new OnSingleClickListener() {
                            @Override
                            protected void onSingleClick() {
                                PunishmentDetailActivity.start(context, id + "");

                            }
                        };
                        String jjsy = JsonUtil.jsonGetString(data, "jjsy");

                        if (jjsy != null && !jjsy.equals("")) {//有理由的时候
                            tvRemark.setText(jjsy);
                            tvRemark.setVisibility(View.VISIBLE);
                        }


                    } else {
                        applyState = "查看";
                        listener = new OnSingleClickListener() {
                            @Override
                            protected void onSingleClick() {
                                PunishmentDetailActivity.start(context, id + "");
                            }
                        };
                    }
                } else {
                    applyState = "查看";

                }


                break;

            case CustomAttachmentType.sys_service_document:// 电子文书
                llType.setVisibility(View.VISIBLE);
                tvTypes.setText("文书类型");
                tvType.setText(JsonUtil.jsonGetString(data, "content"));


                List<String> strings = JSON.parseArray(JsonUtil.jsonGetString(data, "wjlb"), String.class);

                listener = new OnSingleClickListener() {
                    @Override
                    protected void onSingleClick() {
                        LargeImageActivity.startBigImg(context, strings, 0);

                    }
                };
                applyState = "查看";

                break;

            case CustomAttachmentType.sys_casea_agent:// 案件代理
                llType.setVisibility(View.VISIBLE);
                llType02.setVisibility(View.VISIBLE);


                tvTypes.setText("发起人");
                tvType.setText(JsonUtil.jsonGetString(data, "fqr"));
                tvTypes02.setText("当事人");
                tvType02.setText(JsonUtil.jsonGetString(data, "dsr"));

                listener = new OnSingleClickListener() {
                    @Override
                    protected void onSingleClick() {

                        CaseAgentDetailActivity.startAgnetDetail((Activity) context, JsonUtil.jsonGetIntger(data, "id") + "");

                    }
                };

                applyState = "查看";


                break;
            case CustomAttachmentType.sys_mesnotifycation_netcheck://网络查控告知书
                llType.setVisibility(View.VISIBLE);
                tvTypes.setText(context.getResources().getString(R.string.executed_people_));
                tvType.setText(JsonUtil.jsonGetString(data, "bzxr"));
                int id1 = JsonUtil.jsonGetIntger(data, "id");
                listener = new OnSingleClickListener() {
                    @Override
                    protected void onSingleClick() {
                        if (id1 != 0) {
                            NetCheckControlNotifycationActivity.start((Activity) context, id1 + "");

                        }

                    }
                };
                applyState = "查看";


                break;
        }
        llParent.setOnClickListener(listener);

        tvContetn.setText(applyState);

    }

    private void loadImg(String url, ImageView iv) {
        GlidelUtil.loadRoundedCornerImag((Activity) context, url, iv, 20);

    }


}
