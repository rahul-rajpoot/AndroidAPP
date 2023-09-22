package com.alps.shisu.NetworkManager;


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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alps.shisu.Adapters.DirectSponsorListDataAdapter;
import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.GetterSetter.DirectSponsorGetterSetter;
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
public class DirectSponsorFragment extends Fragment {

    ProgressDialog progressDialog;
    Config ut;
    ProgressBar progressBar;
    String directsurl="";
    SessionManagement sessionManagement;
    SharedPreferences preferences;
    RecyclerView recyclerViewDS;
    List<DirectSponsorGetterSetter> lis;
    AlertDialog dialog;


    public DirectSponsorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_direct_sponsor, container, false);
        lis=new ArrayList<>();
        preferences=getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        sessionManagement = new SessionManagement(getActivity().getApplicationContext());
        HashMap<String,String> user = sessionManagement.getUserDetails();
//        directsurl= Config.url +"sponsorlist/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/"+user.get(SessionManagement.KEY_PASSWORD);
        directsurl= Config.url +"MyStructure/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/0";
        // progressDialog=new ProgressDialog(getActivity());
        //   progressDialog.setCancelable(true);
//        Dates=de.findViewById(R.id.date);
        Log.e(TAG, "onCreateView: "+ directsurl );
        progressBar = view.findViewById(R.id.directsponsorprogress);
        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();

        recyclerViewDS = view.findViewById(R.id.recyclerview_DS);
        recyclerViewDS.setHasFixedSize(true);
        recyclerViewDS.setLayoutManager(new LinearLayoutManager(this.getContext()));

        getdata();
        return view;
    }

    private void getdata() {
       dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, directsurl,
                response -> {

//                    object.getString("Package"),
//                            object.getString("regDate"),
//                            object.getString("regNo"),
//                            object.getString("Status"),
//                            object.getString("SponsorCount"),
                    try {
                        lis.clear();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            lis.add(new DirectSponsorGetterSetter(
                                    object.getString("Tooltipname"),
                                    object.getString("Loginid"),
                                    "Green",
                                    "",
                                    "",
                                    "True",
                                    "",
                                    object.getString("Curr_GRBV"),
                                    object.getString("Curr_PRBV"),
                                    object.getString("Curr_TRBV"),
                                    object.getString("Pre_GRBV"),
                                    object.getString("Pre_PRBV"),
                                    object.getString("Pre_TRBV"),
                                    object.getString("tot_GRBV"),
                                    object.getString("tot_PRBV"),
                                    object.getString("tot_TRBV"),
                                    object.getString("rank"),
                                    object.getString("slab")
                            ));
                        }

                        DirectSponsorListDataAdapter data = new DirectSponsorListDataAdapter(getContext(), lis);
                        recyclerViewDS.setAdapter(data);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                if (dialog.isShowing())
                    dialog.dismiss();

                }, error -> {
                    //  Toast.makeText(getContext(),"An error has occurred.",Toast.LENGTH_LONG).show();
    //progressDialog.dismiss();
                   // progressBar.setVisibility(View.GONE);
                    if (dialog.isShowing())
                        dialog.dismiss();
                 //   Toast.makeText(getContext(),"An error has occurred.",Toast.LENGTH_LONG).show();
                    //  swipeRefreshLayout.setRefreshing(false);
                });requestQueue.add(stringRequest);
    }
}
