package com.alps.shisu;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alps.shisu.Adapters.AOProductAdapter;
import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.ApiUtil.SpacesItemDecoration;
import com.alps.shisu.GetterSetter.AOProductGetter;
import com.alps.shisu.Shopping.ShoppingActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class AO_Product_list_Activity extends BaseActivity implements AOProductAdapter.OnItemClickListener{

    String Productid,catname;

    RecyclerView productrecyclerview;
    String ProductUrl;
    Config ut;
    Toolbar toolbar;
    final List<AOProductGetter> list=new ArrayList<>();
    AOProductAdapter adapter;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ao__product_list_);
        Intent intent=getIntent();
        Productid=intent.getStringExtra("catid");
        catname=intent.getStringExtra("catname");
        dialog=new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        toolbar=(Toolbar)findViewById(R.id.toolbar_ao_product);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(catname+" Product");
        toolbar.setTitleTextColor(Color.WHITE);

        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        productrecyclerview=(RecyclerView)findViewById(R.id.recyclerview_ao_product);
        productrecyclerview.setLayoutManager(new GridLayoutManager(this,2));
        productrecyclerview.addItemDecoration(new SpacesItemDecoration(8));

       // ProductUrl=ut.url+"CategoryProducts/M/NGt541897UY/1/HTOLPAYUYYT/Products/"+Productid+"/1";
//        ProductUrl=ut.url+"CategoryProducts/"+ut.merchantid+"/"+ut.securtykey+"/Products/0/1";
        ProductUrl= Config.url +"CategoryProducts/"+ Config.merchantid +"/"+ Config.securtykey +"/Products/0/1";
        loadProduct();
    }

    private void loadProduct() {
        dialog.show();
       // progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request=new StringRequest(Request.Method.GET, ProductUrl, response -> {
            try {
                JSONArray jsonArray=new JSONArray(response);
                for (int i=0;i<=jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    list.add(new AOProductGetter(
                            jsonObject.getString("BV"),
                            jsonObject.getString("DP"),
                            jsonObject.getString("Imgurl"),
                            jsonObject.getString("MRP"),
                            jsonObject.getString("Pid"),
                            jsonObject.getString("Pname"),
                            jsonObject.getString("PV")

                    ));
                    adapter=new AOProductAdapter(AO_Product_list_Activity.this,list,ImageLoader.getInstance());
                    productrecyclerview.setAdapter(adapter);
                    adapter.setOnItemClickListener(AO_Product_list_Activity.this);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing())
                dialog.dismiss();
          //  progressBar.setVisibility(View.GONE);
        }, error -> {
            if (dialog.isShowing())
                dialog.dismiss();
           // progressBar.setVisibility(View.GONE);
        });requestQueue.add(request);
    }


    @Override
    public void onItemClick(int position) {
        Intent godetailsproducts=new Intent(getApplicationContext(),ProductDetailsView.class);
        AOProductGetter getter=list.get(position);
        godetailsproducts.putExtra("pid",getter.getPid());
        godetailsproducts.putExtra("pimg",getter.getImageUrl());
        // ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation(PurchaseNowProduct_List.this,)
        startActivity(godetailsproducts);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){

            Intent intent=new Intent(getApplicationContext(), ShoppingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}
