package com.ziasy.xmppchatapplication.model;

public class JsonModelForChat {
    boolean itemSelect =false;

    public boolean isItemSelect() {
        return itemSelect;
    }

    public void setItemSelect(boolean itemSelect) {
        this.itemSelect = itemSelect;
    }

    public String getPosting() {
        return posting;
    }

    public void setPosting(String posting) {
        this.posting = posting;
    }

    String message;
    String sendName;
    String recieverName;
    String date;
    String id;
    String isread;
    String response;
    String time;
   public boolean isSelect = false;
    String deliver;
    private String posting;
    String mode;
    String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String mid, data, type, lat, lang, heading, timedate;

    public String getResponse() {
        return response;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public JsonModelForChat() {

    }

    public JsonModelForChat(String id, String sendName, String recieverName
            , String timedate, String message, String isread, String deliver,
                            String type, String time, String date, String mid) {
        this.id = id;
        this.sendName = sendName;
        this.recieverName = recieverName;
        this.timedate = timedate;
        this.message = message;
        this.isread = isread;
        this.deliver = deliver;
        this.type = type;
        this.time = time;
        this.date = date;
        this.mid = mid;

    }

    public JsonModelForChat(String message, String sendName, String recieverName,
                            String date, String isread, String response, String time,
                            String deliver, String mid, String data, String type,
                            String lat, String lang, String heading, String timedate,
                            boolean isSelect) {
        this.message = message;
        this.sendName = sendName;
        this.recieverName = recieverName;
        this.date = date;
        this.isread = isread;
        this.response = response;
        this.time = time;
        this.deliver = deliver;
        this.mid = mid;
        this.data = data;
        this.type = type;
        this.lat = lat;
        this.lang = lang;
        this.heading = heading;
        this.isSelect = isSelect;
        this.timedate = timedate;
    }

    @Override
    public String toString() {
        return "\n"+"mid :"+id+"\n"+"Name :"+ sendName+" \n"+"recieverName : "+recieverName+" \n"+
                "timedate :"+timedate+" \n"+" message : "+message+"\n"+ "isread : "+isread+"\n"+"deliver : "+deliver
                +"\n"+"type : "+type+"\n"+ "time : "+time+"\n"+"date : "+date+"\n"+" UID : "+mid;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getIsread() {
        return isread;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }

    public String getDeliver() {
        return deliver;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getRecieverName() {
        return recieverName;
    }

    public void setRecieverName(String recieverName) {
        this.recieverName = recieverName;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

        public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimedate() {
        return timedate;
    }

    public void setTimedate(String timedate) {
        this.timedate = timedate;
    }

    public String getData() {

        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
