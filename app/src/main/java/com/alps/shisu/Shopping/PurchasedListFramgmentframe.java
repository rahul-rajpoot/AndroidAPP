package com.alps.shisu.Shopping;


import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alps.shisu.Adapters.PurchasedlistRecyclerviewAdapter;
import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.GetterSetter.PurchaseListGetter;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchasedListFramgmentframe extends Fragment {

    String ordno;
    String fromdate;
    String todate;
    String statusvalue;
    RecyclerView recyclerView;
    String Url="";
    Config ut;
    SessionManagement sessionManagement;
    HashMap<String,String> user;
    List<PurchaseListGetter> list;
    CoordinatorLayout coordinatorLayout;
    ProgressBar progressBar;


    public PurchasedListFramgmentframe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_purchased_list_framgmentframe, container, false);
        sessionManagement=new SessionManagement(getContext());
        user=sessionManagement.getUserDetails();

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview_purchasedlist);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        list=new ArrayList();

        //progressBar=view.findViewById(R.id.purchasedlistloadprogress);
        //Circle circle=new Circle();
        // progressBar.setIndeterminateDrawable(circle);

        coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinatorpl);

        Bundle bundle=getArguments();
        // ordno = bundle.getString("orderno");
        fromdate = bundle.getString("fdate");
        todate = bundle.getString("tdate");
        statusvalue = bundle.getString("status");
       // Url=ut.url+"Orderlist/"+ut.merchantid+ut.securtykey+"/"+user.get(sessionManagement.KEY_USERNAME)+"/Purchase/"+statusvalue+"/"+fromdate+"/"+todate;
       // Url=ut.url+"Orderlist/M/NGt541897UY/1/HTOLPAYUYYT/"+user.get(sessionManagement.KEY_USERNAME)+"/Purchase/"+statusvalue+"/"+fromdate+"/"+todate;
      //confirm list url
        Url= Config.url +"Orderlist/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/Purchase/0/0/0";
        //pending list url
       // Url=ut.url+"Orderlist/M/NGt541897UY/1/HTOLPAYUYYT/"+user.get(sessionManagement.KEY_USERNAME)+"/Purchase/0/0/0";
        loadRecyclerview();
        return view;
    }

    private void loadRecyclerview() {
//        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue= Volley.newRequestQueue(getContext());
        StringRequest request=new StringRequest(Request.Method.GET, Url, response -> {
            try {
                JSONArray jsonArray=new JSONArray(response);
                for (int i=0;i<=jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    if (jsonObject.getString("Status").equals("0"))
                    {
                        Snackbar snackbar=Snackbar.make(coordinatorLayout,"Not Record Found",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }else{
                        if (list!=null){
                            list.add(new PurchaseListGetter(
                                    jsonObject.getString("Amount"),
                                    jsonObject.getString("BV"),
                                    jsonObject.getString("InvDate"),
                                    jsonObject.getString("InvNO"),
                                    jsonObject.getString("OrderDate"),
                                    jsonObject.getString("OrderNo"),
                                    jsonObject.getString("Qty"),
                                    jsonObject.getString("Status"),
                                    jsonObject.getString("Orderurl"),
                                    jsonObject.getString("Invurl"),
                                    jsonObject.getString("PV")
                            ));
                            PurchasedlistRecyclerviewAdapter adapter = new PurchasedlistRecyclerviewAdapter(getContext(), list,list);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);

                        }else {
                            Snackbar snackbar=Snackbar.make(coordinatorLayout,"Not Record Found",Snackbar.LENGTH_LONG);
                            snackbar.show();

                        }
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
          //  progressBar.setVisibility(View.GONE);
        }, error -> {
            Snackbar snackbar=Snackbar.make(coordinatorLayout,"Not Record Found",Snackbar.LENGTH_LONG);
            snackbar.show();
          //  progressBar.setVisibility(View.GONE);
        });queue.add(request);

    }

}
