package com.ziasy.xmppchatapplication.group_chat.model;

public class GroupUserModel {
    String id,name,num,photo,device;

    public GroupUserModel(String id, String name, String num, String photo, String device) {
        this.id = id;
        this.name = name;
        this.num = num;
        this.photo = photo;
        this.device = device;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
