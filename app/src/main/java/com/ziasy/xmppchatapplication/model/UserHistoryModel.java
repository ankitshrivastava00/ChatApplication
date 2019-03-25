package com.ziasy.xmppchatapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserHistoryModel {
    @SerializedName("result")
    public ArrayList<DataModel> list = new ArrayList();

    public class DataModel {
        @SerializedName("FROM")
        public String FROM;

        public DataModel(String FROM, String body) {
            this.FROM = FROM;
            this.body = body;
        }

        public String getFROM() {

            return FROM;
        }

        public void setFROM(String FROM) {
            this.FROM = FROM;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        @SerializedName("body")
        public String body;
    }
}
