package com.oncloudsoft.sdk.entity;

import java.io.Serializable;

public class StageData extends BaseEntity implements Serializable {

    private String jdmc;
    private String id;
    private boolean isCheck;
    private String jdbs;

    public String getJdbs() {
        return jdbs;
    }

    public void setJdbs(String jdbs) {
        this.jdbs = jdbs;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }


    @Override
    public String toString() {
        return "StageData{" +
                "jdmc='" + jdmc + '\'' +
                ", id=" + id +
                ", isCheck=" + isCheck +
                '}';
    }


    public static String stageName(String stageId) {
        String stageName;
        switch (stageId) {
            case "-1":
                stageName = "不关联";
                break;
            case "13":
                stageName = "调查";
                break;
            case "14":
                stageName = "搜查";
                break;
            case "17":
                stageName = "查封";
                break;
            case "18":
                stageName = "扣押";
                break;
            case "19":
                stageName = "评估";
                break;
            default:
                stageName = "未知";

                break;
        }
        return stageName;
    }

    public static String stageId(String stageName) {
        String stageId = "";


        if (stageName.equals("不关联") || stageName.equals("未关联")) {
            return "-1";
        }
        switch (stageName) {
            case "调查":
                stageId = "13";
                break;
            case "搜查":
                stageId = "14";
                break;
            case "查封":
                stageId = "17";
                break;
            case "扣押":
                stageId = "18";
                break;
            case "评估":
                stageId = "19";
                break;

        }
        return stageId;
    }

}
