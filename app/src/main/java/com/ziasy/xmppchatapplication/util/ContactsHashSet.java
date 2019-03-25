package com.ziasy.xmppchatapplication.util;

/**
 * Created by suheb on 28/4/16.
 */
public class ContactsHashSet {
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

    ContactsHashSet(String name,String number){
        this.name=name;
        this.number=number;
    }
}
