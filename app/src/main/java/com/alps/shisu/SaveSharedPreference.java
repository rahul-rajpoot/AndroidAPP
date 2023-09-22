package com.alps.shisu;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {


    private static final String SESSION_ID = "session_id";
    private static final String SESSION_NAME = "session_name";
    private static final String SESSION_TOKEN = "session_token";
    private static final String SESSION_STATUS = "session_status";
    private static final String USER_ID = "user_ID";
    private static final String regno = "regno";

    private static final String DELIEVER_ADDRESS = "deliever_address";

    private static final String LOGIN_TYPE = "login_type";

    private static final String CHECK_LOGIN = "checklogin";

    public static final String CHECK_COURSE = "course";

    public static String SESSION_COOKIES = "session_cookies";
    public static String UID = "uid";

    public static final String VIDEO_ID = "videoid";

    public static final String TOTAL_AMOUNT = "total_amount";

    private static final String PREVIOUS_ACTIVITY = "previous_activity";

    public static final String BUTTON_CLICK = "buttonClick";
    public static final String ORDER_ID = "order_id";

    public static final String N_ID = "nid";
    public static final String ACTIVITY_SAVE = "activity_save";


    public static final String IMAGE_URL = "image_url";

    static SharedPreferences sharedPreferences;

    static Context mContext;


    public SaveSharedPreference(Context context) {
        mContext = context;
    }

    public static String getRegno() {
        return getSharedPreferences().getString(regno, "");
    }
    public static void setRegno(String regno) {
        getSharedPreferences().edit().putString(regno, regno).apply();
    }

    public static String getImageUrl() {
        return getSharedPreferences().getString(IMAGE_URL, "");
    }

    public static void setImageUrl(String imageUrl) {
        getSharedPreferences().edit().putString(IMAGE_URL, imageUrl).apply();
    }

    public static String getnId() {
        return getSharedPreferences().getString(N_ID, "");
    }

    public static void setnId(String nId) {
        getSharedPreferences().edit().putString(N_ID, nId).apply();
    }

    public static String getActivitySave() {
        return getSharedPreferences().getString(ACTIVITY_SAVE, "");
    }

    public static void setActivitySave(String activitySave) {
        getSharedPreferences().edit().putString(ACTIVITY_SAVE, activitySave).apply();
    }

    public static String getPreviousActivity() {

        return getSharedPreferences().getString(PREVIOUS_ACTIVITY, "");
    }


    public static void setPreviousActivity(String previousActivity) {

        getSharedPreferences().edit().putString(PREVIOUS_ACTIVITY, previousActivity).apply();
    }

    public static String getOrderId() {

        return getSharedPreferences().getString(ORDER_ID, "");
    }

    public static void setOrderId(String orderId) {

        getSharedPreferences().edit().putString(ORDER_ID, orderId).apply();
    }

    public static String getButtonClick() {
        return getSharedPreferences().getString(BUTTON_CLICK, "");

    }

    public static void setButtonClick(String buttonClick) {
        getSharedPreferences().edit().putString(BUTTON_CLICK, buttonClick).apply();
    }


    public static String getDelieverAddress() {
        return getSharedPreferences().getString(DELIEVER_ADDRESS, "");
    }

    public static void setDelieverAddress(String address) {
        getSharedPreferences().edit().putString(DELIEVER_ADDRESS, address).apply();

    }

    public static String getTotalAmount() {

        return getSharedPreferences().getString(TOTAL_AMOUNT, "");
    }

    public static void setTotalAmount(String totalAmount) {
        getSharedPreferences().edit().putString(TOTAL_AMOUNT, totalAmount).apply();
    }

    public static String getVideoId() {
        return getSharedPreferences().getString(VIDEO_ID, "");
    }

    public static void setVideoId(String videoId) {
        getSharedPreferences().edit().putString(VIDEO_ID, videoId).apply();
    }


    public static String getUserId() {
        return getSharedPreferences().getString(USER_ID, "");
    }

    public static void setUserId(String user_id) {
        getSharedPreferences().edit().putString(USER_ID, user_id).apply();

    }

    public static String getCheckCourse() {
        return getSharedPreferences().getString(CHECK_COURSE, "");

    }

    public static void setCheckCourse(String checkCourse) {
        getSharedPreferences().edit().putString(CHECK_COURSE, checkCourse).apply();

    }


    public static void setSession_Id(String sessionId) {
        getSharedPreferences().edit().putString(SESSION_ID, sessionId).apply();
    }

    public static String getSession_Id() {
        return getSharedPreferences().getString(SESSION_ID, "");
    }

    public static void setSession_Name(String sessionName) {
        getSharedPreferences().edit().putString(SESSION_NAME, sessionName).apply();
    }

    public static String getSession_Name() {
        return getSharedPreferences().getString(SESSION_NAME, "");
    }

    public static void setSession_Token(String sessionToken) {

        getSharedPreferences().edit().putString(SESSION_TOKEN, sessionToken).apply();
    }

    public static String getSession_Token() {
        return getSharedPreferences().getString(SESSION_TOKEN, "");
    }


    public static void setSession_Status(int sessionStatus) {
        getSharedPreferences().edit().putInt(SESSION_STATUS, sessionStatus).apply();
    }

    public static int getSession_Status() {
        return getSharedPreferences().getInt(SESSION_STATUS, 0);
    }


    static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public static void setLogin_type(String login_type) {
        getSharedPreferences().edit().putString(LOGIN_TYPE, login_type).apply();
    }


    public static String getLogin_type() {
        return getSharedPreferences().getString(LOGIN_TYPE, "");
    }


    public static void setIsLogin(String login_check) {
        getSharedPreferences().edit().putString(CHECK_LOGIN, login_check).apply();
    }

    public static String getIsLogin() {
        return getSharedPreferences().getString(CHECK_LOGIN, "");
    }

    public static void Logout() {

        setLogin_type(null);
        setIsLogin(null);
        setSession_Token(null);
        setSession_Name(null);
        setSession_Id(null);
        setSession_Status(0);

    }


}