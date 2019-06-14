package com.oncloudsoft.sdk.entity;

/**
 * 作者 caomingduo
 * 创建时间 2019/3/5 13:52
 * 描述 用户-个人基本信息 http://39.105.45.3:8602/web/#/2?page_id=1784
 */

public class UserInfoData {
    private Dsr dsr;

    public void setDsr(Dsr dsr) {
        this.dsr = dsr;
    }

    public Dsr getDsr() {
        return dsr;
    }

    private Fg fg;

    public void setFg(Fg fg) {
        this.fg = fg;
    }

    public Fg getFg() {
        return fg;
    }

    public class Dsr {

        private String dsrdh;
        private String dsrlx;
        private String dsrsfz;
        private String dsrdz;
        private String dsrmc;
        private String ssdwbs;
        private String dsrbs;
        private String ssdw;

        public String getDsrmc() {
            return dsrmc;
        }

        public void setDsrmc(String dsrmc) {
            this.dsrmc = dsrmc;
        }

        public String getSsdwbs() {
            return ssdwbs;
        }

        public void setSsdwbs(String ssdwbs) {
            this.ssdwbs = ssdwbs;
        }

        public String getDsrbs() {
            return dsrbs;
        }

        public void setDsrbs(String dsrbs) {
            this.dsrbs = dsrbs;
        }

        public String getSsdw() {
            return ssdw;
        }

        public void setSsdw(String ssdw) {
            this.ssdw = ssdw;
        }

        public void setDsrdh(String dsrdh) {
            this.dsrdh = dsrdh;
        }

        public String getDsrdh() {
            return dsrdh;
        }

        public void setDsrlx(String dsrlx) {
            this.dsrlx = dsrlx;
        }

        public String getDsrlx() {
            return dsrlx;
        }

        public void setDsrsfz(String dsrsfz) {
            this.dsrsfz = dsrsfz;
        }

        public String getDsrsfz() {
            return dsrsfz;
        }

        public void setDsrdz(String dsrdz) {
            this.dsrdz = dsrdz;
        }

        public String getDsrdz() {
            return dsrdz;
        }
    }

    public class Fg {

        private String fymc;
        private String zw;
        private String fgbs;
        private String fgmc;

        public String getFgmc() {
            return fgmc;
        }

        public void setFgmc(String fgmc) {
            this.fgmc = fgmc;
        }

        public String getFgbs() {
            return fgbs;
        }

        public void setFgbs(String fgbs) {
            this.fgbs = fgbs;
        }

        public void setFymc(String fymc) {
            this.fymc = fymc;
        }

        public String getFymc() {
            return fymc;
        }

        public void setZw(String zw) {
            this.zw = zw;
        }

        public String getZw() {
            return zw;
        }

    }
}
