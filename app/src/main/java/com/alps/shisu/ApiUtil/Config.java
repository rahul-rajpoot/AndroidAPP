package com.alps.shisu.ApiUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alps.shisu.R;

public class Config {

//    public static final String url="http://shisuapi.alpssoftech.biz/DeveloperAPI/";
    public static final String url="https://api.Shisu.co.in/DeveloperAPI/";
    public static final String BASE_URL="https://shisu.co.in/API/V1/";
//    public static final String merchantid="M/A345P3Mart/1/";
    public static final String merchantid="M/S1298Shi58I/1";
    public static final String securtykey="Sipa12fhhy64jkddd9";

    /**
     * Database name of Cart Item
     */
    public static final String DB_NAME = "Glob_Cart_db";

//    public static final String url="https://api.goldways.in/DeveloperAPI/";
//    public static final String merchantid="M/H1298HyAadnya/1/";
//    public static final String securtykey="H1298HyiCofi4542crTy";

    public static void showCustomToast(Activity context, String message){
        LayoutInflater li = context.getLayoutInflater();
        //Getting the View object as defined in the customtoast.xml file
        View layout = li.inflate(R.layout.custom_toast_layout, null);
        TextView textView = layout.findViewById(R.id.toast_text);
        textView.setText(message);

        //Creating the Toast object
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setView(layout);//setting the view of custom toast layout
        toast.show();
    }


    public static void showProcessDialog(ProgressDialog innerProgressDialog, Context context) {
        if (innerProgressDialog != null) {
            innerProgressDialog.show();
        }
    }

    public static void hideProsessDialog(ProgressDialog innerProgressDialog,Context context) {
        if (innerProgressDialog != null && innerProgressDialog.isShowing()) {
            innerProgressDialog.dismiss();
        }
    }
}
 