package com.ziasy.xmppchatapplication.model;

public class ContactModel {
    private String name;
    private String mobile;

    public ContactModel(String name, String mobile, String image) {
        this.name = name;
        this.mobile = mobile;
        this.image = image;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;
}
