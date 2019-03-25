package com.ziasy.xmppchatapplication.util;


import android.os.Environment;



import java.io.File;
import java.util.ArrayList;

public class Constants {
    public static final String DEALS_COUNT = "deals_cnt";
    public static final String IS_LOGIN = "isLogin";
    public static final String IS_DEALS_MUTE = "dealsmute";
    public static final int LOAD_MESSAGE_COUNT = 10;
    public static int LOAD_MESSAGE_POSITION = 0;
    public static final String MY_POST_JSON = "mypstjson";
    public static final String CURRENT_PROFILE_JSON = "prflejson";
    public static final String MY_CART_JSON = "mycrtjson";
    public static final String DEALS_JSON = "deasljson";
    public static final String CAPTURE_AUDIO = "CAPTURE_AUDIO";
    public static final String PICK_AUDIO = "PICK_AUDIO";
    public static final String AUDIO_FORWARD = "frwrdaudio";
    public static final String CHANNEL_ID = "cidbaidu";
    public static final String HEADQUARTER = "Headquater";
    public static final String MAINBRANCH = "MainBranch";
    public static  boolean FROMGROUP = false;

    public static String MSG_ID;


  //  public static ArrayList<ChatModel> forwardAl = new ArrayList<>();
    //public static final String UD_BASE_URL_ONLYUDTAKS = "udtalksprogress2/";
    public static final String UD_BASE_URL_ONLYUDTAKS = "udtalks/";
    //public static final String BASE_URL_CLIENT = "http://dev.probytes.net/";
    // public static final String BASE_URL_CLIENT = "http://www.udtalks.com/";
    public static final String BASE_URL_CLIENT = "http://128.199.234.38/";

    //  http://www.udtalks.com/udtalks/api/

    //    public static final String ROOT_SERVER = "http://182.72.44.11/xmpp/";    //redbytes server
    //**********************************************************************************8
  /*  public static final String HOST = "182.72.44.11"; // Redbytes
    public static final int PORT = 5222;
    public static final String SERVICE = "udtalks";  //redbytes*/


    public static final String HOST = "128.199.234.38"; // Hazem
    public static final int PORT = 5222;
    public static final String SERVICE = "udtalks";

    //******************************************************************************************
    //URL constants
//    public static final String GET_USER_PROFILE_URL = "http://www.udtalks.com/udtalks/api/get_user_details.php";
    public static final int REQUEST_CODE_CAMERA = 111;
    public static final int REQUEST_CODE_GALLERY = 112;
    public static final String THEME_SHARED_PREF = "themesidud";
    public static final int REQUEST_CODE_GALLERY_SP = 878;
    public static final String LOGOUT = "logout";
    public static final String DELETE_POST = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/delete_mypost.php";
    public static final String DELETE_POST_CART = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/delete_mycart.php";
    public static final String GET_LIKES = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/get_likes.php";
    public static final String LIKE_POST = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/hit_like.php";
    public static final String GET_DEALS = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/get_deals.php";
    public static final String SAVE_GRP_WALLPAPER = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/chat_pic.php";
    public static final String EDIT_GROUP = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/edit_group.php";
    public static final String ADD_REMOVE_MEMBERS_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/add_remove_members.php";
    public static final String LEAVE_DELETE_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/group_action.php";
    public static final String PAGING_SEARCH = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/get_search_temp.php";
    public static final String CREATE_GROUP = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/create_group.php";
    public static final String SEARCH_WITH_NUMBER = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/search_mobile_based.php";
    public static final String ADD_USER_TO_CONTACT = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/added_to_userlist.php";

    public static final String MUTE_USER = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/mute_user.php";

    public static final String SHARETODEALS = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/share_post.php";
    public static final String DELETE_USER_CONTACT = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/delete_user.php";
    public static final String GET_GROUP_MEMBERS_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/group_list.php";
    public static final String EDIT_PROFILE = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/edit_user_privacy.php";
    public static final String SET_STATUS_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/set_status.php";
    public static final String FETCH_CART_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/get_mycart.php";
    public static final String GET_BUY_POST = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/get_buy_post_new.php";
    public static final String GET_MOMENT_POST = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/get_moment_new.php";
    public static final String GET_SELL_POST = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/get_sell_post_new.php";
    public static final String BLOCK_CONTACT_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/block_user.php";
    public static final String GET_GROUP_DETAILS_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/get_group_details.php";
    public static final String GET_USER_PROFILE_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/get_user_details.php";
    public static final String API_ACTIVATE_PROFILE_UD = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/activate_user.php";
    public static final String API_EDIT_PROFILE = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/edit_user.php";
    public static final String PAGING_CONTACT_LIST = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/get_added_users.php";
    public static final String PIC_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/profile_pics/";
    public static final String PIC_SELLPOST_BASE = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/files/sellpost/";
    public static final String PIC_BUYPOST_BASE = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/files/buypost/";
    public static final String PIC_MOMENTS_BASE = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/files/moments/";
    public static final String PROFILE_PIC_UD_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/profile_pics/";
    public static final String UNBLOCK_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/unblock_user.php";
    public static final String BUY_POST_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/buy_post_new.php";
    public static final String FETCH_MYPOST_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/get_mypost.php";
    public static final String MOMENT_POST_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/moment_new.php";
    public static final String ADD_TO_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/mycart.php";
    public static final String SELL_POST_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/sell_post_new.php";
    public static final String POST_COMMENT_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/comment.php";
    public static final String GET_COMMENTS_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/get_comments.php";
    public static final String REGISTER_DEVICE_GCM = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/register_device.php";
    //    public static final String REGISTER_DEVICE_GCM = ROOT_SERVER + "register_device.php";
    public static final String SEND_NOTIFICATION = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/push_notification.php";
    public static final String API_REGISTER_MOBILE = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/register_mobile.php";
    public static final String URL_PASTEC_SEARCH = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/register_mobile.php";

    public static final String API_DELETE_COMMENT = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/delete_comment.php";




    public static final String USER_NAME = "ud_user_name";
    public static final String SP_COMPANY = "spcompany";
    public static final String CAREER = "ud_career";
    public static final String COUNTRY = "ud_country";
    public static final String PRODUCTS = "ud_products";
    public static final String VERIFICATION_CODE = "ud_verification_code";

    public static final String XMPP_BASE_URL = BASE_URL_CLIENT + UD_BASE_URL_ONLYUDTAKS + "api/";

    public static final String UD_USER_ID = "ud_user_id";


    //mobile number digit limit
    public static final int NUM_LIMIT = 11;   //India

    public static final String API_URL_IMAGEUPLOAD_TEMP = XMPP_BASE_URL + "file_upload_temp.php";

    public static final String API_VERIFY_MOBILE = XMPP_BASE_URL + "verify_mobile.php";
    public static final String API_URL_IMAGEUPLOAD = XMPP_BASE_URL + "file_upload.php";
    public static final String GET_GROUPS = XMPP_BASE_URL + "group_list.php";
    public static final String SET_GROUPS = XMPP_BASE_URL + "create_group.php";

    public static final String GET_GROUP_MEMBERS = XMPP_BASE_URL + "member_list.php";
    public static final String ADD_REMOVE_MEMBER = XMPP_BASE_URL + "add_remove_members.php";


    public static final String THEME_PREFERENCE = "theme_preference";
    public static final String PREFERENCE_NAME = "com.deepnetdata";
    public static final String PICTURE_URL = "PictureUrl";
    public static final String PICTURE = "Picture";
    public static final String CHATFLAG = "chatflag";
    public static final String GROUP_PICTURE = "group_pic";
    public static final String LATTITUDE = "lattitude";
    public static final String LONGITUDE = "longitude";
    public static final String PROFILE_PICTURE = "profile_picture";
    public static final String GROUP_PIC_URL = "group_pic_url";
    public static final String USER_ID = "user_id";
    public static final String MAP = "map";
    public static final String XML_MESSAGE_TYPE = "xml_mesage_type";
    public static final String IS_REGISTERED = "is_registered";
    public static final String DATA_ARCHIVE = "data_archive";
    public static final String MAP_FLAG = "map_flag";
    public static final String DATA_BACKUP_TIME = "data_backup_time";

    public static final String TIME_FOR_DATABACKUP = "04:00 am";

    public static final String VERIFICAION_FLAG = "RegistrationDetail";
    public static final String OBJECT_ID = "ObjectId";
    public static final String EMAIL_ADDRESS = "EmailAddress";
    public static final String PASSWORD = "Password";


    public static final String MOBILE_NUM = "mobile_num";
    public static final String STATUS = "status";
    public static final String STATUS_FLAG = "status_flag";
    public static final String GROUP_NAME = "group_name";
    public static final String GROUP_MEMBERS = "group_members";
    public static final String STATUS_CUSTOM = "status_custom";
    public static final String XMPP_GROUP_NAME = "xmpp_group_name";
    public static final String PROFILE_FOLDER_PATH = File.separator + "UdTalks/.profiles";
    public static final String POST_FOLDER_PATH = File.separator + "UdTalks/.post";
    public static final String TOPCHAT_FOLDER_PATH = File.separator + "UdTalks";
    public static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    //audio related constants
    public static final String AUDIO_FOLDER_PATH = File.separator + "Audio";
    public static final String AUDIO_NAME = File.separator + "UdTalks-";
    public static final String LOCATION_FOLDER_PATH = File.separator + ".Location";
    //image constants
    public static final String IMAGE_FOLDER_PATH = File.separator + "Image";
    public static final String NICKNAME = "Nickname";

    public static final int COMPOSED_TIME = 3;
    public static final int PAUSED_TIME = 3;
    public static final String INSTALLATION_DATE = "installdate";
    public static final int SELECTMEMBERS = 1010;
    public static final String FILENAME = "UdTalks";
    public static final String IS_FIRST_TIME = "ftim";
    public static final String BACK_PIC_PROFILE = "bkpic";
    public static final String PRIVACY = "privacynum";
    public static final String OWN_PROFILE_URL = "profilepicown";
    public static final int DEALS_THEME = 857;
    public static final String THEME_PREFERENCE_DEALS = "delas_theme";
    public static final String MY_PIC_URL = "mypicurl";
    public static final String VIBRATE_OFF = "offvibrate";

    public static String TEMPBASE64;


    public static String LOCATION_FORWORD_BASE64;


    public static String tempPacketID = "";
    public static final String PHONE_CONTACT = "phone_contact";
    public static boolean isFromContact = false;


    public static final String groupTime = "groupTime";
    public static final String groupName = "groupName";
    public static final String groupNumber = "groupNumber";
    public static final String groupPic = "groupPic";
    public static final String PREVIOUS_DATE = "previous_date";
    public static final String DISPLAY_DATE = "display_date";
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static Long destructTime = 15L;


    public static String CONTACT_ID = "";

    public static final String MESSAGE_TYPE = "MessageType";
    public static final String INCOMING_MESSAGE = "2";
    public static final String OUTGOING_MESSAGE = "1";

    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_CAPTURE_VIDEO = "capture_video";
    public static final String FOLDER_IMAGE = "Image";
    public static final String FOLDER_VIDEO = "Video";
    public static final String FOLDER_AUDIO = "Audio";

    public static final String AUTO_DOWNLOAD_FLAG = "AutoDownloadFlag";

    public static String APPLICATION_PACKAGE = "";

    //Delivery and read receipt constants
    public static final String MSG_STATE_SENT = "message_sent";
    public static final String MSG_STATE_RECEIVED = "message_received";
    public static final String MSG_STATE_READ = "message_read";

    public static final String DATABASE_BACKUP_FOLDER = "UdTalks/Database";
    public static String BACKUP_TIME = "";
    public static boolean BACKUP_FLAG = false;


    //parse credentials
//    public static final String PARSE_APP_ID="vE5lGvL000yUEbB507GJU7AvEehp6sWFZpXiiPEm";
//    public static final String PARSE_CLIENT_KEY="3iFjCGiTT040uO4f6HD1R6KBIwht55diw27JavwT";

    public static final String PARSE_APP_ID = "gYekafSjJWE5vzYv226sbeMenncligFsCw970qCP";
    public static final String PARSE_CLIENT_KEY = "9FWvilfK6Lwc3SwnfRsfzLllVE5CgLbMSpYt0xEb";
    public static final String LOCATION = "locationImage";
    public static final String STATUS_KEY = "STATUS";
    //http://192.168.1.111:8000/android/topchat.git
    public final static String COUNTRIES = "countries";
    public final static String COUNTRY_CODE_1 = "countryCode";
    public final static String COUNTRY_CODE = "+91";
    public final static String COUNTRY_NAME = "countryName";
    public final static String COUNTRY_FLAG = "flag";
    public static final String CUSTOM_STATUS = "givenName";
    public static final String CUSTOM_PIC = "url";
    public static String image = "image";
    public static String video = "video";
    public static String audio = "audio";
    //    public static String map="map";
    public static String map = "location";
    public static int tempPosition = -1;
    public static final String LOCATION_PATH = "location_path";
    public static boolean isRoster = false;
    public static final String NOTIFICATION_URI = "notifyurl";
    public static final String NOTIFICATION_URI_FILE = "filenamenoti";
    public static final String STATUS_SENDER = "1";

    public static final String COUNT_NOTIFICATION = "notification_count";
    public static final String BAIDU_API_KEY = "YTYb9QiKIpyyLpbfBuGFMdhu";

    public static boolean SWIPE_IMAGE=true;
    public static boolean MEDIA_VIEW=false;

}

