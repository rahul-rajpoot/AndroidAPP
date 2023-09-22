package com.alps.shisu.Shopping;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alps.shisu.Adapters.AOProductAdapter;
import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.ApiUtil.SpacesItemDecoration;
import com.alps.shisu.GetterSetter.AOProductGetter;
import com.alps.shisu.ProductDetailsView;
import com.alps.shisu.R;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivationOrderFragment extends Fragment implements AOProductAdapter.OnItemClickListener{

    String Productid,catname;

    RecyclerView productrecyclerview;
    String ProductUrl;
    Config ut;
    Toolbar toolbar;
    final List<AOProductGetter> list=new ArrayList<>();
    AOProductAdapter adapter;
    AlertDialog dialog;

    public ActivationOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_activation_order, container, false);
        dialog=new SpotsDialog.Builder().setContext(getActivity()).setCancelable(false).build();


        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(getActivity()).build();
        ImageLoader.getInstance().init(config);
        productrecyclerview=(RecyclerView)view.findViewById(R.id.recyclerview_ao_product);
        productrecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        productrecyclerview.addItemDecoration(new SpacesItemDecoration(8));

        // ProductUrl=ut.url+"CategoryProducts/M/NGt541897UY/1/HTOLPAYUYYT/Products/"+Productid+"/1";
        ProductUrl= Config.url +"CategoryProducts/"+ Config.merchantid +"/"+ Config.securtykey +"/Products/0/1";
        loadProduct();


        return view;
    }

    private void loadProduct() {
        dialog.show();
        // progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
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
                    adapter=new AOProductAdapter(getContext(),list,ImageLoader.getInstance());
                    productrecyclerview.setAdapter(adapter);
                    adapter.setOnItemClickListener(ActivationOrderFragment.this);


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
        Intent godetailsproducts=new Intent(getContext(), ProductDetailsView.class);
        AOProductGetter getter=list.get(position);
        godetailsproducts.putExtra("pid",getter.getPid());
        godetailsproducts.putExtra("pimg",getter.getImageUrl());
        // ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation(PurchaseNowProduct_List.this,)
        startActivity(godetailsproducts);

    }
}
