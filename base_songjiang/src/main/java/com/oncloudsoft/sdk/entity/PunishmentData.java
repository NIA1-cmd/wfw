package com.oncloudsoft.sdk.entity;

import java.util.List;

/**
 * 作者 黄继栋
 * 创建时间 2019/1/23 16:09
 * 描述  惩戒中的数据对象
 */

public class PunishmentData extends BaseEntity {
    private String cjlx;
    private long id;
    private String sqzt;
    private String bzxr;
    private String sqrmc;
    private String timestamp;


    public static class ItemPunishmentData {
        private long id;

        private List<String> bzxrInfo;
        private List<String> sqrInfo;


        public List<String> getBzxrInfo() {
            return bzxrInfo;
        }

        public void setBzxrInfo(List<String> bzxrInfo) {
            this.bzxrInfo = bzxrInfo;
        }

        public List<String> getSqrInfo() {
            return sqrInfo;
        }

        public void setSqrInfo(List<String> sqrInfo) {
            this.sqrInfo = sqrInfo;
        }

        private String createTime;//	申请时间
        private String bzxr;//被执行人
        private String sqsx;//申请事项
        private String cjlx;//	惩戒类型 0 纳入失信被执行人名单 1 限制高消费 2 限制出境
        private String sqzt;//状态 0 待审核 1 通过 2 拒绝
        private String ajbs;//案件标识
        private String sqrmc;//申请人名称
        private String ssyly;//	事实与理由
        private String ah;//	案号
        private String jjsy;//	拒绝理由

        public String getJjsy() {
            return jjsy;
        }

        public void setJjsy(String jjsy) {
            this.jjsy = jjsy;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getBzxr() {
            return bzxr;
        }

        public void setBzxr(String bzxr) {
            this.bzxr = bzxr;
        }

        public String getSqsx() {
            return sqsx;
        }

        public void setSqsx(String sqsx) {
            this.sqsx = sqsx;
        }

        public String getCjlx() {
            return cjlx;
        }

        public void setCjlx(String cjlx) {
            this.cjlx = cjlx;
        }

        public String getSqzt() {
            return sqzt;
        }

        public void setSqzt(String sqzt) {
            this.sqzt = sqzt;
        }

        public String getAjbs() {
            return ajbs;
        }

        public void setAjbs(String ajbs) {
            this.ajbs = ajbs;
        }

        public String getSqrmc() {
            return sqrmc;
        }

        public void setSqrmc(String sqrmc) {
            this.sqrmc = sqrmc;
        }

        public String getSsyly() {
            return ssyly;
        }

        public void setSsyly(String ssyly) {
            this.ssyly = ssyly;
        }

        public String getAh() {
            return ah;
        }

        public void setAh(String ah) {
            this.ah = ah;
        }
    }

    public String getCjlx() {
        return cjlx;
    }

    public void setCjlx(String cjlx) {
        this.cjlx = cjlx;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSqzt() {
        return sqzt;
    }

    public void setSqzt(String sqzt) {
        this.sqzt = sqzt;
    }

    public String getBzxr() {
        return bzxr;
    }

    public void setBzxr(String bzxr) {
        this.bzxr = bzxr;
    }

    public String getSqrmc() {
        return sqrmc;
    }

    public void setSqrmc(String sqrmc) {
        this.sqrmc = sqrmc;
    }

    public static String getPunishName(String type) {//惩戒类型  0 纳入失信被执行人名单 1 限制高消费 2 限制出境
        String title = "未知";
        switch (type) {
            case "0"://纳入失信被执行人名单
                title = "纳入失信被执行人名单申请书";
                break;
            case "1"://限制高消费
                title = "限制高消费申请书";

                break;
            case "2"://限制出境
                title = "限制出境申请书";

                break;
        }
        return title;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
