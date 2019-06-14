package com.oncloudsoft.sdk.app;


public class Global {

    public static String PATH = "path";
    public static String START = "start";
    public static String END = "end";

    public static String TOKEN = "token";
    public static String PHONE = "phone";
    public static String ID = "id";//通用id
    public static String PALYVOICE_ID = "palyvoice_id";//播放录音时的id
    public static String PRESSED_TYPE = "pressed_type";//摁下类型


    public static String FGZW = "fgzw";
    public static String FYID = "fyid";
    public static String FYMC = "fymc";
    public static String FGMC = "fgmc";


    public static String YUNXIN_ACCONT = "yx_account";
    public static String YUNXIN_PASSWORD = "yx_password";
    public static String URL = "url";
    public static String TITLE = "title";
    public static String PDF_ID = "pdf_Id";//和解协议中pdf的id
    public static String SEC = "sec";
    public static String IMAGES = "images";
    public static String TYPE = "type";
    public static String CONTENT = "content";
    public static String INTENTTYPE = "intenttype"; //进入群成员列表类型  0 会话界面进入,点击item进入私聊   1搜索页面进入,点击item进入消息搜索
    public static String CASEID = "caseid";
    public static String ACCID = "accid";//用户云信账号
    public static String MESSAGE_TYPE = "messagetype";
    public static String VOICE_QUERY = "emotiontype";//录音快查 0 电话通话记录 1 录音采集记录

    public static String AJFL = "ajfl";
    public static String AJLB = "ajlb";
    public static String NAME = "name";


    public static String SHOW_TYPE = "showtype";


    private static String URL_BASE = "https://s2.oncloudsoft.com/shwfy-web/";
//    private static String URL_BASE = "https://test.oncloudsoft.com/";

//


    //app 请求
    private static String URL_BASEREQUEST_APP = URL_BASE + "a/app/";

    //app 云信 请求
    private static String URL_BASEREQUEST_YUNXIN = URL_BASE + "a/yx/";

    //app 有关h5 的请求
    private static String URL_BASEHTML = URL_BASE + "h5/";

    //---------------------------------App有关开始-------------------------------------


    //登录接口
    public static String URL_LOGIN = URL_BASEREQUEST_APP + "user/nLogin";

    //退出登录
    public static String URL_LOGOUT = URL_BASEREQUEST_APP + "user/logout";


    //创建群聊
    public static String URL_CREATETEAM = URL_BASEREQUEST_APP + "im/createTeam";

    //获取tid
    public static String URL_GETUNREADCOUNT = URL_BASEREQUEST_APP + "im/queryTid";

    //终本约谈
    public static String URL_FINALINTERVIEW = URL_BASEREQUEST_APP + "aj/base";

    //案件详情
    public static String URL_CASEINFO = URL_BASEREQUEST_APP + "aj/base";

    //群成员列表
    public static String URL_TEAMMEMBER_LIST = URL_BASEREQUEST_APP + "aj/cy";

    //案件代理详情
    public static String URL_AGENTDETAIL_URL = URL_BASEREQUEST_APP + "aj/dlXq";

    //案件代理处理
    public static String URL_AGENTHANDLER = URL_BASEREQUEST_APP + "aj/dlSp";

    //法官列表
    public static String URL_JUDGE_LIST = URL_BASEREQUEST_APP + "fg/addXzfgList";

    //添加协助法官
    public static String URL_JUDGE_ADD = URL_BASEREQUEST_APP + "aj/teamMemAdd";

    //删除协助法官
    public static String URL_JUDGE_DELETE = URL_BASEREQUEST_APP + "aj/teamMemAtt";

    //系统消息-详情
    public static String URL_SYSTEM_DELETE = URL_BASEREQUEST_APP + "aj/xxxq";

    //案件搜索字典
    public static String URL_SEARCH_DICTIONARY = URL_BASEREQUEST_APP + "aj/ajDic";

    //案件搜索
    public static String URL_SEARCH_CASE = URL_BASEREQUEST_APP + "aj/teamSearch";

    //案件惩戒详情
    public static String URL_PUNISHMENT_DETAIL = URL_BASEREQUEST_APP + "aj/ajcjxq";

    //案件惩戒审批
    public static String URL_PUNISHMENT_HANDLER = URL_BASEREQUEST_APP + "aj/ajcjsp";

    //线索提供查询 列表
    public static String URL_CLUEPROVIDE_QUERY = URL_BASEREQUEST_APP + "aj/ajxslb";

    //线索登记-详情
    public static String URL_VERIFICATION_DETALE = URL_BASEREQUEST_APP + "aj/ajxsxq";

    //线索登记-审核
    public static String URL_VERIFICATION_VERIFY = URL_BASEREQUEST_APP + "aj/ajxssh";

    //拍卖平台选择
    public static String URL_PAIMAI = URL_BASEREQUEST_APP + "sysMsg/detail";

    //用户-个人基本信息
    public static String URL_USER_INFO = URL_BASEREQUEST_APP + "user/basicmsg";

    //和解协议-详情
    public static String URL_HEJE_DETALE = URL_BASEREQUEST_APP + "aj/hjxyxq";

    //和解协议-审批
    public static String URL_HEJE_SHENGPI = URL_BASEREQUEST_APP + "aj/hjxysp";

    //网络查控告知书
    public static String URL_NETCHAECK_CONTROL = URL_BASEREQUEST_APP + "aj/zdztsNotice";


    //---------------------------------------云信有关开始-------------------------------------------
    //按时间搜索历史消息的时间范围
    public static String URL_HISTORYMESSAGE_DATERANGE = URL_BASEREQUEST_YUNXIN + "msg/historyMsgOnsetDate";
    //查询历史消息
    public static String URL_HISTORYMESSAGE = URL_BASEREQUEST_YUNXIN + "msg/historyMsg";

    //------------------------------------------h5有关开始--------------------------------------------

    //html 路径  预览pdf
    public static String URL_PDF = URL_BASEHTML + "pdfjs/web/viewer.html";

}
