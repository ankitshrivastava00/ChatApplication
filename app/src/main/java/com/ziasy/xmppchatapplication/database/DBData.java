package com.ziasy.xmppchatapplication.database;

/**
 * Created by ANDROID on 14-Sep-17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_ADMIN;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_CHAT_DATE;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_CHAT_DATETIME;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_CHAT_DELIVER;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_CHAT_ID;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_CHAT_ISREAD;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_CHAT_MESSAGE;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_CHAT_RECIEVER_ID;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_CHAT_SENDER_ID;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_CHAT_TABLE;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_CHAT_TIME;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_CHAT_TYPE;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_CHAT_UID;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_DEL;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_DESCRIPTION;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_DEVICE_ID;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_ID;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_LIST_TABLE;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_NAME;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_NUM;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_PHOTO;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_USER_STATUS;
import static com.ziasy.xmppchatapplication.database.DBConstants.GROUP_WINDOW_STATUS;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_DATE;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_DELIVER;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_ISREAD;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_TIME;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_TYPE;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_UID;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_TABLE;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_ID;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_SENDER_ID;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_RECIEVER_ID;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_DATETIME;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_MESSAGE;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_DEL;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_DEVICE_ID;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_ID;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_LIST_TABLE;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_NAME;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_NUM;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_PHOTO;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_USER_GROUP;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_USER_ID;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_USER_STATUS;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_WINDOW_STATUS;


/**
 * @author "Sudar Muthu (http://sudarmuthu.com)"
 */
public class DBData extends SQLiteOpenHelper {
    private static final String TAG = "DBData";
    private static final String DATABASE_NAME = "UDChat.db";
    private static final int DATABASE_VERSION = 1;

    // SIINGLE_LIST_TABLE

    private static final String CREATE_EMLOYEE_TABLE =
            "CREATE TABLE " + SINGLE_LIST_TABLE + " ("
                    + SINGLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SINGLE_USER_ID + " TEXT,"
                    + SINGLE_NAME + " TEXT,"
                    + SINGLE_PHOTO + " TEXT,"
                    + SINGLE_USER_STATUS + " TEXT,"
                    + SINGLE_WINDOW_STATUS + " TEXT,"
                    + SINGLE_DEVICE_ID + " TEXT,"
                    + SINGLE_DEL + " TEXT,"
                    + SINGLE_NUM + " TEXT,"
                    + SINGLE_USER_GROUP + " TEXT"
                    + " );";
    //SINGLE_CHAT_TABLE

    private static final String CREATE_SINGLE_CHAT_TABLE =
            "CREATE TABLE " + SINGLE_CHAT_TABLE + " ("
                    + SINGLE_CHAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SINGLE_CHAT_SENDER_ID + " INTEGER,"
                    + SINGLE_CHAT_RECIEVER_ID + " TEXT,"
                    + SINGLE_CHAT_DATETIME + " TEXT,"
                    + SINGLE_CHAT_TIME + " TEXT,"
                    + SINGLE_CHAT_DATE + " TEXT,"
                    + SINGLE_CHAT_MESSAGE + " TEXT,"
                    + SINGLE_CHAT_ISREAD + " TEXT,"
                    + SINGLE_CHAT_DELIVER + " TEXT,"
                    + SINGLE_CHAT_TYPE + " TEXT,"
                    + SINGLE_CHAT_UID + " TEXT"
                    + " );";

    // GROUP_LIST_TABLE
    private static final String CREATE_GROUP_NAME_TABLE =
            "CREATE TABLE " + GROUP_LIST_TABLE + " ("
                    + GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + GROUP_NAME + " TEXT,"
                    + GROUP_PHOTO + " TEXT,"
                    + GROUP_USER_STATUS + " TEXT,"
                    + GROUP_WINDOW_STATUS + " TEXT,"
                    + GROUP_DEVICE_ID + " TEXT,"
                    + GROUP_DEL + " TEXT,"
                    + GROUP_NUM + " TEXT,"
                    + GROUP_ADMIN + " TEXT,"
                    + GROUP_DESCRIPTION + " TEXT"
                    + " );";
    //GROUP_CHAT_TABLE

    private static final String CREATE_GROUP_CHAT_TABLE =
            "CREATE TABLE " + GROUP_CHAT_TABLE + " ("
                    + GROUP_CHAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + GROUP_CHAT_SENDER_ID + " INTEGER,"
                    + GROUP_CHAT_RECIEVER_ID + " TEXT,"
                    + GROUP_CHAT_DATETIME + " TEXT,"
                    + GROUP_CHAT_TIME + " TEXT,"
                    + GROUP_CHAT_DATE + " TEXT,"
                    + GROUP_CHAT_MESSAGE + " TEXT,"
                    + GROUP_CHAT_ISREAD + " TEXT,"
                    + GROUP_CHAT_DELIVER + " TEXT,"
                    + GROUP_CHAT_TYPE + " TEXT,"
                    + GROUP_CHAT_UID + " TEXT"
                    + " );";

    /**
     * @param context
     */
    public DBData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // When the app is installed for the first time

        db.execSQL(CREATE_EMLOYEE_TABLE);
        db.execSQL(CREATE_SINGLE_CHAT_TABLE);
        db.execSQL(CREATE_GROUP_NAME_TABLE);
        db.execSQL(CREATE_GROUP_CHAT_TABLE);
//        db.execSQL(CREATE_TROLLEY_TABLE);
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_EMLOYEE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_SINGLE_CHAT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_GROUP_NAME_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_GROUP_CHAT_TABLE);
        // db.execSQL("DROP TABLE IF EXISTS " + CREATE_TROLLEY_TABLE);
        onCreate(db);
    }
}