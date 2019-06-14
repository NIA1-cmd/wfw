package com.oncloudsoft.sdk.yunxin.session.extension;

/**
 * Created by zhoujianghua on 2015/4/9.
 */
public interface CustomAttachmentType {
    //    // 多端统一
//    int Guess = 1;
//    int SnapChat = 2;
    int Sticker = 3;
//    int RTS = 4;
//    int RedPacket = 5;
//    int OpenedRedPacket = 6;


    int sys_customer = 1000;//系统消息

    String sys_executive_notification = "10003";//执行通知书  样式一    (废弃)
    String sys_process_notification = "10004";//金钱给付类执行案件流程告知书  样式一  (废弃)
    String sys_property_notification = "10005";//	财产报告令  样式一  (废弃)

    String sys_onlyhouse = "11001";//系统消息 中被执行人唯一住房，如何执行  样式一
    String sys_lease = "11002";//系统消息 中被执行人房产处于租赁状态, 如何执行?  样式一


    String sys_bank = "10001";//系统消息 中总对总银行反馈  样式二
    String sys_car = "10002";//系统消息 中总对总车辆反馈  样式二


    String sys_applypeople_enterchat = "10008";//申请人进群提醒  提醒


    String sys_message_01 = "12001";//总对总发起后            样式三  （废弃）
    String sys_message_02 = "12002";//线索核查后         样式三   （废弃）
    String sys_message_03 = "12003";//查控结果无财产           样式三  （废弃）
    String sys_message_04 = "12004";//冻结被执行人财产后         样式三   （废弃）
    String sys_message_05 = "12005";//查封不动产后            样式三   （废弃）
    String sys_message_06 = "12006";//查封动产后         样式三  （废弃）
    String sys_message_07 = "13001";//评估开始后         样式三  （废弃）
    String sys_message_08 = "13002";//评估报告出来后           样式三  （废弃）
    String sys_message_09 = "13003";//通知申请人拍卖平台选择           样式一   （废弃）
    String sys_message_10 = "13004";//填写变卖信息后           样式三  （废弃）
    String sys_message_11 = "13005";//填写以物抵债信息后         样式三  （废弃）
    String sys_message_12 = "14001";//案款已汇入法院           样式三  （废弃）
    String sys_message_13 = "14002";//案款发放成功            样式三  （废弃）
    String sys_message_14 = "15001";//终本申请报结不足          样式三  （废弃）
    String sys_message_15 = "15002";//终本申请报结无财产         样式三  （废弃）
    String sys_message_16 = "16001";//罚款时           样式三  （废弃）
    String sys_message_17 = "16002";//拘留            样式三  （废弃）
    String sys_message_18 = "16003";//限高令           样式三  （废弃）
    String sys_message_19 = "16004";//被执行人失信            样式三  （废弃）
    String sys_message_20 = "16005";//限制出境          样式三  （废弃）


    String sys_casenodify_notification = "18001";//	执行分案修改通知 样式四  （废弃）
    String sys_savecase_notification = "18002";//	保存执行通知书  样式四  （废弃）


    String sys_service_document = "20001";//	文书送达 样式六
    String sys_punishment_lostletter = "17001";//惩戒审批  惩戒申请  样式六  全部合并为这一个
    String sys_clueprovide = "19001";//	线索登记消息 样式六
    String sys_casea_agent = "22001";//	代理人申请 样式六
    String sys_mesnotifycation_netcheck = "10007";//网络查控 样式六

    String sys_reconciliation_agreement = "21001";//	和解协议  样式七

    String sys_mesnotifycation_execute = "300000";//执行通知 样式八

    String sys_mesnotifycation_evaluationreport = "301000";//评估报告 样式八

    String sys_mesnotifycation_auctionplatform = "302000";//拍卖平台选择 样式八

    String sys_mesnotifycation_rulingonproperty = "303000";//以物抵债裁定 样式八

    String sys_mesnotifycation_casepayment = "304000";//案款发放 样式八

    String sys_mesnotifycation_insufficientamount_final = "305001";//不足额终本结案通知 样式八

    String sys_mesnotifycation_noamount_final = "305002";//无财产终本结案通知 样式八


    String sys_clue_remind = "306000";//当事人提交线索后财产线索核查提醒 样式八

    String sys_application_remind = "306001";//法官线索核查，核查结果为确认 样式八

    String sys_verification_remind = "306002";//线索核查后7日，系统自动发送消息提醒 样式八

    String sys_notification_remind = "306003";//非网络查控提醒 样式八

    String sys_allnotification_remind = "306004";//风险提示 样式八

    String sys_final_publicity = "308000";//终本公示  提示


    String sys_wlck = "307001";//网络查控  样式八

    String sys_dj = "307002";//冻结  样式八

    String sys_cf = "307003";//查封  样式八

    String sys_pg = "307004";//评估  样式八

    String sys_pgbg = "307005";//评估报告  样式八

    String sys_pmcg = "307006";//拍卖成功  样式八

    String sys_akdz = "307007";//案款到账通知  样式八


    String sys_nodeacquisition = "310000";//节点采集
    String sys_mediacollection = "310001";//多媒体采集采集
    String sys_termreminder = "310002";//期限提醒

    String sys_tip = "99999";//	  后台发送的tip消息f

}
