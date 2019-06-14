package com.oncloudsoft.sdk.entity.approval;

import com.oncloudsoft.sdk.entity.BaseEntity;

/**
 * 作者 黄继栋
 * 创建时间 2019/1/31 11:48
 * 描述 线索提供数据对象
 */

public class ClueProvideData extends BaseEntity {
    private long timestamp;//	创建时间戳
    private String createTime;//	创建时间 yyyy-MM-dd HH:mm:ss.S
    private int shzt;//审核状态 0 待审核 1 通过 2 拒绝
    private String dsrmc;
    private String cclx;// 财产类型 0房屋 1车辆 2其他
    private long id;//id


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getShzt() {
        return shzt;
    }

    public void setShzt(int shzt) {
        this.shzt = shzt;
    }

    public String getDsrmc() {
        return dsrmc;
    }

    public void setDsrmc(String dsrmc) {
        this.dsrmc = dsrmc;
    }

    public String getCclx() {
        return cclx;
    }

    public void setCclx(String cclx) {
        this.cclx = cclx;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static String getClueProvideName(String type) {//财产类型  	 0 房屋 1 车辆 2 其他
        String title = "未知";
        switch (type) {
            case "0"://房产
                title = "房产";
                break;
            case "1"://车辆
                title = "车辆";

                break;
            case "2"://其他
                title = "其他";

                break;
        }
        return title;
    }

}
