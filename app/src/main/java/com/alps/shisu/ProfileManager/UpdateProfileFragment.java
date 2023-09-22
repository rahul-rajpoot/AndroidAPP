package com.alps.shisu.ProfileManager;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.alps.shisu.adapterclass.MySpinnerAdapter;
import com.alps.shisu.modelclass.StateDetails;
import com.alps.shisu.modelclass.UserProfileData;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Spinner titile, genderspiner, Select_Country, Select_State ;
    private EditText Username, fname, lanme, spousename, phone, emailId,
            Address, pincodeep, bankName, bankadd, ifsccode, accountNumber, panNo, phonecode, cityText;
    private TextView dobtext, countryget, changepassworddialog;
    //    private AppCompatButton updateBtn, cancelBtn;
    private AppCompatButton updateBtn, cardnoshow;
    private String urls ="", updateProfileUrl = "";

    // Session Manager Class
    public SessionManagement sessionManagement;
    HashMap<String, String> user;
    private ArrayList<String> titleList;
    private ArrayList<String> genderList;
    private String title ="", gender ="", countryId ="96", pinCodeStr ="";
    private String stateId="0";
    private String stateName="";
    private String districId ="0";
    private String districtName ="";
    private String cTID="0";
    private String cityName ="";

    private final ArrayList<String> stateItemArrayList = new ArrayList<>();
//    private final ArrayList<String> districtItemArrayList = new ArrayList<>();
    private final ArrayList<String> cityItemArrayList = new ArrayList<>();
    private final HashMap<String, String> stateHashMap = new HashMap<>();
//    private final HashMap<String, String> districtHashMap = new HashMap<>();
    private final HashMap<String, String> cityHashMap = new HashMap<>();
    private final HashMap<String, StateDetails> finalStateDetails = new HashMap<>();

    private boolean feildsEnabled;
    private boolean firstTimeLoaded;
    Dialog customeDialog;
    private EditText oldpassword, newpassword, confirmpassword;
    ProgressDialog loadingBar;


    public UpdateProfileFragment()  {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateProfileFragment.
     */
    public static UpdateProfileFragment newInstance(String param1, String param2) {
        UpdateProfileFragment fragment = new UpdateProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_profile, container, false);

        // All EditTexts
        Username = view.findViewById(R.id.Username);
        fname = view.findViewById(R.id.fname);
        lanme = view.findViewById(R.id.lanme);
        spousename = view.findViewById(R.id.spousename);
        phone = view.findViewById(R.id.phone);
        emailId = view.findViewById(R.id.emailId);
//      nomName = view.findViewById(R.id.nomName);
//      relation = view.findViewById(R.id.relation);
        Address = view.findViewById(R.id.Address);
        pincodeep = view.findViewById(R.id.pincodeep);
        cardnoshow = view.findViewById(R.id.cardnoshow);
        bankName = view.findViewById(R.id.bankName);
        bankadd = view.findViewById(R.id.bankadd);
        ifsccode = view.findViewById(R.id.ifsccode);
        accountNumber = view.findViewById(R.id.accountNumber);
        panNo = view.findViewById(R.id.panNo);
        phonecode = view.findViewById(R.id.phonecode);

        // All Spinners
        titile = view.findViewById(R.id.titile);
        genderspiner = view.findViewById(R.id.genderspiner);
        Select_Country = view.findViewById(R.id.Select_Country);
        Select_State = view.findViewById(R.id.Select_State);
        cityText = view.findViewById(R.id.Cityget);
//        Select_District = view.findViewById(R.id.Select_District);

        titile.setOnItemSelectedListener(this);
        genderspiner.setOnItemSelectedListener(this);
        Select_Country.setOnItemSelectedListener(this);
        Select_State.setOnItemSelectedListener(this);
//        Select_City.setOnItemSelectedListener(this);
//        Select_District.setOnItemSelectedListener(this);

        // All TextViews
        dobtext = view.findViewById(R.id.dobtext);
        countryget = view.findViewById(R.id.countryget);
        changepassworddialog = view.findViewById(R.id.changepassworddialog);

        // All Buttons
        updateBtn = view.findViewById(R.id.updateBtn);
//        cancelBtn = view.findViewById(R.id.cancelBtn);

        sessionManagement = new SessionManagement(getContext());
        sessionManagement.checkLogin();

        user = sessionManagement.getUserDetails();

        customeDialog = new Dialog(getContext());
        loadingBar = new ProgressDialog(getContext());

        genderList = new ArrayList<>();
        genderList.add("Select Gender");
        genderList.add("Male");
        genderList.add("Female");


        titleList = new ArrayList<>();
        titleList.add("Select Title");
        titleList.add("Mr.");
        titleList.add("Mrs.");
        titleList.add("Ms.");
        titleList.add("Proff.");
        titleList.add("Dr.");
        titleList.add("M/s.");
        titleList.add("Other");

        try {

            MySpinnerAdapter adapter = new MySpinnerAdapter(
                    getContext(),
                    R.layout.view_spinner_item,
                    genderList);
            adapter.setDropDownViewResource(R.layout.drop_down_item);
            genderspiner.setAdapter(adapter);
            genderspiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i >0) {
                        gender = adapterView.getItemAtPosition(i).toString();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            MySpinnerAdapter adapter = new MySpinnerAdapter(
                    getContext(),
                    R.layout.view_spinner_item,
                    titleList);
            adapter.setDropDownViewResource(R.layout.drop_down_item);
            titile.setAdapter(adapter);
            titile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i >0) {
                        title = adapterView.getItemAtPosition(i).toString();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        urls = Config.url + "getProfile/" + Config.merchantid + "/" + Config.securtykey + "/" + user.get(SessionManagement.KEY_USERNAME) + "/" + user.get(SessionManagement.KEY_PASSWORD);
        updateProfileUrl = Config.url + "UpdateProfile/" + Config.merchantid + "/" + Config.securtykey + "/" + user.get(SessionManagement.KEY_USERNAME) + "/" + user.get(SessionManagement.KEY_PASSWORD);

        Log.e(TAG, "onCreateView: "+ urls);
        loadProfile();

        changepassworddialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePasswordDialog();
            }
        });


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String userName = Username.getText().toString();
                String fName = fname.getText().toString();
                String lName = lanme.getText().toString();
                String spouseName = spousename.getText().toString();
                String _phone = phone.getText().toString();
                String _emailId = emailId.getText().toString();
//                String _nomName = nomName.getText().toString();
//                String _relation = relation.getText().toString();
                String _address = Address.getText().toString();
                String _pincodeep = pincodeep.getText().toString();
                String _cardnoshow = cardnoshow.getText().toString();
                String _bankName = bankName.getText().toString();
                String _bankadd = bankadd.getText().toString();
                String _ifsccode = ifsccode.getText().toString();
                String _accountNumber = accountNumber.getText().toString();
                String _panNo = panNo.getText().toString();
                String _dobtext = dobtext.getText().toString();

                UserProfileData userProfileData = new UserProfileData();
                userProfileData.setUserName(userName);
                userProfileData.setFirstName(fName);
                userProfileData.setLastName(lName);
                userProfileData.setFatherName(spouseName);
                userProfileData.setMobileNo(_phone);
                userProfileData.setEmail(_emailId);
//                userProfileData.setNomName(_nomName);
//                userProfileData.setNomRelation(_relation);
                userProfileData.setAddress(_address);
                userProfileData.setPinCode(_pincodeep);
                userProfileData.setBank(_bankName);
                userProfileData.setBankAdd(_bankadd);
                userProfileData.setiFSC(_ifsccode);
                userProfileData.setAccNo(_accountNumber);
                userProfileData.setPanNo(_panNo);
                userProfileData.setTitle(title);
                userProfileData.setGender(gender);
                userProfileData.setState(stateName);
                userProfileData.setCity(cityName);
                userProfileData.setDistrict(districtName);
                userProfileData.setdOB(_dobtext);
                userProfileData.setsID(stateId);
                userProfileData.setcTID(cTID);
//                userProfileData.setCountryId(countryId);
                userProfileData.setCountryId("96");
                userProfileData.setdISTID(districId);

                updateProfile(userProfileData);

            }
        });

        return view;
    }

    private void showChangePasswordDialog(){

        customeDialog.setContentView(R.layout.custome_dialogbox_password_change);
        oldpassword = customeDialog.findViewById(R.id.old_password);
        newpassword = customeDialog.findViewById(R.id.new_password);
        confirmpassword = customeDialog.findViewById(R.id.confirm_password);

        Button Submit = customeDialog.findViewById(R.id.submit_change_password_btn);

        ImageView Close = customeDialog.findViewById(R.id.close_dialog_cp);

        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customeDialog.dismiss();
//                Intent in = new Intent(getContext(), HomeActivity.class);
//                startActivity(in);
//                finish();
            }
        });
        customeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customeDialog.show();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldpass = oldpassword.getText().toString();
                String newpass = newpassword.getText().toString();
                String conpass = confirmpassword.getText().toString();

                if (TextUtils.isEmpty(oldpass)) {
                    Toast.makeText(getActivity(), "Please Enter Old Password...", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(newpass)) {
                    Toast.makeText(getActivity(), "Please Enter New Password...", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(conpass)) {
                    Toast.makeText(getActivity(), "Please Enter Confirm Password...", Toast.LENGTH_SHORT).show();
                } else if (newpass.equals(conpass)) {
                    ChangePassword(user.get(sessionManagement.KEY_SHOPID), oldpass, conpass);
                } else {
                    Toast.makeText(getActivity(), "Confirm Password Does not Matched...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void ChangePassword(String shopId, String oldpass, String conpass) {
//        String changepasswordurl="http://tiup.proconnetwork.com/DeveloperAPI/changePassword/1/PR65U610UYTCON/Updatedata";
//        String changepasswordurl=ut.url+"changePassword"+ ut.merchantid + ut.securitykey+"Updatedata";
        String changepasswordurl=Config.url+"changePassword/"+ Config.merchantid +"/"+ Config.securtykey+"/"+ user.get(SessionManagement.KEY_USERNAME)+"/Parameter";
        loadingBar.setTitle("Change Password");
        loadingBar.setMessage("Please Wait ,While we are Change Password...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        RequestQueue requestQueue=Volley.newRequestQueue(getContext());
        JSONArray jsonArray=new JSONArray();
        JSONObject parems=new JSONObject();

        try {
//            parems.put("ShopID",shopId);
            parems.put("oldPass",oldpass);
            parems.put("newPass",conpass);
//            parems.put("SecurityKey", Config.securtykey.replaceAll("/",""));
//            parems.put("intResult","0");
            jsonArray.put(parems);
            Log.e("jsoncp", String.valueOf(jsonArray));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.POST, changepasswordurl, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONObject jsonObject = response.getJSONObject(0);

                    Log.e("msg", String.valueOf(jsonObject));

                    if (jsonObject.getString("Status").equals("True"))
                    {
                        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("ALPSPref",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("confirm_password",conpass);
                        sessionManagement.changepassword(conpass);
                        customeDialog.dismiss();
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "Please Enter Valid Password...!!!", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loadingBar.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingBar.dismiss();
            }
        });requestQueue.add(jsonArrayRequest);


    }

    private void loadProfile() {

        //dialog.show();
//        showDefaultLoaderFragment();
        RequestQueue requestQueue= new Volley().newRequestQueue(getContext());
        StringRequest stringRequest= new StringRequest(Request.Method.GET, urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray object=new JSONArray(response);
                            //    for (int i=0;i<object.length();i++)
                            JSONObject jsonObject=object.getJSONObject(0);
                            //   Boolean b=object.getBoolean("error");

                            if (jsonObject.getBoolean("Status")){
                                //   String user=object.getJSONObject("user").getString("UserID");
                                String usename=jsonObject.getString("UserID");
                                String Firstname=jsonObject.getString("FirstName");
                                String lastname=jsonObject.getString("LastName");
                                String status=jsonObject.getString("Status");
                                String regdate=jsonObject.getString("Regdate");
                                String email=jsonObject.getString("EmailID");
                                String dateofjoining=jsonObject.getString("Regdate");
                                String currency=jsonObject.getString("CurrencySymbol");
                                String userEncrpted=jsonObject.getString("UPEncy");
                                String MobileNo=jsonObject.getString("MobileNo");
                                String address=jsonObject.getString("Address");
                                String State=jsonObject.getString("State");
                                stateName = State;
                                String SID=jsonObject.getString("SID");
                                stateId = SID;
                                String PinCode=jsonObject.getString("PinCode");
                                pinCodeStr = PinCode;
                                String DISTID=jsonObject.getString("DISTID");
                                districId = DISTID;
                                String CID=jsonObject.getString("CID");
                                countryId = CID;
                                String CTID=jsonObject.getString("CTID");
                                String City=jsonObject.getString("City");
                                cityName = City;
                                String District=jsonObject.getString("District");
                                districtName = District;
                                String Country=jsonObject.getString("Country");
                                String countryCode =jsonObject.getString("Countrycode");
                                String Gender=jsonObject.getString("Gender");
                                String Title=jsonObject.getString("Title");
                                String DOB=jsonObject.getString("DOB");
                                String Fathername=jsonObject.getString("Fathername");
                                String Nomname=jsonObject.getString("Nomname");
                                String NomRelation=jsonObject.getString("NomRelation");
                                String Bank=jsonObject.getString("Bank");
                                String BankAdd=jsonObject.getString("BankAdd");
                                String IFSC=jsonObject.getString("IFSC");
                                String AccNo=jsonObject.getString("AccNo");
                                String Panno=jsonObject.getString("Panno");

                                Log.e(TAG, "onResponse: Dis id : "+ districId );
                                Log.e(TAG, "onResponse: state id : "+ stateId );
                                Log.e(TAG, "onResponse: country id : "+ countryId );
                                Log.e(TAG, "onResponse: cityId : "+ CTID );

                                if(!Title.isEmpty()){
                                    titile.setSelection(getSelectedItemPosition(Title, titleList));
                                }
                                if(!Gender.isEmpty()){
                                    genderspiner.setSelection(getSelectedItemPosition(Gender, genderList));
                                }

                                fname.setText(Firstname);
                                lanme.setText(lastname);
                                emailId.setText(email);
                                phone.setText(MobileNo);
                                dobtext.setText(DOB);
                                spousename.setText(Fathername);
                                cityText.setText(cityName);
                                pincodeep.setText(PinCode);
                                Address.setText(address);
                                countryget.setText(Country);
                                phonecode.setText(countryCode);

                                bankadd.setText(BankAdd);
                                bankName.setText(Bank);
                                ifsccode.setText(IFSC);
                                accountNumber.setText(AccNo);
                                panNo.setText(Panno);

                                getStateList();

                            }


                        } catch (JSONException e) {
//                            hideDefaultLoaderFragment();
//                            showErrorSnackBar(loginpage, "Something went wrong");
                            e.printStackTrace();
                        }   /*if (dialog.isShowing())
                            dialog.dismiss();*/

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Snackbar snackbar = Snackbar.make(loginpage,""+error,Snackbar.LENGTH_LONG);
//                snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
//                View snackbarView = snackbar.getView();
//                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
////                snackbar.show();
//                hideDefaultLoaderFragment();
//                showErrorSnackBar(loginpage, "Something went wrong");
//                //Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
               /* if (dialog.isShowing())
                    dialog.dismiss();*/
            }
        })
        {
//            protected Map<String,String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("username",usname);
//                params.put("pass",userspass);
//                return params;
//            }
        };
        requestQueue.add(stringRequest);
    }

    private int getSelectedItemPosition(String itemName, ArrayList<String> itemArrayList) {

        int index = 0;
        if (itemName != null && !itemName.isEmpty()) {
            for (String str :
                    itemArrayList) {
                if (itemName.equalsIgnoreCase(str)) {
                    return index;
                }
                index++;
            }
        }
        return 0;
    }


//    private void getCityList(String stateId, String districtId) {
//
//        try {
//
////      String url = "https://api.goldways.in/DeveloperApi/getCity/M/H1298HyHicofI/1/H1298HyiCofi4542crTy/96/" + stateId + "/" + districtId;
//            String url = "https://api.goldways.in/DeveloperApi/getCity/" + Config.merchantid + "/" + Config.securtykey + "/" + countryId + "/" + stateId + "/" + districtId;
//            Log.e(TAG, "getCityList: " + url);
//            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                    response -> {
//                        try {
//                            JSONArray object = new JSONArray(response);
//
//                            finalStateDetails.clear();
//                            cityItemArrayList.clear();
//
//                            cityItemArrayList.add("Select City");
//
//                            for (int i = 0; i < object.length(); i++) {
//                                JSONObject jsonObject = object.getJSONObject(i);
//
//                                if (jsonObject.getBoolean("Status")) {
//                                    String sID = jsonObject.getString("SID");
//                                    String distID = jsonObject.getString("DistID");
////                                  String distName = jsonObject.getString("DistName");
//                                    String cTID = jsonObject.getString("CTID");
//                                    String cityName = jsonObject.getString("CityName");
//                                    pinCodeStr = jsonObject.getString("Pincode");
//                                    String messageCode = jsonObject.getString("MessageCode");
//                                    String message = jsonObject.getString("message");
//
//                                    StateDetails stateDetails = new StateDetails();
//                                    stateDetails.setsID(sID);
//                                    stateDetails.setStateName(stateName);
//                                    stateDetails.setDistID(distID);
//                                    stateDetails.setDistName(districtName);
//                                    stateDetails.setcTID(cTID);
//                                    stateDetails.setCityName(cityName);
//                                    stateDetails.setPinCode(pinCodeStr);
//
//
//                                    cityItemArrayList.add(cityName);
//                                    cityHashMap.put(cityName, cTID);
//                                    finalStateDetails.put(cityName, stateDetails);
//
//                                }
//                            }
//
//                            if (feildsEnabled) {
//
//                                MySpinnerAdapter adapter = new MySpinnerAdapter(
//                                        getContext(),
//                                        R.layout.view_spinner_item,
//                                        cityItemArrayList);
//                                adapter.setDropDownViewResource(R.layout.drop_down_item);
//                                Select_City.setAdapter(adapter);
////                                cityLL.setBackground(getResources().getDrawable(R.drawable.rectangle_yellow_box_layout));
//
//
//                                if (cityItemArrayList != null && cityItemArrayList.size() > 0) {
//                                    int selectedPosition = getSelectedItemPosition(cityName, cityItemArrayList);
//                                    if (selectedPosition > 0) {
//                                        Select_City.setSelection(selectedPosition);
//                                    }
//                                }
//                            } else {
//                                MySpinnerAdapter adapter = new MySpinnerAdapter(
//                                        getContext(),
//                                        R.layout.disabled_view_spinner,
//                                        cityItemArrayList);
//                                adapter.setDropDownViewResource(R.layout.disabled_drop_down_item);
//                                Select_City.setAdapter(adapter);
////                                cityLL.setBackground(getResources().getDrawable(R.drawable.disabled_edit_text));
//
//
//                                if (cityItemArrayList != null && cityItemArrayList.size() > 0) {
//                                    int selectedPosition = getSelectedItemPosition(cityName, cityItemArrayList);
//                                    if (selectedPosition > 0) {
//                                        Select_City.setSelection(selectedPosition);
//                                    }
//                                }
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }, error -> {
//
//            }) {
//                protected Map<String, String> getParams() {
//                    return new HashMap<>();
//                }
//            };
//            requestQueue.add(stringRequest);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

//    private void getDistrictList(String stateCode) {
//
////      String url = "https://api.goldways.in/DeveloperApi/getCity/M/H1298HyHicofI/1/H1298HyiCofi4542crTy/96/" + stateCode + "/0";
//        String url = "https://api.goldways.in/DeveloperApi/getCity/" + Config.merchantid + "/" + Config.securtykey + "/" + countryId + "/" + stateCode + "/0";
//
//        Log.e(TAG, "getDistrictList: " + url);
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                response -> {
//                    try {
//                        JSONArray object = new JSONArray(response);
//
//                        districtItemArrayList.clear();
//                        districtItemArrayList.add("Select District");
//
//                        for (int i = 0; i < object.length(); i++) {
//                            JSONObject jsonObject = object.getJSONObject(i);
//
//                            if (jsonObject.getBoolean("Status")) {
//                                String sID = jsonObject.getString("SID");
//                                String districId = jsonObject.getString("DistID");
//                                String distName = jsonObject.getString("DistName");
//                                String messageCode = jsonObject.getString("MessageCode");
//                                String message = jsonObject.getString("message");
//
//                                StateDetails stateDetails = new StateDetails();
//                                stateDetails.setsID(sID);
//                                stateDetails.setStateName(stateName);
//                                stateDetails.setDistID(districId);
//                                stateDetails.setDistName(distName);
//
//                                districtItemArrayList.add(distName);
//                                districtHashMap.put(distName, districId);
//
//                            }
//                        }
//
//                        MySpinnerAdapter adapter;
//                        if(feildsEnabled) {
//
//                            adapter = new MySpinnerAdapter(
//                                    getContext(),
//                                    R.layout.view_spinner_item,
//                                    districtItemArrayList);
//
//                            adapter.setDropDownViewResource(R.layout.drop_down_item);
//                            Select_District.setAdapter(adapter);
////                          districtLL.setBackground(getResources().getDrawable(R.drawable.rectangle_yellow_box_layout));
//
//                            if (!stateName.isEmpty()) {
//                                if (districtItemArrayList != null && districtItemArrayList.size() > 0) {
//                                    int selectedPosition = getSelectedItemPosition(districtName, districtItemArrayList);
//                                    if (selectedPosition > 0) {
//                                        Select_District.setSelection(selectedPosition);
//                                        districId = districtHashMap.get(districtName);
//                                    }
//                                }
//                            }
//
//                        } else {
//                            adapter = new MySpinnerAdapter(
//                                    getContext(),
//                                    R.layout.disabled_view_spinner,
//                                    districtItemArrayList);
//
//                            adapter.setDropDownViewResource(R.layout.disabled_drop_down_item);
//                            Select_District.setAdapter(adapter);
////                          districtLL.setBackground(getResources().getDrawable(R.drawable.disabled_edit_text));
//
//                            if (!stateName.isEmpty()) {
//                                if (districtItemArrayList != null && districtItemArrayList.size() > 0) {
//                                    int selectedPosition = getSelectedItemPosition(districtName, districtItemArrayList);
//                                    if (selectedPosition > 0) {
//                                        Select_District.setSelection(selectedPosition);
//                                        districId = districtHashMap.get(districtName);
//                                    }
//                                }
//                            }
//                        }
//                        Log.e(TAG, "getDistrictList: State id : "+ stateId );
//                        Log.e(TAG, "getDistrictList: District id : "+ districId );
//                        getCityList(stateId, districId);
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }   /*if (dialog.isShowing())
//                        dialog.dismiss();*/
//
//                }, error -> {
//
//        }) {
//            protected Map<String, String> getParams() {
//                return new HashMap<>();
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

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

                            if(feildsEnabled) {

                                MySpinnerAdapter adapter = new MySpinnerAdapter(
                                        getContext(),
                                        R.layout.view_spinner_item,
                                        stateItemArrayList);
                                adapter.setDropDownViewResource(R.layout.drop_down_item);
                                Select_State.setAdapter(adapter);
//                                stateLL.setBackground(getResources().getDrawable(R.drawable.rectangle_yellow_box_layout));


                                if (stateItemArrayList != null && stateItemArrayList.size() > 0) {
                                    int selectedPosition = getSelectedItemPosition(stateName, stateItemArrayList);
                                    if (selectedPosition > 0) {
                                        Select_State.setSelection(selectedPosition);
                                    }
                                }
                            } else {

                                MySpinnerAdapter adapter = new MySpinnerAdapter(
                                        getContext(),
                                        R.layout.disabled_view_spinner,
                                        stateItemArrayList);
                                adapter.setDropDownViewResource(R.layout.disabled_drop_down_item);
                                Select_State.setAdapter(adapter);
//                                stateLL.setBackground(getResources().getDrawable(R.drawable.disabled_edit_text));


                                if (stateItemArrayList != null && stateItemArrayList.size() > 0) {
                                    int selectedPosition = getSelectedItemPosition(stateName, stateItemArrayList);
                                    if (selectedPosition > 0) {
                                        Select_State.setSelection(selectedPosition);
                                    }
                                }

                            }

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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//        if (firstTimeLoaded) {
        if (parent.getId() == R.id.Select_State) {
            if (stateItemArrayList != null && stateItemArrayList.size() > 0) {
                if (position > 0) {
                    stateName = stateItemArrayList.get(position);
//                    if (stateHashMap != null && stateHashMap.containsKey(stateName)) {
//                        stateId = stateHashMap.get(stateName.trim());
//                        getDistrictList(stateId);
//                    }
                }
            }
        }
//        else if (parent.getId() == R.id.Select_District) {
//            if (districtItemArrayList != null && districtItemArrayList.size() > 0) {
//                if (position > 0) {
//                    districtName = districtItemArrayList.get(position);
//                    if (districtHashMap != null && districtHashMap.containsKey(districtName)) {
//                        districId = districtHashMap.get(districtName.trim());
//                        getCityList(stateId, districId);
//                    }
//                }
//            }
//        }
//        else if (parent.getId() == R.id.Select_City) {
//            if (cityItemArrayList != null && cityItemArrayList.size() > 0) {
//                if (position > 0) {
//                    cityName = cityItemArrayList.get(position);
//                    if (cityHashMap != null && cityHashMap.containsKey(cityName)) {
//                        String cityId = cityHashMap.get(cityName.trim());
//                        pincodeep.setText(pinCodeStr);
//                    }
//                }
//            }
//        }
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void updateProfile(final UserProfileData userProfileData) {



        JSONArray jsonArray = new JSONArray();
        try {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("title", userProfileData.getTitle());
            jsonObject.put("fname", userProfileData.getFirstName());
            jsonObject.put("lname", userProfileData.getLastName());
            jsonObject.put("dob", userProfileData.getdOB());
            jsonObject.put("Gender", userProfileData.getGender().equalsIgnoreCase("Male") ? "1" : "2");
            jsonObject.put("mobile", userProfileData.getMobileNo());
            jsonObject.put("emailid", userProfileData.getEmail());
            jsonObject.put("nomname", userProfileData.getNomName().equalsIgnoreCase("null")? "" : userProfileData.getNomName());
            jsonObject.put("relation", userProfileData.getNomRelation());
            jsonObject.put("sid", userProfileData.getsID());
            jsonObject.put("oState", userProfileData.getState());
            jsonObject.put("Ctid", userProfileData.getcTID());
            jsonObject.put("oCity", userProfileData.getCity());
            jsonObject.put("pincode", userProfileData.getPinCode());
            jsonObject.put("address", userProfileData.getAddress());
            jsonObject.put("coreBank", userProfileData.getBank());
            jsonObject.put("bankaddress", userProfileData.getBankAdd());
            jsonObject.put("IFSCCode", userProfileData.getiFSC());
            jsonObject.put("accountNo", userProfileData.getAccNo());
            jsonObject.put("countryid", userProfileData.getCountryId());
            jsonObject.put("distid", userProfileData.getdISTID());
            jsonObject.put("distName", userProfileData.getDistrict());
            jsonObject.put("fatherName", userProfileData.getFatherName());
            jsonObject.put("pancard", userProfileData.getPanNo());

            jsonArray.put(jsonObject);

            Log.e(TAG, "saveOrder: " + jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String mRequestBody = jsonArray.toString();

        Log.e(TAG, "onCreateView: " + updateProfileUrl);

        RequestQueue requestQueue = new Volley().newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, updateProfileUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray1 = new JSONArray(response);
                            JSONObject object = jsonArray1.getJSONObject(0);
                            Log.e(TAG, "onResponse: " + object);
                            String status = object.getString("Status");
                            String message = object.getString("message");
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }   /*if (dialog.isShowing())
                            dialog.dismiss();*/
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    uee.printStackTrace();
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
//            protected Map<String,String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                return params;
//            }
        };
        requestQueue.add(stringRequest);
    }
}