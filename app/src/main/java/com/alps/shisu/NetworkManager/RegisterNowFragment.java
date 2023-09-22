package com.alps.shisu.NetworkManager;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.CurrencyWallet.CurrencyWalletActivity;
import com.alps.shisu.R;
import com.alps.shisu.Registrationmoreinformation.RegisterMoreInformationActivity;
import com.alps.shisu.SaveSharedPreference;
import com.alps.shisu.Session.SessionManagement;
import com.alps.shisu.adapterclass.MySpinnerAdapter;
import com.alps.shisu.modelclass.StateDetails;
import com.alps.shisu.service.SendData;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

//import com.alps.aadnyabullion.RegisterationmoreInformation.RegisterMoreInformationActivity;
//import com.alps.aadnyabullion.RegisterationmoreInformation.RegisterMoreInformationFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterNowFragment extends Fragment {
    EditText username1, username2, username, mobilenumber, useremailid, password, confirmpassword, securitypin, confrmsecuritypin, parentId, parentName, sponserUsername, sponserName;
    //  Spinner newcountry,sponsorsideids;
    Spinner newcountry, stateSpinner;
    TextView text01;
    ImageView show_pass_btn, show_confrmpass_btn, show_securitypin_btn, show_confrmsecuritypin_btn;
    Button registerbutton;
    LinearLayout newloginpage, stateLL, inputlayout14, inputlayout15;
    Config ut;
    SessionManagement sessionManagement;
    SharedPreferences.Editor editor;
    SharedPreferences preferences, preferences1;
    HashMap<String, String> user;
    AlertDialog dialog;
    Context context;
    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    final String countryurl = Config.url + "getCity/" + Config.merchantid + "/" + Config.securtykey + "/0/0/0";
    ArrayList<String> countryname;
    ArrayList<String> countryid;
    private int defaultCountryPosition = 0;

    ArrayList<String> statename;
//    ArrayList<String> stateid;

    ArrayList<String> districtname;
    ArrayList<String> districtid;

    ArrayList<String> cityname;
    ArrayList<String> cityid;

    private final ArrayList<String> stateItemArrayList = new ArrayList<>();
    private final HashMap<String, String> stateHashMap = new HashMap<>();
    private boolean feildsEnabled;
    private String userId;
    private String stateName;
    private String stateId;
    private String userName;
    private String countryId, firstName, lastName;
    private boolean parentIdBoolValue;
    private String parentIdMessage;


    public RegisterNowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_now, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ALPSPref", MODE_PRIVATE);
        userId = sharedPreferences.getString("uid", "");
        firstName = sharedPreferences.getString("firstname", "");
        lastName = sharedPreferences.getString("lasname", "");
        userName = !firstName.isEmpty() ? firstName + " " + lastName : lastName;

        inputlayout14 = view.findViewById(R.id.inputlayout14);
        inputlayout15 = view.findViewById(R.id.inputlayout15);
        parentId = view.findViewById(R.id.parentId);
        parentName = view.findViewById(R.id.parentName);
        sponserUsername = view.findViewById(R.id.sponserUsername);
        sponserName = view.findViewById(R.id.sponserName);

        newloginpage = view.findViewById(R.id.newloginpage);
        username1 = view.findViewById(R.id.username1);
        username2 = view.findViewById(R.id.username2);
        username = view.findViewById(R.id.username);
        mobilenumber = view.findViewById(R.id.mobileNumber);
        useremailid = view.findViewById(R.id.useremailid);
        password = view.findViewById(R.id.password);
        confirmpassword = view.findViewById(R.id.confirmpassword);
        securitypin = view.findViewById(R.id.securitypin);
        confrmsecuritypin = view.findViewById(R.id.confrmsecuritypin);
        registerbutton = view.findViewById(R.id.registerbutton);
        newcountry = view.findViewById(R.id.newcountry);
        stateSpinner = view.findViewById(R.id.newState);
        stateLL = view.findViewById(R.id.stateLL);
//        sponsorsideids=view.findViewById(R.id.sponsorsideids);
        text01 = view.findViewById(R.id.text01);
        show_pass_btn = view.findViewById(R.id.show_pass_btn);
        show_confrmpass_btn = view.findViewById(R.id.show_confrmpass_btn);
        show_securitypin_btn = view.findViewById(R.id.show_securitypin_btn);
        show_confrmsecuritypin_btn = view.findViewById(R.id.show_confrmsecuritypin_btn);

        if (userId != null && !userId.isEmpty()) {
            sponserUsername.setText(userId);
            sponserUsername.setEnabled(false);

            sponserName.setText(userName);
            sponserName.setEnabled(false);

        }

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String userName = username.getText().toString();
                if (userName.length() > 0) {
                    checkUserName(userId, userName);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        parentId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String parentIdStr = parentId.getText().toString();
                if (parentIdStr.length() > 0) {
                    validateParentId(userId, parentIdStr);
                } else {
                    parentName.setText("Not Found");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog = new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
        context = getContext();

//        String[] sponsorside = {"Select Sponsor Side", "Left","Right"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sponsorside);
//        sponsorsideids.setAdapter(adapter);

        registerbutton.setOnClickListener(v -> {
//                if (sponsorsideids.getSelectedItemPosition() == 0) {
//                    sponsorsideids.requestFocus();
//                    Toast.makeText(getActivity(), "Please Select Sponsor Side", Toast.LENGTH_SHORT).show();
//                    return;
//                } else
            if (!parentIdBoolValue) {
                parentName.requestFocus();
                parentName.setError(parentIdMessage);
            } else if (username1.getText().toString().equalsIgnoreCase("")) {
                username1.requestFocus();
                username1.setError("Enter Frist Name");
            } else if (username2.getText().toString().equalsIgnoreCase("")) {
                username2.requestFocus();
                username2.setError("Enter Last Name");
            } else if (mobilenumber.getText().toString().equalsIgnoreCase("") || mobilenumber.getText().toString().length() < 6) {
                mobilenumber.requestFocus();
                mobilenumber.setError("Enter Valid Mobile No.");
            } else if (useremailid.getText().toString().equalsIgnoreCase("")) {
                useremailid.requestFocus();
                useremailid.setError("Enter Email Id");
            } else if (!useremailid.getText().toString().trim().matches(emailPattern)) {
                useremailid.requestFocus();
                useremailid.setError("Enter Valid Email Id");
            } else if (newcountry.getSelectedItemPosition() == 0) {
                newcountry.requestFocus();
                Toast.makeText(getActivity(), "Please Select Country", Toast.LENGTH_SHORT).show();
            } else if (username.getText().toString().equalsIgnoreCase("") || username.getText().toString().length() < 4) {
                username.requestFocus();
                username.setError("Enter Min.4 Characters. ");
            } else if (username.getText().toString().contains(" ")) {
                username.requestFocus();
                username.setError("No Spaces Allowed");
            } else if (password.getText().toString().equalsIgnoreCase("") || password.getText().toString().length() < 4) {
                password.requestFocus();
                password.setError("Enter Min.4 Digit Password!");
            } else if (confirmpassword.getText().toString().equalsIgnoreCase("") || confirmpassword.getText().toString().length() < 4) {
                confirmpassword.requestFocus();
                confirmpassword.setError("Enter Min.4 Digit Password!");
            } else if (securitypin.getText().toString().equalsIgnoreCase("") || securitypin.getText().toString().length() < 4) {
                securitypin.requestFocus();
                securitypin.setError("Enter Min.4 Digit Pin!");
            } else if (confrmsecuritypin.getText().toString().equalsIgnoreCase("") || confrmsecuritypin.getText().toString().length() < 4) {
                confrmsecuritypin.requestFocus();
                confrmsecuritypin.setError("Enter Min.4 Digit Pin!");
            } else {
                if (!password.getText().toString().equals(confirmpassword.getText().toString())) {
                    confirmpassword.requestFocus();
                    confirmpassword.setError("Password Not Match");
                } else if (!securitypin.getText().toString().equals(confrmsecuritypin.getText().toString())) {
                    confrmsecuritypin.requestFocus();
                    confrmsecuritypin.setError("Pin Not Match");
                } else {
                    newregisternow();
                }
            }
        });

        newcountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemId = countryid.get(position);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ALPSPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("countryId", selectedItemId);
                editor.apply();

                countryId = selectedItemId;
                getStateList();

                       /* try {
                            if (position==0){
                                getstates();
                                getdisctricts();
                                getcitys();
                            }else {
                                getstate(selectedItemId);
                                // Toast.makeText(getActivity(), "Value is "+selectedItemId, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //search_btn.setEnabled(false);
            }
        });

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemId = stateItemArrayList.get(position);
                stateId = stateHashMap.get(selectedItemId);
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("ALPSPref",MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString("stateId", selectedItemId);
                editor.apply();

                       /* try {
                            if (position==0){
                                getstates();
                                getdisctricts();
                                getcitys();
                            }else {
                                getstate(selectedItemId);
                                // Toast.makeText(getActivity(), "Value is "+selectedItemId, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //search_btn.setEnabled(false);
            }
        });

        show_pass_btn.setOnClickListener(this::ShowHidePass);
        show_confrmpass_btn.setOnClickListener(this::ShowHideConfrmPass);
        show_securitypin_btn.setOnClickListener(this::ShowHidePin);
        show_confrmsecuritypin_btn.setOnClickListener(this::ShowHideConfrmPin);


        preferences = getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        sessionManagement = new SessionManagement(getActivity().getApplicationContext());
        user = sessionManagement.getUserDetails();
        new SaveSharedPreference(getActivity());

        preferences1 = getActivity().getSharedPreferences("ALPSPref", Context.MODE_PRIVATE);
        countryId = preferences1.getString("countryId", null);
        Log.e(TAG, "onCreate: countryId : " + countryId);

        getcountry();
        getStateList();
        countryname = new ArrayList<>();
        countryid = new ArrayList<>();

        return view;
    }

    private void ShowHideConfrmPin(View v) {
        if (v.getId() == R.id.show_confrmsecuritypin_btn) {

            if (confrmsecuritypin.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (v)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                //Show Password
                confrmsecuritypin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (v)).setImageResource(R.drawable.ic_visibility_off_black_24dp);

                //Hide Password
                confrmsecuritypin.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private void ShowHidePin(View v) {
        if (v.getId() == R.id.show_securitypin_btn) {

            if (securitypin.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (v)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                //Show Password
                securitypin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (v)).setImageResource(R.drawable.ic_visibility_off_black_24dp);

                //Hide Password
                securitypin.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private void ShowHideConfrmPass(View v) {
        if (v.getId() == R.id.show_confrmpass_btn) {

            if (confirmpassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (v)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                //Show Password
                confirmpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (v)).setImageResource(R.drawable.ic_visibility_off_black_24dp);

                //Hide Password
                confirmpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private void ShowHidePass(View v) {
        if (v.getId() == R.id.show_pass_btn) {

            if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (v)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                //Show Password
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (v)).setImageResource(R.drawable.ic_visibility_off_black_24dp);

                //Hide Password
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private void getcountry() {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, countryurl, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String SID = jsonObject.getString("SID");
                    String StateName = jsonObject.getString("StateName");

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ALPSPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("stateSID", SID);
                    editor.apply();

                    countryid.add(SID);
                    countryname.add(StateName);
                    if (StateName.equalsIgnoreCase("India")) {
                        defaultCountryPosition = i + 1;
                    }
                }
                countryid.add(0, "");
                countryname.add(0, "Select Country");
                ArrayAdapter<String> category_list_dropdown = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, countryname);
                category_list_dropdown.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                newcountry.setAdapter(category_list_dropdown);
                newcountry.setSelection(defaultCountryPosition);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing())
                dialog.dismiss();

        }, error -> {
            if (dialog.isShowing())
                dialog.dismiss();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    private void checkUserName(String loginuser, String userName) {
        //dialog.show();
        String urls = Config.url + "validateUsername/" + Config.merchantid + "/" + Config.securtykey + "/" + loginuser + "/" + userName + "";
        Log.e(TAG, "checkUserName: " + urls);
        RequestQueue requestQueue = new Volley().newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, "onResponse: " + response);

                            JSONArray object = new JSONArray(response);
                            JSONObject jsonObject = object.getJSONObject(0);

                            String Status = jsonObject.getString("Status");
                            String message = jsonObject.getString("message");
                            String MessageCode = jsonObject.getString("Status");

                            if (!message.equalsIgnoreCase("Available")) {
                                username.setError(message);
                            } else {
                                username.setError(null);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }   /*if (dialog.isShowing())
                            dialog.dismiss();*/

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                Snackbar snackbar=Snackbar.make(loginpage,""+error,Snackbar.LENGTH_LONG);
//                snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
//                View snackbarView = snackbar.getView();
//                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
//                snackbar.show();
                //Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
               /* if (dialog.isShowing())
                    dialog.dismiss();*/
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
//                params.put("username",usname);
//                params.put("pass",userspass);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void validateParentId(String loginuser, String parentIdValue) {
        //dialog.show();
        String urls = Config.url + "validateParent/" + Config.merchantid + "/" + Config.securtykey + "/" + loginuser + "/" + parentIdValue + "";
        Log.e(TAG, "validateParentId: " + urls);
        RequestQueue requestQueue = new Volley().newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, "onResponse: " + response);

                            JSONArray object = new JSONArray(response);
                            JSONObject jsonObject = object.getJSONObject(0);

                            String status = jsonObject.getString("Status");
                            String message = jsonObject.getString("message");
                            String MessageCode = jsonObject.getString("MessageCode");

                            parentIdBoolValue = status.equalsIgnoreCase("true");
                            parentIdMessage = message;
//                            if(!MessageCode.equalsIgnoreCase("1")) {
////                                parentName.setText("Not Found");
//                                parentName.setText(message);
//                            } else {
                            parentName.setText(message);
//                            }


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }   /*if (dialog.isShowing())
                            dialog.dismiss();*/

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                Snackbar snackbar=Snackbar.make(loginpage,""+error,Snackbar.LENGTH_LONG);
//                snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
//                View snackbarView = snackbar.getView();
//                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
//                snackbar.show();
                //Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
               /* if (dialog.isShowing())
                    dialog.dismiss();*/
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
//                params.put("username",usname);
//                params.put("pass",userspass);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void newregisternow() {
        //get form data
        String spusername1 = username1.getText().toString();
        String spusername2 = username2.getText().toString();
        String spusername = username.getText().toString();
        String spmobilenumber = mobilenumber.getText().toString();
        String spuseremailid = useremailid.getText().toString();
        String sppassword = password.getText().toString();
        String spconfirmpassword = confirmpassword.getText().toString();
        String ssecuritypin = securitypin.getText().toString();
        String sconfrmsecuritypin = confrmsecuritypin.getText().toString();
        String parentIdStr = parentId.getText().toString();

        preferences = getActivity().getSharedPreferences("ALPSPref", MODE_PRIVATE);
        String countryId = preferences.getString("countryId", null);
        Log.d("spusername1", toString());

        SaveSharedPreference.setSession_Name(spusername1);
        SaveSharedPreference.setnId(spusername2);

        SaveSharedPreference.setUserId(spusername);
        Log.d("spusername", spusername);
        SaveSharedPreference.setSession_Id(spconfirmpassword);
        Log.d("spconfirmpassword", SaveSharedPreference.getSession_Id());

        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Sponsorid", user.get(SessionManagement.KEY_USERNAME));
//            jsonObject.put("SponsorSide",sponsorsideids.getSelectedItemPosition());
            jsonObject.put("SponsorSide", "Left");
            jsonObject.put("Parentid", parentIdStr);
            jsonObject.put("Firstname", spusername1);
            jsonObject.put("Lastname", spusername2);
            jsonObject.put("Username", spusername);
            jsonObject.put("Mobile", spmobilenumber);
            jsonObject.put("EmailID", spuseremailid);
            jsonObject.put("CID", countryId);
            jsonObject.put("SID", stateId);
            jsonObject.put("DistID", "0");
            jsonObject.put("CTID", "0");
            jsonObject.put("Password", spconfirmpassword);
            jsonObject.put("trPassword", sconfrmsecuritypin);
            jsonArray.put(jsonObject);
            Log.e("registerresponse", jsonArray.toString());


//            if (mobilenumber.length() == 10 ) {
            final String posturls = Config.url + "JoinMember/" + Config.merchantid + "/" + Config.securtykey + "/Object";
            Log.d("posturls", posturls);
            try {
                new SendData(getActivity(), jsonArray, posturls, response -> {
                    // Toast.makeText(getActivity(), " "+response, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonObject1 = new JSONObject(response);
                        Log.d("Response", response);
                        String regno = jsonObject1.getString("regno");
                        String Message = jsonObject1.getString("Message");
                        String MessageCode = jsonObject1.getString("MessageCode");
                        // Log.d("Response", regno + Message + MessageCode);

                        if (Message.contains("LoginId already exists")) {
                            Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();
                            username1.getText().clear();
                            username2.getText().clear();
                            username.getText().clear();
                            mobilenumber.getText().clear();
                            useremailid.getText().clear();
                            password.getText().clear();
                            confirmpassword.getText().clear();
                            securitypin.getText().clear();
                            parentId.getText().clear();
                            parentName.getText().clear();
                            confrmsecuritypin.getText().clear();
                            Intent intent = new Intent(getActivity(), RegisterMoreInformationActivity.class);
                            startActivity(intent);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, true).execute();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("updateException", e.toString());
            }
          /*  }else {
                mobilenumber.requestFocus();
                mobilenumber.setError("Enter valid mobile");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getStateList() {

//      String url = "https://api.goldways.in/DeveloperApi/getCity/M/H1298HyHicofI/1/H1298HyiCofi4542crTy/96/0/0";
        String url = "https://api.goldways.in/DeveloperApi/getCity/" + Config.merchantid + "/" + Config.securtykey + "/" + countryId + "/0/0";
        Log.e(TAG, "getStateList: " + url);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {

                        JSONArray object = new JSONArray(response);

                        stateItemArrayList.clear();
                        stateItemArrayList.add("Select State");

                        for (int i = 0; i < object.length(); i++) {
                            JSONObject jsonObject = object.getJSONObject(i);

//                                if (jsonObject.getBoolean("Status")) {
                            String sID = jsonObject.getString("SID");
                            String stateName = jsonObject.getString("StateName");
                            String messageCode = jsonObject.getString("MessageCode");
                            String message = jsonObject.getString("message");

                            StateDetails stateDetails = new StateDetails();
                            stateDetails.setsID(sID);
                            stateDetails.setStateName(stateName);

                            stateItemArrayList.add(stateName);
                            stateHashMap.put(stateName, sID);

//                                } else {
//                                Snackbar snackbar=Snackbar.make(loginpage,"Please Enter Correct Userid & Password",Snackbar.LENGTH_LONG);
//                                snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
//                                View snackbarView = snackbar.getView();
//                                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
//                                snackbar.show();
//                                }
                        }
                        try {

//                            if(feildsEnabled) {

//                                MySpinnerAdapter adapter = new MySpinnerAdapter(
//                                        getContext(),
//                                        R.layout.view_spinner_item,
//                                        stateItemArrayList);
//                                adapter.setDropDownViewResource(R.layout.drop_down_item);
                            ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, stateItemArrayList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            stateSpinner.setAdapter(adapter);
                            stateLL.setBackground(getResources().getDrawable(R.drawable.rectangle_yellow_box_layout));


                            if (stateItemArrayList != null && stateItemArrayList.size() > 0) {
                                int selectedPosition = getSelectedStatePosition(stateName, stateItemArrayList);
                                stateId = stateHashMap.get(stateName);
                                if (selectedPosition > 0) {
                                    stateSpinner.setSelection(selectedPosition);
                                }
                            }


//                            }
//                            else {
//
//                                MySpinnerAdapter adapter = new MySpinnerAdapter(
//                                        getContext(),
//                                        R.layout.disabled_view_spinner,
//                                        stateItemArrayList);
//                                adapter.setDropDownViewResource(R.layout.disabled_drop_down_item);
//                                stateSpinner.setAdapter(adapter);
//                                stateLL.setBackground(getResources().getDrawable(R.drawable.disabled_edit_text));
//
//
//                                if (stateItemArrayList != null && stateItemArrayList.size() > 0) {
//                                    int selectedPosition = getSelectedStatePosition(stateName, stateItemArrayList);
//                                    if (selectedPosition > 0) {
//                                        stateSpinner.setSelection(selectedPosition);
//                                    }
//                                }
//
//                            }
//
//                            getDistrictList(stateId);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }   /*if (dialog.isShowing())
                        dialog.dismiss();*/

                }, error -> {
        }) {
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };
        requestQueue.add(stringRequest);
    }

    private int getSelectedStatePosition(String stateName, ArrayList<String> stateItemArrayList) {

        int index = 0;
        if (!stateName.isEmpty()) {
            for (String str :
                    stateItemArrayList) {
                if (stateName.equalsIgnoreCase(str)) {
                    return index;
                }
                index++;
            }
        }
        return 0;
    }


}

