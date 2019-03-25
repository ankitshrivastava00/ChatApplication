package com.ziasy.xmppchatapplication.localDB.model;

public class Chat_Modal {
    private String send_id,rcv_id,datetime,message,isread,deliver,type,uid;

    int id;

    public int getId() {
        return id;
    }

    public String getRcv_id() {
        return rcv_id;
    }

    public String getSend_id() {
        return send_id;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getDeliver() {
        return deliver;
    }

    public String getIsread() {
        return isread;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getUid() {
        return uid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRcv_id(String rcv_id) {
        this.rcv_id = rcv_id;
    }

    public void setSend_id(String send_id) {
        this.send_id = send_id;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
