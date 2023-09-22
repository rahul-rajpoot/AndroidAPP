package com.alps.shisu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.content.ContentValues.TAG;

public class PaymentConfirmActivity extends BaseActivity{

    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirm);

        webView = findViewById(R.id.web_view);

        if(getIntent() != null) {

            String orderNo = getIntent().getStringExtra("OrderNo");

            String url = "https://aplusmart.online/payment/appPay.aspx?id="+ orderNo;
            Log.e(TAG, "PaymentConfirmActivity: "+url);

            WebSettings webSettings = webView.getSettings();

            webView.setWebViewClient(new WebViewClient());
            webView.setWebChromeClient(new WebChromeClient());

            webSettings.setUserAgentString("Mozilla/5.0 (Linux; Win64; x64; rv:46.0) Gecko/20100101 Firefox/68.0");
            webSettings.setGeolocationEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setDatabaseEnabled(true);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setSupportMultipleWindows(true);
            webSettings.setSupportZoom(true);

            webView.setInitialScale(0);
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.setWebViewClient(new MyBrowser());

            final Activity activity = this;
            webView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    activity.setProgress(progress * 1000);
                }
            });

            webSettings.setAllowFileAccess(true);
            webSettings.setAppCacheEnabled(true);
            loadWebSite(url);
        }


    }

    private static class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }
    }

    /**
     * Load different type urls with different setting of WebView
     *
     * @param url Url from server
     */
    private void loadWebSite(String url) {
        Log.e("TAG", "loadWebSite: Url : " + url);
//        if (type.equalsIgnoreCase("HTML")) {
//            Log.e("TAG", "loadWebSite: html loaded : " + url);
//            final String mimeType = "text/html";
//            final String encoding = "UTF-8";
//            webView.loadData(
//                    String.valueOf(Html.fromHtml(url)), mimeType, encoding);
//        }
//        else if (type.equalsIgnoreCase("Images") || type.equalsIgnoreCase("GIF File") || type.equalsIgnoreCase("Scanned Paper")) {
//            Log.e("TAG", "loadWebSite: Images or Scanned Paper loaded : " + url);
//            final String mimeType = "text/html";
//            final String encoding = "UTF-8";
//            String Html = "<tr><td> <img src=\"" + url + "\"height=\"100%" + "\"width=\"100%" + "\"></tr></tr>";
//            webView.loadDataWithBaseURL("", Html, mimeType, "utf-8", "");
//        } else if (type.equalsIgnoreCase("Rich Text") || type.equalsIgnoreCase("Text")) {
//            Log.e("TAG", "loadWebSite: Rich Text or Text : " + url);
//            final String mimeType = "text/html";
//            webView.loadDataWithBaseURL("", Html.fromHtml(url).toString(), mimeType, "utf-8", "");
//        } else if (type.equalsIgnoreCase("PDF File") || type.equalsIgnoreCase("Word File")) {
//            Log.e("TAG", "loadWebSite: PDF File or Word File : " + url);
//            String weblink = "http://docs.google.com/gview?embedded=true&url=" + url;
//            webView.loadUrl(weblink);
//        } else {
            webView.loadUrl(url);
//        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}

