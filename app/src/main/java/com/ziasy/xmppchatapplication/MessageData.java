package com.ziasy.xmppchatapplication;

public class MessageData {
    String heading;
    String message;
    String type;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public MessageData(String heading, String message, String type) {
        this.heading = heading;
        this.message = message;
        this.type = type;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}