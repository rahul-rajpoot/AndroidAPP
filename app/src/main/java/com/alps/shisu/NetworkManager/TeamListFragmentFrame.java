package com.alps.shisu.NetworkManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alps.shisu.Adapters.TeamListRecyclerviewAdapter;
import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.GetterSetter.TeamListGetterSetter;
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

public class
TeamListFragmentFrame extends Fragment {
    Config ut;
    String downlineurl="";
    SessionManagement sessionManagement;
    SharedPreferences preferences;
    RecyclerView recyclerView;

    List<TeamListGetterSetter> listData;
    String siderl,cutoofpayid,sessionyear;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView redpepole,purplepeople,greenpeople,totalpeople;
    Toolbar teamlisttoolbar;
    AlertDialog dialog;
    public TeamListFragmentFrame() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_team_list_fragment_frame, container, false);

        preferences=getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        sessionManagement=new SessionManagement(getContext());
        HashMap<String,String> user=sessionManagement.getUserDetails();

        redpepole=view.findViewById(R.id.search_red_mem);
        purplepeople=view.findViewById(R.id.search_purple_mem);
        greenpeople=view.findViewById(R.id.search_green_mem);
        totalpeople=view.findViewById(R.id.searchtotal);

        Bundle bundle=getArguments();
        siderl=bundle.getString("rlside");
        cutoofpayid=bundle.getString("id");
        sessionyear=bundle.getString("year");

        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();

        //downlineurl=ut.url+"getDownlineList/"+ut.merchantid+"/"+ut.securtykey+"/"+user.get(sessionManagement.KEY_USERNAME)+"/"+siderl+"@"+cutoofpayid+"@T@"+sessionyear;

        downlineurl= Config.url +"getDownlineList/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/L@1@T@"+sessionyear;

        recyclerView=view.findViewById(R.id.teamlist_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        listData=new ArrayList<>();

        getData();
        return view;
    }

    private void getData(){
       dialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, downlineurl, response -> {
            try {
                JSONArray jsonArray=new JSONArray(response);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    // if (jsonObject.getString("Package").equals("Red")){

                    redpepole.setText(jsonObject.getString("RedCount"));
                    greenpeople.setText(jsonObject.getString("GreenCount"));
                    purplepeople.setText(jsonObject.getString("PurpleCount"));
                    totalpeople.setText(jsonObject.getString("TotalCount"));
                    listData.add(new TeamListGetterSetter(
                            jsonObject.getString("Loginid"),
                            jsonObject.getString("Package"),
                            jsonObject.getString("fullName"),
                            jsonObject.getString("SponsorCount"),
                            jsonObject.getString("RepurchaseBV")
                    ));
                    TeamListRecyclerviewAdapter adapters=new TeamListRecyclerviewAdapter(getContext(),listData);
                    recyclerView.setAdapter(adapters);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        if (dialog.isShowing())
            dialog.dismiss();
        }, error -> {
            if (dialog.isShowing())
                dialog.dismiss();
        });requestQueue.add(stringRequest);

    }

}
