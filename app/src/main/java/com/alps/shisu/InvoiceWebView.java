package com.alps.shisu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.alps.shisu.Session.SessionManagement;

import java.util.HashMap;

public class InvoiceWebView extends BaseActivity{
    Button Backgo;
    String invoice;
    WebView mwebView;
    ProgressBar bar;
    SessionManagement sessionManagement;
    HashMap<String,String> user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_web_view);
        bar = (ProgressBar) findViewById(R.id.progressBarin);
        // initialize bar
        sessionManagement=new SessionManagement(getApplicationContext());
        Backgo=findViewById(R.id.back);
        // preferences=getSharedPreferences("ALPSPref",MODE_PRIVATE);
        user=sessionManagement.getUserDetails();
        Intent intent=getIntent();
        invoice=intent.getStringExtra("in");
        // DashboardUrl=ut.url+"OBDashboard"+ut.merchantid+ut.securtykey+user.get(sessionManagement.KEY_USERNAME)+"/"+user.get(sessionManagement.KEY_PASSWORD);
       // String value=user.get(sessionManagement.KEY_ENCRYPTEDUSENAME_URL);
        mwebView = (WebView) findViewById(R.id.webViewin);
        mwebView.loadUrl(invoice);
        mwebView.getSettings().setJavaScriptEnabled(true);
        mwebView.setWebViewClient(new MyWebViewClient());

        Backgo.setOnClickListener(v -> {
            Intent inte=new Intent(getApplicationContext(),DashBoard.class);
            startActivity(inte);
        });
    }
    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
            bar.setVisibility(View.VISIBLE);
            // ^^^ use it as it is

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            bar.setVisibility(View.GONE);
            // ^^^ use it as it is
            super.onPageFinished(view, url);
        }
    }


}
