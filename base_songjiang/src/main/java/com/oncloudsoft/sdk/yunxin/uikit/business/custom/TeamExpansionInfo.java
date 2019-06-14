package com.oncloudsoft.sdk.yunxin.uikit.business.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.alibaba.fastjson.JSON;
import com.netease.nimlib.sdk.team.model.Team;
import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.utils.MyLog;
import com.oncloudsoft.sdk.yunxin.uikit.impl.cache.TeamDataCache;

import java.io.Serializable;
import java.util.List;

import static com.oncloudsoft.sdk.yunxin.uikit.business.custom.TeamExpansionInfo.CaseType.yijie;
import static com.oncloudsoft.sdk.yunxin.uikit.business.custom.TeamExpansionInfo.CaseType.zaizhi;
import static com.oncloudsoft.sdk.yunxin.uikit.business.custom.TeamExpansionInfo.CaseType.zhongben;

public class TeamExpansionInfo {

    public static class TeamExpansionData implements Serializable {
        private String ajbs;//案件标识
        private String sqr;//申请人
        private String bzxr;//被执行人
        private String ajlb;//案件类别
        private List<String> sqrid;//所有申请人id
        private List<String> bzxrid;//所有被执行人id
        //	执结方式 1 不予执行 2 驳回申请 3 执行完毕 4 终结执行 5 终结本次执行程序 6 销案 7 不予立案（原不予受理） 8 不予登记立案 （该字段不存在按照0处理 0 表示在执）
        //0 表示在执 5表示终本 其他数字都表示已结
        private int zjfs;

        public int getZjfs() {
            return zjfs == -1 ? 0 : zjfs;
        }

        public void setZjfs(int zjfs) {
            this.zjfs = zjfs;
        }

        public String getAjlb() {
            return ajlb;
        }

        public void setAjlb(String ajlb) {
            this.ajlb = ajlb;
        }

        public String getAjbs() {
            return ajbs;
        }

        public void setAjbs(String ajbs) {
            this.ajbs = ajbs;
        }

        public String getSqr() {
            return sqr;
        }

        public void setSqr(String sqr) {
            this.sqr = sqr;
        }

        public String getBzxr() {
            return bzxr;
        }

        public void setBzxr(String bzxr) {
            this.bzxr = bzxr;
        }

        public List<String> getSqrid() {
            return sqrid;
        }

        public void setSqrid(List<String> sqrid) {
            this.sqrid = sqrid;
        }

        public List<String> getBzxrid() {
            return bzxrid;
        }

        public void setBzxrid(List<String> bzxrid) {
            this.bzxrid = bzxrid;
        }
    }


    /**
     * 扩展字段的json
     *
     * @param json
     * @return
     */
    public static TeamExpansionData getExpansionInfo(String json) {
        return JSON.parseObject(json, TeamExpansionData.class);
    }

    public enum CaseType {
        zaizhi, zhongben, yijie
    }

    /**
     * 获取某个群的案件类型
     * @param teamId
     * @return
     */
    public static CaseType getCaseType(String teamId) {
        CaseType caseType;
        Team team = TeamDataCache.getInstance().getTeamById(teamId);
        String extServer = team.getExtServer();
        MyLog.d("extServer",extServer);
        TeamExpansionInfo.TeamExpansionData expansionInfo = TeamExpansionInfo.getExpansionInfo(extServer);
        int zjfs = expansionInfo.getZjfs();

        switch (zjfs) {
            case 0://在执
                caseType = zaizhi;
                MyLog.d("在执案件");

                break;
            case 5://终本
                caseType = zhongben;
                MyLog.d("终本案件");

                break;
            default://已结
                caseType = yijie;
                MyLog.d("已结案件");
                break;

        }
        return caseType;
    }


    /**
     * h获取当事人角色
     * 成员角色    0 法官     1 申请人       2 被执行人         3 代理人(申)          4 代理人(被)
     *
     * @return
     */
    public static String getPeopleCharacter(String type) {
        String name = "未知角色";

        if (type == null) {
            return name;
        }

        switch (type) {
            case "0"://申请人
                name = "申请人";
                break;
            case "1"://被执行人
                name = "被执行人";
                break;
            case "2"://代理人 申
                name = "申请人(代)";
                break;
            case "3"://代理人 被
                name = "被执行人(代)";
                break;
            case "4"://原案申请人
                name = "原案申请人";
                break;
            case "5"://异议人（被执行人）
                name = "异议人（被执行人）";
                break;
            case "6"://异议人（申请人）
                name = "异议人（申请人）";
                break;
            case "200"://原告
                name = "原告";
                break;
            case "201"://被告
                name = "被告";
                break;

        }
        return name;
    }

    /**
     * h获取当事人角色
     * 成员角色    0 法官     1 申请人       2 被执行人         3 代理人(申)          4 代理人(被)
     *
     * @return
     */
    public static String getPeopleType(String s) {
        String type = "";

        if (s == null) {
            return type;
        }

        switch (s) {
            case "全部"://全部
                type = "";
                break;
            case "申请人"://申请人
                type = "0";
                break;
            case "被执行人"://被执行人
                type = "1";
                break;
            case "代理申请人"://代理人 申
                type = "2";
                break;
            case "代理被执行人"://代理人 被
                type = "3";
                break;


        }
        return type;
    }

    /**
     * h获取当事人头像
     *
     * @return
     */
    public static Bitmap getPeopleAvatar(Context context, String type) {
        Bitmap bitmap;
        int drawable;
        drawable = R.drawable.nim_avatar_default;
        if (type == null) {
            drawable = R.drawable.nim_avatar_default;
        } else {
            switch (type) {
                case "0"://申请人
                    drawable = R.drawable.icon_team_sqr;
                    break;
                case "1"://被执行人
                    drawable = R.drawable.icon_team_bzxr;
                    break;
                case "2"://代理人 申
                    drawable = R.drawable.icon_team_sqr;
                    break;
                case "3"://代理人 被
                    drawable = R.drawable.icon_team_bzxr;
                    break;

            }
        }
        bitmap = BitmapFactory.decodeResource(context.getResources(), drawable);
        return bitmap;
    }


}
