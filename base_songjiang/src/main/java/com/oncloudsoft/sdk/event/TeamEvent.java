package com.oncloudsoft.sdk.event;

/**
 * Created by Administrator on 2018/12/27.
 */

public class TeamEvent {
    String contactId,unnreadCount;
    public TeamEvent(String contactId,String unnreadCount){
        this.contactId = contactId;
        this.unnreadCount = unnreadCount;
    }
    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getUnnreadCount() {
        return unnreadCount;
    }

    public void setUnnreadCount(String unnreadCount) {
        this.unnreadCount = unnreadCount;
    }
}
