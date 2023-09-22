package com.alps.shisu.Session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.alps.shisu.LoginActivity;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Devendra on 28-03-2016.
 */
public class SessionManagement {
    // Shared Preferences
    final SharedPreferences pref;

    // Editor for Shared preferences
    final Editor editor;

    // Context
    final Context _context;

    // Shared pref mode
    final int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "ALPSPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_FIRST_TIME="First_Time_Install";

    // Merchant ID (make variable public to access from outside)
    public static final String  KEY_CURRENCY = "currency";

    // Secret Key (make variable public to access from outside)
    public static final String KEY_DOJ = "dojtkey";

    public static final String KEY_REGDATE = "regdate";


    // User name (make variable public to access from outside)
    public static final String KEY_USERNAME = "username";

    // User name (make variable public to access from outside)
    public static final String KEY_PASSWORD = "password";

    public static final String KEY_SHOPNAME = "shopname";

    public static final String KEY_SHOPID = "shopid";


//    // Name (make variable public to access from outside)
//    public static final String KEY_FNAME = "fname";
//
//    // Name (make variable public to access from outside)
//    public static final String KEY_LNAME = "lname";

    // Name (make variable public to access from outside)
    public static final String KEY_FNAME = "fname";

    // Name (make variable public to access from outside)
    public static final String KEY_LNAME = "lname";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // Mobile No (make variable public to access from outside)
    public static final String KEY_AVAILABLE_BALANCE = "availablebal";

    //Currency Symble
        public static final String KEY_LEDGER_BALANCE="ledgerbal";

    public static final String KEY_STATUS="status";

    public static final String KEY_SHARE_RIGHT="SHAREGURL_B";
    public static final String KEY_SHARE_LEFT="SHAREGURL_A";

    public static final String KEY_SECRET="keysecret";
    // Constructor
    public SessionManagement(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    /**
     * Create login session
     * */

    public  void AvailableBalSession(String Abal,String Lbal){
        editor.putString(KEY_AVAILABLE_BALANCE,Abal);
        editor.putString(KEY_LEDGER_BALANCE,Lbal);
    }

    public void sharekey(String shareleft,String shareright)
    {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_SHARE_LEFT, shareleft);

        // Storing name in pref
        editor.putString(KEY_SHARE_RIGHT, shareright);
        editor.commit();
    }

    public void changepassword(String UserPassword)
    {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_PASSWORD, UserPassword);
        editor.commit();
    }

  //  public void createLoginSession(String fname, String lname, String email, String username,
    // String merchantid, String secretkey, String mobielno, String password, String currency, String ConfPayNo ){
    public void createLoginSession(String fname,String lname,String email,String regDate,
                                   String status,String uid,String password,String doj,String currency,String userEncrpted){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_FNAME, fname);

        // Storing name in pref
        editor.putString(KEY_LNAME, lname);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // Storing Reg date in pref
        editor.putString(KEY_REGDATE, regDate);

        // Storing Status in pref
        editor.putString(KEY_STATUS, status);

        editor.putString(KEY_USERNAME,uid);

        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_DOJ,doj);

        editor.putString(KEY_CURRENCY, currency);
//
//        // Storing SecretKey in pref
        editor.putString(KEY_SECRET, userEncrpted);
//
//        // Storing MobileNo in pref
//        editor.putString(KEY_MOBILE, mobielno);
//
//        //Currency
//        editor.putString(KEY_CURRENCYSYMBLE,currency);
//
//        //Currency
//        if(ConfPayNo.equals("0")) {
//            editor.putString(KEY_STATUS, "Not Active");
//        }
//        else
//        {
//            editor.putString(KEY_STATUS, "Active");
//        }

        // commit changes
        editor.commit();
    }
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        // first name
        user.put(KEY_FNAME, pref.getString(KEY_FNAME, null));

        user.put(KEY_SHARE_LEFT, pref.getString(KEY_SHARE_LEFT, null));
        user.put(KEY_SHARE_RIGHT, pref.getString(KEY_SHARE_RIGHT, null));


        // last name
        user.put(KEY_LNAME, pref.getString(KEY_LNAME, null));

        // user User Name
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));


      user.put(KEY_DOJ, pref.getString(KEY_DOJ, null));
        // user MerchantID
     //   user.put(KEY_MERCHANT, pref.getString(KEY_MERCHANT, null));

        // user Secret Key
       // user.put(KEY_SECRET, pref.getString(KEY_SECRET, null));

        //Password
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        //Currency
        user.put(KEY_CURRENCY, pref.getString(KEY_CURRENCY, null));

        //Status
        user.put(KEY_STATUS, pref.getString(KEY_STATUS, null));

        user.put(KEY_SECRET, pref.getString(KEY_SECRET, null));

        user.put(KEY_LEDGER_BALANCE,pref.getString(KEY_LEDGER_BALANCE,null));
user.put(KEY_AVAILABLE_BALANCE,pref.getString(KEY_AVAILABLE_BALANCE,null));
        // return user
        return user;
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }


    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
       setFirstTime(_context);

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public static void setFirstTime(Context _context)
    {
        try {
            SharedPreferences sp = _context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(KEY_FIRST_TIME, false);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean isFirtstTimeInstall(Context context)
    {
        SharedPreferences pref=context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        return pref.getBoolean(KEY_FIRST_TIME,true);
    }
}