package com.alps.shisu.NetworkManager;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchingTreeFragment extends Fragment {
    SessionManagement sessionManagement;
    ProgressBar progressBar;
    Config ut;
    String idback;
    CircularImageView rootimnage,lchildimage,rchildimage;
    AppCompatActivity appCompatActivity;
    String treeurl="";
    String binaryurl="";
    String moreright,moreleft;
    String backurl="";
    FragmentManager fm;
    TextView rightcfmt,rightcurrentmt,righttotalmt;
    TextView leftcfmt,leftcurrentmt,lefttotalmt;
    TextView rootusername,leftchildusername,rightchildusername;
    TextView rootsponsorid,leftchildsponsorid,rightchildsponsorid;
    TextView rootdividers,leftchildivider,rightchilddivider;
    ProgressDialog pd;
    String dialogurl="";
    String Leftsid,Rightsid,RootSid;
    //Textview for tree open more childs
    TextView leftmore,rightmore,more,moreR;
    String rootspID;
    SharedPreferences preferences;
    TreedialogBoxFragment editNameDialogFragment;
    FragmentTransaction transaction;
    String leftchildusernames, leftchilduserImages ;
    String  rightchildnames, rightchildImages;
    String roousename, rootImage;
    LinearLayout Le,Re;
    String morerequest;
    TextView backpress;
    MatchingTreeFragment context;
    LinearLayout showback;
   // View LLINELEFT,LLINERIGHT;
    AlertDialog dialog;

    //this Textview is used onclick when click linearLayout open a dialogbox
    LinearLayout leftchildLinear,rightChildLiner,rootLinear;


    public MatchingTreeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View c= inflater.inflate(R.layout.fragment_matching_tree, container, false);

       /* LLINELEFT=(View)c.findViewById(R.id.lllinelft) ;
        LLINERIGHT=(View)c.findViewById(R.id.lllineryt) ;*/

        //images
        rootimnage=c.findViewById(R.id.image1);
        lchildimage=c.findViewById(R.id.image2);
        rchildimage=c.findViewById(R.id.image3);

        //appCompatActivity= (AppCompatActivity) context;
        rightcfmt=c.findViewById(R.id.right_mt_cf);
        rightcurrentmt=c.findViewById(R.id.right_mt_cuurent);
        righttotalmt=c.findViewById(R.id.right_mt_total);

        backpress=c.findViewById(R.id.backtrees);
        showback=c.findViewById(R.id.backlineary);

        leftcfmt=c.findViewById(R.id.left_mt_cf);
        leftcurrentmt=c.findViewById(R.id.left_mt_cuurent);
        lefttotalmt=c.findViewById(R.id.left_mt_total);

        rootusername=c.findViewById(R.id.root_username);
        rootsponsorid=c.findViewById(R.id.root_sponsorid);
        rootdividers=c.findViewById(R.id.rootdivider);

        leftchildusername=c.findViewById(R.id.left_child_username);
        leftchildsponsorid=c.findViewById(R.id.left_child_sponserid);
        leftchildivider=c.findViewById(R.id.left_child_divider);

        rightchildsponsorid=c.findViewById(R.id.right_child_usponsor);
        rightchildusername=c.findViewById(R.id.right_child_username);
        rightchilddivider=c.findViewById(R.id.right_child_divider);

        //open more child in the tree ids
        leftmore=c.findViewById(R.id.more_left);
        rightmore=c.findViewById(R.id.more_right);
        //   more=c.findViewById(R.id.more);
        //moreR=c.findViewById(R.id.moreRI);
        //onclick listner used to  open dialog box
        rootLinear=c.findViewById(R.id.root);
        leftchildLinear=c.findViewById(R.id.leftChild);
        rightChildLiner=c.findViewById(R.id.rightChild);

        Le=c.findViewById(R.id.leftmore);
        Re=c.findViewById(R.id.rightmore);
        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
        Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"fonts/digital-7.ttf");
        rightcfmt.setTypeface(typeface);
        rightcurrentmt.setTypeface(typeface);
        righttotalmt.setTypeface(typeface);

        leftcfmt.setTypeface(typeface);
        leftcurrentmt.setTypeface(typeface);
        lefttotalmt.setTypeface(typeface);

        Le.setOnClickListener(view -> {
            //morerequest=Leftsid.toString();
            morerequest=leftchildusernames;
            idback=roousename;

                backpress.setVisibility(View.VISIBLE);
                showback.setVisibility(View.VISIBLE);

            tree();
        });

        Re.setOnClickListener(view -> {

                backpress.setVisibility(View.VISIBLE);
                showback.setVisibility(View.VISIBLE);

            morerequest=rightchildnames;
            idback=roousename;
            tree();
        });
        progressBar=c.findViewById(R.id.matchingtreeprogress);

        //bundle=new Bundle();

        transaction=getFragmentManager().beginTransaction();
        //   final TreedialogBoxFragment treedialogBoxFragment=new TreedialogBoxFragment();

        fm = getFragmentManager();
        editNameDialogFragment =new  TreedialogBoxFragment();
        // OnclickListner start

        preferences=getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        sessionManagement=new SessionManagement(getActivity().getApplicationContext());
        HashMap<String,String> user=sessionManagement.getUserDetails();
        // pd=new ProgressDialog(getActivity());
        //  pd.setCancelable(true);
       // binaryurl=ut.url+"getBinary/"+ut.merchantid+"/"+ut.securtykey+"/"+user.get(sessionManagement.KEY_USERNAME)+"/0";
        binaryurl= Config.url +"getBinary/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/"+user.get(SessionManagement.KEY_PASSWORD);

        getdata();

        //   ArrayList<String>rootnames=new ArrayList<>();
        // rootnames.add(idback);
        // Log.e("id"+rootnames);
        // Log.d("id", String.valueOf(rootnames));
        backpress.setOnClickListener(view -> {
            getdata();
           // showback.setVisibility(View.GONE);
            backpress.setVisibility(View.GONE);

        });

        return c;
    }
//
//    /**
//     * This method is called when swipe refresh is pulled down ,
//     */
//    @Override
//    public void onRefresh(){
//        getdata();
//    }

    private void tree(){
        preferences=getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        sessionManagement=new SessionManagement(getActivity().getApplicationContext());
        HashMap<String,String> user=sessionManagement.getUserDetails();
       // treeurl=ut.url+"getBinary/"+ut.merchantid+ut.securtykey+morerequest+"/0";
        treeurl= Config.url +"getBinary/"+ Config.merchantid +"/"+ Config.securtykey +"/"+morerequest+"/"+user.get(SessionManagement.KEY_PASSWORD);
        progressBar.setVisibility(View.VISIBLE);
        // swipeRefreshLayout.setRefreshing(true);
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, treeurl,
                response -> {
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        JSONObject object=jsonArray.getJSONObject(0);

                      //  Toast.makeText(getActivity(), ""+morerequest+response, Toast.LENGTH_SHORT).show();

                        //Root Side Tree Image

                       /* if (object.getString("binaryStatus").equals("Green")){
                            rootimnage.setImageResource(R.drawable.greenmatchingtree);
                        }
                        else if (object.getString("binaryStatus").equals("Purple")) {
                            rootimnage.setImageResource(R.drawable.purplematchingtree);
                        }
                        else {

                            rootimnage.setImageResource(R.drawable.greymatchingtree);

                        }*/
                        //Left Side Tree Image

                       /* if (object.getString("binaryStatusLeft").equals("Green")){

                            lchildimage.setImageResource(R.drawable.greenmatchingtree);

                        }
                        else if (object.getString("binaryStatusLeft").equals("Purple")) {

                            lchildimage.setImageResource(R.drawable.purplematchingtree);
                        }
                        else if (object.getString("userNameLeft").equals("0"))
                        {
                            lchildimage.setImageResource(R.drawable.defaulttreee);
                        }
                        else if (object.getString("binaryStatusLeft").equals("Red")){

                            lchildimage.setImageResource(R.drawable.greymatchingtree);
                        }*/

                        //Right Side Tree Image

                       /* if (object.getString("binaryStatusRight").equals("Green")){
                            rchildimage.setImageResource(R.drawable.greenmatchingtree);
                        }
                        else if (object.getString("binaryStatusRight").equals("Purple")) {
                            rchildimage.setImageResource(R.drawable.purplematchingtree);
                        }
                        else if (object.getString("userNameRight").equals("0")){
                            rchildimage.setImageResource(R.drawable.defaulttreee);
                        }
                        else if (object.getString("binaryStatusRight").equals("Red")){
                            rchildimage.setImageResource(R.drawable.greymatchingtree);
                        }*/

                        roousename = object.getString("userNameSelf");
                        rootImage = object.getString("imgSelf");
                        Bundle bundle = new Bundle();
                        bundle.putString("UserId", roousename);
                        bundle.putString("UserImage", rootImage);

                        editNameDialogFragment.setArguments(bundle);

                        rootusername.setText(roousename);

                        rootspID = object.getString("SponsorIDSelf");
                        rootsponsorid.setText(rootspID);
                        rootdividers.setText("/");

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("rootusername", roousename);
                        editor.commit();

                        //Left side in the tree value set
                        leftchildusernames = object.getString("userNameLeft");
                        leftchilduserImages = object.getString("imgLeft");
                        Bundle bundles = new Bundle();
                        bundles.putString("UserId", leftchildusernames);
                        bundles.putString("UserLeftImage", leftchilduserImages);
                        editNameDialogFragment.setArguments(bundles);
                        leftchildusername.setText(leftchildusernames);

                        Leftsid = object.getString("SponsorIDLeft");
                        leftchildsponsorid.setText(Leftsid);
                        leftchildivider.setText("/");
                        if (leftchildusernames.equals("0"))
                        {

                        }
                        else {
                            leftchildLinear.setOnClickListener(view -> {

                                Bundle bundle12 = new Bundle();
                                bundle12.putString("leftchildid", leftchildusernames);
                                editNameDialogFragment.setArguments(bundle12);
                                editNameDialogFragment.show(fm, "fragment_treedialog_box");
                            });
                        }

                        //Right side in the tree calue set
                        rightchildnames = object.getString("userNameRight");
                        Bundle bundlees = new Bundle();
                        bundles.putString("UserId", rightchildnames);
                        editNameDialogFragment.setArguments(bundlees);
                        rightchildusername.setText(rightchildnames);

                        rightchildsponsorid.setText(object.getString("SponsorIDRight"));
                        rightchilddivider.setText("/");
                        moreleft=object.getString("totalMLeft");
                        moreright=object.getString("totalMRight");

                        if (rightchildnames.equals("0"))
                        {

                        }else {
                            rightChildLiner.setOnClickListener(view -> {

                                Bundle bundle1 = new Bundle();
                                bundle1.putString("leftchildid", rightchildnames);
                                editNameDialogFragment.setArguments(bundle1);
                                editNameDialogFragment.show(fm, "fragment_treedialog_box");

                            });
                        }

                        //more value
                        if (moreleft.equals("0"))
                        {

                            //LLINELEFT.setVisibility(View.GONE);
                            Le.setVisibility(View.GONE);

                        }else {

                            //LLINELEFT.setVisibility(View.VISIBLE);
                            Le.setVisibility(View.VISIBLE);
                            leftmore.setText(moreleft +  "More");
                        }
                        if (moreright.equals("0"))
                        {

                            //LLINERIGHT.setVisibility(View.GONE);
                            Re.setVisibility(View.GONE);

                        }else {

                           // LLINERIGHT.setVisibility(View.VISIBLE);
                            Re.setVisibility(View.VISIBLE);
                            rightmore.setText(moreright +  "More");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    progressBar.setVisibility(View.GONE);

                }, error -> {
                    progressBar.setVisibility(View.GONE);
                    //  swipeRefreshLayout.setRefreshing(false);
                    //pd.dismiss();
                });requestQueue.add(stringRequest);

    }
    private void getdata(){
       // progressBar.setVisibility(View.VISIBLE);
        dialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, binaryurl,
                response -> {
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        JSONObject object=jsonArray.getJSONObject(0);
                        leftcfmt.setText(object.getString("cfMLeft"));
                        leftcurrentmt.setText(object.getString("currentPVLeft"));
                        lefttotalmt.setText(object.getString("leftRemainingTotal"));

                        rightcfmt.setText(object.getString("cfMRight"));
                        rightcurrentmt.setText(object.getString("currentPVRight"));
                        righttotalmt.setText(object.getString("rightRemainingTotal"));

                      /*  if (object.getString("binaryStatus").equals("Green")){

                            rootimnage.setImageResource(R.drawable.goldbankicon);
                        }
                        else if (object.getString("binaryStatus").equals("Purple")) {

                            rootimnage.setImageResource(R.drawable.purplematchingtree);
                        }
                        else if (object.getString("binaryStatus").equals("Red")){

                            rootimnage.setImageResource(R.drawable.greymatchingtree);

                        }*/

                    //left side tree image
                        /*if (object.getString("binaryStatusLeft").equals("Green")){

                            lchildimage.setImageResource(R.drawable.greenmatchingtree);

                        }
                        else if (object.getString("binaryStatusLeft").equals("Purple")) {

                            lchildimage.setImageResource(R.drawable.purplematchingtree);
                        }
                        else if (object.getString("userNameLeft").equals("0")){

                            lchildimage.setImageResource(R.drawable.defaulttreee);

                        }
                        else if (object.getString("binaryStatusLeft").equals("Red")){

                            lchildimage.setImageResource(R.drawable.greymatchingtree);
                        }*/
                    //right side tree image
                        /*if (object.getString("binaryStatusRight").equals("Green")){

                            rchildimage.setImageResource(R.drawable.greenmatchingtree);

                        }
                        else if (object.getString("binaryStatusRight").equals("Purple")) {

                            rchildimage.setImageResource(R.drawable.purplematchingtree);
                        }
                        else if (object.getString("userNameRight").equals("0")){

                            rchildimage.setImageResource(R.drawable.defaulttreee);

                        }
                        else if (object.getString("binaryStatusRight").equals("Red")){

                            rchildimage.setImageResource(R.drawable.greymatchingtree);
                        }*/

                        roousename=object.getString("userNameSelf");
                        rootspID=object.getString("SponsorIDSelf");
                        if (roousename.equals("0"))
                        {}
                        else {
                            rootLinear.setOnClickListener(view -> {

                                Bundle bundle = new Bundle();
                                bundle.putString("leftchildid", roousename);
                                editNameDialogFragment.setArguments(bundle);
                                editNameDialogFragment.show(fm, "fragment_treedialog_box");

                            });
                        }

                        Bundle bundle=new Bundle();
                        bundle.putString("UserId",roousename);
                        editNameDialogFragment.setArguments(bundle);
                        rootusername.setText(roousename);

                        rootsponsorid.setText(rootspID);
                        rootdividers.setText("/");

                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("rootusername",roousename);
                        editor.commit();

                        //Left data show in the tree
                        leftchildusernames=object.getString("userNameLeft");
                        Leftsid=object.getString("SponsorIDLeft");
                        if (leftchildusernames.equals("0"))
                        {}
                        else {
                            leftchildLinear.setOnClickListener(view -> {

                                Bundle bundle12 = new Bundle();
                                bundle12.putString("leftchildid", leftchildusernames);
                                editNameDialogFragment.setArguments(bundle12);
                                editNameDialogFragment.show(fm, "fragment_treedialog_box");
                            });
                        }
                        Bundle bundles=new Bundle();
                        bundles.putString("UserId",leftchildusernames);
                        editNameDialogFragment.setArguments(bundles);
                        leftchildusername.setText(leftchildusernames);
                        Leftsid = object.getString("SponsorIDLeft");
                        leftchildsponsorid.setText(Leftsid);
                        leftchildivider.setText("/");

                        //Right data show in the tree
                        rightchildnames=object.getString("userNameRight");
                        rightchildsponsorid.setText(object.getString("SponsorIDRight"));

                        if (rightchildnames.equals("0"))
                        {

                        }else {
                            rightChildLiner.setOnClickListener(view -> {

                                Bundle bundle1 = new Bundle();
                                bundle1.putString("leftchildid", rightchildnames);
                                editNameDialogFragment.setArguments(bundle1);
                                editNameDialogFragment.show(fm, "fragment_treedialog_box");

                            });
                        }

                        Bundle bundlees=new Bundle();
                        bundlees.putString("UserId",rightchildnames);
                        editNameDialogFragment.setArguments(bundlees);
                        rightchildusername.setText(rightchildnames);

                        rightchilddivider.setText("/");

                        moreleft=object.getString("totalMLeft");
                        moreright=object.getString("totalMRight");

                        if (moreleft.equals("0"))
                        {
                            Le.setVisibility(View.GONE);
                           // LLINELEFT.setVisibility(View.GONE);

                        }else {
                            Le.setVisibility(View.VISIBLE);
                           // LLINELEFT.setVisibility(View.VISIBLE);
                            leftmore.setText(moreleft + " More");
                        }
                        if (moreright.equals("0"))
                        {
                            Re.setVisibility(View.GONE);
                            //LLINERIGHT.setVisibility(View.GONE);

                        }else {

                           // LLINERIGHT.setVisibility(View.VISIBLE);
                            Re.setVisibility(View.VISIBLE);
                            rightmore.setText(moreright+ " More");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (dialog.isShowing())
                        dialog.dismiss();
                    //progressBar.setVisibility(View.GONE);
                }, error -> {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    //   progressBar.setVisibility(View.GONE);
                });requestQueue.add(stringRequest);

    }
}
