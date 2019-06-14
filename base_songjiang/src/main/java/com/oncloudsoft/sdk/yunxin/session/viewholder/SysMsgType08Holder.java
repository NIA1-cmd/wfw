package com.oncloudsoft.sdk.yunxin.session.viewholder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.DialogActivity;
import com.oncloudsoft.sdk.activity.webview.LongImageWebActivity;
import com.oncloudsoft.sdk.utils.DateUtil;
import com.oncloudsoft.sdk.yunxin.session.extension.CustomAttachmentType;
import com.oncloudsoft.sdk.yunxin.session.extension.SysMsgType08Attachment;
import com.oncloudsoft.sdk.yunxin.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.oncloudsoft.sdk.yunxin.uikit.common.util.sys.ScreenUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.oncloudsoft.sdk.utils.DateUtil.DateFormat.YearmonthdayHourminutes_01;

/**
 * 新的需求 消息通知
 */
public class SysMsgType08Holder extends CustomBaseHolder<SysMsgType08Attachment> {
    private LinearLayout llParent;
    //    private TextView tvTitle;

    private TextView gaozhixiang;

    private TextView xiayibu;

    private View lineView;

    private int w = (int) (ScreenUtil.getDisplayWidth() * 0.6);

    public SysMsgType08Holder(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.sysmsg_itemtype08;

    }

    @Override
    protected void inflateContentView() {

        llParent = findViewById(R.id.ll_parent);

        gaozhixiang = getBlueTextView(getStringById(R.string.inform_item));
        xiayibu = getTitleTextView(getStringById(R.string.next_measure));
        lineView = LayoutInflater.from(context).inflate(R.layout.line_layout, null);
        LinearLayout.LayoutParams lineLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        lineLayoutParams.setMargins(0, 10, 0, 10);
        lineView.setLayoutParams(lineLayoutParams);

        xiayibu.setMaxWidth(w);
    }

    @Override
    protected void bindCustomContentView(SysMsgType08Attachment sysMsgType08Attachment) {
        View.OnClickListener onClickListener = null;

        final SysMsgType08Attachment sysMsgAttachment = (SysMsgType08Attachment) message.getAttachment();
        if (sysMsgAttachment == null) {
            return;
        }
        llParent.removeAllViews();
        llParent.addView(getTitleTextView(sysMsgAttachment.getMsgTitle()));
        String json = sysMsgAttachment.getMsgData();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject = new JSONObject();
        }

        try {
            switch (sysMsgAttachment.getMsgType()) {
                case CustomAttachmentType.sys_mesnotifycation_execute://执行通知
                    String wslx = jsonObject.optString("wslx");
                    long jssj = jsonObject.optLong("jssj");
                    long ytsj = jsonObject.optLong("ytsj");
                    String ytdd = jsonObject.optString("ytdd");
                    String bz = jsonObject.optString("bz");
                    String gzx2 = jsonObject.optString("gzx");
                    llParent.addView(getNormalTextView(R.string.wenshu_type, wslx));
                    llParent.addView(getNormalTextView(R.string.send_date, getTime(jssj)));

                    onClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //跳转到长图中
                            LongImageWebActivity.start(context, gzx2, "执行通知");
                        }
                    };

                    llParent.addView(gaozhixiang);
                    llParent.addView(lineView);
                    llParent.addView(xiayibu);
                    llParent.addView(getNormalTextView(R.string.interview_time, DateUtil.longToTime(ytsj, YearmonthdayHourminutes_01)));
                    llParent.addView(getNormalTextView(R.string.interview_address, ytdd));
                    llParent.addView(getNormalTextView(-1, bz));


                    break;
                case CustomAttachmentType.sys_mesnotifycation_evaluationreport://评估报告
                    String pgbdwmc = jsonObject.optString("pgbdwmc");
                    String pgbdwsyr = jsonObject.optString("pgbdwsyr");
                    double pgjg = jsonObject.optDouble("pgjg");
                    long jssj1 = jsonObject.optLong("jssj");
                    String xybcs = jsonObject.optString("xybcs");
                    String gzx1 = jsonObject.optString("gzx");
                    llParent.addView(getNormalTextView(R.string.evaluation_subjectname, pgbdwmc));
                    llParent.addView(getNormalTextView(R.string.evaluation_subjectpeople, pgbdwsyr));
                    llParent.addView(getNormalTextView(R.string.evaluation_price, pgjg + ""));
                    llParent.addView(getNormalTextView(R.string.send_date, getTime(jssj1)));
                    onClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog(gzx1);
                        }
                    };

                    llParent.addView(gaozhixiang);
                    llParent.addView(lineView);
                    llParent.addView(xiayibu);
                    llParent.addView(getNormalTextView(-1, xybcs));
                    break;
                case CustomAttachmentType.sys_mesnotifycation_auctionplatform://拍卖平台选择


                    JSONArray jsonArray = jsonObject.optJSONArray("pmpt");
                    String xybcs1 = jsonObject.optString("xybcs");
                    String gzx = jsonObject.optString("gzx");
                    //
                    for (int i = 0; i < jsonArray.length(); i++) {
                        llParent.addView(getNormalTextView(-1, (String) jsonArray.get(i)));
                    }
                    onClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog(gzx);
                        }
                    };
                    //
                    llParent.addView(gaozhixiang);
                    llParent.addView(lineView);
                    llParent.addView(xiayibu);
                    llParent.addView(getNormalTextView(-1, xybcs1));

                    break;
                case CustomAttachmentType.sys_mesnotifycation_rulingonproperty://以物抵债裁定
                    String bdwmc = jsonObject.optString("bdwmc");
                    String bdwsyr = jsonObject.optString("bdwsyr");
                    double zdje = jsonObject.optDouble("zdje");
                    long jssj2 = jsonObject.optLong("jssj");
                    String xybcs2 = jsonObject.optString("xybcs");
                    llParent.addView(getNormalTextView(R.string.subjec_tname, bdwmc));
                    llParent.addView(getNormalTextView(R.string.subject_people, bdwsyr));
                    llParent.addView(getNormalTextView(R.string.deductible_amount, zdje + ""));
                    llParent.addView(getNormalTextView(R.string.send_date, getTime(jssj2)));
                    llParent.addView(lineView);
                    llParent.addView(xiayibu);
                    llParent.addView(getNormalTextView(-1, xybcs2));

                    break;
                case CustomAttachmentType.sys_mesnotifycation_casepayment://案款发放

                    double ffje = jsonObject.optDouble("ffje");
                    long ffsj = jsonObject.optLong("ffsj");
                    llParent.addView(getNormalTextView(R.string.provide_date, getTime(ffsj)));
                    llParent.addView(getNormalTextView(R.string.provide_amount, ffje + ""));

                    break;
                case CustomAttachmentType.sys_mesnotifycation_insufficientamount_final://不足额终本结案通知
                    String txt = jsonObject.optString("txt");
                    llParent.addView(getNormalTextView(-1, txt));
                    break;
                case CustomAttachmentType.sys_mesnotifycation_noamount_final://无财产终本结案通知
                    String txt1 = jsonObject.optString("txt");
                    llParent.addView(getNormalTextView(-1, txt1));
                    break;


                case CustomAttachmentType.sys_clue_remind://当事人提交财产线索后
                    llParent.addView(getNormalTextView(-1, json));
                    break;
                case CustomAttachmentType.sys_application_remind://法官线索核查，核查结果为确认
                    llParent.addView(getNormalTextView(-1, json));
                    break;
                case CustomAttachmentType.sys_verification_remind://线索核查后7日，系统自动发送消息提醒
                    llParent.addView(getNormalTextView(-1, json));
                    break;
                case CustomAttachmentType.sys_notification_remind://非网络查控提醒
                    llParent.addView(getNormalTextView(-1, json));
                    break;
                case CustomAttachmentType.sys_allnotification_remind://风险提示
                    llParent.addView(getNormalTextView(-1, json));
                    break;


                case CustomAttachmentType.sys_wlck://网络查控
                    String qdsj = jsonObject.optString("qdsj");
                    String ckfw = jsonObject.optString("ckfw");
                    String wlck_xybcs = jsonObject.optString("xybcs");
//                    String wlck_qdsj = jsonObject.optString("qdsj");
                    llParent.addView(getNormalTextView(R.string.starttime, qdsj));
                    llParent.addView(getNormalTextView(R.string.ckfw, ckfw));
                    llParent.addView(lineView);
                    llParent.addView(xiayibu);
                    putSubString(wlck_xybcs);
                    break;
                case CustomAttachmentType.sys_dj://冻结
                    String djsj = jsonObject.optString("djsj");
                    String djcwsyr = jsonObject.optString("djcwsyr");
                    String djqx = jsonObject.optString("djqx");
                    String dj_xybcs = jsonObject.optString("xybcs");
                    llParent.addView(getNormalTextView(R.string.djsj, djsj));
                    llParent.addView(getNormalTextView(R.string.djcwsyr, djcwsyr));
                    llParent.addView(getNormalTextView(R.string.djqx, djqx));
                    llParent.addView(lineView);
                    llParent.addView(xiayibu);
                    putSubString(dj_xybcs);
                    break;
                case CustomAttachmentType.sys_cf://查封
                    String cfsj = jsonObject.optString("cfsj");
                    String cfwmc = jsonObject.optString("cfwmc");
                    String cfwsyr = jsonObject.optString("cfwsyr");
                    String cfqx = jsonObject.optString("cfqx");
                    String cf_xybcs = jsonObject.optString("xybcs");
                    String cf_gzx = jsonObject.optString("gzx");

                    llParent.addView(getNormalTextView(R.string.cfsj, cfsj));
                    llParent.addView(getNormalTextView(R.string.cfwmc, cfwmc));
                    llParent.addView(getNormalTextView(R.string.cfwsyr, cfwsyr));
                    llParent.addView(getNormalTextView(R.string.cfqx, cfqx));
                    llParent.addView(gaozhixiang);
                    llParent.addView(lineView);
                    llParent.addView(xiayibu);
                    putSubString(cf_xybcs);
                    onClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog(cf_gzx);
                        }
                    };
                    break;
                case CustomAttachmentType.sys_pg://评估
                    String pg_qdsj = jsonObject.optString("qdsj");
                    String pg_pgbdwmc = jsonObject.optString("pgbdwmc");
                    String pg_pgbdwsyr = jsonObject.optString("pgbdwsyr");
                    String pg_xybcs = jsonObject.optString("xybcs");

                    llParent.addView(getNormalTextView(R.string.starttime, pg_qdsj));
                    llParent.addView(getNormalTextView(R.string.evaluation_subjectname, pg_pgbdwmc));
                    llParent.addView(getNormalTextView(R.string.evaluation_subjectpeople, pg_pgbdwsyr));
                    llParent.addView(lineView);
                    llParent.addView(xiayibu);
                    putSubString(pg_xybcs);
                    break;
                case CustomAttachmentType.sys_pgbg://评估报告

                    String pgbg_pgbdwmc = jsonObject.optString("pgbdwmc");
                    String pgbg_pgbdwsyr = jsonObject.optString("pgbdwsyr");
                    String pgbg_pgjg = jsonObject.optString("pgjg");
                    String pgbg_jssj = jsonObject.optString("jssj");
                    String pgbg_xybcs = jsonObject.optString("xybcs");
                    String pgbg_gzx = jsonObject.optString("gzx");

                    llParent.addView(getNormalTextView(R.string.evaluation_subjectname, pgbg_pgbdwmc));
                    llParent.addView(getNormalTextView(R.string.evaluation_subjectpeople, pgbg_pgbdwsyr));
                    llParent.addView(getNormalTextView(R.string.evaluation_price, pgbg_pgjg));
                    llParent.addView(getNormalTextView(R.string.send_date, pgbg_jssj));
                    llParent.addView(gaozhixiang);
                    llParent.addView(lineView);
                    llParent.addView(xiayibu);
                    putSubString(pgbg_xybcs);
                    onClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog(pgbg_gzx);
                        }
                    };
                    break;
                case CustomAttachmentType.sys_pmcg://拍卖成功
                    String pmcg_pmsj = jsonObject.optString("pmsj");
                    String pmcg_pmbdwmc = jsonObject.optString("pmbdwmc");
                    String pmcg_pmbdwsyr = jsonObject.optString("pmbdwsyr");
                    String pmcg_cjj = jsonObject.optString("cjj");
                    String pmcg_xybcs = jsonObject.optString("xybcs");

                    llParent.addView(getNormalTextView(R.string.pmsj, pmcg_pmsj));
                    llParent.addView(getNormalTextView(R.string.pmbdwmc, pmcg_pmbdwmc));
                    llParent.addView(getNormalTextView(R.string.pmbdwsyr, pmcg_pmbdwsyr));
                    llParent.addView(getNormalTextView(R.string.cjj, pmcg_cjj));
                    llParent.addView(lineView);
                    llParent.addView(xiayibu);
                    putSubString(pmcg_xybcs);
                    break;
                case CustomAttachmentType.sys_akdz://案款到账通知
                    String akdz_dzje = jsonObject.optString("dzje");
                    String akdz_dzsj = jsonObject.optString("dzsj");
                    String akdz_xybcs = jsonObject.optString("xybcs");
                    String akdz_gzx = jsonObject.optString("gzx");
                    llParent.addView(getNormalTextView(R.string.dzje, akdz_dzje));
                    llParent.addView(getNormalTextView(R.string.dzsj, akdz_dzsj));
                    llParent.addView(gaozhixiang);
                    llParent.addView(lineView);
                    llParent.addView(xiayibu);
                    putSubString(akdz_xybcs);
                    onClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog(akdz_gzx);
                        }
                    };
                    break;
                case CustomAttachmentType.sys_nodeacquisition://节点采集
                    String jdmc = jsonObject.optString("jdmc");
                    String dclx = jsonObject.optString("dclx");
                    String bdcrmc = jsonObject.optString("bdcrmc");
                    long dcsj = jsonObject.optLong("dcsj");
                    String dcdd = jsonObject.optString("dcdd");
                    llParent.addView(getNormalTextView(R.string.nodename, jdmc));
                    llParent.addView(getNormalTextView(R.string.surveytype, dclx));
                    llParent.addView(getNormalTextView(R.string.surveypeople, bdcrmc));
                    llParent.addView(getNormalTextView(R.string.surveytime, DateUtil.longToTime(dcsj, YearmonthdayHourminutes_01)));
                    llParent.addView(getNormalTextView(R.string.surveyaddress, dcdd));

                    break;
                case CustomAttachmentType.sys_mediacollection://多媒体采集采集通知
                    String zxcs = jsonObject.optString("zxcs");
                    String bzxrmc = jsonObject.optString("bzxrmc");
                    String cjrmc = jsonObject.optString("cjrmc");
                    long cjsj = jsonObject.optLong("cjsj");
                    String cjdd = jsonObject.optString("cjdd");
                    llParent.addView(getNormalTextView(R.string.implementation_measures, zxcs));
                    llParent.addView(getNormalTextView(R.string.executed_people, bzxrmc));
                    llParent.addView(getNormalTextView(R.string.collection_people, cjrmc));
                    llParent.addView(getNormalTextView(R.string.collection_time, DateUtil.longToTime(cjsj, YearmonthdayHourminutes_01)));
                    llParent.addView(getNormalTextView(R.string.collection_address, cjdd));
                    break;
                case CustomAttachmentType.sys_termreminder://期限提醒
                    String czlx = jsonObject.optString("czlx");//财产措施
                    String txnr = jsonObject.optString("txnr");//提醒内容
                    llParent.addView(getNormalTextView(R.string.property_measure, czlx));
                    llParent.addView(getNormalTextView(R.string.reminder_content, txnr));
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        gaozhixiang.setOnClickListener(onClickListener);
        llParent.setLayoutParams(new FrameLayout.LayoutParams(w, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void putSubString(String value) {
        int i = value.indexOf("\\n");
        if (i != -1) {
            String value1 = value.substring(0, i);
            String value2 = value.substring(i + 2, value.length()).replaceAll(" ", "");
            llParent.addView(getNormalTextView(-1, value1));
            llParent.addView(getNormalTextView(-1, value2));
        } else {
            llParent.addView(getNormalTextView(-1, value));
        }
    }

    @Override
    protected int leftBackground() {
        return R.drawable.nim_message_item_left_white_selector;
    }

    @Override
    protected int rightBackground() {
        return R.drawable.nim_message_item_right_white_selector;
    }


    private TextView getNormalTextView(int nameStrId, String contetn) {

        TextView textView = new TextView(context);
        textView.setTextAppearance(context, R.style.text_style_08);

        if (nameStrId == -1) {
            textView.setText(contetn);

        } else {
            textView.setText(getStringById(nameStrId) + getStringById(R.string.colon) + contetn);

        }

        return setTextViewLayoutParams(textView);
    }


    /**
     * 生成标题文字
     *
     * @param text
     * @return
     */
    private TextView getTitleTextView(String text) {

        TextView textView = new TextView(context);
        textView.setTextAppearance(context, R.style.text_style_09);
        textView.setText(text);

        return setTextViewLayoutParams(textView);
    }

    private TextView getBlueTextView(String text) {
        TextView textView = getTitleTextView(text);
        textView.setTextColor(context.getResources().getColor(R.color.maincolor_02));

        return setTextViewLayoutParams(textView);

    }


    private String getStringById(int id) {

        return context.getResources().getString(id);

    }


    private String getTime(long time) {
        return DateUtil.longToTime(time, DateUtil.DateFormat.Yearmonthday_02);
    }


    private void showDialog(String content) {
        DialogActivity.showTextDialog((Activity) context, content, getStringById(R.string.got_it), null);

    }


    private TextView setTextViewLayoutParams(TextView textView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 5, 0, 5);
        textView.setLayoutParams(layoutParams);

        textView.setMaxWidth(w);
        textView.setMinWidth(w);
        return textView;
    }
}
