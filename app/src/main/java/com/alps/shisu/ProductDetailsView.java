package com.alps.shisu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alps.shisu.Adapters.ViewPagerAdapter;
import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.Database.DatabaseHelper;
import com.alps.shisu.GetterSetter.CartGetter;
import com.alps.shisu.InteroSlider.SliderUtil;
import com.alps.shisu.Session.SessionManagement;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductDetailsView extends BaseActivity{
    String ProId,Proname,Probv,Propv,Promrp,Procpdp,Proimage,prbvs;
    List<SliderUtil> sliderImg;
    String pidvalue,pimage;
    String URLProductsDetails="";
    Config ut;
    CoordinatorLayout dashboard;
    TextView Pname,Pdescrption,SKU,BVAMOUNT,MRP,cutamrp;
    ImageView ShowImages;
    ElegantNumberButton numberButton;
    CollapsingToolbarLayout collapsingToolbarLayout;
    SessionManagement sessionManagement;
    HashMap<String,String> user;
    Toolbar toolbar;
    Context context;
    ViewPager viewPager;
    LinearLayout sliderdots;
    private int dotscount;

    private ImageView[] dots;
    ViewPagerAdapter viewPagerAdapter;
    FloatingActionButton addtocart;
    RequestQueue rq;
    String TotalAmount,userid;
    String request_url="",ProfileUrl="";
    DatabaseHelper myDb;
    String Proqty="1";
    double tot=0;
    String username,useremail,userphone,useraddress,cid,ctid,sid,disid,pincode,ProductQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_view);
        if (!isConnection(ProductDetailsView.this))buildDialog(ProductDetailsView.this).show();
        rq= Volley.newRequestQueue(this);


        sessionManagement=new SessionManagement(getApplicationContext());
        user=sessionManagement.getUserDetails();
        sliderImg=new ArrayList<>();
        final Intent intent=getIntent();
        pidvalue=intent.getStringExtra("pid");
        pimage=intent.getStringExtra("pimg");
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        context=getApplicationContext();
        Pname=(TextView)findViewById(R.id.product_details_name);
        Pdescrption=(TextView)findViewById(R.id.product_descption);
        SKU=(TextView)findViewById(R.id.product_sku);
        dashboard=(CoordinatorLayout)findViewById(R.id.productdetails);
        BVAMOUNT=(TextView)findViewById(R.id.product_bv);
        //   ShowImages=(ImageView)findViewById(R.id.img_food);
        MRP=(TextView) findViewById(R.id.product_details_price);
        cutamrp=(TextView)findViewById(R.id.mrpcuta);
        numberButton=(ElegantNumberButton) findViewById(R.id.number_button);
        collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collasping);
        addtocart=(FloatingActionButton)findViewById(R.id.addcart);
        myDb = new DatabaseHelper(this);

        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppbar);

        //String url="http://api.kalshyangroup.in/DeveloperAPI/CategoryProducts/M/kalshyan4group/1/LIVEKALSHYAN789321/Details/10/1";
       // URLProductsDetails=ut.url+"CategoryProducts/M/NGt541897UY/1/HTOLPAYUYYT/Details/"+pidvalue+"/1";
        URLProductsDetails= Config.url +"CategoryProducts/"+ Config.merchantid +"/"+ Config.securtykey +"/Details/"+pidvalue+"/0";
        //String url="http://api.kalshyangroup.in/DeveloperAPI/CategoryProducts/M/kalshyan4group/1/LIVEKALSHYAN789321/Image/9/1";
        request_url= Config.url +"CategoryProducts/"+ Config.merchantid +"/"+ Config.securtykey +"/Image/"+pidvalue+"/1";
        ProfileUrl= Config.url +"getProfile/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/"+user.get(SessionManagement.KEY_PASSWORD);
        volleydata();

        userid=user.get(SessionManagement.KEY_USERNAME);
        sliderdots=(LinearLayout)findViewById(R.id.sliderdots);
        viewPager=(ViewPager)findViewById(R.id.viewpagger);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i=0;i<dotscount;i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dots));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dots));

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        numberButton.setOnValueChangeListener((view, oldValue, newValue) -> Proqty=numberButton.getNumber());

        // TotalAmounts=String.valueOf(tot);
        // TotalAmount=(Double.parseDouble(Proqty))*(Double.parseDouble(Procpdp));
        // final String total=String.valueOf(TotalAmount);
        addtocart.setOnClickListener(v -> {
            boolean isexist=myDb.checkCartProduct(pidvalue,userid);
            if (!isexist){
                myDb.addtoCart(new CartGetter(
                        pidvalue,
                        Proname,
                        Proqty,
                        Probv,
                        Procpdp,
                        Promrp,
                        pimage,
                        userid,
                        Procpdp
                ));
            }else {

                myDb.CartUpdate(pidvalue,userid);
            }

            Intent inten=new Intent(ProductDetailsView.this,CartActivity.class);
            startActivity(inten);
            Toast.makeText(context, "Item Added to cart", Toast.LENGTH_SHORT).show();

            //           Toast.makeText(context, "Qty"+Proqty, Toast.LENGTH_SHORT).show();
            Snackbar sna=Snackbar.make(dashboard,"Coming Soon",Snackbar.LENGTH_LONG);
            View snackbarView = sna.getView();
            TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
            tv.setTextColor(getResources().getColor(R.color.colorWhite));
            // Change the Snackbar default background color
            snackbarView.setBackgroundColor(getResources().getColor(R.color.tabselectedcolor));
            sna.show();
        });


        loaddata();
        sendRequest();
    }
    public void sendRequest(){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, request_url, null, response -> {
            for (int i=0;i<response.length();i++){
                SliderUtil sliderUtil=new SliderUtil();
                try {
                    JSONObject jsonObject=response.getJSONObject(i);
                    Proimage=jsonObject.getString("Image_url");
                    sliderUtil.setSliderImageUrl(Proimage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sliderImg.add(sliderUtil);
            }
            viewPagerAdapter=new ViewPagerAdapter(sliderImg,ProductDetailsView.this);
            viewPager.setAdapter(viewPagerAdapter);

            dotscount=viewPagerAdapter.getCount();
            dots=new ImageView[dotscount];
            for (int i=0;i<dotscount;i++){
                dots[i]=new ImageView(ProductDetailsView.this);
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dots));
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(8,0,8,0);
                sliderdots.addView(dots[i],params);
            }
            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dots));

        }, error -> {

        });rq.add(jsonArrayRequest);
    }
    private  void volleydata(){
        //progressBar.setVisibility(View.VISIBLE);
        RequestQueue q=Volley.newRequestQueue(getApplicationContext());
        StringRequest request=new StringRequest(Request.Method.GET, ProfileUrl, response -> {
            try {
                JSONArray jsonArray=new JSONArray(response);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    username=  jsonObject.getString("FirstName")+" "+jsonObject.getString("LastName");
                    useraddress=jsonObject.getString("Address");
                    useremail=jsonObject.getString("EmailID");
                    userphone=jsonObject.getString("MobileNo");
                    cid=jsonObject.getString("CID");
                    sid=jsonObject.getString("SID");
                    ctid=jsonObject.getString("CTID");
                    disid=jsonObject.getString("DISTID");
                    pincode=jsonObject.getString("PinCode");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //progressBar.setVisibility(View.GONE);
        }, error -> {
            Toast.makeText(ProductDetailsView.this, "Your Internet Connection Weak!!!   ", Toast.LENGTH_SHORT).show();
            // progressBar.setVisibility(View.GONE);
        });q.add(request);

    }
    private void loaddata() {
        RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
        StringRequest request=new StringRequest(Request.Method.GET, URLProductsDetails, response -> {
            try {
                JSONArray jsonArray=new JSONArray(response);
                JSONObject jsonObject=jsonArray.getJSONObject(0);
                Proname=jsonObject.getString("Pname");
                Pname.setText(Proname);

                Procpdp=jsonObject.getString("DP");
                Promrp=jsonObject.getString("MRP");
                Probv=jsonObject.getString("BV");
                ProductQty=jsonObject.getString("Quantity");
                Pdescrption.setText(jsonObject.getString("Description"));
                SKU.setText(jsonObject.getString("Pcode"));
               // Propv=jsonObject.getString("PV");
                BVAMOUNT.setText(Probv);
               // product_bvs.setText(Propv);
                MRP.setText(user.get(SessionManagement.KEY_CURRENCY)+Procpdp);
                cutamrp.setText(user.get(SessionManagement.KEY_CURRENCY)+Promrp);
                cutamrp.setPaintFlags(cutamrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                //  total=Integer.parseInt(Procpdp)*Integer.parseInt(Proqty);
                //    TotalAmount=String.valueOf(total);
                // toolbar.setTitle(name);
                //  String Image=jsonObject.getString("Imgurl_default");
                // Picasso.with(context).load(Image).into(ShowImages);
                tot=(Double.parseDouble(Proqty))*(Double.parseDouble(Procpdp));
                int start=1;
                int end=Integer.parseInt(ProductQty);
                numberButton.setRange(start,end);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });queue.add(request);
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
    public AlertDialog.Builder buildDialog(Context context) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or Wifi to access this. Press ok to Exit");

        builder.setPositiveButton("OK", (dialogInterface, i) -> finish());
        return builder;
    }
}
