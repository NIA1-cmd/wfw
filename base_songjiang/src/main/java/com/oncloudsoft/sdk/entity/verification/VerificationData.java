/**
  * Copyright 2019 bejson.com 
  */
package com.oncloudsoft.sdk.entity.verification;
import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2019-01-31 11:33:47
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class VerificationData {
    private String shbz;
    private String cclx;//	财产类型 0 房屋 1 车辆 2 其他
    private String fwwz;//	房屋位置
    private Date createTime;//	创建时间 yyyy-MM-dd HH:mm:ss.S
    private String ajbs;//	案件标识
    private String cph;//	车牌号
    private int shzt;//	审核状态 0 待审核 1 通过 2 拒绝
    private int id;
    private String clwz;//	车辆位置
    private List<String> wjList;//	图片文件列表
    private List<String> shwjList;//	法官填写的图片
    private String fwlx;//房屋类型
    private String xsms;//	线索描述 cclx为其他时存在
    private String dsrmc;//	申请人名称
    private String fwmj;//	房屋面积
    private String cllx;//	车辆类型
    private String syqr;//所有权人
    private long timestamp;//	创建时间戳

    public String getShbz() {
        return shbz;
    }

    public String getSyqr() {
        return syqr;
    }

    public void setSyqr(String syqr) {
        this.syqr = syqr;
    }

    public void setShbz(String shbz) {
        this.shbz = shbz;
    }

    public void setCclx(String cclx) {
         this.cclx = cclx;
     }
     public String getCclx() {
         return cclx;
     }

    public void setFwwz(String fwwz) {
         this.fwwz = fwwz;
     }
     public String getFwwz() {
         return fwwz;
     }

    public void setCreateTime(Date createTime) {
         this.createTime = createTime;
     }
     public Date getCreateTime() {
         return createTime;
     }

    public void setAjbs(String ajbs) {
         this.ajbs = ajbs;
     }
     public String getAjbs() {
         return ajbs;
     }

    public void setCph(String cph) {
         this.cph = cph;
     }
     public String getCph() {
         return cph;
     }

    public void setShzt(int shzt) {
         this.shzt = shzt;
     }
     public int getShzt() {
         return shzt;
     }

    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public List<String> getShwjList() {
        return shwjList;
    }

    public void setShwjList(List<String> shwjList) {
        this.shwjList = shwjList;
    }

    public void setClwz(String clwz) {
         this.clwz = clwz;
     }
     public String getClwz() {
         return clwz;
     }

    public void setWjList(List<String> wjList) {
         this.wjList = wjList;
     }
     public List<String> getWjList() {
         return wjList;
     }

    public void setFwlx(String fwlx) {
         this.fwlx = fwlx;
     }
     public String getFwlx() {
         return fwlx;
     }

    public void setXsms(String xsms) {
         this.xsms = xsms;
     }
     public String getXsms() {
         return xsms;
     }

    public void setDsrmc(String dsrmc) {
         this.dsrmc = dsrmc;
     }
     public String getDsrmc() {
         return dsrmc;
     }

    public void setFwmj(String fwmj) {
         this.fwmj = fwmj;
     }
     public String getFwmj() {
         return fwmj;
     }

    public void setCllx(String cllx) {
         this.cllx = cllx;
     }
     public String getCllx() {
         return cllx;
     }

    public void setTimestamp(long timestamp) {
         this.timestamp = timestamp;
     }
     public long getTimestamp() {
         return timestamp;
     }

}