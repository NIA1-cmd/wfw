package com.oncloudsoft.sdk.yunxin.session.extension;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netease.nimlib.sdk.avchat.constant.AVChatRecordState;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.model.AVChatAttachment;
import com.netease.nimlib.sdk.msg.attachment.AudioAttachment;
import com.netease.nimlib.sdk.msg.attachment.FileAttachment;
import com.netease.nimlib.sdk.msg.attachment.ImageAttachment;
import com.netease.nimlib.sdk.msg.attachment.LocationAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.VideoAttachment;
import com.netease.nimlib.sdk.msg.constant.AttachStatusEnum;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.MemberPushOption;
import com.netease.nimlib.sdk.msg.model.NIMAntiSpamOption;
import com.netease.nimlib.sdk.robot.model.RobotAttachment;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.utils.JsonUtil;
import com.oncloudsoft.sdk.utils.SharedPreferencesUtils;

import java.util.Map;

/**
 * 作者 黄继栋
 * 创建时间 2019/3/11 13:38
 * 描述  自定义消息的解析工具类
 */

public class AttachParserUtils {

    public static CustomAttachment getCustomAttachMent(String json) {


        CustomAttachment attachment;

        try {
            JSONObject object = JSON.parseObject(json);

            int type = object.getInteger("type");
            JSONObject data = object.getJSONObject("data");
            switch (type) {
//                case CustomAttachmentType.Guess:
//                    attachment = new GuessAttachment();
//                    break;
//                case CustomAttachmentType.SnapChat:
//                    return new SnapChatAttachment(data);
                case CustomAttachmentType.Sticker:
                    attachment = new StickerAttachment();
                    break;


//                case CustomAttachmentType.RTS:
//                    attachment = new RTSAttachment();
//                    break;
//                case CustomAttachmentType.RedPacket:
//                    attachment = new RedPacketAttachment();
//                    break;
//                case CustomAttachmentType.OpenedRedPacket:
//                    attachment = new RedPacketOpenedAttachment();
//                    break;
//
//                case CustomAttachmentType.custom:
//                    attachment=new MyCustomAttachment();
//                    break;

                case CustomAttachmentType.sys_customer:

//                JSONObject contentData = data.getJSONObjct(KEY_DATA);//深一层解析
                    String tcontentType = data.getString("xxlx");
                    switch (tcontentType) {
                        case CustomAttachmentType.sys_bank://系统消息 中总对总银行反馈  样式二
                            attachment = new SysMsgType02Attachment(Integer.parseInt(CustomAttachmentType.sys_bank));
                            break;
                        case CustomAttachmentType.sys_car://系统消息 中总对总车辆反馈  样式二
                            attachment = new SysMsgType02Attachment(Integer.parseInt(CustomAttachmentType.sys_car));
                            break;
                        case CustomAttachmentType.sys_executive_notification://执行通知书  样式一
                            attachment = new SysMsgType01Attachment(Integer.parseInt(CustomAttachmentType.sys_executive_notification));
                            break;
                        case CustomAttachmentType.sys_process_notification://金钱给付类执行案件流程告知书  样式一
                            attachment = new SysMsgType01Attachment(Integer.parseInt(CustomAttachmentType.sys_process_notification));
                            break;
                        case CustomAttachmentType.sys_property_notification://财产报告令  样式一
                            attachment = new SysMsgType01Attachment(Integer.parseInt(CustomAttachmentType.sys_property_notification));
                            break;
                        case CustomAttachmentType.sys_onlyhouse://系统消息 中被执行人唯一住房，如何执行  样式一
                            attachment = new SysMsgType01Attachment(Integer.parseInt(CustomAttachmentType.sys_onlyhouse));
                            break;
                        case CustomAttachmentType.sys_lease://系统消息 中被执行人房产处于租赁状态, 如何执行?  样式一
                            attachment = new SysMsgType01Attachment(Integer.parseInt(CustomAttachmentType.sys_lease));
                            break;
                        case CustomAttachmentType.sys_punishment_lostletter://纳入失信被执行人名单   样式六
                            attachment = new SysMsgType06Attachment(Integer.parseInt(CustomAttachmentType.sys_punishment_lostletter));
                            break;
                        case CustomAttachmentType.sys_casenodify_notification://限制出境  样式四
                            attachment = new SysMsgType04Attachment(Integer.parseInt(CustomAttachmentType.sys_casenodify_notification));
                            break;
                        case CustomAttachmentType.sys_savecase_notification://保存执行  样式四
                            attachment = new SysMsgType04Attachment(Integer.parseInt(CustomAttachmentType.sys_savecase_notification));
                            break;

                        case CustomAttachmentType.sys_clueprovide://线索登记--消息  样式六
                            attachment = new SysMsgType06Attachment(Integer.parseInt(CustomAttachmentType.sys_clueprovide));
                            break;
                        case CustomAttachmentType.sys_casea_agent://案件代理  样式六
                            attachment = new SysMsgType06Attachment(Integer.parseInt(CustomAttachmentType.sys_casea_agent));
                            break;

                        case CustomAttachmentType.sys_message_01://总对总发起后            样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_01));
                            break;
                        case CustomAttachmentType.sys_message_02://线索核查后         样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_02));
                            break;
                        case CustomAttachmentType.sys_message_03://查控结果无财产           样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_03));
                            break;
                        case CustomAttachmentType.sys_message_04://冻结被执行人财产后         样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_04));
                            break;
                        case CustomAttachmentType.sys_message_05://查封不动产后            样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_05));
                            break;
                        case CustomAttachmentType.sys_message_06://查封动产后         样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_06));
                            break;
                        case CustomAttachmentType.sys_message_07://评估开始后         样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_07));
                            break;
                        case CustomAttachmentType.sys_message_08://评估报告出来后           样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_08));
                            break;
                        case CustomAttachmentType.sys_message_09://通知申请人拍卖平台选择           样式一
                            attachment = new SysMsgType01Attachment(Integer.parseInt(CustomAttachmentType.sys_message_09));
                            break;
                        case CustomAttachmentType.sys_message_10://填写变卖信息后           样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_10));
                            break;
                        case CustomAttachmentType.sys_message_11://填写以物抵债信息后         样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_11));
                            break;
                        case CustomAttachmentType.sys_message_12://案款已汇入法院           样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_12));
                            break;
                        case CustomAttachmentType.sys_message_13://案款发放成功            样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_13));
                            break;
                        case CustomAttachmentType.sys_message_14://终本申请报结不足          样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_14));
                            break;
                        case CustomAttachmentType.sys_message_15://终本申请报结无财产         样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_15));
                            break;
                        case CustomAttachmentType.sys_message_16://罚款时           样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_16));
                            break;
                        case CustomAttachmentType.sys_message_17://拘留            样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_17));
                            break;
                        case CustomAttachmentType.sys_message_18://限高令           样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_18));
                            break;
                        case CustomAttachmentType.sys_message_19://被执行人失信            样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_19));
                            break;
                        case CustomAttachmentType.sys_message_20://限制出境    样式三
                            attachment = new SysMsgType03Attachment(Integer.parseInt(CustomAttachmentType.sys_message_20));
                            break;
                        case CustomAttachmentType.sys_service_document://文书送达 样式六
                            attachment = new SysMsgType06Attachment(Integer.parseInt(CustomAttachmentType.sys_service_document));
                            break;

                        case CustomAttachmentType.sys_reconciliation_agreement://文书送达 样式七
                            attachment = new SysMsgType07Attachment(Integer.parseInt(CustomAttachmentType.sys_reconciliation_agreement));
                            break;
                        case CustomAttachmentType.sys_tip:// 自定义提示类消息
                            attachment = new SysMsgTypeCustomTipsAttachment(Integer.parseInt(CustomAttachmentType.sys_tip));
                            break;
                        case CustomAttachmentType.sys_mesnotifycation_execute:// 执行通知
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_mesnotifycation_execute));
                            break;
                        case CustomAttachmentType.sys_mesnotifycation_evaluationreport:////评估报告
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_mesnotifycation_evaluationreport));
                            break;
                        case CustomAttachmentType.sys_mesnotifycation_auctionplatform:// 拍卖平台选择
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_mesnotifycation_auctionplatform));
                            break;
                        case CustomAttachmentType.sys_mesnotifycation_rulingonproperty:// 以物抵债裁定
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_mesnotifycation_rulingonproperty));
                            break;
                        case CustomAttachmentType.sys_mesnotifycation_casepayment:// 案款发放
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_mesnotifycation_casepayment));
                            break;
                        case CustomAttachmentType.sys_mesnotifycation_insufficientamount_final:// 不足额终本结案通知
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_mesnotifycation_insufficientamount_final));
                            break;
                        case CustomAttachmentType.sys_mesnotifycation_noamount_final:// 无财产终本结案通知
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_mesnotifycation_noamount_final));
                            break;
                        case CustomAttachmentType.sys_mesnotifycation_netcheck:// 网络查控告知书
                            attachment = new SysMsgType06Attachment(Integer.parseInt(CustomAttachmentType.sys_mesnotifycation_netcheck));
                            break;
                        case CustomAttachmentType.sys_applypeople_enterchat://申请人进群通知
                            attachment = new SysMsgTypeCustomTipsAttachment(Integer.parseInt(CustomAttachmentType.sys_applypeople_enterchat));
                            break;
                        case CustomAttachmentType.sys_final_publicity://终本公示消息
                            attachment = new SysMsgTypeCustomTipsAttachment(Integer.parseInt(CustomAttachmentType.sys_final_publicity));
                            break;
                        case CustomAttachmentType.sys_clue_remind://当事人提交线索后
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_clue_remind));
                            break;
                        case CustomAttachmentType.sys_application_remind://法官线索核查，核查结果为确认
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_application_remind));
                            break;
                        case CustomAttachmentType.sys_verification_remind://纵队总告知书发送后，核查结果均为0时
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_verification_remind));
                            break;
                        case CustomAttachmentType.sys_notification_remind://非网络查控提醒
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_notification_remind));
                            break;
                        case CustomAttachmentType.sys_allnotification_remind://风险提示
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_allnotification_remind));
                            break;
                        case CustomAttachmentType.sys_wlck://网络查控
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_wlck));
                            break;
                        case CustomAttachmentType.sys_dj://冻结
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_dj));
                            break;
                        case CustomAttachmentType.sys_cf://查封
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_cf));
                            break;
                        case CustomAttachmentType.sys_pg://评估
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_pg));
                            break;
                        case CustomAttachmentType.sys_pgbg://评估报告
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_pgbg));
                            break;
                        case CustomAttachmentType.sys_pmcg://拍卖成功
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_pmcg));
                            break;
                        case CustomAttachmentType.sys_akdz://案款到账通知
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_akdz));
                            break;
                        case CustomAttachmentType.sys_nodeacquisition://节点采集
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_nodeacquisition));
                            break;
                        case CustomAttachmentType.sys_mediacollection://多媒体采集采集
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_mediacollection));
                            break;
                            case CustomAttachmentType.sys_termreminder://期限提醒
                            attachment = new SysMsgType08Attachment(Integer.parseInt(CustomAttachmentType.sys_termreminder));
                            break;
                        default:
                            attachment = new DefaultCustomAttachment();
                            break;

                    }

                    break;


                default:
                    attachment = new DefaultCustomAttachment();
                    break;
            }
            attachment.fromJson(data);
        } catch (Exception e) {
            attachment = new DefaultCustomAttachment();

        }

        return attachment;
    }


    public static IMMessage getImMessage(String fromNick, String fromAccount, MsgTypeEnum mesType, String content, Long time, String sesstionId) {
        MsgAttachment msgAttachment = new DefaultCustomAttachment();
        switch (mesType) {
            case image:
                msgAttachment = new ImageAttachment(content);
                break;
            case video:
                msgAttachment = new VideoAttachment(content);
                break;
            case text:
                break;
            case custom://自定义消息
                msgAttachment = getCustomAttachMent(content);
                break;

            case notification:
                msgAttachment = new DefaultCustomAttachment();//如果是 通知   数据被转译了 所以数据有问题
                break;

            case audio://语音消息

                msgAttachment = new AudioAttachment(content);

                break;


            case location:

                msgAttachment = new LocationAttachment();

                break;

            case file:

                msgAttachment = new FileAttachment();

                break;

            case avchat:

                msgAttachment = new AVChatAttachment() {
                    @Override
                    public AVChatRecordState getState() {
                        return null;
                    }

                    @Override
                    public int getDuration() {
                        return 0;
                    }

                    @Override
                    public AVChatType getType() {
                        return null;
                    }

                    @Override
                    public boolean isMultiUser() {
                        return false;
                    }

                    @Override
                    public String toJson(boolean b) {
                        return null;
                    }
                };

                break;

            case tip:
//                msgAttachment = new Tipat();

                break;

            case robot:
                msgAttachment = new RobotAttachment();

                break;

        }


        MsgAttachment finalMsgAttachment = msgAttachment;
        return new IMMessage() {
            @Override
            public String getUuid() {


                if (content != null && content.contains("md5")) {
                    return JsonUtil.jsonGetString(content, "md5");

                } else {
                    return "";
                }
            }

            @Override
            public boolean isTheSame(IMMessage imMessage1) {
                return false;
            }

            @Override
            public String getSessionId() {
                return sesstionId;
            }

            @Override
            public SessionTypeEnum getSessionType() {
                return SessionTypeEnum.Team;
            }

            @Override
            public String getFromNick() {
                return fromNick;
            }

            @Override
            public MsgTypeEnum getMsgType() {

                return mesType;
            }

            @Override
            public MsgStatusEnum getStatus() {
                return MsgStatusEnum.read;
            }

            @Override
            public void setStatus(MsgStatusEnum msgStatusEnum) {

            }

            @Override
            public void setDirect(MsgDirectionEnum msgDirectionEnum) {

            }

            @Override
            public MsgDirectionEnum getDirect() {

                return SharedPreferencesUtils.getParam(Global.YUNXIN_ACCONT, "").equals(fromAccount) ? MsgDirectionEnum.Out : MsgDirectionEnum.In;

            }

            @Override
            public void setContent(String s) {

            }

            @Override
            public String getContent() {


                return content;

            }

            @Override
            public long getTime() {
                return time;
            }

            @Override
            public void setFromAccount(String s) {

            }

            @Override
            public String getFromAccount() {
                return fromAccount;
            }

            @Override
            public void setAttachment(MsgAttachment msgAttachment) {

            }

            @Override
            public MsgAttachment getAttachment() {


                return finalMsgAttachment;

            }

            @Override
            public AttachStatusEnum getAttachStatus() {
                return AttachStatusEnum.transferred;
            }

            @Override
            public void setAttachStatus(AttachStatusEnum attachStatusEnum) {

            }

            @Override
            public CustomMessageConfig getConfig() {
                return null;
            }

            @Override
            public void setConfig(CustomMessageConfig customMessageConfig) {

            }

            @Override
            public Map<String, Object> getRemoteExtension() {
                return null;
            }

            @Override
            public void setRemoteExtension(Map<String, Object> map) {

            }

            @Override
            public Map<String, Object> getLocalExtension() {
                return null;
            }

            @Override
            public void setLocalExtension(Map<String, Object> map) {

            }

            @Override
            public String getPushContent() {
                return null;
            }

            @Override
            public void setPushContent(String s) {

            }

            @Override
            public Map<String, Object> getPushPayload() {
                return null;
            }

            @Override
            public void setPushPayload(Map<String, Object> map) {

            }

            @Override
            public MemberPushOption getMemberPushOption() {
                return null;
            }

            @Override
            public void setMemberPushOption(MemberPushOption memberPushOption) {

            }

            @Override
            public boolean isRemoteRead() {
                return false;
            }

            @Override
            public boolean needMsgAck() {
                return false;
            }

            @Override
            public void setMsgAck() {

            }

            @Override
            public boolean hasSendAck() {
                return false;
            }

            @Override
            public int getTeamMsgAckCount() {
                return 0;
            }

            @Override
            public int getTeamMsgUnAckCount() {
                return 0;
            }

            @Override
            public int getFromClientType() {
                return 0;
            }

            @Override
            public NIMAntiSpamOption getNIMAntiSpamOption() {
                return null;
            }

            @Override
            public void setNIMAntiSpamOption(NIMAntiSpamOption nimAntiSpamOption) {

            }

            @Override
            public void setClientAntiSpam(boolean b) {

            }

            @Override
            public void setForceUploadFile(boolean b) {

            }
        };

    }

}
