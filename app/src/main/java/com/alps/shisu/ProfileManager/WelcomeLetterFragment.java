package com.alps.shisu.ProfileManager;


import android.app.AlertDialog;
import android.content.Context;
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
public class WelcomeLetterFragment extends Fragment {

    JustifyTextView Paragtaph;
    String boldword="World Crown";
    TextView wlname,wlbday,wlgender,wlregdate,wlemail,wlmobile,wlusername,wlnoimeename,wlnoimeerelation,
            wladdress,wllandmark,wlcity,wlstate,wlpincode,wlcountry,ofcadd;
    CircularImageView userprofileimage;
    SessionManagement sessionManagement;
    HashMap<String,String> user;
    ProgressBar progressBar;
    String WLURL="";
    Context context;
    Config ut;
    AlertDialog dialog;
    public WelcomeLetterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_welcome_letter, container, false);
        Paragtaph=view.findViewById(R.id.paragraph);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Paragtaph.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }
        Paragtaph.setText("We would like to take this Opportunity to welcome you as a new member. \n" +
                "At Shisu we pride ourselves on offering our members responsive, Competent and excellent services. Our members are the most important part of our business and we our work  tirelessly to ensure  your complete satisfaction, now and for as long as you are a member. I encourage you to contact me at any time with your question, comments and Feedback. Thank you again for entrusting  Shisu of Companies with your most important Business needs. We are honoured to serve you.\n");

        //Paragtaph.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        sessionManagement=new SessionManagement(getActivity().getApplicationContext());
        user=sessionManagement.getUserDetails();
        //HeadUsername=(TextView)view.findViewById(R.id.welcomeletterusername);
        wlname= view.findViewById(R.id.pdwlname);
        wlbday= view.findViewById(R.id.pdwlbday);
        wlgender=(TextView)view.findViewById(R.id.pdwlgender);
        wlregdate=(TextView)view.findViewById(R.id.pdwlregdate);
        wlemail=(TextView)view.findViewById(R.id.pdwlemail);
        wlmobile=(TextView)view.findViewById(R.id.pdwlmobile);
        wlusername=(TextView)view.findViewById(R.id.pdwlusername);

        wlnoimeename=(TextView)view.findViewById(R.id.pdwlnname);
        wlnoimeerelation=(TextView)view.findViewById(R.id.pdwlnrelation);
        wladdress=(TextView)view.findViewById(R.id.pdwladdress);
        //  wllandmark=(TextView)view.findViewById(R.id.pdwllandmark);
        wlcity=(TextView)view.findViewById(R.id.pdwlcity);
        wlstate=(TextView)view.findViewById(R.id.pdwlState);
        wlpincode=(TextView)view.findViewById(R.id.pdwlpincode);
        wlcountry=(TextView)view.findViewById(R.id.pdwlcountry);
        ofcadd=(TextView)view.findViewById(R.id.addressoffice);
        userprofileimage=(CircularImageView)view.findViewById(R.id.welcomeletteimageuser);
        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
        context=getContext();
       // progressBar=view.findViewById(R.id.welcomeletterprogress);
       // FadingCircle circle=new FadingCircle();
       // progressBar.setIndeterminateDrawable(circle);
        WLURL= Config.url +"getProfile/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/"+user.get(SessionManagement.KEY_PASSWORD);
        loadWl();
        return view;

    }

    private void loadWl() {
      dialog.show();
      //  progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue= Volley.newRequestQueue(getContext());
        StringRequest request=new StringRequest(Request.Method.GET, WLURL, response -> {
            try {
                JSONArray jsonArray=new JSONArray(response);
                JSONObject jsonObject=jsonArray.getJSONObject(0);
                Log.d("re", String.valueOf(jsonObject));
               /* if (jsonObject.getString("Gender").equals("Female")){
                  //  HeadUsername.setText("Ms."+jsonObject.getString("FirstName")+" "+jsonObject.getString("LastName"));
                    wlname.setText("Ms."+jsonObject.getString("FirstName")+" "+jsonObject.getString("LastName"));
                }else {
                    //HeadUsername.setText("Mr."+jsonObject.getString("FirstName")+" "+jsonObject.getString("LastName"));
                    wlname.setText("Mr."+jsonObject.getString("FirstName")+" "+jsonObject.getString("LastName"));
                }*/
                wlname.setText(jsonObject.getString("FirstName")+" "+jsonObject.getString("LastName"));


                String image=jsonObject.getString("profileImage");
                //Picasso.with(context).load(image).into(userprofileimage);
                Glide.with(context).load(image).into(userprofileimage);



                wlbday.setText(jsonObject.getString("DOB"));
                wlgender.setText(jsonObject.getString("Gender"));
                wlregdate.setText(jsonObject.getString("Regdate"));
                wlemail.setText(jsonObject.getString("EmailID"));

                wlmobile.setText("+"+jsonObject.getString("Countrycode")+" "+jsonObject.getString("MobileNo"));
                wlusername.setText(jsonObject.getString("UserID"));
                // wlnoimeename.setText(jsonObject.getString("Nomname"));
                // wlnoimeerelation.setText(jsonObject.getString("NomRelation"));
                wladdress.setText(jsonObject.getString("Address"));
                // wllandmark.setText(jsonObject.getString(""));
                wlcity.setText(jsonObject.getString("City"));
                wlstate.setText(jsonObject.getString("State"));
                wlpincode.setText(jsonObject.getString("PinCode"));
                // wlcountry.setText(jsonObject.getString("Country"));
                ofcadd.setText(jsonObject.getString("OfficeAddress"));

                String country=jsonObject.getString("Country");
                wlcountry.setText(country);
                if (country.equals("India")||country.equals("INDIA")) {
                    wlnoimeename.setText(jsonObject.getString("Nomname"));
                    wlnoimeerelation.setText(jsonObject.getString("NomRelation"));
                    //pancardno.setText(jsonObject.getString("Panno"));
                    // disctic.setText(jsonObject.getString("District")+",");

                }
                else {
                    wlnoimeerelation.setText("N/A");
                    wlnoimeename.setText("N/A");
                    // pancardno.setText("N/A");
                    // disctic.setText("N/A,");
                }


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

