package com.alps.shisu.Registrationmoreinformation;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.R;
import com.alps.shisu.SaveSharedPreference;
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
import me.biubiubiu.justifytext.library.JustifyTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterMoreInformationFragment extends Fragment {

    JustifyTextView Paragtaph;
    TextView congratsuername,newregisterpdwlname,newregisterpdwlregdate,newregisterpdwlemail,newregisterpdwlmobile,
            newregisterpdwlusername, newregisterpdwladdress,newregisterpdwlcity,
            newregisterpdwlState,newregisterpdwlpincode,newregisterpdwlcountry,newregisteraddressoffice;
    CircularImageView newregisterimagelogo;
    SessionManagement sessionManagement;
    HashMap<String, String> user;
    SharedPreferences preferences;
    ProgressBar progressBar;
    String WLURL="";
    Context context;
    Config ut;
    AlertDialog dialog;

    public RegisterMoreInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register_more_information, container, false);

        Paragtaph=view.findViewById(R.id.paragraph);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Paragtaph.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

        Paragtaph.setText("We would like to take this Opportunity to welcome you as a new member. \n" +
                "At Shisu we pride ourselves on offering our members responsive, Competent and excellent services. Our members are the most important part of our business and we our work  tirelessly to ensure  your complete satisfaction, now and for as long as you are a member. I encourage you to contact me at any time with your question, comments and Feedback. Thank you again for entrusting  Aplus Mart of Companies with your most important Business needs. We are honoured to serve you.\n");

        sessionManagement=new SessionManagement(getActivity().getApplicationContext());
        user=sessionManagement.getUserDetails();

        congratsuername=(TextView)view.findViewById(R.id.congratsuername);
        newregisterpdwlname=(TextView)view.findViewById(R.id.newregisterpdwlname);
        newregisterpdwlregdate=(TextView)view.findViewById(R.id.newregisterpdwlregdate);
        newregisterpdwlemail=(TextView)view.findViewById(R.id.newregisterpdwlemail);
        newregisterpdwlmobile=(TextView)view.findViewById(R.id.newregisterpdwlmobile);
        newregisterpdwlusername=(TextView)view.findViewById(R.id.newregisterpdwlusername);

        newregisterpdwladdress=(TextView)view.findViewById(R.id.newregisterpdwladdress);
        newregisterpdwlcity=(TextView)view.findViewById(R.id.newregisterpdwlcity);
        newregisterpdwlState=(TextView)view.findViewById(R.id.newregisterpdwlState);
        newregisterpdwlpincode=(TextView)view.findViewById(R.id.newregisterpdwlpincode);
        newregisterpdwlcountry=(TextView)view.findViewById(R.id.newregisterpdwlcountry);
        newregisteraddressoffice=(TextView)view.findViewById(R.id.newregisteraddressoffice);
        newregisterimagelogo=(CircularImageView)view.findViewById(R.id.newregisterimagelogo);
        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
        context=getContext();

        new SaveSharedPreference(getActivity());

        WLURL= Config.url +"getProfile/"+ Config.merchantid +"/"+ Config.securtykey +"/"+ SaveSharedPreference.getUserId()+"/"+ SaveSharedPreference.getSession_Id();
        Log.d("WLURL",WLURL);
        loadWl();

        congratsuername.setText("Congratulations  "+SaveSharedPreference.getSession_Name()+" "+SaveSharedPreference.getnId());
        return view;
    }

    private void loadWl() {
        dialog.show();
        RequestQueue queue= Volley.newRequestQueue(getContext());
        StringRequest request=new StringRequest(Request.Method.GET, WLURL, response -> {
            try {
                JSONArray jsonArray=new JSONArray(response);
                JSONObject jsonObject=jsonArray.getJSONObject(0);
                Log.d("re", String.valueOf(jsonObject));
               /* if (jsonObject.getString("Gender").equals("Female")){
                    newregisterpdwlname.setText("Ms."+jsonObject.getString("FirstName")+" "+jsonObject.getString("LastName"));
                }else {
                    newregisterpdwlname.setText("Mr."+jsonObject.getString("FirstName")+" "+jsonObject.getString("LastName"));
                }*/

                String image=jsonObject.getString("profileImage");
//                Picasso.with(context).load(image).into(newregisterimagelogo);
                Glide.with(context).load(image).into(newregisterimagelogo);


                newregisterpdwlname.setText(jsonObject.getString("FirstName")+" "+jsonObject.getString("LastName"));
                newregisterpdwlregdate.setText(jsonObject.getString("Regdate"));
                newregisterpdwlemail.setText(jsonObject.getString("EmailID"));

                newregisterpdwlmobile.setText("+"+jsonObject.getString("Countrycode")+" "+jsonObject.getString("MobileNo"));
                newregisterpdwlusername.setText(jsonObject.getString("UserID"));
                newregisterpdwladdress.setText(jsonObject.getString("Address"));
                newregisterpdwlcity.setText(jsonObject.getString("City"));
                newregisterpdwlState.setText(jsonObject.getString("State"));
                newregisterpdwlpincode.setText(jsonObject.getString("PinCode"));
                newregisteraddressoffice.setText(jsonObject.getString("OfficeAddress"));

                String country=jsonObject.getString("Country");
                newregisterpdwlcountry.setText(country);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing())
                dialog.dismiss();
            //    progressBar.setVisibility(View.GONE);
        }, error -> {
            if (dialog.isShowing())
                dialog.dismiss();
            // progressBar.setVisibility(View.GONE);
        });queue.add(request);
    }



}

