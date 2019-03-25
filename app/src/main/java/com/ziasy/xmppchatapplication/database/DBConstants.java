package com.ziasy.xmppchatapplication.database;

import android.provider.BaseColumns;

/**
 * Created by ANDROID on 14-Sep-17.
 */

public interface DBConstants extends BaseColumns {
    //EMPLOYEE_LIST_TABLE
    public static final String SINGLE_LIST_TABLE = "single_list";
    //columns in the entry table
    public static final String SINGLE_ID = "single_id";
    public static final String SINGLE_USER_ID = "single_user_id";
    public static final String SINGLE_NAME = "single_name";
    public static final String SINGLE_PHOTO = "single_photo";
    public static final String SINGLE_USER_STATUS = "single_user_status";
    public static final String SINGLE_WINDOW_STATUS = "single_window_status";
    public static final String SINGLE_DEVICE_ID = "single_device_id";
    public static final String SINGLE_DEL = "single_del";
    public static final String SINGLE_NUM = "single_num";
    public static final String SINGLE_USER_GROUP = "single_user_group";


    //SINGLE_CHAT_TABLE
    public static final String SINGLE_CHAT_TABLE = "single_chat";
    //columns in the entry table
    public static final String SINGLE_CHAT_ID = "ID";
    public static final String SINGLE_CHAT_SENDER_ID = "SINGLE_CHAT_SENDER_ID";
    public static final String SINGLE_CHAT_RECIEVER_ID = "SINGLE_CHAT_RECIEVER_ID";
    public static final String SINGLE_CHAT_DATETIME = "SINGLE_CHAT_DATETIME";
    public static final String SINGLE_CHAT_TIME = "SINGLE_CHAT_TIME";
    public static final String SINGLE_CHAT_DATE = "SINGLE_CHAT_DATE";
    public static final String SINGLE_CHAT_MESSAGE = "SINGLE_CHAT_MESSAGE";
    public static final String SINGLE_CHAT_ISREAD = "SINGLE_CHAT_ISREAD";
    public static final String SINGLE_CHAT_DELIVER = "SINGLE_CHAT_DELIVER";
    public static final String SINGLE_CHAT_TYPE = "SINGLE_CHAT_TYPE";
    public static final String SINGLE_CHAT_UID = "SINGLE_CHAT_UID";

    //GROUP_LIST_TABLE
    public static final String GROUP_LIST_TABLE = "group_list";
    //columns in the entry table
    public static final String GROUP_ID = "group_id";
    public static final String GROUP_NAME = "group_name";
    public static final String GROUP_PHOTO = "group_photo";
    public static final String GROUP_USER_STATUS = "group_user_status";
    public static final String GROUP_WINDOW_STATUS = "group_window_status";
    public static final String GROUP_DEVICE_ID = "group_device_id";
    public static final String GROUP_DEL = "group_del";
    public static final String GROUP_NUM = "group_num";
    public static final String GROUP_ADMIN = "group_admin";
    public static final String GROUP_DESCRIPTION = "group_description";


    //SINGLE_CHAT_TABLE
    public static final String GROUP_CHAT_TABLE = "group_chat";
    //columns in the entry table
    public static final String GROUP_CHAT_ID = "GROUP_CHAT_ID";
    public static final String GROUP_CHAT_SENDER_ID = "GROUP_CHAT_SENDER_ID";
    public static final String GROUP_CHAT_RECIEVER_ID = "GROUP_CHAT_RECIEVER_ID";
    public static final String GROUP_CHAT_DATETIME = "GROUP_CHAT_DATETIME";
    public static final String GROUP_CHAT_TIME = "GROUP_CHAT_TIME";
    public static final String GROUP_CHAT_DATE = "GROUP_CHAT_DATE";
    public static final String GROUP_CHAT_MESSAGE = "GROUP_CHAT_MESSAGE";
    public static final String GROUP_CHAT_ISREAD = "GROUP_CHAT_ISREAD";
    public static final String GROUP_CHAT_DELIVER = "GROUP_CHAT_DELIVER";
    public static final String GROUP_CHAT_TYPE = "GROUP_CHAT_TYPE";
    public static final String GROUP_CHAT_UID = "GROUP_CHAT_UID";
}