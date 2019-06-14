package com.oncloudsoft.sdk.entity;

import java.util.List;

public class CaseDetailData extends BaseEntity {
    private String zxbd;//执行标的  "":"20764.88",

    private FilingInfo la;//立案
    private BasisExecution zxyj;//执行依据
    private ExecutiveBody zxzt;//执行主体
    private Cpws cpws;

    public Cpws getCpws() {
        return cpws;
    }

    public void setCpws(Cpws cpws) {
        this.cpws = cpws;
    }


    public String getZxbd() {
        return zxbd;
    }

    public void setZxbd(String zxbd) {
        this.zxbd = zxbd;
    }

    public FilingInfo getLa() {
        return la;
    }

    public void setLa(FilingInfo la) {
        this.la = la;
    }

    public BasisExecution getZxyj() {
        return zxyj;
    }

    public void setZxyj(BasisExecution zxyj) {
        this.zxyj = zxyj;
    }

    public ExecutiveBody getZxzt() {
        return zxzt;
    }

    public void setZxzt(ExecutiveBody zxzt) {
        this.zxzt = zxzt;
    }
    public class Cpws{
        private  String url;
        private  String wjmc;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWjmc() {
            return wjmc;
        }

        public void setWjmc(String wjmc) {
            this.wjmc = wjmc;
        }
    }
    public class FilingInfo {
        private String laay;//   案由 Caseorigin

        private String larq;//立案日期

        private String ajly;//立案来源
        private String jarq;//结案日期
        private String ajlb;// 0 不动 4 原案申请人/ 异议人（被执行人）

        public String getAjlb() {
            return ajlb;
        }

        public void setAjlb(String ajlb) {
            this.ajlb = ajlb;
        }

        public String getJarq() {
            return jarq;
        }

        public void setJarq(String jarq) {
            this.jarq = jarq;
        }

        public String getLaay() {
            return laay;
        }

        public void setLaay(String laay) {
            this.laay = laay;
        }

        public String getLarq() {
            return larq;
        }

        public void setLarq(String larq) {
            this.larq = larq;
        }

        public String getAjly() {
            return ajly;
        }

        public void setAjly(String ajly) {
            this.ajly = ajly;
        }
    }


    public class BasisExecution {
        private String zxyjlx;//执行依据类型
        private String zxyjah;//执行依据文号
        private String zxyjdw;//执行依据单位
        private String zxyjwh;// 执行依据文号

        public String getZxyjwh() {
            return zxyjwh;
        }

        public void setZxyjwh(String zxyjwh) {
            this.zxyjwh = zxyjwh;
        }

        public String getZxyjlx() {
            return zxyjlx;
        }

        public void setZxyjlx(String zxyjlx) {
            this.zxyjlx = zxyjlx;
        }

        public String getZxyjah() {
            return zxyjah;
        }

        public void setZxyjah(String zxyjah) {
            this.zxyjah = zxyjah;
        }

        public String getZxyjdw() {
            return zxyjdw;
        }

        public void setZxyjdw(String zxyjdw) {
            this.zxyjdw = zxyjdw;
        }
    }

    public class ExecutiveBody {
        private List<ExecutivePersonal> sqrList;//申请人列表
        private List<ExecutivePersonal> bzxrList;//被执行人列表
        private List<ExecutivePersonal> ygList;//原告列表
        private List<ExecutivePersonal> bgList;//被告列表

        public List<ExecutivePersonal> getYgList() {
            return ygList;
        }

        public void setYgList(List<ExecutivePersonal> ygList) {
            this.ygList = ygList;
        }

        public List<ExecutivePersonal> getBgList() {
            return bgList;
        }

        public void setBgList(List<ExecutivePersonal> bgList) {
            this.bgList = bgList;
        }

        public List<ExecutivePersonal> getSqrList() {
            return sqrList;
        }

        public void setSqrList(List<ExecutivePersonal> sqrList) {
            this.sqrList = sqrList;
        }

        public List<ExecutivePersonal> getBzxrList() {
            return bzxrList;
        }

        public void setBzxrList(List<ExecutivePersonal> bzxrList) {
            this.bzxrList = bzxrList;
        }
    }


    public  static class ExecutivePersonal {
        private String dsrmc;//当事人姓名
        private int id;
        private String yx_accid;//云信账号

        private String dsrdz;//当事人地址

        private String dsrlx;//当事人类型

        private String dsrbs;//当事人标识

        private String yx_token;//云信密码

        private String dsrsfz;//当事人身份证

        private String dsrdh;//当事人电话


        public String getDsrlx() {
            return dsrlx;
        }

        public void setDsrlx(String dsrlx) {
            this.dsrlx = dsrlx;
        }

        public String getDsrmc() {
            return dsrmc;
        }

        public void setDsrmc(String dsrmc) {
            this.dsrmc = dsrmc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getYx_accid() {
            return yx_accid;
        }

        public void setYx_accid(String yx_accid) {
            this.yx_accid = yx_accid;
        }

        public String getDsrdz() {
            return dsrdz;
        }

        public void setDsrdz(String dsrdz) {
            this.dsrdz = dsrdz;
        }

        public String getDsrbs() {
            return dsrbs;
        }

        public void setDsrbs(String dsrbs) {
            this.dsrbs = dsrbs;
        }

        public String getYx_token() {
            return yx_token;
        }

        public void setYx_token(String yx_token) {
            this.yx_token = yx_token;
        }

        public String getDsrsfz() {
            return dsrsfz;
        }

        public void setDsrsfz(String dsrsfz) {
            this.dsrsfz = dsrsfz;
        }

        public String getDsrdh() {
            return dsrdh;
        }

        public void setDsrdh(String dsrdh) {
            this.dsrdh = dsrdh;
        }
    }
}
