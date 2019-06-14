package com.oncloudsoft.sdk.entity;

import java.util.List;

/**
 * 文件描述： 网络查控
 * 作者：黄继栋
 * 创建时间：2019/4/30
 */
public class NetCheckControlData {


    private List<NetCheckControl> newList;
    private List<NetCheckControl> history;
    private Content tsjl;//推动记录

    public Content getTsjl() {
        return tsjl;
    }

    public void setTsjl(Content tsjl) {
        this.tsjl = tsjl;
    }

    public List<NetCheckControl> getNewList() {
        return newList;
    }

    public void setNewList(List<NetCheckControl> newList) {
        this.newList = newList;
    }

    public List<NetCheckControl> getHistory() {
        return history;
    }

    public void setHistory(List<NetCheckControl> history) {
        this.history = history;
    }

    public static class NetCheckControl{
        private String xzdw;
        private String fkjg;

        public String getXzdw() {
            return xzdw;
        }

        public void setXzdw(String xzdw) {
            this.xzdw = xzdw;
        }

        public String getFkjg() {
            return fkjg;
        }

        public void setFkjg(String fkjg) {
            this.fkjg = fkjg;
        }
    }
    public static class Content{


        private String bzxrmc;//被执行人
        private int cheLiang;//车辆
        private double cunKuan;//存款
        private int fangChan;//房产
        private String larq;//立案日期
        private String tssj;//推送日期


        public String getBzxrmc() {
            return bzxrmc;
        }

        public void setBzxrmc(String bzxrmc) {
            this.bzxrmc = bzxrmc;
        }

        public int getCheLiang() {
            return cheLiang;
        }

        public void setCheLiang(int cheLiang) {
            this.cheLiang = cheLiang;
        }

        public double getCunKuan() {
            return cunKuan;
        }

        public void setCunKuan(double cunKuan) {
            this.cunKuan = cunKuan;
        }

        public int getFangChan() {
            return fangChan;
        }

        public void setFangChan(int fangChan) {
            this.fangChan = fangChan;
        }

        public String getLarq() {
            return larq;
        }

        public void setLarq(String larq) {
            this.larq = larq;
        }

        public String getTssj() {
            return tssj;
        }

        public void setTssj(String tssj) {
            this.tssj = tssj;
        }
    }
}
