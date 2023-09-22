package com.alps.shisu.ProfileManager;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewProfileFragment extends Fragment {

    Config ut;
    String ProfileUrl="", image;
    SessionManagement sessionManagement;
    HashMap<String,String> user;
    SharedPreferences preferences;
    AlertDialog dialog;
    //Personal Deatils
    CircularImageView profileimage;
    Context context;
    TextView username, name, bday, gender, registration, mail, mobile, noimee_name, noimee_relation;
    //Bank
    TextView banknam,bankusername,pancardno,accountno;
    //Address
    TextView cityname,countryname,disctic,pincode,adduser,state, ifsccode, pan_no, bankbranch ;
    ProgressBar progressBar;

    public ViewProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_profile, container, false);

        profileimage= view.findViewById(R.id.personalimage);

        username = view.findViewById(R.id.username);
        name = view.findViewById(R.id.profile_name);
        bday= view.findViewById(R.id.userbday);
        gender= view.findViewById(R.id.profile_gender);
        registration= view.findViewById(R.id.user_regi);
        mail= view.findViewById(R.id.user_email);
        mobile= view.findViewById(R.id.user_mobile);
        noimee_name= view.findViewById(R.id.noi_name_profile);
        noimee_relation= view.findViewById(R.id.noi_rel_profile);

        ifsccode= view.findViewById(R.id.ifsccode);
        pan_no= view.findViewById(R.id.pan_no);
        bankbranch= view.findViewById(R.id.bankbranch);
        banknam= view.findViewById(R.id.bankname);
//        bankaddress=(TextView)view.findViewById(R.id.bankadd);
        accountno= view.findViewById(R.id.acno);

        cityname= view.findViewById(R.id.usercity);
       // disctic=(TextView)view.findViewById(R.id.userdisc);
        countryname= view.findViewById(R.id.countrynames);
        pincode= view.findViewById(R.id.userpcode);
        adduser= view.findViewById(R.id.useraddress);
        state= view.findViewById(R.id.userstate);


        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();


        context=getContext();

        openimage();

        progressBar=view.findViewById(R.id.viewprofileprogress);

        preferences=getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        sessionManagement=new SessionManagement(getActivity().getApplicationContext());
        user=sessionManagement.getUserDetails();
        //urls=ut.url+"getProfile/"+ut.merchantid+"/"+ut.securtykey+"/"+uname+"/"+pass;

        ProfileUrl= Config.url +"getProfile/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/"+user.get(SessionManagement.KEY_PASSWORD);
        loadData();
        return view;
    }

    private void openimage() {
        profileimage.setOnClickListener(v -> {
            Dialog dialog=new Dialog(context);
            dialog.setContentView(R.layout.imageshow);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
            ImageView showimage=dialog.findViewById(R.id.img);

            Glide.with(context).load(image).into(showimage);

            dialog.show();
        });
    }

    private void loadData() {
        dialog.show();
       // progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, ProfileUrl, s -> {

            try {
                JSONArray jsonArray=new JSONArray(s);
                JSONObject jsonObject=jsonArray.getJSONObject(0);
//                    name.setText(jsonObject.getString("FirstName")+" "+jsonObject.getString("LastName"));
                name.setText(jsonObject.getString("Title")+" "+jsonObject.getString("FirstName")+" "+jsonObject.getString("LastName"));
                username.setText(jsonObject.getString("UserID"));
                bday.setText(jsonObject.getString("DOB"));
                gender.setText(jsonObject.getString("Gender"));
                registration.setText(jsonObject.getString("Regdate"));
                mail.setText(jsonObject.getString("EmailID"));
                mobile.setText("+"+jsonObject.getString("Countrycode")+" "+jsonObject.getString("MobileNo"));

                if(jsonObject.has("Country")) {
                    String country = jsonObject.getString("Country");
                    countryname.setText(country);
                    if (country.equals("India") || country.equals("INDIA")) {
                        noimee_name.setText(jsonObject.getString("Nomname"));
                        noimee_relation.setText(jsonObject.getString("NomRelation"));
                        // pancardno.setText(jsonObject.getString("Panno"));
                        // disctic.setText(jsonObject.getString("District")+",");

                    } else {
                        noimee_relation.setText("N/A");
                        noimee_name.setText("N/A");
                        // pancardno.setText("N/A");
                        // disctic.setText("N/A,");
                    }
                } else{
                    noimee_relation.setText("N/A");
                    noimee_name.setText("N/A");
                    // pancardno.setText("N/A");
                    // disctic.setText("N/A,");
                }

                image=jsonObject.getString("profileImage");
                Glide.with(context).load(image).into(profileimage);

                //Bankdeatils
//                    bankusername.setText(jsonObject.getString("UserID"));

                banknam.setText(jsonObject.getString("Bank"));
//                    bankaddress.setText(jsonObject.getString("BankAdd"));
//                    ifsc.setText(jsonObject.getString("IFSC"));
                ifsccode.setText(jsonObject.getString("IFSC"));
                pan_no.setText(jsonObject.getString("Panno"));
                bankbranch.setText(jsonObject.getString("BankAdd"));
                accountno.setText(jsonObject.getString("AccNo"));


                //address
                cityname.setText(jsonObject.getString("City")+"");

                String p=jsonObject.getString("PinCode");
                pincode.setText(""+p+"");
                adduser.setText(jsonObject.getString("Address"));
                state.setText(jsonObject.getString("State")+"");


            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing())
                dialog.dismiss();
              //progressBar.setVisibility(View.GONE);
        }, volleyError -> {
            if (dialog.isShowing())
                dialog.dismiss();

        });
        requestQueue.add(stringRequest);
    }

}
