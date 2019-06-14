package com.oncloudsoft.sdk.entity.approval;

import com.oncloudsoft.sdk.entity.BaseEntity;

import java.util.List;

public class AgentData extends BaseEntity {

    private int totalRow;
    private int pageNumber;
    private boolean firstPage;
    private boolean lastPage;
    private int totalPage;
    private int pageSize;
    private List<CaseAgentData> list;

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<CaseAgentData> getList() {
        return list;
    }

    public void setList(List<CaseAgentData> list) {
        this.list = list;
    }

    public static class CaseAgentData extends BaseEntity {

        private String dlrmc;
        private String createTime;
        private String ah;
        private int id;
        private String dlzt;//代理状态//	代理状态 0 待审批 1 已通过 3 已拒绝

        public String getDlzt() {
            return dlzt;
        }

        public void setDlzt(String dlzt) {
            this.dlzt = dlzt;
        }

        public String getDlrmc() {
            return dlrmc;
        }

        public void setDlrmc(String dlrmc) {
            this.dlrmc = dlrmc;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAh() {
            return ah;
        }

        public void setAh(String ah) {
            this.ah = ah;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }


        //        "":"赵可",
//                "":"2018-12-14 20:16:00",
//                "":"（2018）沪0117执保2223号",
//                "id":10
//
//
//        private String dsrbs;
//        private String create_time;
//        private String dwmc;
//        private String ah;
//        private String lxfs;
//        private String dsrsfzh;
//        private String zxztmc;
//        private String dlzt;
//        private String dlrsfz;
//        private String fybs;
//        private String ajbs;
//        private int id;
//        private String dllx;
//        private String dsrssdw;
//
//        public String getDsrbs() {
//            return dsrbs;
//        }
//
//        public void setDsrbs(String dsrbs) {
//            this.dsrbs = dsrbs;
//        }
//
//        public String getCreate_time() {
//            return create_time;
//        }
//
//        public void setCreate_time(String create_time) {
//            this.create_time = create_time;
//        }
//
//        public String getDwmc() {
//            return dwmc;
//        }
//
//        public void setDwmc(String dwmc) {
//            this.dwmc = dwmc;
//        }
//
//        public String getAh() {
//            return ah;
//        }
//
//        public void setAh(String ah) {
//            this.ah = ah;
//        }
//
//        public String getLxfs() {
//            return lxfs;
//        }
//
//        public void setLxfs(String lxfs) {
//            this.lxfs = lxfs;
//        }
//
//        public String getDsrsfzh() {
//            return dsrsfzh;
//        }
//
//        public void setDsrsfzh(String dsrsfzh) {
//            this.dsrsfzh = dsrsfzh;
//        }
//
//        public String getZxztmc() {
//            return zxztmc;
//        }
//
//        public void setZxztmc(String zxztmc) {
//            this.zxztmc = zxztmc;
//        }
//
//        public String getDlzt() {
//            return dlzt;
//        }
//
//        public void setDlzt(String dlzt) {
//            this.dlzt = dlzt;
//        }
//
//        public String getDlrsfz() {
//            return dlrsfz;
//        }
//
//        public void setDlrsfz(String dlrsfz) {
//            this.dlrsfz = dlrsfz;
//        }
//
//        public String getFybs() {
//            return fybs;
//        }
//
//        public void setFybs(String fybs) {
//            this.fybs = fybs;
//        }
//
//        public String getAjbs() {
//            return ajbs;
//        }
//
//        public void setAjbs(String ajbs) {
//            this.ajbs = ajbs;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getDllx() {
//            return dllx;
//        }
//
//        public void setDllx(String dllx) {
//            this.dllx = dllx;
//        }
//
//        public String getDsrssdw() {
//            return dsrssdw;
//        }
//
//        public void setDsrssdw(String dsrssdw) {
//            this.dsrssdw = dsrssdw;
//        }
    }

    public static class AgentDetailData extends BaseEntity {
        private String dwmc;
        private String createTime;
        private String zxztmc;
        private String ajbs;
        private int id;
        private String fybs;
        private String dlzt;
        private String dsrbs;
        private String dsrsfzh;
        private String dsrssdw;
        private String dlrsfz;
        private String lxfs;
        private String dlrmc;
        private String dllx;
        private String ah;


        public String getDwmc() {
            return dwmc;
        }

        public void setDwmc(String dwmc) {
            this.dwmc = dwmc;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getZxztmc() {
            return zxztmc;
        }

        public void setZxztmc(String zxztmc) {
            this.zxztmc = zxztmc;
        }

        public String getAjbs() {
            return ajbs;
        }

        public void setAjbs(String ajbs) {
            this.ajbs = ajbs;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFybs() {
            return fybs;
        }

        public void setFybs(String fybs) {
            this.fybs = fybs;
        }

        public String getDlzt() {
            return dlzt;
        }

        public void setDlzt(String dlzt) {
            this.dlzt = dlzt;
        }

        public String getDsrbs() {
            return dsrbs;
        }

        public void setDsrbs(String dsrbs) {
            this.dsrbs = dsrbs;
        }

        public String getDsrsfzh() {
            return dsrsfzh;
        }

        public void setDsrsfzh(String dsrsfzh) {
            this.dsrsfzh = dsrsfzh;
        }

        public String getDsrssdw() {
            return dsrssdw;
        }

        public void setDsrssdw(String dsrssdw) {
            this.dsrssdw = dsrssdw;
        }

        public String getDlrsfz() {
            return dlrsfz;
        }

        public void setDlrsfz(String dlrsfz) {
            this.dlrsfz = dlrsfz;
        }

        public String getLxfs() {
            return lxfs;
        }

        public void setLxfs(String lxfs) {
            this.lxfs = lxfs;
        }

        public String getDlrmc() {
            return dlrmc;
        }

        public void setDlrmc(String dlrmc) {
            this.dlrmc = dlrmc;
        }

        public String getDllx() {
            return dllx;
        }

        public void setDllx(String dllx) {
            this.dllx = dllx;
        }

        public String getAh() {
            return ah;
        }

        public void setAh(String ah) {
            this.ah = ah;
        }
    }

}
