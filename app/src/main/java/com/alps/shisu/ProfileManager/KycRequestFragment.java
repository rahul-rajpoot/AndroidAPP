package com.alps.shisu.ProfileManager;


import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.R;
import com.alps.shisu.SaveSharedPreference;
import com.alps.shisu.Session.SessionManagement;
import com.alps.shisu.ImagePicker;
import com.alps.shisu.retrofit_service.NetworkManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;
import okhttp3.HttpUrl;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class KycRequestFragment extends Fragment {

    LinearLayout kyclinrearout1, kyclinrearout2, kyclinrearout3, kyclinrearout4, kyclinrearout5;

    WebView webview;
    Context context;

    ProgressBar progressBar;
    HashMap<String, String> user;
    SharedPreferences preferences;
    public SessionManagement session;
    AlertDialog dialog;

    private CardView ra_pan_details_cv, ra_upload_aadhar_details_cv, ra_show_bank_details_cv;
    private TextView pan_submit_tv, adhar_submit_tv, bank_submit_tv, kyc_submit_mobile_tv, kyc_upload_back_aadhar_tv,
            kyc_upload_front_aadhar_tv;
    private LinearLayout kyc_upload_pan_tv, kyc_upload_cancelled_cheque_tv;
    private EditText kyc_bank_name_et, kyc_account_number_et, kyc_ifsc_code_et, kyc_otp_et,
            kyc_name_et, kyc_mobile_et, kyc_aadhar_et, kyc_pan_et, kyc_bank_branch_et;
//    private CheckBox kyc_check_box;
    private ImageView kyc_aadhar_iv, kyc_pan_iv, kyc_upload_cancelled_cheque_iv, kyc_aadhar_front_iv, kyc_aadhar_back_iv;
    private RelativeLayout cb_aadhar_front_rl, cb_aadhar_back_rl, kyc_upload_cancelled_cheque_rl, kyc_pan_rl;
    private ImageView cb_aadhar_front_remove_iv, cb_aadhar_back_remove_iv, kyc_upload_cancel_cheque_remove_iv,
            cb_pan_remove_iv, ic_menu_icon, ic_home_icon, ic_logout_iv;

    private static final String F_AADHAAR = "f_aadhaar";
    private static final String B_AADHAAR = "b_aadhaar";
    private static final String PAN = "pan";
    private static final String BANK_DETAILS = "bank_details";
    private ProgressDialog innerProgressDialog;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private int PICK_IMAGE_REQUEST_AADHAR_FRONT = 100;
    private int PICK_IMAGE_REQUEST_AADHAR_BACK = 150;
    private int PICK_IMAGE_REQUEST_BANK_DETAILS = 250;
    private int PICK_IMAGE_REQUEST_PAN = 200;
    private Bitmap bitmapPan, bitmapAadharFront, bitmapAadharBack, bitmapBankDetails;
    private Uri filePathPan, filePathAadharFront, filePathAadharBack, filePathBankDetails;
    private String selectedFilePathAadharFront, selectedFilePathAadharBack, selectedFilePathPan, selectedFilePathBankDetails;
    private int pic_id = 700;
    private TextView pan_details_approved_tv, aadhaar_details_approved_tv, bank_details_approved_tv, kyc_Approved_tv;
    private String aadhar_f_url = "", aadhar_b_url = "", pan_url = "", bank_details_url = "";
    private String loginId ="";
    SessionManagement sessionManagement;

    public KycRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kyc_request, container, false);
        new SaveSharedPreference(getContext());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ALPSPref", MODE_PRIVATE);
        loginId = !sharedPreferences.getString("uid", "").isEmpty() ? sharedPreferences.getString("uid", "") : "";

        webview = view.findViewById(R.id.videoView1);
        dialog = new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
        initializViews(view);
        setOnClickListeners(view);

        preferences=getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        sessionManagement=new SessionManagement(getActivity().getApplicationContext());
        user=sessionManagement.getUserDetails();

        getCustomerDetails();
//        loadurl();

        return view;
    }

    private void initializViews(View view) {
//        ic_logout_iv = view.findViewById(R.id.ic_logout_iv);
//        ic_home_icon = view.findViewById(R.id.ic_home_icon);
//        ic_menu_icon = view.findViewById(R.id.ic_menu_icon);
        kyc_upload_cancel_cheque_remove_iv = view.findViewById(R.id.kyc_upload_cancel_cheque_remove_iv);
        kyc_upload_cancelled_cheque_rl = view.findViewById(R.id.kyc_upload_cancelled_cheque_rl);
        cb_aadhar_front_rl = view.findViewById(R.id.cb_aadhar_front_rl);
        cb_aadhar_back_rl = view.findViewById(R.id.cb_aadhar_back_rl);
        cb_aadhar_front_remove_iv = view.findViewById(R.id.cb_aadhar_front_remove_iv);
        cb_aadhar_back_remove_iv = view.findViewById(R.id.cb_aadhar_back_remove_iv);
        kyc_pan_rl = view.findViewById(R.id.kyc_pan_rl);
        ra_pan_details_cv = view.findViewById(R.id.ra_pan_details_cv);
        ra_upload_aadhar_details_cv = view.findViewById(R.id.ra_upload_aadhar_details_cv);
        ra_show_bank_details_cv = view.findViewById(R.id.ra_show_bank_details_cv);
        pan_submit_tv = view.findViewById(R.id.pan_details_submit_tv);
        adhar_submit_tv = view.findViewById(R.id.adhar_details_submit_tv);
        bank_submit_tv = view.findViewById(R.id.bank_details_submit_tv);
//        kyc_submit_mobile_tv = view.findViewById(R.id.kyc_submit_mobile_tv);
        kyc_upload_cancelled_cheque_tv = view.findViewById(R.id.kyc_upload_cancelled_cheque_tv);
        kyc_bank_name_et = view.findViewById(R.id.kyc_bank_name_et);
        kyc_account_number_et = view.findViewById(R.id.kyc_account_number_et);
        kyc_ifsc_code_et = view.findViewById(R.id.kyc_ifsc_code_et);
//        kyc_otp_et = view.findViewById(R.id.kyc_otp_et);
//        kyc_name_et = view.findViewById(R.id.kyc_name_et);
//        kyc_check_box = view.findViewById(R.id.kyc_check_box);
//        kyc_mobile_et = view.findViewById(R.id.kyc_mobile_et);
        kyc_upload_front_aadhar_tv = view.findViewById(R.id.kyc_upload_front_aadhar_tv);
        kyc_upload_back_aadhar_tv = view.findViewById(R.id.kyc_upload_back_aadhar_tv);
        kyc_aadhar_et = view.findViewById(R.id.kyc_aadhar_et);
        kyc_pan_et = view.findViewById(R.id.kyc_pan_et);
        kyc_upload_back_aadhar_tv = view.findViewById(R.id.kyc_upload_back_aadhar_tv);
        kyc_upload_front_aadhar_tv = view.findViewById(R.id.kyc_upload_front_aadhar_tv);
        kyc_pan_iv = view.findViewById(R.id.kyc_pan_iv);
        kyc_upload_pan_tv = view.findViewById(R.id.kyc_upload_pan_tv);
        kyc_bank_branch_et = view.findViewById(R.id.kyc_bank_branch_et);
        kyc_upload_cancelled_cheque_iv = view.findViewById(R.id.kyc_upload_cancelled_cheque_iv);
        kyc_aadhar_back_iv = view.findViewById(R.id.kyc_aadhar_back_iv);
        kyc_aadhar_front_iv = view.findViewById(R.id.kyc_aadhar_front_iv);
        cb_pan_remove_iv = view.findViewById(R.id.cb_pan_remove_iv);
        //ra_check_box_tv = findViewById(R.id.ra_check_box_tv);
        pan_details_approved_tv = view.findViewById(R.id.pan_details_approved_tv);
        aadhaar_details_approved_tv = view.findViewById(R.id.aadhaar_details_approved_tv);
        bank_details_approved_tv = view.findViewById(R.id.bank_details_approved_tv);
//        kyc_Approved_tv = view.findViewById(R.id.kyc_Approved_tv);
    }


    private void setOnClickListeners(View view) {
//        ic_logout_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Config.doLogout(CustomerDetailsActivity.this);
//            }
//        });
//        ic_home_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Config.goToDashboard(CustomerDetailsActivity.this);
//            }
//        });
//        ic_menu_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDrawerLayout.openDrawer(Gravity.LEFT);
//            }
//        });
//        kyc_submit_mobile_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (kyc_submit_mobile_tv.getText().equals("Verify")) {
//
//                    String mobile = kyc_mobile_et.getText().toString();
//                    if (mobile.isEmpty()) {
//                        kyc_mobile_et.requestFocus();
//                        kyc_mobile_et.setError("Enter Mobile No.");
//                    } else if (mobile.length() < 10) {
//                        kyc_mobile_et.requestFocus();
//                        kyc_mobile_et.setError("Enter Valid Mobile No.");
//                    } else {
//                        callcheckMobileAPI(mobile);
//                    }
//                }
//
//                else {
//                    clearAllFields();
//                }
//            }
//        });
        pan_submit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkPanDataForServer();

            }
        });

        bank_submit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkBankDetailsDataForServer();

            }
        });

        adhar_submit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAdharDataForServer();

            }
        });
        kyc_upload_front_aadhar_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showFileChooser(F_AADHAAR);
                selectImage(F_AADHAAR);
            }
        });
        kyc_upload_back_aadhar_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(B_AADHAAR);
            }
        });
        kyc_upload_pan_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(PAN);
            }
        });

        kyc_upload_cancelled_cheque_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(BANK_DETAILS);
            }
        });
        kyc_pan_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPanPreviewDialog(PAN, bitmapPan, view);
            }
        });
        kyc_aadhar_front_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPanPreviewDialog(F_AADHAAR, bitmapAadharFront, view);
            }
        });
        kyc_aadhar_back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPanPreviewDialog(B_AADHAAR, bitmapAadharBack, view);
            }

        });
        kyc_upload_cancelled_cheque_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPanPreviewDialog(BANK_DETAILS, bitmapBankDetails, view);
            }
        });
        cb_pan_remove_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePanDetails();

            }
        });
        cb_aadhar_front_remove_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFrontAadharDetails();
            }
        });
        cb_aadhar_back_remove_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeBackAadharDetails();
            }
        });
        kyc_upload_cancel_cheque_remove_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCancelledChequeDetails();

            }
        });
    }

    public static boolean isAccountValid(String accountNo) {
        String regExp = "[0-9]{9,18}";
        boolean isValid = false;
        if (accountNo.length() > 0) {
            isValid = accountNo.matches(regExp);
        }
        return isValid;
    }

    public static boolean isIfscCodeValid(String IFSCCode) {
        String regExp = "^[A-Z]{4}[0][A-Z0-9]{6}$";
        boolean isvalid = false;

        if (IFSCCode.length() > 0) {
            isvalid = IFSCCode.matches(regExp);
        }
        return isvalid;
    }

    public static boolean isValidPanCardNo(String panCardNo) {
        // Regex to check valid PAN Card number.
        String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        if (panCardNo == null) {
            return false;
        }
        Matcher m = p.matcher(panCardNo);
        return m.matches();
    }

    private void removeCancelledChequeDetails() {
        bitmapBankDetails = null;
        selectedFilePathBankDetails = "";
        filePathBankDetails = null;
        kyc_upload_cancelled_cheque_rl.setVisibility(View.GONE);
        kyc_upload_cancelled_cheque_tv.setVisibility(View.VISIBLE);
        bank_details_url = "";
    }

    private void removePanDetails() {
        bitmapPan = null;
        selectedFilePathPan = "";
        filePathPan = null;
        kyc_pan_rl.setVisibility(View.GONE);
        kyc_upload_pan_tv.setVisibility(View.VISIBLE);
        pan_url = "";
    }

    private void removeBackAadharDetails() {
        bitmapAadharBack = null;
        selectedFilePathAadharBack = "";
        filePathAadharBack = null;
        cb_aadhar_back_rl.setVisibility(View.GONE);
        kyc_upload_back_aadhar_tv.setVisibility(View.VISIBLE);
        aadhar_b_url = "";
    }

    private void removeFrontAadharDetails() {
        bitmapAadharFront = null;
        selectedFilePathAadharFront = "";
        filePathAadharFront = null;
        cb_aadhar_front_rl.setVisibility(View.GONE);
        kyc_upload_front_aadhar_tv.setVisibility(View.VISIBLE);
        aadhar_f_url = "";
    }

    private void checkPanDataForServer() {
        String regno, pan_no, pan_image, pan_imagename;

//        regno = SaveSharedPreference.getRegno();
        regno = loginId;
//        mobileno = kyc_mobile_et.getText().toString();
//        otp = kyc_otp_et.getText().toString();
//        name = kyc_name_et.getText().toString();
        pan_no = kyc_pan_et.getText().toString();

        if (bitmapPan == null) {
            pan_image = "";
            pan_imagename = "";
        } else {
            pan_image = convertBitmapToString(bitmapPan);
            pan_imagename = "pan_image.png";
        }
        if ((pan_url.isEmpty() && pan_image.isEmpty()) && !pan_no.isEmpty()) {
            if (isValidPanCardNo(pan_no)) {
                Config.showCustomToast(getActivity(), "Upload Pan Image");
            } else {
                kyc_pan_et.requestFocus();
                kyc_pan_et.setError("Enter Valid Pan No.");
            }
        } else if ((!pan_image.isEmpty() || !pan_url.isEmpty()) && pan_no.isEmpty()) {
            kyc_pan_et.requestFocus();
            kyc_pan_et.setError("Enter Pan");
        }
//        else if (!kyc_check_box.isChecked()) {
//            Config.showCustomToast(getActivity(), "Accept Terms and Conditions");
//        }
        else {
            if (!pan_no.isEmpty() && !isValidPanCardNo(pan_no)) {
                kyc_pan_et.requestFocus();
                kyc_pan_et.setError("Enter Valid PAN No.");
            } else if (pan_no.isEmpty()) {
                Config.showCustomToast(getActivity(), "Please complete any section of KYC");
            } else {
                sendPanDataToServer(regno, pan_no, pan_image, pan_imagename);
            }

        }

    }

        private void checkAdharDataForServer() {
        String regno, aadhaar_no, aadhaarfront_image, aadhaarback_image, aadhaarback_imagename,
               aadhaarfront_imagename;

//        regno = SaveSharedPreference.getRegno();
        regno = loginId;

        aadhaar_no = kyc_aadhar_et.getText().toString();
        if (bitmapAadharFront == null) {
            aadhaarfront_image = "";
            aadhaarfront_imagename = "";

        } else {
            aadhaarfront_image = convertBitmapToString(bitmapAadharFront);
            aadhaarfront_imagename = "aadhaar_front.png";

        }
        if (bitmapAadharBack == null) {
            aadhaarback_image = "";
            aadhaarback_imagename = "";

        } else {
            aadhaarback_imagename = "aadhaar_front.png";
//            aadhaarback_imagename = "aadhaar_back.png";
            aadhaarback_image = convertBitmapToString(bitmapAadharBack);

        }

        if ((!aadhaarfront_image.isEmpty() || !aadhar_f_url.isEmpty()) && aadhaar_no.isEmpty()) {
            kyc_aadhar_et.requestFocus();
            kyc_aadhar_et.setError("Enter Aadhaar No");
        } else if ((!aadhaarback_image.isEmpty() || !aadhar_b_url.isEmpty()) && aadhaar_no.isEmpty()) {
            kyc_aadhar_et.requestFocus();
            kyc_aadhar_et.setError("Enter Aadhaar No");
        } else if (!aadhaar_no.isEmpty() && (aadhaarfront_image.isEmpty() && aadhar_f_url.isEmpty())) {
            Config.showCustomToast(getActivity(), "Upload Aadhaar Front image");
        } else if (!aadhaar_no.isEmpty() && (aadhaarback_image.isEmpty() && aadhar_b_url.isEmpty())) {
            Config.showCustomToast(getActivity(), "Upload Aadhaar Back image");
        }
//        else if (!kyc_check_box.isChecked()) {
//            Config.showCustomToast(getActivity(), "Accept Terms and Conditions");
//        }
        else {

            if(aadhaar_no.isEmpty()){
                Config.showCustomToast(getActivity(), "Please complete any section of KYC");
            }
            else {
                sendAdharDataToServer( regno, aadhaar_no,
                        aadhaarfront_imagename, aadhaarfront_image, aadhaarback_imagename, aadhaarback_image );
            }

        }

    }

    private void sendBankDetailsDataToServer(String loginId, String bankName, String accountNo, String ifscCode, String bankBranch, String backImage, String backImageName) {

        Config.showProcessDialog(innerProgressDialog, getContext());

        HashMap<String, String> hashMap = new HashMap();
        //regno":"","mobileno":"","otp":"","name":"","nooforders":"","receivedamount":""
        hashMap.put("Loginid", loginId);
        hashMap.put("bankname", bankName);
        hashMap.put("account_no", accountNo);
        hashMap.put("ifsc_code", ifscCode);
        hashMap.put("bank_branch", bankBranch);
        hashMap.put("bank_image", backImage);
        hashMap.put("bank_imagename", backImageName);

        Log.e("param", "--" + hashMap);

        Call<ResponseBody> call = NetworkManager.getInstance().getApiServices().uploadBankDetails(hashMap);
        HttpUrl str = call.request().url();
        Log.e("TAG", "uploadKYC--> " + str);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
//                    Config.hideProsessDialog(innerProgressDialog, CustomerDetailsActivity.this);
                    try {
                        String status, message, brid, brcode, brname;
                        String res = response.body().string();
                        Log.e("TAG", "uploadKYC--" + res);
                        JSONArray rootJA = new JSONArray(res);
                        JSONObject baseJO = rootJA.getJSONObject(0);
                        status = baseJO.getString("status");
                        message = baseJO.getString("message");
                        if (status.equals("200")) {
                            clearAllFields();
                            //  Config.goToDashboard(CustomerDetailsActivity.this);
                            Config.showCustomToast(getActivity(), message);
                        } else {
                            Config.showCustomToast(getActivity(), message);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        Config.showCustomToast(getActivity(), String.valueOf(getResources().getString(R.string.error_message)));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Config.showCustomToast(getActivity(), String.valueOf(getResources().getString(R.string.error_message)));
                    }
                } else {
                    Log.e("error", "response not successfull");
                    Config.hideProsessDialog(innerProgressDialog, getContext());
                    Config.showCustomToast(getActivity(), String.valueOf(getResources().getString(R.string.error_message)));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error", t.getMessage());
                Config.hideProsessDialog(innerProgressDialog, getContext());
                Config.showCustomToast(getActivity(), String.valueOf(getResources().getString(R.string.error_message)));
            }
        });

    }


        private void checkBankDetailsDataForServer() {
        String regno, mobileno, otp, name, pan_no, pan_image, pan_imagename, aadhaar_no, aadhaarfront_image, aadhaarback_image, aadhaarback_imagename,
                bankname, account_no, ifsc_code, bank_branch, bank_image, bank_imagename, aadhaarfront_imagename;

//        regno = SaveSharedPreference.getRegno();
        regno = loginId;

        bankname = kyc_bank_name_et.getText().toString();
        account_no = kyc_account_number_et.getText().toString();
        ifsc_code = kyc_ifsc_code_et.getText().toString();
        bank_branch = kyc_bank_branch_et.getText().toString();


        if (bitmapBankDetails == null) {
            bank_image = "";
            bank_imagename = "";
        } else {
            bank_image = convertBitmapToString(bitmapBankDetails);

            bank_imagename = "cheque_image.png";

        }

        boolean isBankDetailsFilled = checkBankDetailsFillingValidation(bankname, bank_image, bank_branch, ifsc_code, account_no);
        if (isBankDetailsFilled && bankname.isEmpty()) {
            kyc_bank_name_et.requestFocus();
            kyc_bank_name_et.setError("Enter Bank Name");
        }

        else if (isBankDetailsFilled && account_no.isEmpty()) {
            kyc_account_number_et.requestFocus();
            kyc_account_number_et.setError("Enter Account No.");

        }
        else if (isBankDetailsFilled && ifsc_code.isEmpty()) {
            kyc_ifsc_code_et.requestFocus();
            kyc_ifsc_code_et.setError("Enter IFSC Code");

        }
        else if (isBankDetailsFilled && bank_branch.isEmpty()) {
            kyc_bank_branch_et.requestFocus();
            kyc_bank_branch_et.setError("Enter Bank Branch Name");

        }
        else if (isBankDetailsFilled && (bank_image.isEmpty() && bank_details_url.isEmpty())) {
            Config.showCustomToast(getActivity(), "Upload Cancelled Cheque Image");

        }
//        else if (!kyc_check_box.isChecked()) {
//            Config.showCustomToast(getActivity(), "Accept Terms and Conditions");
//        }
        else {
            if (!ifsc_code.isEmpty() && !isIfscCodeValid(ifsc_code)) {
                kyc_ifsc_code_et.requestFocus();
                kyc_ifsc_code_et.setError("Enter Valid IFSC code");
            } else if (!account_no.isEmpty() && !isAccountValid(account_no)) {
                kyc_account_number_et.requestFocus();
                kyc_account_number_et.setError("Enter Valid Account Number");

            }
            else if(account_no.isEmpty() ){
                Config.showCustomToast(getActivity(), "Please complete any section of KYC");
            }
            else {
                sendBankDetailsDataToServer(regno, bankname, account_no, ifsc_code, bank_branch,
                        bank_image, bank_imagename);
            }

        }

    }


    public boolean checkBankDetailsFillingValidation(String bankname, String bank_image, String bank_branch,
                                                     String ifsc_code, String account_no) {

        if (!(bank_image.isEmpty() || bank_details_url.isEmpty())
                || !bankname.isEmpty()
                || !bank_branch.isEmpty()
                || !ifsc_code.isEmpty()
                || !account_no.isEmpty()) {
            return true;
        }
        {
            return false;
        }


    }

    //    private void checkDataForServer() {
//        String regno, mobileno, otp, name, pan_no, pan_image, pan_imagename, aadhaar_no, aadhaarfront_image, aadhaarback_image, aadhaarback_imagename,
//                bankname, account_no, ifsc_code, bank_branch, bank_image, bank_imagename, aadhaarfront_imagename;
//
//        regno = SaveSharedPreference.getRegno();
//        mobileno = kyc_mobile_et.getText().toString();
//        otp = kyc_otp_et.getText().toString();
//        name = kyc_name_et.getText().toString();
//        pan_no = kyc_pan_et.getText().toString();
//
//        if (bitmapPan == null) {
//
//            pan_image = "";
//            pan_imagename = "";
//
//        } else {
//            pan_image = convertBitmapToString(bitmapPan);
//
//            pan_imagename = "pan_image.png";
//
//        }
//        aadhaar_no = kyc_aadhar_et.getText().toString();
//        if (bitmapAadharFront == null) {
//            aadhaarfront_image = "";
//            aadhaarfront_imagename = "";
//
//        } else {
//            aadhaarfront_image = convertBitmapToString(bitmapAadharFront);
//            aadhaarfront_imagename = "aadhaar_front.png";
//
//        }
//        if (bitmapAadharBack == null) {
//            aadhaarback_image = "";
//            aadhaarback_imagename = "";
//
//        } else {
//            aadhaarback_imagename = "aadhaar_front.png";
//            aadhaarback_image = convertBitmapToString(bitmapAadharBack);
//
//        }
//
//        bankname = kyc_bank_name_et.getText().toString();
//        account_no = kyc_account_number_et.getText().toString();
//        ifsc_code = kyc_ifsc_code_et.getText().toString();
//        bank_branch = kyc_bank_branch_et.getText().toString();
//
//
//        if (bitmapBankDetails == null) {
//            bank_image = "";
//            bank_imagename = "";
//        } else {
//            bank_image = convertBitmapToString(bitmapBankDetails);
//
//            bank_imagename = "cheque_image.png";
//
//        }
//
//        boolean isBankDetailsFilled = checkBankDetailsFillingValidation(bankname, bank_image, bank_branch, ifsc_code, account_no);
//        if (mobileno.isEmpty()) {
//            kyc_mobile_et.requestFocus();
//            kyc_mobile_et.setError("Enter Mobile Number");
//        } else if (kyc_otp_et.getVisibility() == View.VISIBLE && otp.isEmpty()) {
//            kyc_otp_et.requestFocus();
//            kyc_otp_et.setError("Enter OTP");
//
//        } else if (name.isEmpty()) {
//            kyc_name_et.requestFocus();
//            kyc_name_et.setError("Enter Customer Name");
//        }
//        else if ((pan_url.isEmpty() && pan_image.isEmpty()) && !pan_no.isEmpty()) {
//            if (isValidPanCardNo(pan_no)) {
//                Config.showCustomToast(CustomerDetailsActivity.this, "Upload Pan Image");
//            } else {
//                kyc_pan_et.requestFocus();
//                kyc_pan_et.setError("Enter Valid Pan No.");
//            }
//        }
//        else if ((!pan_image.isEmpty() || !pan_url.isEmpty()) && pan_no.isEmpty()) {
//            kyc_pan_et.requestFocus();
//            kyc_pan_et.setError("Enter Pan");
//        } else if ((!aadhaarfront_image.isEmpty() || !aadhar_f_url.isEmpty()) && aadhaar_no.isEmpty()) {
//            kyc_aadhar_et.requestFocus();
//            kyc_aadhar_et.setError("Enter Aadhaar No");
//        } else if ((!aadhaarback_image.isEmpty() || !aadhar_b_url.isEmpty()) && aadhaar_no.isEmpty()) {
//            kyc_aadhar_et.requestFocus();
//            kyc_aadhar_et.setError("Enter Aadhaar No");
//        } else if (!aadhaar_no.isEmpty() && (aadhaarfront_image.isEmpty() && aadhar_f_url.isEmpty())) {
//            Config.showCustomToast(CustomerDetailsActivity.this, "Upload Aadhaar Front image");
//        } else if (!aadhaar_no.isEmpty() && (aadhaarback_image.isEmpty() && aadhar_b_url.isEmpty())) {
//            Config.showCustomToast(CustomerDetailsActivity.this, "Upload Aadhaar Back image");
//        }
//        else if (isBankDetailsFilled && bankname.isEmpty()) {
//            kyc_bank_name_et.requestFocus();
//            kyc_bank_name_et.setError("Enter Bank Name");
//        }
//
//        else if (isBankDetailsFilled && account_no.isEmpty()) {
//            kyc_account_number_et.requestFocus();
//            kyc_account_number_et.setError("Enter Account No.");
//
//        }
//        else if (isBankDetailsFilled && ifsc_code.isEmpty()) {
//            kyc_ifsc_code_et.requestFocus();
//            kyc_ifsc_code_et.setError("Enter IFSC Code");
//
//        }
//        else if (isBankDetailsFilled && bank_branch.isEmpty()) {
//            kyc_bank_branch_et.requestFocus();
//            kyc_bank_branch_et.setError("Enter Bank Branch Name");
//
//        }
//        else if (isBankDetailsFilled && (bank_image.isEmpty() && bank_details_url.isEmpty())) {
//            Config.showCustomToast(CustomerDetailsActivity.this, "Upload Cancelled Cheque Image");
//
//        }
//        else if (!kyc_check_box.isChecked()) {
//            Config.showCustomToast(CustomerDetailsActivity.this, "Accept Terms and Conditions");
//        }
//        else {
//            if (!pan_no.isEmpty() && !isValidPanCardNo(pan_no)) {
//                kyc_pan_et.requestFocus();
//                kyc_pan_et.setError("Enter Valid PAN No.");
//            } else if (!ifsc_code.isEmpty() && !isIfscCodeValid(ifsc_code)) {
//                kyc_ifsc_code_et.requestFocus();
//                kyc_ifsc_code_et.setError("Enter Valid IFSC code");
//            } else if (!account_no.isEmpty() && !isAccountValid(account_no)) {
//                kyc_account_number_et.requestFocus();
//                kyc_account_number_et.setError("Enter Valid Account Number");
//
//            }
//            else if(pan_no.isEmpty() && aadhaar_no.isEmpty() && account_no.isEmpty() ){
//                Config.showCustomToast(CustomerDetailsActivity.this, "Please complete any section of KYC");
//            }
//            else {
//                sendDataToServer(regno, mobileno, otp, name, pan_no, pan_image, pan_imagename, aadhaar_no,
//                        aadhaarfront_image, aadhaarback_image, aadhaarback_imagename
//                        , bankname, account_no, ifsc_code, bank_branch, bank_image, bank_imagename, aadhaarfront_imagename);
//            }
//
//        }
//
//    }
//
//
    private void sendPanDataToServer(String loginId, String panNo, String panImage, String panImageName) {

        Config.showProcessDialog(innerProgressDialog, getContext());

        HashMap<String, String> hashMap = new HashMap();
        //regno":"","mobileno":"","otp":"","name":"","nooforders":"","receivedamount":""
        hashMap.put("Loginid", loginId);
        hashMap.put("pan_no", panNo);
        hashMap.put("pan_image", panImage);
        hashMap.put("pan_imagename", panImageName);

        Log.e("param", "--" + hashMap);

        Call<ResponseBody> call = NetworkManager.getInstance().getApiServices().uploadPAN(hashMap);
        HttpUrl str = call.request().url();
        Log.e("TAG", "uploadKYC--> " + str);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
//                    Config.hideProsessDialog(innerProgressDialog, CustomerDetailsActivity.this);
                    try {
                        String status, message, brid, brcode, brname;
                        String res = response.body().string();
                        Log.e("TAG", "uploadKYC--" + res);
                        JSONArray rootJA = new JSONArray(res);
                        JSONObject baseJO = rootJA.getJSONObject(0);
                        status = baseJO.getString("status");
                        message = baseJO.getString("message");
                        if (status.equals("200")) {
                            clearAllFields();
                            //  Config.goToDashboard(CustomerDetailsActivity.this);
                            Config.showCustomToast(getActivity(), message);
                        } else {
                            Config.showCustomToast(getActivity(), message);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        Config.showCustomToast(getActivity(), String.valueOf(getResources().getString(R.string.error_message)));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Config.showCustomToast(getActivity(), String.valueOf(getResources().getString(R.string.error_message)));
                    }
                } else {
                    Log.e("error", "response not successfull");
                    Config.hideProsessDialog(innerProgressDialog, getContext());
                    Config.showCustomToast(getActivity(), String.valueOf(getResources().getString(R.string.error_message)));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error", t.getMessage());
                Config.hideProsessDialog(innerProgressDialog, getContext());
                Config.showCustomToast(getActivity(), String.valueOf(getResources().getString(R.string.error_message)));
            }
        });

    }


    private void sendAdharDataToServer(String loginId, String adharNo, String adharImageName, String adharImage, String adharBackImageName, String adharBackImage) {

      Config.showProcessDialog(innerProgressDialog, getContext());

        HashMap<String, String> hashMap = new HashMap();
        //regno":"","mobileno":"","otp":"","name":"","nooforders":"","receivedamount":""
        hashMap.put("Loginid", loginId);
        hashMap.put("aadhaar_no", adharNo);
        hashMap.put("aadhaarfront_imagename", adharImageName);
        hashMap.put("aadhaarfront_image", adharImage);
        hashMap.put("aadhaarback_imagename", adharBackImageName);
        hashMap.put("aadhaarback_image", adharBackImage);

        Log.e("param", "--" + hashMap);

        Call<ResponseBody> call = NetworkManager.getInstance().getApiServices().uploadAdhar(hashMap);
        HttpUrl str = call.request().url();
        Log.e("TAG", "uploadKYC--> " + str);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
//                    Config.hideProsessDialog(innerProgressDialog, CustomerDetailsActivity.this);
                    try {
                        String status, message, brid, brcode, brname;
                        String res = response.body().string();
                        Log.e("TAG", "uploadKYC--" + res);
                        JSONArray rootJA = new JSONArray(res);
                        JSONObject baseJO = rootJA.getJSONObject(0);
                        status = baseJO.getString("status");
                        message = baseJO.getString("message");
                        if (status.equals("200")) {
                            clearAllFields();
                            //  Config.goToDashboard(CustomerDetailsActivity.this);
                            Config.showCustomToast(getActivity(), message);
                        } else {
                            Config.showCustomToast(getActivity(), message);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        Config.showCustomToast(getActivity(), String.valueOf(getResources().getString(R.string.error_message)));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Config.showCustomToast(getActivity(), String.valueOf(getResources().getString(R.string.error_message)));
                    }
                } else {
                    Log.e("error", "response not successfull");
                    Config.hideProsessDialog(innerProgressDialog, getContext());
                    Config.showCustomToast(getActivity(), String.valueOf(getResources().getString(R.string.error_message)));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error", t.getMessage());
                Config.hideProsessDialog(innerProgressDialog, getContext());
                Config.showCustomToast(getActivity(), String.valueOf(getResources().getString(R.string.error_message)));
            }
        });

    }



    private void clearAllFields() {

//        kyc_submit_mobile_tv.setText("Verify");
//        kyc_submit_mobile_tv.setEnabled(true);
//        removeBackAadharDetails();
//        removeFrontAadharDetails();
//        removeCancelledChequeDetails();
//        removePanDetails();
//        kyc_bank_name_et.setText("");
//        kyc_bank_branch_et.setText("");
//        kyc_pan_et.setText("");
//        kyc_aadhar_et.setText("");
//        kyc_otp_et.setText("");
//        kyc_otp_et.setVisibility(View.GONE);
//        kyc_name_et.setText("");
//        kyc_mobile_et.setText("");
//        kyc_mobile_et.setEnabled(true);
//        kyc_mobile_et.requestFocus();
//        kyc_mobile_et.setFocusableInTouchMode(true);
//        kyc_ifsc_code_et.setText("");
//        kyc_account_number_et.setText("");
//        kyc_check_box.setChecked(false);
////        kyc_submit_tv.setVisibility(View.VISIBLE);
//        enableAllFieldsEditing();

    }

    ImagePicker imagePicker;

    private void selectImage(String type) {
        imagePicker = new ImagePicker(new ImagePicker.GetImage() {
            String mediaPath;

            @Override
            public void setGalleryImage(Uri imageUri) {

                Log.i("ImageURI", imageUri + "");
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(imageUri, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                if (type.equalsIgnoreCase(PAN)) {
                    try {
                        bitmapPan = getResizedBitmapFromGallery(mediaPath);
//                         bitmapPan = decodeSampledBitmapFromResource(getResources(),R.id.kyc_pan_iv,100,70,columnIndex);
                        kyc_pan_rl.setVisibility(View.VISIBLE);
                        kyc_upload_pan_tv.setVisibility(View.GONE);
                        kyc_pan_iv.setImageBitmap(bitmapPan);
                    } catch (Exception ex) {
                        Log.e("panError", ex.getMessage());
                    }
                } else if (type.equalsIgnoreCase(F_AADHAAR)) {

                    try {
                        bitmapAadharFront = getResizedBitmapFromGallery(mediaPath);
                        cb_aadhar_front_rl.setVisibility(View.VISIBLE);
                        kyc_upload_front_aadhar_tv.setVisibility(View.GONE);
                        kyc_aadhar_front_iv.setImageBitmap(bitmapAadharFront);

                    } catch (Exception ex2) {
                        Log.e("aadharFError", ex2.getMessage());
                    }
                } else if (type.equalsIgnoreCase(B_AADHAAR)) {

                    try {
                        bitmapAadharBack = getResizedBitmapFromGallery(mediaPath);
                        cb_aadhar_back_rl.setVisibility(View.VISIBLE);
                        kyc_upload_back_aadhar_tv.setVisibility(View.GONE);
                        kyc_aadhar_back_iv.setImageBitmap(bitmapAadharBack);

                    } catch (Exception ex2) {
                        Log.e("aadharBError", ex2.getMessage());
                    }
                } else if (type.equalsIgnoreCase(BANK_DETAILS)) {
                    try {
                        bitmapBankDetails = getResizedBitmapFromGallery(mediaPath);
                        kyc_upload_cancelled_cheque_rl.setVisibility(View.VISIBLE);
                        kyc_upload_cancelled_cheque_tv.setVisibility(View.GONE);
                        kyc_upload_cancelled_cheque_iv.setImageBitmap(bitmapBankDetails);

                    } catch (Exception ex2) {
                        Log.e("bankDetailsError", ex2.getMessage());
                    }
                }
                cursor.close();
                imagePicker.dismiss();

            }

            @Override
            public void setCameraImage(String filePath, File cameraImage) {
                mediaPath = filePath;
                //Glide.with(CustomerDetailsActivity.this).load(filePath).into(kyc_aadhar_front_iv);
                if (type.equalsIgnoreCase(PAN)) {
                    try {
                        bitmapPan = getResizedBitmapFromCamera(cameraImage);
                        // bitmapPan =  getBitmap(cameraImage);
                        kyc_pan_rl.setVisibility(View.VISIBLE);
                        kyc_upload_pan_tv.setVisibility(View.GONE);
                        kyc_pan_iv.setImageBitmap(bitmapPan);
                    } catch (Exception ex) {
                        Log.e("panError", ex.getMessage());
                    }
                } else if (type.equalsIgnoreCase(F_AADHAAR)) {

                    try {
                        bitmapAadharFront = getResizedBitmapFromCamera(cameraImage);
                        cb_aadhar_front_rl.setVisibility(View.VISIBLE);
                        kyc_upload_front_aadhar_tv.setVisibility(View.GONE);
                        kyc_aadhar_front_iv.setImageBitmap(bitmapAadharFront);

                    } catch (Exception ex2) {
                        Log.e("aadharFError", ex2.getMessage());
                    }
                } else if (type.equalsIgnoreCase(B_AADHAAR)) {

                    try {
                        bitmapAadharBack = getResizedBitmapFromCamera(cameraImage);
                        cb_aadhar_back_rl.setVisibility(View.VISIBLE);
                        kyc_upload_back_aadhar_tv.setVisibility(View.GONE);
                        kyc_aadhar_back_iv.setImageBitmap(bitmapAadharBack);

                    } catch (Exception ex2) {
                        Log.e("aadharBError", ex2.getMessage());
                    }
                } else if (type.equalsIgnoreCase(BANK_DETAILS)) {
                    try {

                        bitmapBankDetails = getResizedBitmapFromCamera(cameraImage);
                        kyc_upload_cancelled_cheque_rl.setVisibility(View.VISIBLE);
                        kyc_upload_cancelled_cheque_tv.setVisibility(View.GONE);
                        kyc_upload_cancelled_cheque_iv.setImageBitmap(bitmapBankDetails);

                    } catch (Exception ex2) {
                        Log.e("bankDetailsError", ex2.getMessage());
                    }
                }
                imagePicker.dismiss();

            }

            @Override
            public void setImageFile(File file) {

                cameraImage = file;

            }
        }, true);
        imagePicker.show(getActivity().getSupportFragmentManager(), imagePicker.getTag());
    }

    private Bitmap getResizedBitmapFromGallery(String mediaPath) {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(mediaPath);//loading the large bitmap is fine.
        int w = bitmap.getWidth();//get width
        int h = bitmap.getHeight();//get height
        int aspRat = w / h;//get aspect ratio
        int W, H;
        if (aspRat <= 0) {
            W = w / 2;
            H = h / 2;
        } else {
            W = w / 2;
            H = W * aspRat;
        }//set the height based on width and aspect ratio

        Bitmap b = Bitmap.createScaledBitmap(bitmap, W, H, false);
        Bitmap b2 = Bitmap.createScaledBitmap(bitmap, w / 20, h / 20, false);
        Log.e("sizePan2", String.valueOf(bitmap.getByteCount()) + " , " + String.valueOf(b.getByteCount()));
        if (bitmap.getByteCount() <= 27108864) {
            return bitmap;
        } else if (bitmap.getByteCount() > 27108864 && bitmap.getByteCount() < 47108864) {
            return b;
        } else {
            return b2;
        }
        // return  b;
    }


    private Bitmap getResizedBitmapFromCamera(File path) {

        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(path), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
        //loading the large bitmap is fine.
        int w = bitmap.getWidth();//get width
        int h = bitmap.getHeight();//get height
        int aspRat = w / h;//get aspect ratio
        //set the height based on width and aspect ratio
        int W, H;
        if (aspRat <= 0) {
            W = w / 2;
            H = h / 2;
        } else {
            W = w / 2;
            H = W * aspRat;
        }

        Bitmap b = Bitmap.createScaledBitmap(bitmap, W, H, false);
        Bitmap b2 = Bitmap.createScaledBitmap(bitmap, w / 10, h / 10, false);
        Log.e("sizePan2", String.valueOf(bitmap.getByteCount()) + " , " + String.valueOf(b.getByteCount()));
        if (bitmap.getByteCount() <= 27108864) {
            return bitmap;
        } else if (bitmap.getByteCount() > 27108864 && bitmap.getByteCount() < 47108864) {
            return b;
        } else {
            return b2;
        }
        // return  b;
    }

    private String cameraFilePath;
    File cameraImage;


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    public String convertBitmapToString(final Bitmap bmp) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream) byteArrayOutputStream);
        final byte[] byteArray = byteArrayOutputStream.toByteArray();
        final String imageStr = Base64.encodeToString(byteArray, Base64.NO_WRAP);
        return imageStr;
    }
//
//    private String aadhar_f_url = "", aadhar_b_url = "", pan_url = "", bank_details_url = "";
//
    private void showPanPreviewDialog(String type, Bitmap bitmap, View view) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.image_preview_dialog_layout, viewGroup, false);
        //Now we need an AlertDialog.Builder object
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        ImageView preview_dialog_close, preview_dialog_iv;
        preview_dialog_iv = dialogView.findViewById(R.id.preview_dialog_iv);
        preview_dialog_close = dialogView.findViewById(R.id.preview_dialog_close);
        if (bitmap == null) {
            if (type.equalsIgnoreCase(PAN)) {
                Glide.with(getActivity())
                        .load(pan_url)
                        .placeholder(R.drawable.progress_animation)
                        .apply(RequestOptions.skipMemoryCacheOf(true))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .into(preview_dialog_iv);
            } else if (type.equalsIgnoreCase(B_AADHAAR)) {
                Glide.with(getActivity()).load(aadhar_b_url).placeholder(R.drawable.progress_animation)
                        .apply(RequestOptions.skipMemoryCacheOf(true))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .into(preview_dialog_iv);
            } else if (type.equalsIgnoreCase(F_AADHAAR)) {
                Glide.with(getActivity()).load(aadhar_f_url).placeholder(R.drawable.progress_animation)
                        .apply(RequestOptions.skipMemoryCacheOf(true))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .into(preview_dialog_iv);
            } else if (type.equalsIgnoreCase(BANK_DETAILS)) {
                Glide.with(getActivity()).load(bank_details_url).placeholder(R.drawable.progress_animation)
                        .apply(RequestOptions.skipMemoryCacheOf(true))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .into(preview_dialog_iv);
            }

        } else {

            preview_dialog_iv.setImageBitmap(bitmap);

        }

        preview_dialog_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equalsIgnoreCase("PAN")) {
                    //removePanDetails();
                    alertDialog.dismiss();
                } else {
                    //removeAadharDetails();
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }
//
//    private void disableAadharEditing() {
//        kyc_aadhar_et.setEnabled(false);
//        cb_aadhar_back_remove_iv.setVisibility(View.GONE);
//        cb_aadhar_front_remove_iv.setVisibility(View.GONE);
//    }
//
//    private void disablePanEditing() {
//        kyc_pan_et.setEnabled(false);
//        cb_pan_remove_iv.setVisibility(View.GONE);
//    }
//
//    private void disableBankDetailsEditing() {
//        kyc_bank_name_et.setEnabled(false);
//        kyc_account_number_et.setEnabled(false);
//        kyc_ifsc_code_et.setEnabled(false);
//        kyc_bank_branch_et.setEnabled(false);
//        kyc_upload_cancel_cheque_remove_iv.setVisibility(View.GONE);
//
//
//    }
//
//
//    private void enableAadharEditing() {
//        kyc_aadhar_et.setEnabled(true);
//        kyc_aadhar_et.setFocusableInTouchMode(true);
//        kyc_aadhar_et.setFocusable(true);
//        cb_aadhar_back_remove_iv.setVisibility(View.VISIBLE);
//        cb_aadhar_front_remove_iv.setVisibility(View.VISIBLE);
//    }
//
//    private void enablePanEditing() {
//        kyc_pan_et.setEnabled(true);
//        kyc_pan_et.setFocusableInTouchMode(true);
//        kyc_pan_et.setFocusable(true);
//        cb_pan_remove_iv.setVisibility(View.VISIBLE);
//    }
//
//    private void enableBankDetailsEditing() {
//        kyc_bank_name_et.setEnabled(true);
//        kyc_bank_name_et.setFocusable(true);
//        kyc_bank_name_et.setFocusableInTouchMode(true);
//
//        kyc_account_number_et.setEnabled(true);
//        kyc_account_number_et.setFocusable(true);
//        kyc_account_number_et.setFocusableInTouchMode(true);
//
//
//        kyc_ifsc_code_et.setEnabled(true);
//        kyc_ifsc_code_et.setFocusable(true);
//        kyc_ifsc_code_et.setFocusableInTouchMode(true);
//
//
//        kyc_bank_branch_et.setEnabled(true);
//        kyc_bank_branch_et.setFocusable(true);
//        kyc_bank_branch_et.setFocusableInTouchMode(true);
//        kyc_upload_cancel_cheque_remove_iv.setVisibility(View.VISIBLE);
//    }
//

    private void loadurl() {
        // dialog.show();
        final String url = "https://kalshyangroup.com/BOM/kycFormMobile.aspx?u=startid&p=12345";

        webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.setWebChromeClient(new WebChromeClient());
        webview.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Win64; x64; rv:46.0) Gecko/20100101 Firefox/68.0");
        webview.getSettings().setGeolocationEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setDatabaseEnabled(true);
        webview.getSettings().setSupportMultipleWindows(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setNeedInitialFocus(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setBlockNetworkImage(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        final Activity activity = getActivity();
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 1);
            }
        });
    }

    public boolean isConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = connectivityManager.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;

        } else
            return false;

    }

    private void getCustomerDetails() {
        String url = Config.url +"getKYCStatus/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/0";
        //http://sahayaktest.saharaevols.net/API/v1/Getcustomersdetails?userid={Mobile No.}
        Log.e(TAG, "getCustomerDetails: "+ url );
        Call<ResponseBody> call = NetworkManager.getInstance().getApiServices().getDataFromUrlWithHeader(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Config.hideProsessDialog(innerProgressDialog, getContext());
                    try {
                        String status, message, name, pan_no, pan_imageurl, pan_status, aadhaar_no,
                                aadhaarfront_imageurl, aadhaarback_imageurl, aadhaar_status, bank_name,
                                account_no, ifsc_code, bank_branch, bank_imageurl, bank_status,rideshareacceptance;
                        String res = response.body().string();
                        Log.e("TAG", "customerDetails--" + res);
                        JSONArray rootJA = new JSONArray(res);
                        JSONObject jo = rootJA.getJSONObject(0);
//                        status = baseJO.getString("status");
//                        message = baseJO.getString("message");
//                        if (status.equalsIgnoreCase("200")) {
//                            JSONArray dataJA = baseJO.getJSONArray("data");
//                            JSONObject jo = dataJA.getJSONObject(0);
                            pan_no = jo.getString("PANNo");
                            pan_imageurl = jo.getString("PANImage");
                            pan_status = jo.getString("PANApproved");
                            aadhaar_no = jo.getString("Address_addhaarNo");
                            aadhaarfront_imageurl = jo.getString("Address_addhaarFront");
                            aadhaarback_imageurl = jo.getString("Address_addhaarBack");
                            aadhaar_status = jo.getString("AddressApproved");
                            bank_name = jo.getString("Bank_Name");
                            account_no = jo.getString("Bank_Ac");
                            ifsc_code = jo.getString("Bank_IFSC");
                            bank_branch = jo.getString("Bank_Branch");
                            bank_imageurl = jo.getString("Bank_Cheque");
                            bank_status = jo.getString("BankApproved");
//                            rideshareacceptance = jo.getString("rideshareacceptance");

                            setDetailsOnViews(pan_no, pan_imageurl, pan_status, aadhaar_no,
                                    aadhaarfront_imageurl, aadhaarback_imageurl, aadhaar_status, bank_name,
                                    account_no, ifsc_code, bank_branch, bank_imageurl, bank_status);


//                        }
//                        Config.showCustomToast(getActivity(), message);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Config.hideProsessDialog(innerProgressDialog,getActivity());
                        Config.showCustomToast(getActivity(), String.valueOf(getResources().getString(R.string.error_message)));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Config.hideProsessDialog(innerProgressDialog,getActivity());
                        Config.showCustomToast(getActivity(), String.valueOf(getResources().getString(R.string.error_message)));
                    }
                } else {
                    Log.e("error", "response not successfull");
                    Config.hideProsessDialog(innerProgressDialog,getActivity());
                    Config.showCustomToast(getActivity(), String.valueOf(getResources().getString(R.string.error_message)));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error", t.getMessage());
                Config.hideProsessDialog(innerProgressDialog,getActivity());
                Config.showCustomToast(getActivity(), String.valueOf(getResources().getString(R.string.error_message)));
            }
        });


    }

    private void setDetailsOnViews( String pan_no, String pan_imageurl,
                                   String pan_status, String aadhaar_no, String aadhaarfront_imageurl,
                                   String aadhaarback_imageurl, String aadhaar_status, String bank_name,
                                   String account_no, String ifsc_code, String bank_branch,
                                   String bank_imageurl, String bank_status) {
//        kyc_name_et.setText(name);
        kyc_pan_et.setText(pan_no);
        kyc_aadhar_et.setText(aadhaar_no);
        kyc_bank_name_et.setText(bank_name);
        kyc_bank_branch_et.setText(bank_branch);
        kyc_ifsc_code_et.setText(ifsc_code);
        kyc_account_number_et.setText(account_no);
        if(aadhaar_status.equalsIgnoreCase("True") && pan_status.equalsIgnoreCase("True")
                && bank_status.equalsIgnoreCase("True")){
            disablePanEditing();
            disableAadharEditing();
            disableBankDetailsEditing();
//            kyc_submit_tv.setVisibility(View.GONE);
            pan_submit_tv.setVisibility(View.GONE);
            adhar_submit_tv.setVisibility(View.GONE);
            bank_submit_tv.setVisibility(View.GONE);
        }
        else if(aadhaar_status.equalsIgnoreCase("True") && pan_status.equalsIgnoreCase("True")
                && !bank_status.equalsIgnoreCase("True")){
            disablePanEditing();
            disableAadharEditing();
            enableBankDetailsEditing();
//            kyc_submit_tv.setVisibility(View.GONE);
            pan_submit_tv.setVisibility(View.GONE);
            adhar_submit_tv.setVisibility(View.GONE);
            bank_submit_tv.setVisibility(View.VISIBLE);
//            kyc_Approved_tv.setVisibility(View.GONE);
        }
        else if(aadhaar_status.equalsIgnoreCase("True") && !pan_status.equalsIgnoreCase("True")
                && bank_status.equalsIgnoreCase("True")){
            enablePanEditing();
            disableAadharEditing();
            disableBankDetailsEditing();
//            kyc_submit_tv.setVisibility(View.GONE);
            pan_submit_tv.setVisibility(View.VISIBLE);
            adhar_submit_tv.setVisibility(View.GONE);
            bank_submit_tv.setVisibility(View.GONE);
//            kyc_Approved_tv.setVisibility(View.GONE);
        }
        else if(!aadhaar_status.equalsIgnoreCase("True") && pan_status.equalsIgnoreCase("True")
                && bank_status.equalsIgnoreCase("True")){
            disablePanEditing();
            enableAadharEditing();
            disableBankDetailsEditing();
//            kyc_submit_tv.setVisibility(View.GONE);
            pan_submit_tv.setVisibility(View.GONE);
            adhar_submit_tv.setVisibility(View.VISIBLE);
            bank_submit_tv.setVisibility(View.VISIBLE);
//            kyc_Approved_tv.setVisibility(View.GONE);
        }
        else if(!aadhaar_status.equalsIgnoreCase("True") && !pan_status.equalsIgnoreCase("True")
                && bank_status.equalsIgnoreCase("True")){
            enablePanEditing();
            enableAadharEditing();
            disableBankDetailsEditing();
//            kyc_submit_tv.setVisibility(View.GONE);
            pan_submit_tv.setVisibility(View.VISIBLE);
            adhar_submit_tv.setVisibility(View.VISIBLE);
            bank_submit_tv.setVisibility(View.GONE);
//            kyc_Approved_tv.setVisibility(View.GONE);
        }
        else if(!aadhaar_status.equalsIgnoreCase("True") && pan_status.equalsIgnoreCase("True")
                && !bank_status.equalsIgnoreCase("True")){
            disablePanEditing();
            enableAadharEditing();
            enableBankDetailsEditing();
//            kyc_submit_tv.setVisibility(View.GONE);
            pan_submit_tv.setVisibility(View.GONE);
            adhar_submit_tv.setVisibility(View.VISIBLE);
            bank_submit_tv.setVisibility(View.VISIBLE);
//            kyc_Approved_tv.setVisibility(View.GONE);
        }
        else if(aadhaar_status.equalsIgnoreCase("True") && !pan_status.equalsIgnoreCase("True")
                && !bank_status.equalsIgnoreCase("True")){
            enablePanEditing();
            disableAadharEditing();
            enableBankDetailsEditing();
//            kyc_submit_tv.setVisibility(View.GONE);
            pan_submit_tv.setVisibility(View.VISIBLE);
            adhar_submit_tv.setVisibility(View.GONE);
            bank_submit_tv.setVisibility(View.VISIBLE);
//            kyc_Approved_tv.setVisibility(View.GONE);
        }
        else {
            enablePanEditing();
            enableAadharEditing();
            enableBankDetailsEditing();
//            kyc_submit_tv.setVisibility(View.GONE);
            pan_submit_tv.setVisibility(View.VISIBLE);
            adhar_submit_tv.setVisibility(View.VISIBLE);
            bank_submit_tv.setVisibility(View.VISIBLE);
//            kyc_Approved_tv.setVisibility(View.GONE);
        }
        if(pan_status.equalsIgnoreCase("False")){
            enablePanEditing();
            pan_details_approved_tv.setVisibility(View.GONE);
        }
        else{
            disablePanEditing();
            pan_details_approved_tv.setVisibility(View.VISIBLE);
        }


        if(aadhaar_status.equalsIgnoreCase("False")){
            enableAadharEditing();
            aadhaar_details_approved_tv.setVisibility(View.GONE);
        }
        else{
            disableAadharEditing();
            aadhaar_details_approved_tv.setVisibility(View.VISIBLE);
        }
        if(bank_status.equalsIgnoreCase("False")){
            bank_details_approved_tv.setVisibility(View.GONE);
            enableBankDetailsEditing();
        }
        else{
            bank_details_approved_tv.setVisibility(View.VISIBLE);
            disableBankDetailsEditing();
        }

//        if(rideshareacceptance.equalsIgnoreCase("1")){
//            kyc_check_box.setChecked(true);
//        }

        if (bank_imageurl.isEmpty()) {
            kyc_upload_cancelled_cheque_rl.setVisibility(View.GONE);
            kyc_upload_cancelled_cheque_tv.setVisibility(View.VISIBLE);
        }
        else {
            bank_details_url = bank_imageurl;
            kyc_upload_cancelled_cheque_rl.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(bank_imageurl).
                    placeholder(R.drawable.progress_animation)
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(kyc_upload_cancelled_cheque_iv);
            kyc_upload_cancelled_cheque_tv.setVisibility(View.GONE);
        }
        if (pan_imageurl.isEmpty()) {
            kyc_pan_rl.setVisibility(View.GONE);
            kyc_upload_pan_tv.setVisibility(View.VISIBLE);
        }
        else {
            pan_url = pan_imageurl;
            kyc_pan_rl.setVisibility(View.VISIBLE);
            kyc_upload_pan_tv.setVisibility(View.GONE);

            Glide.with(getActivity())
                    .load(pan_imageurl)
                    .placeholder(R.drawable.progress_animation)
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(kyc_pan_iv);
        }
        if (aadhaarback_imageurl.isEmpty()) {
            cb_aadhar_back_rl.setVisibility(View.GONE);
            kyc_upload_back_aadhar_tv.setVisibility(View.VISIBLE);
        }
        else {
            aadhar_b_url = aadhaarback_imageurl;
            cb_aadhar_back_rl.setVisibility(View.VISIBLE);
            kyc_upload_back_aadhar_tv.setVisibility(View.GONE);
            Glide.with(getActivity())
                    .load(aadhaarback_imageurl)
                    .placeholder(R.drawable.progress_animation)
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(kyc_aadhar_back_iv);

        }
        if (aadhaarfront_imageurl.isEmpty()) {
            cb_aadhar_front_rl.setVisibility(View.GONE);
            kyc_upload_front_aadhar_tv.setVisibility(View.VISIBLE);
        }
        else {
            aadhar_f_url = aadhaarfront_imageurl;
            cb_aadhar_front_rl.setVisibility(View.VISIBLE);
            kyc_upload_front_aadhar_tv.setVisibility(View.GONE);
            Glide.with(getActivity()).load(aadhaarfront_imageurl)
                    .placeholder(R.drawable.progress_animation)
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(kyc_aadhar_front_iv);

        }


    }
    private void enableAllFieldsEditing(){
        enablePanEditing();
        enableAadharEditing();
        enableBankDetailsEditing();
//        kyc_name_et.setEnabled(true);
//        kyc_name_et.setFocusable(true);
//        kyc_name_et.setFocusableInTouchMode(true);
//        kyc_mobile_et.setEnabled(true);
//        kyc_mobile_et.setFocusable(true);
//        kyc_mobile_et.setFocusableInTouchMode(true);
//        kyc_submit_tv.setVisibility(View.VISIBLE);

    }

    private void disableAadharEditing() {
        kyc_aadhar_et.setEnabled(false);
        cb_aadhar_back_remove_iv.setVisibility(View.GONE);
        cb_aadhar_front_remove_iv.setVisibility(View.GONE);
    }

    private void disablePanEditing() {
        kyc_pan_et.setEnabled(false);
        cb_pan_remove_iv.setVisibility(View.GONE);
    }

    private void disableBankDetailsEditing() {
        kyc_bank_name_et.setEnabled(false);
        kyc_account_number_et.setEnabled(false);
        kyc_ifsc_code_et.setEnabled(false);
        kyc_bank_branch_et.setEnabled(false);
        kyc_upload_cancel_cheque_remove_iv.setVisibility(View.GONE);


    }


    private void enableAadharEditing() {
        kyc_aadhar_et.setEnabled(true);
        kyc_aadhar_et.setFocusableInTouchMode(true);
        kyc_aadhar_et.setFocusable(true);
        cb_aadhar_back_remove_iv.setVisibility(View.VISIBLE);
        cb_aadhar_front_remove_iv.setVisibility(View.VISIBLE);
    }

    private void enablePanEditing() {
        kyc_pan_et.setEnabled(true);
        kyc_pan_et.setFocusableInTouchMode(true);
        kyc_pan_et.setFocusable(true);
        cb_pan_remove_iv.setVisibility(View.VISIBLE);
    }

    private void enableBankDetailsEditing() {
        kyc_bank_name_et.setEnabled(true);
        kyc_bank_name_et.setFocusable(true);
        kyc_bank_name_et.setFocusableInTouchMode(true);

        kyc_account_number_et.setEnabled(true);
        kyc_account_number_et.setFocusable(true);
        kyc_account_number_et.setFocusableInTouchMode(true);


        kyc_ifsc_code_et.setEnabled(true);
        kyc_ifsc_code_et.setFocusable(true);
        kyc_ifsc_code_et.setFocusableInTouchMode(true);


        kyc_bank_branch_et.setEnabled(true);
        kyc_bank_branch_et.setFocusable(true);
        kyc_bank_branch_et.setFocusableInTouchMode(true);
        kyc_upload_cancel_cheque_remove_iv.setVisibility(View.VISIBLE);
    }


}
