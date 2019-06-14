package com.oncloudsoft.sdk.entity;

/**
 * 作者 Mingduo Cao
 * 创建时间 2019/6/11 16:48
 * <p>
 * 描述 咨询回复
 */

public class ConsultingReplyData {
    private String dsr = "";
    private String dsrtime="";
    private String dsrMessage = "";
    private String fg = "";
    private String fgtime = "";
    private String fgMessage;


    public String getDsr() {
        return dsr;
    }

    public void setDsr(String dsr) {
        this.dsr = dsr;
    }

    public String getDsrtime() {
        return dsrtime;
    }

    public void setDsrtime(String dsrtime) {
        this.dsrtime = dsrtime;
    }

    public String getDsrMessage() {
        return dsrMessage;
    }

    public void setDsrMessage(String dsrmessage) {
        this.dsrMessage = dsrmessage;
    }

    public String getFg() {
        return fg;
    }

    public void setFg(String fg) {
        this.fg = fg;
    }

    public String getFgtime() {
        return fgtime;
    }

    public void setFgtime(String fgtime) {
        this.fgtime = fgtime;
    }

    public String getFgMessage() {
        return fgMessage;
    }

    public void setFgMessage(String fgMessage) {
        this.fgMessage = fgMessage;
    }
}
