package com.ziasy.xmppchatapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ziasy.xmppchatapplication.model.JsonModelForChat;
import java.util.ArrayList;
import java.util.List;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_DATE;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_DELIVER;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_ISREAD;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_TIME;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_TYPE;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_TABLE;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_ID;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_SENDER_ID;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_RECIEVER_ID;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_DATETIME;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_MESSAGE;
import static com.ziasy.xmppchatapplication.database.DBConstants.SINGLE_CHAT_UID;
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
 * Created by ANDROID on 14-Sep-17.
 */

public class DBUtil {
    public DBUtil() {

    }

    private static final String TAG = "DBUtil";

    /**
     * Insert type into db
     *
     * @param context
     * @param
     */

    public static List<EmployeeModel> fetchAllEmployeeList(Context context) {
        String[] FROM = {SINGLE_ID, SINGLE_NAME, SINGLE_NUM, SINGLE_PHOTO, SINGLE_DEVICE_ID, SINGLE_DEL,
                SINGLE_USER_STATUS,SINGLE_WINDOW_STATUS,SINGLE_USER_GROUP};
        SQLiteDatabase db = new DBData(context).getReadableDatabase();
        List<EmployeeModel> day = new ArrayList<EmployeeModel>();
        Cursor cursor = db.query(SINGLE_LIST_TABLE, FROM, null, null, null, null, SINGLE_NAME);
        while (cursor.moveToNext()) {
            EmployeeModel temp = new EmployeeModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
            day.add(temp);
        }
        cursor.close();
        db.close();
        return day;
    }

    public static EmployeeModel fetchSingleEmployee(Context context, int id) {
        String[] FROM = {SINGLE_ID, SINGLE_NAME, SINGLE_NUM, SINGLE_PHOTO, SINGLE_DEVICE_ID, SINGLE_DEL
                , SINGLE_USER_STATUS,SINGLE_WINDOW_STATUS,SINGLE_USER_GROUP};
        String where = SINGLE_ID + "=" + id;
        SQLiteDatabase db = new DBData(context).getReadableDatabase();
        Cursor cursor = db.query(SINGLE_LIST_TABLE, FROM, where, null, null, null, SINGLE_NAME);
        cursor.moveToNext();
        EmployeeModel temp = new EmployeeModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
        cursor.close();
        db.close();
        return temp;
    }

    public static EmployeeModel employeeInsert(Context context, int EmployeeId, String SINGLE_USERID, String EmployeeName, String Employee_Number, String EmployeeImage, String EmployeeDeviceId, String EmployeeDel
            , String EmployeeStatus, String windowStatus, String EmployeeUserGroup) {
         SQLiteDatabase db = new DBData(context).getWritableDatabase();
        ContentValues values = new ContentValues();
      //  values.put(SINGLE_ID, EmployeeId);
        values.put(SINGLE_USER_ID, SINGLE_USERID);
        values.put(SINGLE_NAME, EmployeeName);
        values.put(SINGLE_PHOTO, EmployeeImage);
        values.put(SINGLE_DEVICE_ID, EmployeeDeviceId);
        values.put(SINGLE_DEL, EmployeeDel);
        values.put(SINGLE_USER_STATUS, EmployeeStatus);
        values.put(SINGLE_WINDOW_STATUS, windowStatus);
        values.put(SINGLE_NUM, Employee_Number);
        values.put(SINGLE_USER_GROUP, EmployeeUserGroup);
        long id = db.insertOrThrow(SINGLE_LIST_TABLE, null, values);
        db.close();
        EmployeeModel newType = fetchSingleEmployee(context, (int) id);
        return newType;
    }

    /**
     * Insert type into db
     *
     * @param context
     * @param
     */
    public static JsonModelForChat chatInsert(Context context, int SINGLE_CHATID, String SINGLE_CHAT_SENDERID
            ,String SINGLE_CHAT_RECIEVERID, String SINGLE_CHATDATETIME, String SINGLE_CHATMESSAGE, String SINGLE_CHATISREAD
            ,String SINGLE_CHATDELIVER, String SINGLE_CHATTYPE, String SINGLE_CHATTIME, String SINGLE_CHATDATE, String SINGLE_CHATUID) {
        SQLiteDatabase db = new DBData(context).getWritableDatabase();
        ContentValues values = new ContentValues();
     //   values.put(SINGLE_CHAT_ID, SINGLE_CHATID);
        values.put(SINGLE_CHAT_SENDER_ID, SINGLE_CHAT_SENDERID);
        values.put(SINGLE_CHAT_RECIEVER_ID, SINGLE_CHAT_RECIEVERID);
        values.put(SINGLE_CHAT_DATETIME, SINGLE_CHATDATETIME);
        values.put(SINGLE_CHAT_TIME, SINGLE_CHATTIME);
        values.put(SINGLE_CHAT_DATE, SINGLE_CHATDATE);
        values.put(SINGLE_CHAT_MESSAGE, SINGLE_CHATMESSAGE);
        values.put(SINGLE_CHAT_ISREAD, SINGLE_CHATISREAD);
        values.put(SINGLE_CHAT_DELIVER, SINGLE_CHATDELIVER);
        values.put(SINGLE_CHAT_TYPE, SINGLE_CHATTYPE);
        values.put(SINGLE_CHAT_UID, SINGLE_CHATUID);
        long id = db.insertOrThrow(SINGLE_CHAT_TABLE, null, values);
        db.close();
        JsonModelForChat newType = fetchSingleChat(context, (int) id);
        return newType;
    }
    /**
     * Fetch all Exercises
     *
     * @param context
     * @return
     */
    public static List<JsonModelForChat> fetchAllChatList(Context context,String sender_id,String reciever_id) {
       /* select * from `chat_demo` where `send_id`="'+obj.reciverid+'" AND `rcv_id`="'+obj.senderid+'"
                                                   UNION select * from `chat_demo` where `send_id`="'+obj.senderid                                                  +'" AND `rcv_id`="'+obj.reciverid+'" ORDER BY `id` */
        String[] FROM = {SINGLE_CHAT_ID, SINGLE_CHAT_SENDER_ID, SINGLE_CHAT_RECIEVER_ID, SINGLE_CHAT_DATETIME,
                SINGLE_CHAT_MESSAGE, SINGLE_CHAT_ISREAD, SINGLE_CHAT_DELIVER,SINGLE_CHAT_TYPE,SINGLE_CHAT_TIME,SINGLE_CHAT_DATE,SINGLE_CHAT_UID};


   /*     String FROM  ="*"; {SINGLE_CHAT_ID, SINGLE_CHAT_SENDER_ID, SINGLE_CHAT_RECIEVER_ID, SINGLE_CHAT_DATETIME,
                SINGLE_CHAT_MESSAGE, SINGLE_CHAT_ISREAD, SINGLE_CHAT_DELIVER,SINGLE_CHAT_TYPE,SINGLE_CHAT_TIME,SINGLE_CHAT_DATE};*/
        String where = "SELECT * FROM "+SINGLE_CHAT_TABLE +" WHERE "+SINGLE_CHAT_ID + "=" + reciever_id + " AND "+ SINGLE_CHAT_RECIEVER_ID + " = "+sender_id + " UNION SELECT * FROM " + SINGLE_CHAT_TABLE + " WHERE "+SINGLE_CHAT_SENDER_ID + " = "+sender_id+" AND "+ SINGLE_CHAT_RECIEVER_ID + " = "+ reciever_id ;
/*
  String where = "SELECT "+SINGLE_CHAT_ID +","+ SINGLE_CHAT_SENDER_ID+","+ SINGLE_CHAT_RECIEVER_ID+","+ SINGLE_CHAT_DATETIME+","+
        SINGLE_CHAT_MESSAGE+","+ SINGLE_CHAT_ISREAD+","+ SINGLE_CHAT_DELIVER+","+SINGLE_CHAT_TYPE+","+SINGLE_CHAT_TIME+","+SINGLE_CHAT_DATE+","+SINGLE_CHAT_UID+" FROM "+SINGLE_CHAT_TABLE +" WHERE "+SINGLE_CHAT_ID + "=" + reciever_id + " AND "+ SINGLE_CHAT_RECIEVER_ID + " = "+sender_id + " UNION SELECT * FROM " + SINGLE_CHAT_TABLE + " WHERE "+SINGLE_CHAT_SENDER_ID + " = "+sender_id+" AND "+ SINGLE_CHAT_RECIEVER_ID + " = "+ reciever_id ;
*/

        SQLiteDatabase db = new DBData(context).getReadableDatabase();
        List<JsonModelForChat> chat = new ArrayList<JsonModelForChat>();
        Cursor cursor =  db.rawQuery(where,null);
     //   Cursor cursor = db.query(SINGLE_CHAT_TABLE, FROM, where, null, null, null, SINGLE_CHAT_SENDER_ID);
        while (cursor.moveToNext()) {
            JsonModelForChat temp = new JsonModelForChat(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(4), cursor.getString(5),cursor.getString(10));
                chat.add(temp);
        }
        cursor.close();
        db.close();
        return chat;
    }
    /**
     * Fetch a type by id
     *
     * @param context
     * @param id
     * @return
     */

    public static JsonModelForChat fetchSingleChat(Context context, int id) {
        String[] FROM = {SINGLE_CHAT_ID, SINGLE_CHAT_SENDER_ID, SINGLE_CHAT_RECIEVER_ID, SINGLE_CHAT_DATETIME,
                SINGLE_CHAT_MESSAGE, SINGLE_CHAT_ISREAD, SINGLE_CHAT_DELIVER,SINGLE_CHAT_TYPE,SINGLE_CHAT_TIME,SINGLE_CHAT_DATE,SINGLE_CHAT_UID};
        String where = SINGLE_CHAT_ID + "=" + id;
        SQLiteDatabase db = new DBData(context).getReadableDatabase();
        Cursor cursor = db.query(SINGLE_CHAT_TABLE, FROM, where, null, null, null, SINGLE_CHAT_SENDER_ID);
        cursor.moveToNext();
        JsonModelForChat temp = new JsonModelForChat(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9),cursor.getString(10));
        cursor.close();
        db.close();
        return temp;
    }
    /**
     * Delete a Exercise
     *
     * @param context
     * @param id
     */
    public static void deleteChatListEmlopyee(Context context, int id) {

        SQLiteDatabase db = new DBData(context).getWritableDatabase();
        String where = SINGLE_CHAT_ID + "=" + id;
        db.delete(SINGLE_LIST_TABLE, where, null);
        db.close();
    }

    public static boolean checkSingleChat(Context context, String uid) {
        // array of columns to fetch
        String[] columns = {SINGLE_CHAT_UID};
        SQLiteDatabase db = new DBData(context).getReadableDatabase();
        // selection criteria
        String selection = SINGLE_CHAT_UID + " = ?";
        // selection argument
        String[] selectionArgs = {uid};
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(SINGLE_CHAT_TABLE, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
  /*  public static ScanProductDatabaseModel scanproductInsert(Context context, int productId, String productName, String productQuantity, String productWeigth, String productImage, String productPrice, String productRetailerId) {
        SQLiteDatabase db = new DBData(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SCAN_PRODUCT_ID, productId);
        values.put(SCAN_PRODUCT_NAME, productName);
        values.put(SCAN_PRODUCT_QUANTITY, productQuantity);
        values.put(SCAN_PRODUCT_WIEGTH, productWeigth);
        values.put(SCAN_PRODUCT_IMAGE, productImage);
        values.put(SCAN_PRODUCT_PRICE, productPrice);
        values.put(SCAN_PRODUCT_RETAILR_ID, productRetailerId);

        long id = db.insertOrThrow(SCAN_PRODUCT_LIST_TABLE_NAME, null, values);
        db.close();
        ScanProductDatabaseModel newType = scanfetchDay(context, (int) id);
        return newType;
    }

    *//**
     * Fetch all Exercises
     *
     * @param context
     * @return
     *//*
    public static List<ScanProductDatabaseModel> scanfetchAllDay(Context context) {
        String[] FROM = {SCAN_ID, SCAN_PRODUCT_ID, SCAN_PRODUCT_NAME, SCAN_PRODUCT_QUANTITY, SCAN_PRODUCT_WIEGTH, SCAN_PRODUCT_IMAGE, SCAN_PRODUCT_PRICE,SCAN_PRODUCT_RETAILR_ID};
        SQLiteDatabase db = new DBData(context).getReadableDatabase();
        List<ScanProductDatabaseModel> day = new ArrayList<ScanProductDatabaseModel>();
        Cursor cursor = db.query(SCAN_PRODUCT_LIST_TABLE_NAME, FROM, null, null, null, null, SCAN_PRODUCT_NAME);
        while (cursor.moveToNext()) {
            ScanProductDatabaseModel temp = new ScanProductDatabaseModel(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
            day.add(temp);
        }

        cursor.close();
        db.close();
        return day;
    }

    *//**
     * Fetch a type by id
     *
     * @param context
     * @param typeId
     * @return
     *//*

    public static ScanProductDatabaseModel scanfetchDay(Context context, int typeId) {
        String[] FROM = {SCAN_ID, SCAN_PRODUCT_ID, SCAN_PRODUCT_NAME, SCAN_PRODUCT_QUANTITY, SCAN_PRODUCT_WIEGTH, SCAN_PRODUCT_IMAGE, SCAN_PRODUCT_PRICE,SCAN_PRODUCT_RETAILR_ID};
        String where = SCAN_ID + "=" + typeId;

        SQLiteDatabase db = new DBData(context).getReadableDatabase();
        Cursor cursor = db.query(SCAN_PRODUCT_LIST_TABLE_NAME, FROM, where, null, null, null, SCAN_PRODUCT_NAME);
        cursor.moveToNext();
        ScanProductDatabaseModel temp = new ScanProductDatabaseModel(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
        cursor.close();
        db.close();

        return temp;
    }

    *//**
     * Delete a Exercise
     *
     * @param context
     * @param dayId
     *//*
    public static void scandeleteDay(Context context, int dayId) {

        SQLiteDatabase db = new DBData(context).getWritableDatabase();
        String where = SCAN_PRODUCT_ID + "=" + dayId;
        db.delete(SCAN_PRODUCT_LIST_TABLE_NAME, where, null);
        db.close();
    }

    *//**
     * This method to update user record
     *
     * @param user
     *//*

    public static void updateProduct(Context context, ProductDatabaseModel user) {
        SQLiteDatabase db = new DBData(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PRODUCT_QUANTITY, user.getProductQuantity());

        // updating row
        db.update(PRODUCT_LIST_TABLE_NAME, values, PRODUCT_ID + " = ?",
                new String[]{String.valueOf(user.getProductId())});
        db.close();
    }
*/
    /*public static void deleteAllExersice(Context context, int exerciseId) {
        SQLiteDatabase db = new DBData(context).getWritableDatabase();
        db.delete(PRODUCT_LIST_TABLE_NAME, null, null);
        db.delete(SCAN_PRODUCT_LIST_TABLE_NAME, null, null);
        db.close();

    }*/
    /**
     * This method to check user exist or not
     *
     * @param
     * @return true/false
     */
    /*
    public static boolean checkProduct(Context context, String product_id) {
        // array of columns to fetch
        String[] columns = {PRODUCT_ID};
        SQLiteDatabase db = new DBData(context).getReadableDatabase();
        // selection criteria
        String selection = PRODUCT_ID + " = ?";
        // selection argument
        String[] selectionArgs = {product_id};
        // query user table with condition
        *//**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         *//*
        Cursor cursor = db.query(PRODUCT_LIST_TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }*/


    /**
     * This method to update user record
     *
     * @param user
     *//*
    public static void scanUpdateProduct(Context context, ScanProductDatabaseModel user) {
        SQLiteDatabase db = new DBData(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SCAN_PRODUCT_QUANTITY, user.getProductQuantity());

        // updating row
        db.update(SCAN_PRODUCT_LIST_TABLE_NAME, values, SCAN_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(user.getProductId())});
        db.close();
    }
*/
    /**
     * This method to check user exist or not
     *
     * @param
     * @return true/false
     */
    /*
    public static boolean scanCheckProduct(Context context, String product_id) {
        // array of columns to fetch
        String[] columns = {SCAN_PRODUCT_ID};
        SQLiteDatabase db = new DBData(context).getReadableDatabase();
        // selection criteria
        String selection = SCAN_PRODUCT_ID + " = ?";
        // selection argument
        String[] selectionArgs = {product_id};
        // query user table with condition
        *//**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         *//*
        Cursor cursor = db.query(SCAN_PRODUCT_LIST_TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
*/
/*
    public static void getAllDataForVolley(final Context context, final String barcode) {

        StringBuffer bufferProduct_Id = new StringBuffer();
        StringBuffer bufferProduct_Name = new StringBuffer();
        StringBuffer bufferQuantity = new StringBuffer();
        StringBuffer bufferPrice = new StringBuffer();
        StringBuffer bufferWeigth = new StringBuffer();
        StringBuffer bufferRetailer = new StringBuffer();
        double tottalWeigth = 0;
        double tottalPrice = 0;
        double tottalQauntity = 0;

        SQLiteDatabase db = new DBData(context).getWritableDatabase();
        String[] FROM = {ID, PRODUCT_ID, PRODUCT_NAME, PRODUCT_QUANTITY, PRODUCT_WIEGTH, PRODUCT_IMAGE, PRODUCT_PRICE,PRODUCT_RETAILR_ID};
        Cursor cursor = db.query(PRODUCT_LIST_TABLE_NAME, FROM, null, null, null, null, null);

        while (cursor.moveToNext()) {

            ProductDatabaseModel temp = new ProductDatabaseModel(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
            String product_id = String.valueOf(temp.getProductId());
            String product_name = String.valueOf(temp.getProductName()).toUpperCase();
            String product_price = String.valueOf(temp.getProductPrice()).toLowerCase();
            String product_quantity = String.valueOf(temp.getProductQuantity()).toLowerCase();
            String product_weigth = String.valueOf(temp.getProductQuantity()).toLowerCase();
            String product_retailer = String.valueOf(temp.getProduct_retailer_id()).toLowerCase();

            if (bufferProduct_Id.length() == 0) {
                bufferProduct_Id.append(product_id);
            } else {
                bufferProduct_Id.append("," + product_id);
            }

            if (bufferRetailer.length() == 0) {
                bufferRetailer.append(product_retailer);
            } else {
                bufferRetailer.append("," + product_retailer);
            }

            if (bufferProduct_Name.length() == 0) {
                bufferProduct_Name.append(product_name);
            } else {
                bufferProduct_Name.append("," + product_name);
            }
            if (bufferWeigth.length() == 0) {
                bufferWeigth.append(product_weigth);
            } else {
                bufferWeigth.append("," + product_weigth);
            }

            if (bufferQuantity.length() == 0) {
                bufferQuantity.append(product_quantity);

            } else {
                bufferQuantity.append("," + product_quantity);
            }

            if (bufferPrice.length() == 0) {
                bufferPrice.append(product_price);
            } else {
                bufferPrice.append("," + product_price);
            }
            tottalPrice = tottalPrice + Double.parseDouble(product_price);
            tottalQauntity = tottalQauntity + Double.parseDouble(product_quantity);
            tottalWeigth = tottalWeigth + Double.parseDouble(product_weigth);
           // Log.e("DAYS", tottalQauntity + " : " + tottalPrice + ":" + bufferProduct_Id);
        }
        cursor.close();
        db.close();
        if (barcode != null) {
            submitVolley(context, barcode, String.valueOf(bufferProduct_Id), String.valueOf(bufferProduct_Name), String.valueOf(bufferQuantity), String.valueOf(bufferPrice), String.valueOf(tottalPrice), String.valueOf(tottalQauntity),String.valueOf(tottalWeigth),String.valueOf(bufferWeigth),String.valueOf(bufferRetailer));
        }
    }

    public static void submitVolley(final Context mContext, final String trolleyId, final String str_product_id, final String str_ProductName, final String str_productQuantity, final String str_ProductPrice, final String str_totalPrice, final String str_totalQuantity, final String str_totalWeigth, final String str_productWeigth, final String str_productRetailer) {
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        final SessionManagement sd = new SessionManagement(mContext);
        ConnectionDetector cd = new ConnectionDetector(mContext);
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(((Activity) mContext).findViewById(android.R.id.content), R.string.NoConnection, Snackbar.LENGTH_SHORT).show();

        } else {
            //  progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.SEND_PRELIST,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progressDialog.dismiss();
                                JSONObject jsonObject = new JSONObject(response);
                                String result = jsonObject.getString("response");
                                if (result.equalsIgnoreCase("success")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(mContext, "Submit Succesfully", Toast.LENGTH_SHORT).show();
                                    sd.setUserTrolleyId(trolleyId);
                                    Intent i = new Intent(mContext, ScanBarcodeActivity.class);
                                //    Intent i = new Intent(mContext, BlueToothMainActivity.class);
                                    mContext.startActivity(i);
                                    ((Activity) mContext).finish();

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(mContext, "Submit Unsuccesfully", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                                Toast.makeText(mContext, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(mContext, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", sd.getUserId());
                    params.put("product_id", str_product_id);
                    params.put("quantity", str_productQuantity);
                    params.put("price", str_ProductPrice);
                    params.put("weight", str_productWeigth);
                    params.put("totalquantity", str_totalQuantity);
                    params.put("totalamount", str_totalPrice);
                    params.put("totalweight", str_totalWeigth);

                    return params;
                }
            };
            stringRequest.setRetryPolicy(new
                    DefaultRetryPolicy(50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(stringRequest);
        }
    }

    public static void getScanAllDataForVolley(final Context context, final String barcode) {

                        StringBuffer bufferProduct_Id = new StringBuffer();
                        StringBuffer bufferProduct_Name = new StringBuffer();
                        StringBuffer bufferQuantity = new StringBuffer();
                        StringBuffer bufferPrice = new StringBuffer();
                        StringBuffer bufferWeigth = new StringBuffer();
                        StringBuffer bufferRetailerId = new StringBuffer();
                        double tottalWeigth = 0;
                        double tottalPrice = 0;
                        double tottalQauntity = 0;
                        SQLiteDatabase db = new DBData(context).getWritableDatabase();
                        String[] FROM = {SCAN_ID, SCAN_PRODUCT_ID, SCAN_PRODUCT_NAME, SCAN_PRODUCT_QUANTITY, SCAN_PRODUCT_WIEGTH, SCAN_PRODUCT_IMAGE, SCAN_PRODUCT_PRICE,SCAN_PRODUCT_RETAILR_ID};
                        Cursor cursor = db.query(SCAN_PRODUCT_LIST_TABLE_NAME, FROM, null, null, null, null, null);

                        while (cursor.moveToNext()) {

                            ScanProductDatabaseModel temp = new ScanProductDatabaseModel(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
                            String product_id = String.valueOf(temp.getProductId());
                            String product_name = String.valueOf(temp.getProductName()).toUpperCase();
                            String product_price = String.valueOf(temp.getProductPrice()).toLowerCase();
                            String product_quantity = String.valueOf(temp.getProductQuantity()).toLowerCase();
                            String product_weigth = String.valueOf(temp.getProductQuantity()).toLowerCase();
                            String product_product_id = String.valueOf(temp.getProduct_retailer_id()).toLowerCase();

                            if (bufferProduct_Id.length() == 0) {
                                bufferProduct_Id.append(product_id);
                            } else {
                                bufferProduct_Id.append("," + product_id);
                            }

                            if (bufferRetailerId.length() == 0) {
                                bufferRetailerId.append(product_product_id);
                            } else {
                                bufferRetailerId.append("," + product_product_id);
                            }
                            if (bufferWeigth.length() == 0) {
                                bufferWeigth.append(product_weigth);
                            } else {
                                bufferWeigth.append("," + product_weigth);
                            }
                            if (bufferProduct_Name.length() == 0) {
                                bufferProduct_Name.append(product_name);
                            } else {
                                bufferProduct_Name.append("," + product_name);
                            }

                            if (bufferQuantity.length() == 0) {
                                bufferQuantity.append(product_quantity);

                            } else {
                                bufferQuantity.append("," + product_quantity);
                            }

                            if (bufferPrice.length() == 0) {
                                bufferPrice.append(product_price);
                            } else {
                                bufferPrice.append("," + product_price);
                            }
                            tottalPrice = tottalPrice + Double.parseDouble(product_price);
                            tottalQauntity = tottalQauntity + Double.parseDouble(product_quantity);
                            tottalWeigth = tottalWeigth + Double.parseDouble(product_weigth);
                            Log.e("DAYS", tottalQauntity + " : " + tottalPrice + ":" + bufferProduct_Id);
                        }
                        cursor.close();
                        db.close();
                        if (barcode != null) {
                            submitScanVolley(context, barcode, String.valueOf(bufferProduct_Id), String.valueOf(bufferProduct_Name), String.valueOf(bufferQuantity), String.valueOf(bufferPrice), String.valueOf(tottalPrice), String.valueOf(tottalQauntity),String.valueOf(tottalWeigth),String.valueOf(bufferWeigth),String.valueOf(bufferRetailerId));

                        }
                    }

    public static void submitScanVolley(final Context mContext, final String trolleyId, final String str_product_id, final String str_ProductName, final String str_productQuantity, final String str_ProductPrice, final String str_totalPrice, final String str_totalQuantity, final String str_totalWeigth, final String str_productWeigth, final String str_productRetailerId){
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        final SessionManagement sd = new SessionManagement(mContext);
        ConnectionDetector cd = new ConnectionDetector(mContext);
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(((Activity) mContext).findViewById(android.R.id.content), R.string.NoConnection, Snackbar.LENGTH_SHORT).show();

        } else {
            //  progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.CHECK_OUT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progressDialog.dismiss();
                                JSONObject jsonObject = new JSONObject(response);
                                String result = jsonObject.getString("response");
                                if (result.equalsIgnoreCase("success")) {
                                    progressDialog.dismiss();
                                   deleteAllExersice(mContext,0);
                                    Toast.makeText(mContext, "CheckOut Succesfully", Toast.LENGTH_SHORT).show();

                                   sd.setUserTrolleyId("USER_TROLLEY_ID");
                                    Intent i = new Intent(mContext, CheckOutActivity.class);
                                    mContext.startActivity(i);
                                    ((Activity) mContext).finish();

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(mContext, "CheckOut Unsuccesfully", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                                Toast.makeText(mContext, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(mContext, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", sd.getUserId());
                    params.put("trolley_id", trolleyId);
                    params.put("product_id", str_product_id);
                    params.put("quantity", str_productQuantity);
                    params.put("price", str_ProductPrice);
                    params.put("weight", str_productWeigth);
                    params.put("totalquantity", str_totalQuantity);
                    params.put("totalamount", str_totalPrice);
                    params.put("totalweight", str_totalWeigth);
                    params.put("retailer_id", str_productRetailerId);

                    return params;
                }
            };
            stringRequest.setRetryPolicy(new
                    DefaultRetryPolicy(50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(stringRequest);
        }
    }
*/
}