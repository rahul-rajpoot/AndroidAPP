package com.alps.shisu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alps.shisu.Adapters.CartRecyclerviewAdapter;
import com.alps.shisu.Database.DatabaseHelper;
import com.alps.shisu.GetterSetter.CartGetter;
import com.alps.shisu.Session.SessionManagement;
import com.alps.shisu.Shopping.ShoppingActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CartActivity extends BaseActivity{
    SessionManagement sessionManagement;
    HashMap<String,String> user;
    public String Userid;
    public RecyclerView recyclerViewcart;
    RecyclerView.LayoutManager layoutManager;
    DatabaseHelper mydb;
    public TextView txtTotalPrice,textnoitenaddtocart;
    public CartRecyclerviewAdapter adapter;
    Toolbar toolbarcart;
    Button addcartbtn,shopnowbutton;
    double total=0,sum=0;
    String cartvaluesum,loginid;
    double bvtoal=0;
    final double pvtoal=0;
    List<CartGetter> list=new ArrayList<>();
    ProgressBar progressBar;
    private BottomSheetBehavior mBottomSheetBehaviour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        if (!isConnection(CartActivity.this))buildDialog(CartActivity.this).show();

        sessionManagement=new SessionManagement(getApplicationContext());
        user=sessionManagement.getUserDetails();
        //Toolbar
        toolbarcart=findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbarcart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Cart");
        toolbarcart.setTitleTextColor(Color.WHITE);
        loginid=user.get(SessionManagement.KEY_USERNAME);
        mydb = new DatabaseHelper(this);
        list=mydb.getCart(loginid);
        progressBar=findViewById(R.id.progresscart);
        recyclerViewcart=(RecyclerView)findViewById(R.id.recyclerview_cart);
        layoutManager=new LinearLayoutManager(this);
        recyclerViewcart.setLayoutManager(new LinearLayoutManager(this));
        for (CartGetter order:list) {
            bvtoal += (Double.parseDouble(order.getProductesBv())) * (Double.parseDouble(order.getProductesQty()));
          //  pvtoal += (Double.parseDouble(order.getProductesPv())) * (Double.parseDouble(order.getProductesQty()));
        }
        Userid=user.get(SessionManagement.KEY_USERNAME);
        addcartbtn=(Button)findViewById(R.id.btnPlaceOrder);
        shopnowbutton=findViewById(R.id.shopnowbutton);
        textnoitenaddtocart=findViewById(R.id.textnoitenaddtocart);
        txtTotalPrice=(TextView)findViewById(R.id.total);


        cartvaluesum=mydb.TotalCal(loginid);
        sum=Double.parseDouble(cartvaluesum);
        loadListCart();
        checklistsize();

        shopnowbutton.setOnClickListener(v -> {
            Intent intent=new Intent(CartActivity.this,ShoppingActivity.class);
            startActivity(intent);
        });

        addcartbtn.setOnClickListener(v -> {

            if (list.size() > 0) {

                // double ve=adapter.total;

                Intent intent = new Intent(CartActivity.this,PaymentActivity.class);
                //    adapter.onBindViewHolder(total);
                for (CartGetter cartGetter1:list)
                    intent.putExtra("product_id",cartGetter1.getTotalAMounts());
                // intent.putExtra("product_id",String.valueOf(total));
                intent.putExtra("pbv",String.valueOf(bvtoal));
                intent.putExtra("pv",String.valueOf(pvtoal));

                for (CartGetter cartGetter1:list)
                    intent.putExtra("pid",cartGetter1.getProductesid());
                for (CartGetter cartGetter1:list)
                    intent.putExtra("pqty",cartGetter1.getProductesQty());
                for (CartGetter cartGetter1:list)
                    intent.putExtra("pmrp",cartGetter1.getProductesMrpc());
                for (CartGetter cartGetter1:list)
                    intent.putExtra("dpmrp",cartGetter1.getProductesCp().toString());

                startActivity(intent);

                  //  Toast.makeText(CartActivity.this, "ok", Toast.LENGTH_SHORT).show();

            } else {
                    Toast.makeText(CartActivity.this, "Your cart is empty !!!", Toast.LENGTH_SHORT).show();

            }
        });


}

    private void checklistsize() {
        if (list.size()==0){
            addcartbtn.setVisibility(View.GONE);
            recyclerViewcart.setVisibility(View.GONE);
            shopnowbutton.setVisibility(View.VISIBLE);
            textnoitenaddtocart.setVisibility(View.VISIBLE);
        }else {
            addcartbtn.setVisibility(View.VISIBLE);
            recyclerViewcart.setVisibility(View.VISIBLE);
            shopnowbutton.setVisibility(View.GONE);
            textnoitenaddtocart.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){

            Intent intent=new Intent(getApplicationContext(), ShoppingActivity.class);
            intent.putExtra("s",0);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //overridePendingTransition(R.anim.side_in_left,R.anim.slide_out_right);

            //end the activity
//            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CartActivity.this, DashBoard.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    private void loadListCart() {
        adapter=new CartRecyclerviewAdapter(list,this);
        adapter.notifyDataSetChanged();
        recyclerViewcart.setAdapter(adapter);


        adapter.setOnItemClickListener(position -> {
            mydb.deleteTitle(list.get(position).getProductesid());
            adapter.notifyItemRemoved(position);


        });

       for (CartGetter order:list)
           total=(Double.parseDouble(order.getProductesCp().toString()))*(Double.parseDouble(order.getProductesQty()));
       Locale locale=new Locale("en","IN");
       NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);
        txtTotalPrice.setText(user.get(SessionManagement.KEY_CURRENCY)+sum);
    }

    //Internet Connection check
    public  boolean isConnection(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo netinfo=connectivityManager.getActiveNetworkInfo();

        if (netinfo !=null && netinfo.isConnectedOrConnecting()){
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

    @Override
    protected void onResume() {

        super.onResume();
    }
}
