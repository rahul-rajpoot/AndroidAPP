package com.alps.shisu.KycManager;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alps.shisu.Adapters.AllotedListPinListDataAdapter;
import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.GetterSetter.AllotedListPinGetterSetter;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllListPinFragment extends Fragment {

    SessionManagement sessionManagement;
    SharedPreferences preferences;
    //ProgressDialog Box
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog progressDialog;
    RecyclerView recyclerViewLp;
    List<AllotedListPinGetterSetter>LPlist;
    Config ut;
    String urlLP="";
    ProgressBar progressBar;
    AlertDialog dialog;
    private RelativeLayout no_record_found_lay;
    public AllListPinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_alloted_list_pin, container, false);
        preferences=getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        sessionManagement=new SessionManagement(getActivity().getApplicationContext());
        HashMap<String,String> user=sessionManagement.getUserDetails();
        LPlist=new ArrayList<>();
        urlLP= Config.url +"getEpinList/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/Sold";

        Log.e(TAG, "onCreateView: all Pin : "+ urlLP );
        no_record_found_lay= v.findViewById(R.id.no_record_found_lay);
        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
        recyclerViewLp=v.findViewById(R.id.recyclerview_listpinSold);
        recyclerViewLp.setHasFixedSize(true);
        recyclerViewLp.setLayoutManager(new LinearLayoutManager(this.getContext()));
     //   progressBar=v.findViewById(R.id.soldprogress);
       // Circle circle=new Circle();
      //  progressBar.setIndeterminateDrawable(circle);
        getdata();
        return v;
    }

    private void getdata(){
      dialog.show();
        //  progressBar.setVisibility(View.VISIBLE);
        //  swipeRefreshLayout.setRefreshing(true);
        RequestQueue requestQueue2= Volley.newRequestQueue(getContext());
        StringRequest request2=new StringRequest(Request.Method.GET, urlLP,
                response -> {
                    try {
                        JSONArray array2=new JSONArray(response);
                        for (int j=0;j<array2.length();j++) {
                            JSONObject object2 = array2.getJSONObject(j);
                            if (object2!=null) {
                                LPlist.add(new AllotedListPinGetterSetter(
                                        object2.getString("EpinNo"),
                                        object2.getString("genDate"),
                                        object2.getString("usedByID"),
                                        object2.getString("usedByName"),
                                        object2.getString("usedDate"),
                                        object2.getString("MessageCode")
                                ));
                                AllotedListPinListDataAdapter allotedListPinListDataAdapter = new AllotedListPinListDataAdapter(getContext(), LPlist);
                                recyclerViewLp.setAdapter(allotedListPinListDataAdapter);
                            } //                                    Toast.makeText(getContext(),"No Record Found",Toast.LENGTH_LONG).show();

                            if(LPlist.size() == 1 && LPlist.get(0).getEpin().equalsIgnoreCase("null")){
                                no_record_found_lay.setVisibility(View.VISIBLE);
                                recyclerViewLp.setVisibility(View.GONE);
                            } else {
                                no_record_found_lay.setVisibility(View.GONE);
                                recyclerViewLp.setVisibility(View.VISIBLE);
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (dialog.isShowing())
                        dialog.dismiss();
                    //  progressBar.setVisibility(View.GONE);
                    //      swipeRefreshLayout.setRefreshing(false);
                    //             progressDialog.dismiss();
                }, error -> {
                    //       progressDialog.dismiss();
                    if (dialog.isShowing())
                        dialog.dismiss();
                   // progressBar.setVisibility(View.GONE);
                    //   swipeRefreshLayout.setRefreshing(false);
                });requestQueue2.add(request2);
    }
}
