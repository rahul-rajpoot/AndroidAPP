package com.alps.shisu.ProfileManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.alps.shisu.BaseActivity;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;

import java.util.HashMap;

public class KYCOPSTWebActivity extends BaseActivity {

    WebView webview;
    Context context;
    private ValueCallback<Uri> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE=1;
    ProgressBar progressBar;
    HashMap<String,String> user;
    SharedPreferences preferences;
    public SessionManagement session;
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return;
            Uri result = intent == null || resultCode != RESULT_OK ? null
                    : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_y_c_o_p_s_t_web);

        webview =findViewById(R.id.videoView1);

        final String url ="https://kalshyangroup.com/BOM/kycFormMobile.aspx?u="+user.get(SessionManagement.KEY_USERNAME)+"&p="+user.get(SessionManagement.KEY_PASSWORD);
      //  final String url="https://kalshyangroup.com/BOM/kycFormMobile.aspx?u=startid&p=12345";
        webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);

        webview.getSettings().setUseWideViewPort(true);
//        webview.setWebChromeClient(new WebChromeClient());
  //      webview.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Win64; x64; rv:46.0) Gecko/20100101 Firefox/68.0");
    //    webview.getSettings().setGeolocationEnabled(true);
      //  webview.getSettings().setDomStorageEnabled(true);
        //webview.getSettings().setDatabaseEnabled(true);
       // webview.getSettings().setSupportMultipleWindows(true);
      //  webview.getSettings().setAppCacheEnabled(true);
       // webview.getSettings().setNeedInitialFocus(true);
       // webview.getSettings().setLoadWithOverviewMode(true);
       // webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
       // webview.getSettings().setBlockNetworkImage(true);
       // webview.getSettings().setPluginState(WebSettings.PluginState.ON);
       // webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        webview.setWebChromeClient(new WebChromeClient(){
            //The undocumented magic method override
            //Eclipse will swear at you if you try to put @Override here
            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {

                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                KYCOPSTWebActivity.this.startActivityForResult(Intent.createChooser(i,"File Chooser"), FILECHOOSER_RESULTCODE);

            }

            // For Android 3.0+
            public void openFileChooser( ValueCallback uploadMsg, String acceptType ) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                KYCOPSTWebActivity.this.startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FILECHOOSER_RESULTCODE);
            }

            //For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                KYCOPSTWebActivity.this.startActivityForResult( Intent.createChooser( i, "File Chooser" ), KYCOPSTWebActivity.FILECHOOSER_RESULTCODE );

            }
        });
        setContentView(webview);
        final AppCompatActivity activity = this;
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 1000);
            }
        });
    }

    //Internet Connection check
    public  boolean isConnection(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo=connectivityManager.getActiveNetworkInfo();

        if (netinfo !=null&&netinfo.isConnectedOrConnecting()){
            NetworkInfo wifi=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile!=null && mobile.isConnectedOrConnecting()) || (wifi!=null && wifi.isConnectedOrConnecting() ))return true;
            else return false;

        }else
            return false;

    }
}