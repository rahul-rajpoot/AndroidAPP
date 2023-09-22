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
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alps.shisu.Adapters.SoldButNotJoinListpinListDataAdapter;
import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.GetterSetter.SoldButNotJoinListPinGetterSetter;
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
public class SoldButNotJoinListPinFragment extends Fragment {
    SessionManagement sessionManagement;
    SharedPreferences preferences;
    //ProgressDialog Box
    ProgressDialog progressDialog;
    RecyclerView recyclerViewLp;
    //  SwipeRefreshLayout swipeRefreshLayout;

    List<SoldButNotJoinListPinGetterSetter> LPlist;
    Config ut;
    String urlLP2="";
    private RelativeLayout no_record_found_lay;
    //ProgressBar progressBar;
        AlertDialog dialog;
    public SoldButNotJoinListPinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vi= inflater.inflate(R.layout.fragment_sold_but_not_join_list_pin, container, false);

        preferences=getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        sessionManagement=new SessionManagement(getActivity().getApplicationContext());
        HashMap<String,String> user=sessionManagement.getUserDetails();
        LPlist=new ArrayList<>();

       // progressBar=vi.findViewById(R.id.freeprogress);
        //Circle circle=new Circle();
       // progressBar.setIndeterminateDrawable(circle);
        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
//        Available Epin --- https://api.aplusmart.online/DeveloperApi/getEpinList/M/A345P3Mart/1/AP1298mAtr4340BtMN/sanjeevsingh/SoldButNotJoin
        urlLP2= Config.url +"getEpinList/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/SoldButNotJoin";
        no_record_found_lay= vi.findViewById(R.id.no_record_found_lay);
        Log.e(TAG, "onCreateView: Available Epin : "+ urlLP2 );
        recyclerViewLp=vi.findViewById(R.id.recyclerview_listpinfree);
        recyclerViewLp.setHasFixedSize(true);
        recyclerViewLp.setLayoutManager(new LinearLayoutManager(this.getContext()));
        getdata();


        return vi;
    }

    private void getdata(){
      dialog.show();
        //  progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest request=new StringRequest(Request.Method.GET, urlLP2,
                response -> {
                    try {
                        JSONArray array=new JSONArray(response);
                        for (int j=0;j<array.length();j++){
                            JSONObject object=array.getJSONObject(j);
                            if (object!=null) {
                                LPlist.add(new SoldButNotJoinListPinGetterSetter(
                                        object.getString("EpinNo"),
                                        object.getString("genDate"),
                                        object.getString("usedByID"),
                                        object.getString("usedByName"),
                                        object.getString("usedDate"),
                                        object.getString("MessageCode")
                                ));

                                SoldButNotJoinListpinListDataAdapter soldButNotJoinListpinListDataAdapter = new SoldButNotJoinListpinListDataAdapter(getContext(), LPlist);
                                recyclerViewLp.setAdapter(soldButNotJoinListpinListDataAdapter);
                            }  //                                    Toast.makeText(getContext(),"No Record Found",Toast.LENGTH_LONG).show();

                            if(LPlist.size() == 1 && LPlist.get(0).getEpin2().equalsIgnoreCase("null")){
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
                    //            progressDialog.dismiss();
                }, error -> {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    //    progressDialog.dismiss();
                  //  progressBar.setVisibility(View.GONE);
                });requestQueue.add(request);
    }
}
