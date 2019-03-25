package com.ziasy.xmppchatapplication.localDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.ziasy.xmppchatapplication.ChatUserList;
import com.ziasy.xmppchatapplication.User;
import com.ziasy.xmppchatapplication.localDB.model.Chat_Modal;
import com.ziasy.xmppchatapplication.model.JsonModelForChat;

import java.util.ArrayList;

public class LocalDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION=1;
    private static final String DB_NAME= "XMPPLocalDatabase.db";
    private static final String SINGLE_CHAT_TABLE ="single_chat";
    private static final String GROUP_CHAT_TABLE="group_chat";
    private static final String CHAT_LIST_TABLE ="chat_list";
    private static final String USERS_LIST="users_list";
    private static final String COLUMN_ID="id";
    private SQLiteDatabase msqLiteDatabase;

    public LocalDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("ANIKET_TABLE","n");
       // sqLiteDatabase.execSQL("create table IF NOT EXISTS " + CHAT_DEMO_TABLE + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, send_id VARCHAR, rcv_id VARCHAR, datetime DATETIME, message TEXT, isread VARCHAR, deliver VARCHAR, type VARCHAR, uid VARCHAR);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + GROUP_CHAT_TABLE + " ( " + COLUMN_ID + " VARCHAR , send_id VARCHAR, rcv_id VARCHAR, date VARCHAR, message TEXT, isread VARCHAR, deliver VARCHAR, type VARCHAR, response VARCHAR, time VARCHAR, mid VARCHAR, lat VARCHAR, long VARCHAR, heading VARCHAR, datetime VARCHAR, is_select VARCHAR,status VARCHAR, UNIQUE(mid) );");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + SINGLE_CHAT_TABLE + " ( " + COLUMN_ID + " VARCHAR , send_id VARCHAR, rcv_id VARCHAR, date VARCHAR, message TEXT, isread VARCHAR, deliver VARCHAR, type VARCHAR, response VARCHAR, time VARCHAR, mid VARCHAR, lat VARCHAR, long VARCHAR, heading VARCHAR, datetime VARCHAR, is_select VARCHAR, UNIQUE(mid) );");
        sqLiteDatabase.execSQL( "CREATE TABLE IF NOT EXISTS " + USERS_LIST + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name" + " TEXT,"
                + "device_id" + " VARCHAR,"
                + "count" + " VARCHAR,"
                + "datetime" + " VARCHAR,"
                + "image" + " TEXT"
                + " );");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + CHAT_LIST_TABLE + " ( id VARCHAR, name VARCHAR , description TEXT,  lastMessage TEXT, datetime VARCHAR , time varchar , userstatus VARCHAR , photo TEXT , dtype VARCHAR , message TEXT , chattype VARCHAR , did VARCHAR , admin VARCHAR, UNIQUE(id) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertSingleChat(JsonModelForChat singleChatList, String rid){
        msqLiteDatabase=this.getWritableDatabase();
        onCreate(msqLiteDatabase);
        String Query = "Select * from " + SINGLE_CHAT_TABLE + " where mid = '" + singleChatList.getMid() + "'";
        Cursor cursor = msqLiteDatabase.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            msqLiteDatabase.execSQL("REPLACE INTO "+ SINGLE_CHAT_TABLE +" ( id, send_id, rcv_id, date, message, isread, deliver, type, mid, response, time, lat, long, heading, datetime, is_select "+") " +
                    "VALUES ( '" + rid + "','"+ singleChatList.getSendName()+"', '"+ singleChatList.getRecieverName() +"', '"+ singleChatList.getDate() +"' , '"+ singleChatList.getMessage() +"', '"+ singleChatList.getIsread() +"', '"+ singleChatList.getDeliver() +"', '"+ singleChatList.getType() +"'" +
                    ", '"+ singleChatList.getMid() +"', '"+ singleChatList.getResponse() +"', '"+ singleChatList.getTime() +"', '"+ singleChatList.getLat() +"', '"+ singleChatList.getLang() +"', '"+ singleChatList.getHeading() +"', '"+ singleChatList.getTimedate() +"', '"+ singleChatList.isSelect() +"');");
            cursor.close();
            Log.d("No_internet_data","inserted");
        }else{
            Log.d("No_internet_data","Already present");
        }
        msqLiteDatabase.close();
    }

    public void insertChatList(ChatUserList chatUserList){
        Log.d("No_internet_data",chatUserList.toString());
        msqLiteDatabase=this.getWritableDatabase();
        onCreate(msqLiteDatabase);
        msqLiteDatabase.execSQL("REPLACE INTO "+ CHAT_LIST_TABLE +" ( id, name , description,  lastMessage, datetime, userstatus, time , photo, dtype, message, chattype, did, admin "+") " +
                "VALUES ( '"+ chatUserList.getId()+"','"+ chatUserList.getName()+"', '"+ chatUserList.getDescription() +"', '"+ chatUserList.getLastMessage() +"' , '"+ chatUserList.getDatetime() +"', '"+ chatUserList.getIsOnline() +"', '"+ chatUserList.getTime() +"', '"+ chatUserList.getImageUrl() +"'" +
                ", '"+ chatUserList.getType() +"', '"+ chatUserList.getLastMessage() +"', '"+ chatUserList.getChattype() +"', '"+ chatUserList.getDid() +"', '"+ chatUserList.getAdmin() +"');");
        msqLiteDatabase.close();
    }

    public void insertUserList(User userList){
        msqLiteDatabase=this.getWritableDatabase();
        onCreate(msqLiteDatabase);
        msqLiteDatabase.execSQL("REPLACE INTO "+ USERS_LIST +" ( id, name , device_id,  count, datetime, image "+") " +
                "VALUES ( '"+ userList.getId()+"','"+ userList.getName()+"', '"+ userList.getDid() +"', '"+ userList.getCount() +"' , '"+ userList.getDatetime() +"', '"+ userList.getImageUrl() +"');");
        Log.d("No_internet_data","inserted");
        msqLiteDatabase.close();
    }
    public void insertGroupMessages(JsonModelForChat groupChatList, String rid){
        msqLiteDatabase=this.getWritableDatabase();
        onCreate(msqLiteDatabase);
        String Query = "Select * from " + GROUP_CHAT_TABLE + " where mid = '" + groupChatList.getMid() + "'";
        Cursor cursor = msqLiteDatabase.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            msqLiteDatabase.execSQL("REPLACE INTO "+ GROUP_CHAT_TABLE +" (id, send_id, rcv_id, date, message, isread, deliver, type, mid, response, time, lat, long, heading, datetime, is_select "+") " +
                    "VALUES ( '" + rid + "', '"+ groupChatList.getSendName()+"', '"+ groupChatList.getRecieverName() +"', '"+ groupChatList.getDate() +"' , '"+ groupChatList.getMessage() +"', '"+ groupChatList.getIsread() +"', '"+ groupChatList.getDeliver() +"', '"+ groupChatList.getType() +"'" +
                    ", '"+ groupChatList.getMid() +"', '"+ groupChatList.getResponse() +"', '"+ groupChatList.getTime() +"', '"+ groupChatList.getLat() +"', '"+ groupChatList.getLang() +"', '"+ groupChatList.getHeading() +"', '"+ groupChatList.getTimedate() +"', '"+ groupChatList.isSelect() +"');");
            cursor.close();
            Log.d("No_internet_data","inserted");
        }else{
            Log.d("No_internet_data","Already present");
        }
        msqLiteDatabase.close();
    }

    public void insertGroupMessagesOffline(JsonModelForChat groupChatList, String rid){
        msqLiteDatabase=this.getWritableDatabase();
        onCreate(msqLiteDatabase);
        String Query = "Select * from " + GROUP_CHAT_TABLE + " where mid = '" + groupChatList.getMid() + "'";
        Cursor cursor = msqLiteDatabase.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            msqLiteDatabase.execSQL("REPLACE INTO "+ GROUP_CHAT_TABLE +" (id, send_id, rcv_id, date, message, isread, deliver, type, mid, response, time, lat, long, heading, datetime, is_select, status "+") " +
                    "VALUES ( '" + rid + "', '"+ groupChatList.getSendName()+"', '"+ groupChatList.getRecieverName() +"', '"+ groupChatList.getDate() +"' , '"+ groupChatList.getMessage() +"', '"+ groupChatList.getIsread() +"', '"+ groupChatList.getDeliver() +"', '"+ groupChatList.getType() +"'" +
                    ", '"+ groupChatList.getMid() +"', '"+ groupChatList.getResponse() +"', '"+ groupChatList.getTime() +"', '"+ groupChatList.getLat() +"', '"+ groupChatList.getLang() +"', '"+ groupChatList.getHeading() +"', '"+ groupChatList.getTimedate() +"', '"+ groupChatList.isSelect() +"', '"+ "offline" +"');");
            cursor.close();
            Log.d("No_internet_data","inserted");
        }else{
            Log.d("No_internet_data","Already present");
        }
        msqLiteDatabase.close();
    }

    /*public void deleteChatSender(Chat_Modal chat_modal){
        msqLiteDatabase=this.getReadableDatabase();
        msqLiteDatabase.execSQL("DELETE FROM "+CHAT_DEMO_TABLE+ " WHERE "+COLUMN_ID + " = '" + chat_modal.getSend_id()+ "'");
        msqLiteDatabase.close();
    }*/

    /*public void deleteChatReceiver(Chat_Modal chat_modal){
        msqLiteDatabase=this.getReadableDatabase();
        msqLiteDatabase.execSQL("DELETE FROM "+CHAT_DEMO_TABLE+ " WHERE "+COLUMN_ID + " = '" + chat_modal.getRcv_id()+ "'");
        msqLiteDatabase.close();
    }*/

    /*public void deleteGroupChatReceiver(Chat_Modal chat_modal){
        msqLiteDatabase=this.getReadableDatabase();
        msqLiteDatabase.execSQL("DELETE FROM "+GROUP_CHAT_TABLE+ " WHERE "+COLUMN_ID + " = '" + chat_modal.getRcv_id()+ "'");
        msqLiteDatabase.close();
    }

    public void deleteGroupChatSender(Chat_Modal chat_modal){
        msqLiteDatabase=this.getReadableDatabase();
        msqLiteDatabase.execSQL("DELETE FROM "+GROUP_CHAT_TABLE+ " WHERE "+COLUMN_ID + " = '" + chat_modal.getSend_id()+ "'");
        msqLiteDatabase.close();
    }*/

    public ArrayList<ChatUserList> getAllChatList(){
        msqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = msqLiteDatabase.rawQuery("SELECT * FROM " + CHAT_LIST_TABLE + " ORDER BY datetime DESC ", null);
        Log.d("CURSOR",cursor.toString());
        ArrayList<ChatUserList> chats= new ArrayList<ChatUserList>();
        ChatUserList chatUserList;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                chatUserList= new ChatUserList();
                chatUserList.setId(cursor.getString(0));
                chatUserList.setName(cursor.getString(1));
                chatUserList.setDescription(cursor.getString(2));
                chatUserList.setLastMessage(cursor.getString(3));
                chatUserList.setDatetime(cursor.getString(4));
                chatUserList.setIsOnline(cursor.getString(5));
                chatUserList.setTime(cursor.getString(6));
                chatUserList.setImageUrl(cursor.getString(7));
                chatUserList.setType(cursor.getString(8));
                chatUserList.setData(cursor.getString(9));
                chatUserList.setChattype(cursor.getString(10));
                chatUserList.setDid(cursor.getString(11));
                chatUserList.setAdmin(cursor.getString(12));
                chats.add(chatUserList);
            }
        }
        cursor.close();
        msqLiteDatabase.close();
        return chats;
    }

    public ArrayList<JsonModelForChat> getAllGroupMessage(String id){
        msqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = msqLiteDatabase.rawQuery("SELECT * FROM " + GROUP_CHAT_TABLE +" WHERE id = '" + id +"'", null);
        ArrayList<JsonModelForChat> chats= new ArrayList<JsonModelForChat>();
        JsonModelForChat groupchat;
        Log.d("CURSOR",cursor.toString()+id);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                groupchat= new JsonModelForChat();
                groupchat.setId(cursor.getString(0));
                groupchat.setSendName(cursor.getString(1));
                groupchat.setRecieverName(cursor.getString(2));
                groupchat.setDate(cursor.getString(3));
                groupchat.setMessage(cursor.getString(4));
                groupchat.setIsread(cursor.getString(5));
                groupchat.setDeliver(cursor.getString(6));
                groupchat.setType(cursor.getString(7));
                groupchat.setResponse(cursor.getString(8));
                groupchat.setTime(cursor.getString(9));
                groupchat.setMid(cursor.getString(10));
                groupchat.setLat(cursor.getString(11));
                groupchat.setLang(cursor.getString(12));
                groupchat.setHeading(cursor.getString(13));
                groupchat.setTimedate(cursor.getString(14));
                groupchat.setSelect(Boolean.parseBoolean(cursor.getString(15)));
                groupchat.setStatus(cursor.getString(16));
                chats.add(groupchat);
            }
        }
        cursor.close();
        msqLiteDatabase.close();
        return chats;
    }

    public ArrayList<JsonModelForChat> getAllGroupMessageImages(String id){
        msqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = msqLiteDatabase.rawQuery("SELECT * FROM " + GROUP_CHAT_TABLE +" WHERE id = '" + id +"' AND type = 'img'", null);
        ArrayList<JsonModelForChat> chats= new ArrayList<JsonModelForChat>();
        JsonModelForChat groupchat;
        Log.d("CURSOR",cursor.toString()+id);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                groupchat= new JsonModelForChat();
                groupchat.setId(cursor.getString(0));
                groupchat.setSendName(cursor.getString(1));
                groupchat.setRecieverName(cursor.getString(2));
                groupchat.setDate(cursor.getString(3));
                groupchat.setMessage(cursor.getString(4));
                groupchat.setIsread(cursor.getString(5));
                groupchat.setDeliver(cursor.getString(6));
                groupchat.setType(cursor.getString(7));
                groupchat.setResponse(cursor.getString(8));
                groupchat.setTime(cursor.getString(9));
                groupchat.setMid(cursor.getString(10));
                groupchat.setLat(cursor.getString(11));
                groupchat.setLang(cursor.getString(12));
                groupchat.setHeading(cursor.getString(13));
                groupchat.setTimedate(cursor.getString(14));
                groupchat.setSelect(Boolean.parseBoolean(cursor.getString(15)));
                chats.add(groupchat);
            }
        }
        cursor.close();
        msqLiteDatabase.close();
        return chats;
    }

    public ArrayList<User> getAllUsersList(){
        msqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = msqLiteDatabase.rawQuery("SELECT * FROM " + USERS_LIST, null);
        Log.d("CURSOR",cursor.toString());
        ArrayList<User> users= new ArrayList<User>();
        User userList;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                userList= new User();
                userList.setId(cursor.getString(0));
                userList.setName(cursor.getString(1));
                userList.setDid(cursor.getString(2));
                userList.setCount(cursor.getString(3));
                userList.setDatetime(cursor.getString(4));
                userList.setImageUrl(cursor.getString(5));
                users.add(userList);
            }
        }
        cursor.close();
        msqLiteDatabase.close();
        return users;
    }

    public ArrayList<JsonModelForChat> getAllSingleChat(String id){
        msqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = msqLiteDatabase.rawQuery("SELECT * FROM " + SINGLE_CHAT_TABLE + " WHERE rcv_id = '"+ id +"'", null);
        ArrayList<JsonModelForChat> chats= new ArrayList<JsonModelForChat>();
        JsonModelForChat jsonModelForChat;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                jsonModelForChat= new JsonModelForChat();
                jsonModelForChat.setId(cursor.getString(0));
                jsonModelForChat.setSendName(cursor.getString(1));
                jsonModelForChat.setRecieverName(cursor.getString(2));
                jsonModelForChat.setDate(cursor.getString(3));
                jsonModelForChat.setMessage(cursor.getString(4));
                jsonModelForChat.setIsread(cursor.getString(5));
                jsonModelForChat.setDeliver(cursor.getString(6));
                jsonModelForChat.setType(cursor.getString(7));
                jsonModelForChat.setResponse(cursor.getString(8));
                jsonModelForChat.setTime(cursor.getString(9));
                jsonModelForChat.setMid(cursor.getString(10));
                jsonModelForChat.setLat(cursor.getString(11));
                jsonModelForChat.setLang(cursor.getString(12));
                jsonModelForChat.setHeading(cursor.getString(13));
                jsonModelForChat.setTimedate(cursor.getString(14));
                jsonModelForChat.setSelect(Boolean.parseBoolean(cursor.getString(15)));
                chats.add(jsonModelForChat);
            }
        }
        cursor.close();
        msqLiteDatabase.close();
        return chats;
    }


  /*  public class GetChatData extends AsyncTask<Void,Void,ArrayList<Chat_Modal>>{
        @Override
        protected ArrayList<Chat_Modal> doInBackground(Void... voids) {
            ArrayList<Chat_Modal> allChat=getAllChat();
            return allChat;
        }
    }*/


}
