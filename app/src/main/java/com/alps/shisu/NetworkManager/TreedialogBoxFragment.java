package com.alps.shisu.NetworkManager;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class TreedialogBoxFragment extends DialogFragment {
    SharedPreferences shared;
    Config ut;
    String dialogurl="";
    TextView ids;
    TextView l,leftcurrentid,lefttotal;
    TextView r,rightcurrentid,rightTTotal;
    String spid;
    SessionManagement sessionManagement;

    public TreedialogBoxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vtr= inflater.inflate(R.layout.fragment_treedialog_box, container, false);

        ids=vtr.findViewById(R.id.SponsorId);

        l=vtr.findViewById(R.id.left_previousid);
        leftcurrentid=vtr.findViewById(R.id.left_currentid);
        lefttotal=vtr.findViewById(R.id.left_totalid);

        r=vtr.findViewById(R.id.right_previousid);
        rightcurrentid=vtr.findViewById(R.id.right_currentid);
        rightTTotal=vtr.findViewById(R.id.right_totalid);


        Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"fonts/digital-7.ttf");
       // ids.setTypeface(typeface);
        l.setTypeface(typeface);
        leftcurrentid.setTypeface(typeface);
        lefttotal.setTypeface(typeface);
        r.setTypeface(typeface);
        rightcurrentid.setTypeface(typeface);
        rightTTotal.setTypeface(typeface);

        shared=getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);

        Bundle bundle1=getArguments();
        if (null!=bundle1){
            spid=bundle1.getString("leftchildid");
        }
        dialogurl= Config.url +"getBinary/"+ Config.merchantid +"/"+ Config.securtykey +"/"+spid+"/s";
        ids.setText(spid);
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, dialogurl,
                response -> {
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        JSONObject jsonObject=jsonArray.getJSONObject(0);
                        String leftcuid=jsonObject.getString("CFPVLeft");
                        l.setText(leftcuid);
                        String leftpvleft=jsonObject.getString("currentPVLeft");
                        leftcurrentid.setText(leftpvleft);

                        String leftTotal=jsonObject.getString("leftRemainingTotal");
                        lefttotal.setText(leftTotal);

                        String rightcuid=jsonObject.getString("CFPVRight");
                        r.setText(rightcuid);


                        String rightpvrright=jsonObject.getString("currentPVRight");
                        rightcurrentid.setText(rightpvrright);
                        String righttotal=jsonObject.getString("rightRemainingTotal");
                        rightTTotal.setText(righttotal);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {

                });requestQueue.add(stringRequest);

        return  vtr;
    }
}