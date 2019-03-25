package com.ziasy.xmppchatapplication.util;

/**
 * Created by suheb on 31/3/16.
 */
public class ContactsModelClass {
    String name;
    String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ContactsModelClass(String name, String number) {
        this.name = name;
        this.number = number;
    }
}
