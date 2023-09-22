package com.alps.shisu;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alps.shisu.Adapters.DashboardGridAdapter;
import com.alps.shisu.Adapters.ExpandableListAdapter;
import com.alps.shisu.Adapters.IncomeLevelDetalisAdapter;
import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.CurrencyWallet.CurrencyWalletActivity;
import com.alps.shisu.Database.DatabaseHelper;
import com.alps.shisu.FinanacialReport.FinancialReportActivity;
import com.alps.shisu.GetterSetter.DataModel;
import com.alps.shisu.NetworkManager.NetworkManagerActivity;
import com.alps.shisu.ProfileManager.ProfileMangerActivity;
import com.alps.shisu.Session.SessionManagement;
import com.alps.shisu.Shopping.ShoppingActivity;
import com.alps.shisu.activateaccount.ActivateAccountActivity;
import com.alps.shisu.customersupport.CustomerSupportActivity;
import com.alps.shisu.db.local.RoomDBRepository;
import com.alps.shisu.db.local.entity.ProductsDetails;
import com.alps.shisu.modelclass.IncomeLevelDetailsItem;
import com.alps.shisu.promotionalmaterial.PromotionalMaterial;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nex3z.notificationbadge.NotificationBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;

import static android.content.ContentValues.TAG;

public class DashBoard extends
        BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    int ResultVar = 0;
    private long backPressedTime;
    Button kycStatusUpload;
    //   private DonutProgress donutProgress;
    private final Handler mHandler = new Handler();
    int mProgressStatus = 0;
    int in;
    String ShareLeftLink, ShareRightLink;
    LinearLayout dashboard;
    private int lastposition = -1;
    private int CartCount;
    RecyclerView recyclerView;
    DashboardGridAdapter adapter;
    List<DataModel> carItemList;
    TextView btnsheet;
    // Session Manager Class
    public SessionManagement sessionManagement;
    HashMap<String, String> user;

    CoordinatorLayout coordinatorLayout;
    Context context;
    ProgressDialog pd;
    ExpandableListAdapter listAdapter;
    DatabaseHelper db;
    ExpandableListView expListView;
    //these are the list for the categories and subcategories
    ArrayList<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    SharedPreferences preferences;

    LinearLayout linearoutdashfooter1, linearoutdashfooter2, linearoutdashfooter4, linearoutdashfooter5;
    private float totalAmount;
    DrawerLayout drawer;
    //navigation image
    ImageView ShoppingCart;
    //    CircularImageView userphoto;
    RoundedImageView userphoto;

    TextView bonusrecruitment, bonusperformance, bonussilverclub, bonusgoldclub, bonusplatinumclub, bonusdiamondclub, bonusoverriding, houseFund, carFund;
    TextView texttotal, texttotallogo, cartcurrencysymbol, textsamount, business_txt, income_txt, teamsiztext, mydirectrytext1, textmember, textpv, textgoldbankno, textcount1, textcount2, textcount3,
            lefttotalgbv, leftcfgbv, leftcurrentgbv, righttotalgbv, rightcfgbv, rightcurrentgbv, bonusselfrepurchase, bonusselfrepurchasemakng, bonussponsr;
    ImageView panimagetru, panimagefalse, bankimagefalse, bankimagetru, addresimagetru, addresimagefalse;
    RelativeLayout kycpersentagetext0, kycpersentagetext1, kycpersentagetext2, kycpersentagetext3;
    private

    //this three string is api url
    String urls = "";
    String dashboardurls = "";
    String shopurl = "";
    String urlbinary = "";
    String urlkycstatus = "";
    String urlLevelDetails = "";
    Config ut;
    int Downline;
    int Sponsor;
    android.app.AlertDialog dialog;
    NotificationBadge notificationBadge;
    private RecyclerView rv_level_details;
    private final List<IncomeLevelDetailsItem> incomeLevelDetailsItemsList = new ArrayList<>();

    private final Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    List<HashMap<String, String>> aList;

    private String  cPBV, cGBV, tPBV, tGBV;
    private TextView _cPBV, _cGBV, _tPBV, _tGBV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Local db instance
        RoomDBRepository postRoomDBRepository = new RoomDBRepository(getApplication());

        int blueColor = getResources().getColor(R.color.menu_blue);
        int grayColor = getResources().getColor(R.color.gray_color);
        ColorStateList grayColorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[]{
                        grayColor,
                        grayColor,
                        grayColor,
                        grayColor,
                        grayColor
                }
        );

        ColorStateList blueColorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[]{
                        blueColor,
                        blueColor,
                        blueColor,
                        blueColor,
                        blueColor
                }
        );


        if (!isConnection(DashBoard.this)) buildDialog(DashBoard.this).show();
//Font Style

        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();

        sessionManagement = new SessionManagement(getApplicationContext());
        sessionManagement.checkLogin();

        new GooglePlayStoreAppVersionNameLoader(this, isUpdateAvailable -> {
            if (isUpdateAvailable) {
                openGoToPlayStoreAlert();
            }
        }).execute();

        preferences = getSharedPreferences("ALPSPref", MODE_PRIVATE);
        user = sessionManagement.getUserDetails();
        db = new DatabaseHelper(this);
        ShoppingCart = (ImageView) findViewById(R.id.cartopen);
        dashboard = findViewById(R.id.dashboard);
        notificationBadge = (NotificationBadge) findViewById(R.id.notification_badge);

        texttotal = findViewById(R.id.texttotal);
        texttotallogo = findViewById(R.id.texttotallogo);
        cartcurrencysymbol = findViewById(R.id.cartcurrencysymbol);
        textsamount = findViewById(R.id.textsamount);
        business_txt = findViewById(R.id.business_txt);
        income_txt = findViewById(R.id.income_txt);
        teamsiztext = findViewById(R.id.teamsiztext);
        mydirectrytext1 = findViewById(R.id.mydirectrytext1);
        textmember = findViewById(R.id.textmember);
        textgoldbankno = findViewById(R.id.textgoldbankno);
        textpv = findViewById(R.id.textpv);
        textcount1 = findViewById(R.id.textcount1);
        textcount2 = findViewById(R.id.textcount2);
        textcount3 = findViewById(R.id.textcount3);

        _cPBV = findViewById(R.id.cPBV);
        _cGBV = findViewById(R.id.cGBV);
        _tPBV = findViewById(R.id.tPBV);
        _tGBV = findViewById(R.id.tGBV);

        rv_level_details = findViewById(R.id.rv_level_details);

//        lefttotalgbv = findViewById(R.id.lefttotalgbv);
//        leftcfgbv = findViewById(R.id.leftcfgbv);
//        leftcurrentgbv = findViewById(R.id.leftcurrentgbv);
//        righttotalgbv = findViewById(R.id.righttotalgbv);
//        rightcfgbv = findViewById(R.id.rightcfgbv);
//        rightcurrentgbv = findViewById(R.id.rightcurrentgbv);

        TextView totalAmountValue = findViewById(R.id.totalAmountValue);
        bonusrecruitment = findViewById(R.id.bonusrecruitment);
        bonusperformance = findViewById(R.id.bonusperformance);
        bonussilverclub = findViewById(R.id.bonussilverclub);
        bonusgoldclub = findViewById(R.id.bonusgoldclub);
        bonusplatinumclub = findViewById(R.id.bonusplatinumclub);
        bonusdiamondclub = findViewById(R.id.bonusdiamondclub);
        bonusoverriding = findViewById(R.id.bonusoverriding);
        carFund = findViewById(R.id.carFund);
        houseFund = findViewById(R.id.houseFund);
//        bonusselfrepurchase = findViewById(R.id.bonusselfrepurchase);
//        bonusselfrepurchasemakng = findViewById(R.id.bonusselfrepurchasemakng);
//        bonussponsr = findViewById(R.id.bonussponsr);

        kycpersentagetext0 = findViewById(R.id.kycpersentagetext0);
        kycpersentagetext1 = findViewById(R.id.kycpersentagetext1);
        kycpersentagetext2 = findViewById(R.id.kycpersentagetext2);
        kycpersentagetext3 = findViewById(R.id.kycpersentagetext3);
        panimagetru = findViewById(R.id.panimagetru);
        panimagefalse = findViewById(R.id.panimagefalse);
        bankimagefalse = findViewById(R.id.bankimagefalse);
        bankimagetru = findViewById(R.id.bankimagetru);
        addresimagetru = findViewById(R.id.addresimagetru);
        addresimagefalse = findViewById(R.id.addresimagefalse);


        linearoutdashfooter1 = findViewById(R.id.linearoutdashfooter1);
        linearoutdashfooter2 = findViewById(R.id.linearoutdashfooter2);
        linearoutdashfooter4 = findViewById(R.id.linearoutdashfooter4);
        linearoutdashfooter5 = findViewById(R.id.linearoutdashfooter5);

        business_txt.setText("Business ("+user.get(SessionManagement.KEY_CURRENCY) +")");
        income_txt.setText("Income ("+user.get(SessionManagement.KEY_CURRENCY) +")");

        linearoutdashfooter1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProfileMangerActivity.class);
            startActivity(intent);
        });
        linearoutdashfooter2.setOnClickListener(v -> sharinglink());

        linearoutdashfooter4.setOnClickListener(v -> {
//                Intent intent = new Intent(getApplicationContext(), NetworkManagerActivity.class);
//                startActivity(intent);
            Intent ds = new Intent(DashBoard.this, NetworkManagerActivity.class);
            ds.putExtra("nm", 1);
            startActivity(ds);
//                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        });
        linearoutdashfooter5.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), FinancialReportActivity.class);
            startActivity(intent);
        });

        //  ustatus.setText(user.get(session.KEY_STATUS));
        pd = new ProgressDialog(getApplicationContext());
        pd.setCancelable(true);
        context = getApplicationContext();
        // urls=ut.url+"getProfile/"+ut.merchantid+"/"+ut.securtykey+"/"+uname+"/"+pass;

        //uncomment
        urls = Config.url + "getProfile/" + Config.merchantid + "/" + Config.securtykey + "/" + user.get(SessionManagement.KEY_USERNAME) + "/" + user.get(SessionManagement.KEY_PASSWORD);
        urlbinary = Config.url + "getBinary/" + Config.merchantid + "/" + Config.securtykey + "/" + user.get(SessionManagement.KEY_USERNAME) + "/s";
        urlkycstatus = Config.url + "getKYCStatus/" + Config.merchantid + "/" + Config.securtykey + "/" + user.get(SessionManagement.KEY_USERNAME) + "/0";
        dashboardurls = Config.url + "getDashboard/" + Config.merchantid + "/" + Config.securtykey + "/" + user.get(SessionManagement.KEY_USERNAME) + "/" + user.get(SessionManagement.KEY_PASSWORD);
        shopurl = Config.url + "CategoryProducts/" + Config.merchantid +"/"+ Config.securtykey + "/Category/0/0";
        urlLevelDetails = Config.url +"LevelDetails/" + Config.merchantid +"/"+ Config.securtykey + "/" + user.get(SessionManagement.KEY_USERNAME) + "/" + user.get(SessionManagement.KEY_PASSWORD);
//        urlLevelDetails = Config.url +"MyStructure/" + Config.merchantid +"/"+ Config.securtykey + "/" + user.get(SessionManagement.KEY_USERNAME) + "/" + user.get(SessionManagement.KEY_PASSWORD);

        // end
        Log.e(TAG, "onCreateView urls : "+ urls );
        Log.e(TAG, "onCreateView urlbinary : "+ urlbinary );
        Log.e(TAG, "onCreateView urlkycstatus : "+ urlkycstatus );
        Log.e(TAG, "onCreateView dashboardurls : "+ dashboardurls );
        Log.e(TAG, "onCreateView shopurl : "+ shopurl );
        Log.e(TAG, "onCreateView urlLevelDetails : "+ urlLevelDetails );

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // final LinearLayout holder=findViewById(R.id.holder);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                float scaleFactor = 7f;
                float slideX = drawerView.getWidth() * slideOffset;

                // holder.setTranslationX(slideX);
                // holder.setScaleX(1 - (slideOffset / scaleFactor));aadnya
                // holder.setScaleY(1 - (slideOffset / scaleFactor));dj29ng14

                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);// will remove all possible our aactivity's window bounds
        }
        drawer.addDrawerListener(toggle);
        drawer.setScrimColor(Color.TRANSPARENT);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headre = navigationView.getHeaderView(0);

        TextView username = headre.findViewById(R.id.loginusername);
        userphoto = headre.findViewById(R.id.nav_imageview);
        //      View v=navigationView.getHeaderView(1);
        TextView getuserid = headre.findViewById(R.id.nav_userid);
        //us.setText(data);

        // name.setText(jsonObject.getString("FirstName")+" "+jsonObject.getString("LastName"));
        username.setText(user.get(SessionManagement.KEY_FNAME) + " " + user.get(SessionManagement.KEY_LNAME));
        getuserid.setText(user.get(SessionManagement.KEY_USERNAME));

//        CartCount = db.CartTotal(sessionManagement.KEY_USERNAME);
        List<ProductsDetails> productsDetailsList = postRoomDBRepository.getAllProductsDetails(user.get(SessionManagement.KEY_USERNAME));
        if( productsDetailsList != null && productsDetailsList.size() > 0 ) {
            CartCount = productsDetailsList.size();
            notificationBadge.setText(""+CartCount);
        }
//        Toast.makeText(this, "CartCount"+CartCount, Toast.LENGTH_SHORT).show();

        ShoppingCart.setOnClickListener(v -> {
            if(CartCount >0) {
                Intent intent = new Intent(getApplicationContext(), ShoppingActivity.class);
                intent.putExtra("ShowCart", "True");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                // snack();
            } else {
                Toast.makeText(getBaseContext(), "No item available in cart", Toast.LENGTH_LONG).show();
            }
        });

        //uncomment
//        getLevelDetails();
        loadProfile();
        //end
        loadDashboarddata();
        loadKYCData();
        //Expandable listview
        // get the listview
        expListView =  findViewById(R.id.expandableListView);

        // preparing list data
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, blueColorStateList);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setChoiceMode(ExpandableListView.CHOICE_MODE_NONE);

        expListView.setOnGroupExpandListener(i -> {
            if (lastposition != -1 && i != lastposition) {
                expListView.collapseGroup(lastposition);
            }
            lastposition = i;
            if (i == 0) {
                DashBoard.this.drawer.closeDrawers();
                new Handler().postDelayed(() -> {
                    Intent matchingtree = new Intent(DashBoard.this, DashBoard.class);
//                          matchingtree.putExtra("ds", childPosition);
                    startActivity(matchingtree);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                }, 250L);
            }
//            else if (i == 2) {
//                DashBoard.this.drawer.closeDrawers();
//                Intent activateAccount = new Intent(DashBoard.this, ActivateAccountActivity.class);
////                          matchingtree.putExtra("ds", childPosition);
//                startActivity(activateAccount);
//                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
//            }
//                else if (i == 6) {
            else if (i == 6) {
                DashBoard.this.drawer.closeDrawers();
                Intent activateAccount = new Intent(DashBoard.this, CustomerSupportActivity.class);
//                          matchingtree.putExtra("ds", childPosition);
                startActivity(activateAccount);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
            else if (i == 7) {
                DashBoard.this.drawer.closeDrawers();
                Intent activateAccount = new Intent(DashBoard.this, PromotionalMaterial.class);
//                          matchingtree.putExtra("ds", childPosition);
                startActivity(activateAccount);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
            else if (i == 8) {
                Intent intent = new Intent(context, LoginActivity.class);
                sessionManagement.logoutUser();
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });


        expListView.setOnItemClickListener((adapterView, view, i, l) -> {

            view.setSelected(true);
//                else {
//                    view.setSelected(true);
//                }
        });

        expListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {

            //Shopping
/*
            if (groupPosition==0) {
                if (childPosition == 0) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent matchingtree = new Intent(DashBoard.this, ShoppingActivity.class);
                            matchingtree.putExtra("s", childPosition);
                            startActivity(matchingtree);
                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                        }
                    }, 250L);

                }
                if (childPosition == 1) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent matchingtree = new Intent(DashBoard.this, ShoppingActivity.class);
                            matchingtree.putExtra("s", childPosition);
                            startActivity(matchingtree);
                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                        }
                    }, 250L);

                }
                if (childPosition == 2) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent matchingtree = new Intent(DashBoard.this, ShoppingActivity.class);
                            matchingtree.putExtra("s", childPosition);
                            startActivity(matchingtree);
                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                        }
                    }, 250L);

                }

            }
*/

//                 if (groupPosition==0) {
////                     if (childPosition == 0) {
//                         DashBoard.this.drawer.closeDrawers();
//                         new Handler().postDelayed(new Runnable() {
//                             @Override
//                             public void run() {
//                                 Intent matchingtree = new Intent(DashBoard.this, DashBoard.class);
//                                 matchingtree.putExtra("ds", childPosition);
//                                 startActivity(matchingtree);
//                                 overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
//                             }
//                         }, 250L);
//
////                     }
//                 }

            //Profile Manager
            if (groupPosition == 3) {
                if (childPosition == 0) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent matchingtree = new Intent(DashBoard.this, ProfileMangerActivity.class);
                        matchingtree.putExtra("pf", childPosition);
                        startActivity(matchingtree);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }, 250L);

                }

                if (childPosition == 1) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent ds = new Intent(DashBoard.this, ProfileMangerActivity.class);
                        ds.putExtra("pf", childPosition);
                        startActivity(ds);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                    }, 250L);

                }
                if (childPosition == 2) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent matchingtree = new Intent(DashBoard.this, ProfileMangerActivity.class);
                        matchingtree.putExtra("pf", childPosition);
                        startActivity(matchingtree);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }, 250L);

                }

                if (childPosition == 3) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() ->  {
                        Intent ds = new Intent(DashBoard.this, ProfileMangerActivity.class);
                        ds.putExtra("pf", childPosition);
                        startActivity(ds);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }, 250L);

                }
                if (childPosition == 4) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent matchingtree = new Intent(DashBoard.this, ProfileMangerActivity.class);
                        matchingtree.putExtra("pf", childPosition);
                        startActivity(matchingtree);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }, 250L);
                }

                if (childPosition == 5) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent ds = new Intent(DashBoard.this, ProfileMangerActivity.class);
                        ds.putExtra("pf", childPosition);
                        startActivity(ds);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                    }, 250L);
                }
            }
            //Network Manager
            if (groupPosition == 1) {
                if (childPosition == 0) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent matchingtree = new Intent(DashBoard.this, NetworkManagerActivity.class);
                        matchingtree.putExtra("nm", childPosition);
                        startActivity(matchingtree);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }, 250L);
                }

                if (childPosition == 1) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent ds = new Intent(DashBoard.this, NetworkManagerActivity.class);
                        ds.putExtra("nm", childPosition);
                        startActivity(ds);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                    }, 250L);

                }
                if (childPosition == 2) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent matchingtree = new Intent(DashBoard.this, NetworkManagerActivity.class);
                        matchingtree.putExtra("nm", childPosition);
                        startActivity(matchingtree);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }, 250L);
                }

                if (childPosition == 3) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent ds = new Intent(DashBoard.this, NetworkManagerActivity.class);
                        ds.putExtra("nm", childPosition);
                        startActivity(ds);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                    }, 250L);
                }
            }


//            //Kyc Manager
//            if (groupPosition == 2) {
//                if (childPosition == 0) {
//                    DashBoard.this.drawer.closeDrawers();
//                    new Handler().postDelayed(() -> {
//                        Intent matchingtree = new Intent(DashBoard.this, KycManager.class);
//                        matchingtree.putExtra("km", childPosition);
//                        startActivity(matchingtree);
//                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
//                    }, 250L);
//
//                }
//
//                if (childPosition == 1) {
//                    DashBoard.this.drawer.closeDrawers();
//                    new Handler().postDelayed(() -> {
//                        Intent ds = new Intent(DashBoard.this, KycManager.class);
//                        ds.putExtra("km", childPosition);
//                        startActivity(ds);
//                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
//                    }, 250L);
//
//                }
//            }

            if (groupPosition==2) {
                if (childPosition == 0) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent matchingtree = new Intent(DashBoard.this, ShoppingActivity.class);
                        matchingtree.putExtra("s", childPosition);
                        startActivity(matchingtree);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                    }, 250L);

                }
                if (childPosition == 1) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent matchingtree = new Intent(DashBoard.this, ShoppingActivity.class);
                        matchingtree.putExtra("s", childPosition);
                        startActivity(matchingtree);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                    }, 250L);

                }
                if (childPosition == 2) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent matchingtree = new Intent(DashBoard.this, ShoppingActivity.class);
                        matchingtree.putExtra("s", childPosition);
                        startActivity(matchingtree);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                    }, 250L);

                }

            }
//                //Financial Report
            if (groupPosition == 4) {
                if (childPosition == 0) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent matchingtree = new Intent(DashBoard.this, FinancialReportActivity.class);
                        matchingtree.putExtra("fr", childPosition);
                        startActivity(matchingtree);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }, 250L);
                }
                if (childPosition == 1) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent ds = new Intent(DashBoard.this, FinancialReportActivity.class);
                        ds.putExtra("fr", childPosition);
                        startActivity(ds);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }, 250L);
                }
                if (childPosition == 2) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent matchingtree = new Intent(DashBoard.this, FinancialReportActivity.class);
                        matchingtree.putExtra("fr", childPosition);
                        startActivity(matchingtree);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }, 250L);
                }

                if (childPosition == 3) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent ds = new Intent(DashBoard.this, FinancialReportActivity.class);
                        ds.putExtra("fr", childPosition);
                        startActivity(ds);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }, 250L);
                }
                if (childPosition == 4) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent ds = new Intent(DashBoard.this, FinancialReportActivity.class);
                        ds.putExtra("fr", childPosition);
                        startActivity(ds);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                    }, 250L);
                }
            }

//                //Currency Wallet
            if (groupPosition == 5) {
                if (childPosition == 0) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent matchingtree = new Intent(DashBoard.this, CurrencyWalletActivity.class);
                        matchingtree.putExtra("cw", childPosition);
                        startActivity(matchingtree);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                    }, 250L);
                }
                if (childPosition == 1) {
                    DashBoard.this.drawer.closeDrawers();
                    new Handler().postDelayed(() -> {
                        Intent matchingtree = new Intent(DashBoard.this, CurrencyWalletActivity.class);
                        matchingtree.putExtra("cw", childPosition);
                        startActivity(matchingtree);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                    }, 250L);
                }
            }

            //Log Out
//                if (groupPosition == 6) {
////                    if (childPosition == 0) {
//                    DashBoard.this.drawer.closeDrawers();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent intent = new Intent(context, LoginActivity.class);
//                            session.logoutUser();
//                            startActivity(intent);
//                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
//                        }
//                    }, 250L);
////                    }
//                }
            return false;
        });
    }


//    private void sharinglink() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);
//        builder.setMessage("Share Joinig Link");
//        builder.setPositiveButton("Left", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//                String shareBody = ShareLeftLink;
//                Log.e(TAG, "onClick: ShareLeftLink : "+  ShareLeftLink);
//                String shareSub = "Your subject here";
//                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
//                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
//                startActivity(Intent.createChooser(sharingIntent, "Share using"));
//            }
//        });
//        builder.setNegativeButton("Right", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//                String shareBody = ShareRightLink;
//                Log.e(TAG, "onClick: ShareRightLink : "+  ShareRightLink);
//                String shareSub = "Your subject here";
//                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
//                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
//                startActivity(Intent.createChooser(sharingIntent, "Share using"));
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }

    private void openGoToPlayStoreAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);
        builder.setCancelable(false);
        builder.setTitle("NEW UPDATES");
        builder.setMessage("A New Update is Available");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }).setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadDashboarddata() {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, dashboardurls, response -> {
            // Toast.makeText(DashBoard.this,""+response,Toast.LENGTH_LONG).show();
            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String Status = jsonObject.getString("Status");
                String MerchantID = jsonObject.getString("MerchantID");
                String UserID = jsonObject.getString("UserID");
                // String Sponsor=jsonObject.getString("Sponsor");
                mydirectrytext1.setText((jsonObject.getString("Sponsor")));
                textcount2.setText((jsonObject.getString("Sponsor")));
                String SponsorA = jsonObject.getString("SponsorA");
                // String Downline=jsonObject.getString("Downline");
                teamsiztext.setText((jsonObject.getString("Downline")));
                textcount1.setText((jsonObject.getString("Downline")));
                String DownlineA = jsonObject.getString("DownlineA");
                textcount3.setText((jsonObject.getInt("Downline") - jsonObject.getInt("Sponsor")) + "");

                texttotallogo.setText((jsonObject.getString("CurrencySymbol")));
                cartcurrencysymbol.setText((jsonObject.getString("CurrencySymbol")));
                textsamount.setText((jsonObject.getString("AcBalance")));
                texttotal.setText((jsonObject.getString("TotalIncome")));

                String ConfpayNo = jsonObject.getString("ConfpayNo");

                SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("ALPSPref",MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString("ConfpayNo", ConfpayNo);
                editor.apply();



                _cPBV.setText((jsonObject.getString("cPBV")));
                _cGBV.setText((jsonObject.getString("cGBV")));
                _tPBV.setText((jsonObject.getString("tPBV")));
                _tGBV.setText((jsonObject.getString("tGBV")));

//                    lefttotalgbv.setText((jsonObject.getString("TBVLeft")));
//                    leftcfgbv.setText((jsonObject.getString("CFBVLeft")));
//                    leftcurrentgbv.setText((jsonObject.getString("CBVLeft")));
//
//                    righttotalgbv.setText((jsonObject.getString("TBVRight")));
//                    rightcfgbv.setText((jsonObject.getString("CFBVRight")));
//                    rightcurrentgbv.setText((jsonObject.getString("CBVRight")));

                if(jsonObject != null && jsonObject.has("Direct_Bonus") && !jsonObject.getString("Direct_Bonus").isEmpty()) {
                    bonusrecruitment.setText((jsonObject.getString("CurrencySymbol") + (jsonObject.getString("Direct_Bonus"))));
                } else {
                    bonusrecruitment.setText((jsonObject.getString("CurrencySymbol") + "0.00"));
                }
                if(jsonObject != null && jsonObject.has("Performance_Bonus") && !jsonObject.getString("Performance_Bonus").isEmpty()) {
                    bonusperformance.setText((jsonObject.getString("CurrencySymbol") + (jsonObject.getString("Performance_Bonus"))));
                } else {
                    bonusperformance.setText((jsonObject.getString("CurrencySymbol") + "0.00"));
                }
                if(jsonObject != null && jsonObject.has("SilverClub_Bonus") && !jsonObject.getString("SilverClub_Bonus").isEmpty()) {
                    bonussilverclub.setText((jsonObject.getString("CurrencySymbol") + (jsonObject.getString("SilverClub_Bonus"))));
                } else {
                    bonussilverclub.setText((jsonObject.getString("CurrencySymbol") + "0.00"));
                }
                if(jsonObject != null && jsonObject.has("GoldClub_Bonus") && !jsonObject.getString("GoldClub_Bonus").isEmpty()) {
                    bonusgoldclub.setText((jsonObject.getString("CurrencySymbol") + (jsonObject.getString("GoldClub_Bonus"))));
                } else {
                    bonusgoldclub.setText((jsonObject.getString("CurrencySymbol") + "0.00"));
                }
                if(jsonObject != null && jsonObject.has("PlatinumClub_Bonus") && !jsonObject.getString("PlatinumClub_Bonus").isEmpty()) {
                    bonusplatinumclub.setText((jsonObject.getString("CurrencySymbol") + (jsonObject.getString("PlatinumClub_Bonus"))));
                } else {
                    bonusplatinumclub.setText((jsonObject.getString("CurrencySymbol") + "0.00"));
                }
                if(jsonObject != null && jsonObject.has("DiamondClub_Bonus") && !jsonObject.getString("DiamondClub_Bonus").isEmpty()) {
                    bonusdiamondclub.setText((jsonObject.getString("CurrencySymbol") + (jsonObject.getString("DiamondClub_Bonus"))));
                } else {
                    bonusdiamondclub.setText((jsonObject.getString("CurrencySymbol") + "0.00"));
                }
                if(jsonObject != null && jsonObject.has("Overriding_Bonus") && !jsonObject.getString("Overriding_Bonus").isEmpty()) {
                    bonusoverriding.setText((jsonObject.getString("CurrencySymbol") + (jsonObject.getString("Overriding_Bonus"))));
                } else {
                    bonusoverriding.setText((jsonObject.getString("CurrencySymbol") + "0.00"));
                }
                if(jsonObject != null && jsonObject.has("CarFund") && !jsonObject.getString("CarFund").isEmpty()) {
                    carFund.setText((jsonObject.getString("CurrencySymbol") + (jsonObject.getString("CarFund"))));
                } else {
                    carFund.setText((jsonObject.getString("CurrencySymbol") + "0.00"));
                }
                if(jsonObject != null && jsonObject.has("HouseFund") && !jsonObject.getString("HouseFund").isEmpty()) {
                    houseFund.setText((jsonObject.getString("CurrencySymbol") + (jsonObject.getString("HouseFund"))));
                } else {
                    houseFund.setText((jsonObject.getString("CurrencySymbol")+ "0.00"));
                }
//                bonusselfrepurchase.setText((jsonObject.getString("CurrencySymbol") + (jsonObject.getString("SelfRepurchase_Bonus"))));
//                bonusselfrepurchasemakng.setText((jsonObject.getString("CurrencySymbol") + (jsonObject.getString("MatchingRepurchase_Bonus"))));
//                bonussponsr.setText((jsonObject.getString("CurrencySymbol") + (jsonObject.getString("SponsorRepurchase_Bonus"))));


                String CID = jsonObject.getString("CID");
                String SHAREURL = jsonObject.getString("SHAREURL");
//                String ConfpayNo = jsonObject.getString("ConfpayNo");
                String Regdate = jsonObject.getString("Regdate");
                String Doc = jsonObject.getString("Doc");
                String Country = jsonObject.getString("Country");
                String profileImage = jsonObject.getString("profileImage");

                String GoldAcNo = jsonObject.getString("GoldAcNo");
                //commented Anshu
//                    textgoldbankno.setText((jsonObject.getString("GoldAcNo")));
                textgoldbankno.setText(UserID);

                String cRank = jsonObject.getString("cRank");
                String cSlab = jsonObject.getString("cSlab");
                textmember.setText(cRank);


                // textcdistibuter.setText(jsonObject.getString("nRank")+" ("+jsonObject.getString("nSlab")+"%)");

                textpv.setText("(" + jsonObject.getString("nRankPV") + "PV)");

//                String MPBV = jsonObject.getString("MPBV");
//                String APBV = jsonObject.getString("APBV");
//                String MGBV = jsonObject.getString("MGBV");
//                String AGBV = jsonObject.getString("AGBV");
//                String MPBV_Bonus = jsonObject.getString("MPBV_Bonus");
//                String MPBV_TBonus = jsonObject.getString("MPBV_TBonus");
//                String MGBV_Bonus = jsonObject.getString("MGBV_Bonus");
//                String MGBV_TBonus = jsonObject.getString("MGBV_TBonus");
                String PhotoLoaded = jsonObject.getString("PhotoLoaded");
                String PhotoApproved = jsonObject.getString("PhotoApproved");
                String PANLoaded = jsonObject.getString("PANLoaded");
                String PANApproved = jsonObject.getString("PANApproved");
                String AddressLoaded = jsonObject.getString("AddressLoaded");
                String AddressApproved = jsonObject.getString("AddressApproved");
                String IDLoaded = jsonObject.getString("IDLoaded");
                String IDApproved = jsonObject.getString("IDApproved");
                String BankLoaded = jsonObject.getString("BankLoaded");
                String BankApproved = jsonObject.getString("BankApproved");


                if (PANApproved.equals("True")) {
                    panimagetru.setVisibility(View.VISIBLE);
                    panimagefalse.setVisibility(View.GONE);
                }
                if (BankApproved.equals("True")) {
                    bankimagetru.setVisibility(View.VISIBLE);
                    bankimagefalse.setVisibility(View.GONE);
                }
                if (AddressApproved.equals("True")) {
                    addresimagetru.setVisibility(View.VISIBLE);
                    addresimagefalse.setVisibility(View.GONE);
                }

                if (PANApproved.equals("True") && (BankApproved.equals("False") && (AddressApproved.equals("False")))) {
                    kycpersentagetext0.setVisibility(View.GONE);
                    kycpersentagetext1.setVisibility(View.VISIBLE);
                    kycpersentagetext2.setVisibility(View.GONE);
                    kycpersentagetext3.setVisibility(View.GONE);

                } else if (PANApproved.equals("False") && (BankApproved.equals("True") && (AddressApproved.equals("False")))) {
                    kycpersentagetext0.setVisibility(View.GONE);
                    kycpersentagetext1.setVisibility(View.VISIBLE);
                    kycpersentagetext2.setVisibility(View.GONE);
                    kycpersentagetext3.setVisibility(View.GONE);

                } else if (PANApproved.equals("False") && (BankApproved.equals("False") && (AddressApproved.equals("True")))) {
                    kycpersentagetext0.setVisibility(View.GONE);
                    kycpersentagetext1.setVisibility(View.VISIBLE);
                    kycpersentagetext2.setVisibility(View.GONE);
                    kycpersentagetext3.setVisibility(View.GONE);

                    //.................
                } else if (PANApproved.equals("True") && (BankApproved.equals("True") && (AddressApproved.equals("False")))) {
                    kycpersentagetext0.setVisibility(View.GONE);
                    kycpersentagetext1.setVisibility(View.GONE);
                    kycpersentagetext2.setVisibility(View.VISIBLE);
                    kycpersentagetext3.setVisibility(View.GONE);

                } else if (PANApproved.equals("True") && (BankApproved.equals("False") && (AddressApproved.equals("True")))) {
                    kycpersentagetext0.setVisibility(View.GONE);
                    kycpersentagetext1.setVisibility(View.GONE);
                    kycpersentagetext2.setVisibility(View.VISIBLE);
                    kycpersentagetext3.setVisibility(View.GONE);

                } else if (PANApproved.equals("False") && (BankApproved.equals("True") && (AddressApproved.equals("True")))) {
                    kycpersentagetext0.setVisibility(View.GONE);
                    kycpersentagetext1.setVisibility(View.GONE);
                    kycpersentagetext2.setVisibility(View.VISIBLE);
                    kycpersentagetext3.setVisibility(View.GONE);

                }
                if (PANApproved.equals("True") && (BankApproved.equals("True") && (AddressApproved.equals("True")))) {
                    kycpersentagetext0.setVisibility(View.GONE);
                    kycpersentagetext1.setVisibility(View.GONE);
                    kycpersentagetext2.setVisibility(View.GONE);
                    kycpersentagetext3.setVisibility(View.VISIBLE);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing())
                dialog.dismiss();
        }, error -> {
            if (dialog.isShowing())
                dialog.dismiss();
        });
        queue.add(request);
    }


    private void loadKYCData() {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, urlkycstatus, response -> {
            // Toast.makeText(DashBoard.this,""+response,Toast.LENGTH_LONG).show();
            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = jsonArray.getJSONObject(0);


                String PhotoLoaded = jsonObject.getString("PhotoLoaded");
                String PhotoApproved = jsonObject.getString("PhotoApproved");
                String PANLoaded = jsonObject.getString("PANLoaded");
                String PANApproved = jsonObject.getString("PANApproved");
                String AddressLoaded = jsonObject.getString("AddressLoaded");
                String AddressApproved = jsonObject.getString("AddressApproved");
                String IDLoaded = jsonObject.getString("IDLoaded");
                String IDApproved = jsonObject.getString("IDApproved");
                String BankLoaded = jsonObject.getString("BankLoaded");
                String BankApproved = jsonObject.getString("BankApproved");


                if (PANApproved.equals("True")) {
                    panimagetru.setVisibility(View.VISIBLE);
                    panimagefalse.setVisibility(View.GONE);
                }
                if (BankApproved.equals("True")) {
                    bankimagetru.setVisibility(View.VISIBLE);
                    bankimagefalse.setVisibility(View.GONE);
                }
                if (AddressApproved.equals("True")) {
                    addresimagetru.setVisibility(View.VISIBLE);
                    addresimagefalse.setVisibility(View.GONE);
                }

                if (PANApproved.equals("True") && (BankApproved.equals("False") && (AddressApproved.equals("False")))) {
                    kycpersentagetext0.setVisibility(View.GONE);
                    kycpersentagetext1.setVisibility(View.VISIBLE);
                    kycpersentagetext2.setVisibility(View.GONE);
                    kycpersentagetext3.setVisibility(View.GONE);

                } else if (PANApproved.equals("False") && (BankApproved.equals("True") && (AddressApproved.equals("False")))) {
                    kycpersentagetext0.setVisibility(View.GONE);
                    kycpersentagetext1.setVisibility(View.VISIBLE);
                    kycpersentagetext2.setVisibility(View.GONE);
                    kycpersentagetext3.setVisibility(View.GONE);

                } else if (PANApproved.equals("False") && (BankApproved.equals("False") && (AddressApproved.equals("True")))) {
                    kycpersentagetext0.setVisibility(View.GONE);
                    kycpersentagetext1.setVisibility(View.VISIBLE);
                    kycpersentagetext2.setVisibility(View.GONE);
                    kycpersentagetext3.setVisibility(View.GONE);

                    //.................
                } else if (PANApproved.equals("True") && (BankApproved.equals("True") && (AddressApproved.equals("False")))) {
                    kycpersentagetext0.setVisibility(View.GONE);
                    kycpersentagetext1.setVisibility(View.GONE);
                    kycpersentagetext2.setVisibility(View.VISIBLE);
                    kycpersentagetext3.setVisibility(View.GONE);

                } else if (PANApproved.equals("True") && (BankApproved.equals("False") && (AddressApproved.equals("True")))) {
                    kycpersentagetext0.setVisibility(View.GONE);
                    kycpersentagetext1.setVisibility(View.GONE);
                    kycpersentagetext2.setVisibility(View.VISIBLE);
                    kycpersentagetext3.setVisibility(View.GONE);

                } else if (PANApproved.equals("False") && (BankApproved.equals("True") && (AddressApproved.equals("True")))) {
                    kycpersentagetext0.setVisibility(View.GONE);
                    kycpersentagetext1.setVisibility(View.GONE);
                    kycpersentagetext2.setVisibility(View.VISIBLE);
                    kycpersentagetext3.setVisibility(View.GONE);

                }
                if (PANApproved.equals("True") && (BankApproved.equals("True") && (AddressApproved.equals("True")))) {
                    kycpersentagetext0.setVisibility(View.GONE);
                    kycpersentagetext1.setVisibility(View.GONE);
                    kycpersentagetext2.setVisibility(View.GONE);
                    kycpersentagetext3.setVisibility(View.VISIBLE);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing())
                dialog.dismiss();
        }, error -> {
            if (dialog.isShowing())
                dialog.dismiss();
        });
        queue.add(request);
    }


    private void loadProfile() {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, urls, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String image = jsonObject.getString("profileImage");
                Glide.with(context).load(image).into(userphoto);
            /*if(jsonObject.has("profileImage")) {
                // imageView should be declared in layout xml file with id `imageView`
                com.squareup.picasso.Picasso.with(context).
                        load(jsonObject.getString("profileImage")).
                        placeholder(R.mipmap.ic_launcher).
                        into(userphoto);
            }*/
                // Glide.with(context).load(image).placeholder(R.drawable.indicator_corner_bg).into(userphoto);

                ShareLeftLink = jsonObject.getString("SHAREGURL_A");
                ShareRightLink = jsonObject.getString("SHAREGURL_B");

                Log.e(TAG, "onResponse: ShareLeftLink : "+ ShareLeftLink );
                Log.e(TAG, "onResponse: ShareRightLink : "+ ShareRightLink );

                SharedPreferences sharedPreferences = getSharedPreferences("ALPSPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("SHAREGURL_A", ShareLeftLink);
                editor.putString("SHAREGURL_B", ShareRightLink);
                editor.commit();
                sessionManagement.sharekey(ShareLeftLink, ShareRightLink);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing())
                dialog.dismiss();
        }, error -> {
            if (dialog.isShowing())
                dialog.dismiss();
        });
        queue.add(request);

    }

//    private void getLevelDetails() {
//        dialog.show();
//        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
//        StringRequest request = new StringRequest(Request.Method.GET, urlLevelDetails, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONArray jsonArray = new JSONArray(response);
//                    for (int i = 0; i < jsonArray.length() ; i++) {
//
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        String levels = jsonObject.getString("levels");
//                        String business = jsonObject.getString("Business");
//                        String levelIncome = jsonObject.getString("levelIncome");
//                        String rate = jsonObject.getString("paidPV");
//                        String noOfMembers = jsonObject.getString("mCount");
//
//                        IncomeLevelDetailsItem incomeLevelDetailsItem = new IncomeLevelDetailsItem();
//                        incomeLevelDetailsItem.setIncome(levelIncome);
//                        incomeLevelDetailsItem.setBusiness(business);
//                        incomeLevelDetailsItem.setLevel("Level "+levels);
//                        incomeLevelDetailsItem.setRate(rate);
//                        incomeLevelDetailsItem.setNoOfMembers(noOfMembers);
//
//                        incomeLevelDetailsItemsList.add(incomeLevelDetailsItem);
//
//                        totalAmount = totalAmount+ Float.parseFloat(levelIncome);
//
//                    }
//
//                    LevelDetailsAdapter DataAdapter=new LevelDetailsAdapter(getBaseContext(),incomeLevelDetailsItemsList);
//                    rv_level_details.setLayoutManager(new LinearLayoutManager(getBaseContext()));
//                    rv_level_details.setAdapter(DataAdapter);
//
//                    totalAmountValue.setText(""+totalAmount +" ₹");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                if (dialog.isShowing())
//                    dialog.dismiss();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if (dialog.isShowing())
//                    dialog.dismiss();
//            }
//        });
//        queue.add(request);
//
//    }


    private void getLevelDetails() {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, urlLevelDetails, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length() ; i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String levels = jsonObject.getString("levels");
                    String business = jsonObject.getString("Business");
                    String levelIncome = jsonObject.getString("levelIncome");

                    IncomeLevelDetailsItem incomeLevelDetailsItem = new IncomeLevelDetailsItem();
                    incomeLevelDetailsItem.setIncome(levelIncome);
                    incomeLevelDetailsItem.setBusiness(business);
//                        incomeLevelDetailsItem.setLevel("Level "+levels);
                    incomeLevelDetailsItem.setLevel(levels);

                    incomeLevelDetailsItemsList.add(incomeLevelDetailsItem);

                }

                IncomeLevelDetalisAdapter DataAdapter=new IncomeLevelDetalisAdapter(getBaseContext(),incomeLevelDetailsItemsList);
                rv_level_details.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                rv_level_details.setAdapter(DataAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing())
                dialog.dismiss();
        }, error -> {
            if (dialog.isShowing())
                dialog.dismiss();
        });
        queue.add(request);

    }


    private void initializeCarItemList() {
        if (carItemList == null) {
            carItemList = new ArrayList<>();
            carItemList.add(new DataModel("Generate Pin", R.drawable.generatepinicon));
            carItemList.add(new DataModel("List Pin", R.drawable.listpinicon));
            carItemList.add(new DataModel("View Profile", R.drawable.viewprofileicon));
            carItemList.add(new DataModel("Welcome Letter", R.drawable.welcomelettericon));
            // carItemList.add(new DataModel("Matching Tree",R.drawable.total60));
            carItemList.add(new DataModel("Direct Sponsor", R.drawable.directsponsoricon));
            carItemList.add(new DataModel("Team List", R.drawable.teamlisticonicon));
            carItemList.add(new DataModel("Payout Report", R.drawable.payoutdeductionicon));
            carItemList.add(new DataModel("Payout Statement", R.drawable.payoutreporticon));
            carItemList.add(new DataModel("Payout Deduction", R.drawable.payoutstatementicon));
            carItemList.add(new DataModel("TDS Report", R.drawable.tdsreporticon));
            carItemList.add(new DataModel("Statement", R.drawable.statementicon));
            //  carItemList.add(new DataModel("Shop Now",R.drawable.shoppingicon2));
            // carItemList.add(new DataModel("Purchase List", R.drawable.purchaselisticon));
            carItemList.add(new DataModel("Share", R.drawable.shareicon));
            carItemList.add(new DataModel("Wallet", R.drawable.walleticon));
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
//        expListView.setIndicatorBounds(expListView.getRight()-120, expListView.getWidth()-20);

    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        // do something when the button is clicked
// do something when the button is clicked
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", (arg0, arg1) -> {

                    finish();
                    //close();
                    onBackPressed();


                })
                .setNegativeButton("No", (arg0, arg1) -> {
                })
                .show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            sessionManagement.checkLogin();
            moveTaskToBack(true);
            finish();

        }
        super.onBackPressed();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {

        listDataHeader = new ArrayList<>();

        listDataChild = new HashMap<>();

//        Dashboard
//        My Network --> Sponsor New, Matching Tree, Direct Referrals, My Team
//        KYC Manager (E-Pin Manager) --> Generate pin (Purchase E-Pin), List Pin (List E-Pin)
//        Profile Manager --> Your Profile, Welcome Letter, Activate Account, KYC Status
//        Financial Report --> Payout Report, Payout Statement, Payout Deduction, TDS Report
//        Currency Wallet --> Statement
//        Logout

        //Adding child data
        // listDataHeader.add("Shop");
        listDataHeader.add("Dashboard");
        listDataHeader.add("My Network");
//        listDataHeader.add("E-Pin Manager");
//        listDataHeader.add("Activate Account");
        listDataHeader.add("Shop");
        listDataHeader.add("Profile Manager");
        listDataHeader.add("Financial Report");
        listDataHeader.add("Currency Wallet");
        listDataHeader.add("Customer Support");
        listDataHeader.add("Promotional Materials");
        listDataHeader.add("Logout");

        // List<String> Shop = new ArrayList<String>();
        // Shopping.add("Comming Soon");
       /* Shop.add("Order Now");
        Shop.add("Purchase List");
        Shop.add("Delivery Status");*/


//        1. Dashboard
//        2. My Network --> Sponsor New, Matching Tree (Level Details), Direct Referrals, My Team
//        3. E-Pin Manager --> Purchase E-Pin, List E-Pin
//        4. Activate Account
//        5. Shop --> Shop Now, Orders, Delivery Status
//        6. Profile Manager --> Your Profile, Welcome Letter, KYC Status
//        7. Financial Report --> Payout Report, Payout Statement, Payout Deduction, TDS Report
//        8. Currency Wallet --> Statement
//        9. Logout


        List<String> profilemanager = new ArrayList<>();
        // profilemanager.add("Application Form");
          profilemanager.add("Update Profile");
        profilemanager.add("Your Profile");
        profilemanager.add("Welcome Letter");
//        profilemanager.add("Activate Account");
        profilemanager.add("KYC Status");

//        profilemanager.add("Your profile");
//        profilemanager.add("Welcome Letter");
//        profilemanager.add("Upgrade Account");
//        //profilemanager.add("Upgrade Account");

        List<String> kycmanager = new ArrayList<>();
        //  kycmanager.add("Alloted List Pin");
        kycmanager.add("Purchase E-Pin");
        kycmanager.add("List E-Pin");

//        kycmanager.add("Generate Pin");
//        kycmanager.add("List Pin");
        // kycmanager.add("Sold Pin");

        List<String> mymanager = new ArrayList<>();
        mymanager.add("Sponsor New");
//        mymanager.add("Level Details");
        mymanager.add("Direct Referrals");
//        mymanager.add("My Team");
//        mymanager.add("Matching Tree");
//        mymanager.add("Sponsor New");
//        mymanager.add("Direct Referrals");
//        mymanager.add("My Team");
        //networkmanager.add("Team Business");

        List<String> financialreport = new ArrayList<>();
        financialreport.add("Payout Report");
//        financialreport.add("Payout Statement");
        financialreport.add("Payout Deduction");
        // financialreport.add("Payout Received");
        financialreport.add("TDS Report");
//
////        financialreport.add("Payout Report");
////        financialreport.add("Payout Statement");
////        financialreport.add("Payout Deduction");
////        // financialreport.add("Payout Received");
////        financialreport.add("TDS Report");
//
        List<String> currencywallet = new ArrayList<>();
        currencywallet.add("Wallet Summary");
        currencywallet.add("Transaction Records");


        List<String> shopNow = new ArrayList<>();
        //currencywallet.add("Summary");
        shopNow.add("Shop Now");
        shopNow.add("Orders");
        shopNow.add("Delivery Status");

       /* List<String> multiplefunds=new ArrayList<String>();
        multiplefunds.add("Car Fund");
        multiplefunds.add("Travel Fund");
        multiplefunds.add("House Fund");*/

//        List<String> logout=new ArrayList<String>();
//        logout.add("Log Out");
//
//
//        List<String> dashboard=new ArrayList<String>();
//        dashboard.add("Dashboard");

       /* listDataChild.put(listDataHeader.get(0), kycmanager); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Shopping);
        listDataChild.put(listDataHeader.get(2), profilemanager);
        listDataChild.put(listDataHeader.get(3),networkmanager);
        listDataChild.put(listDataHeader.get(4),financialreport);
        // listDataChild.put(listDataHeader.get(4),reward);
        listDataChild.put(listDataHeader.get(5),currencywallet);
        //listDataChild.put(listDataHeader.get(7),multiplefunds);
        listDataChild.put(listDataHeader.get(6),logout);*/

        // listDataChild.put(listDataHeader.get(0), kycmanager); // Header, Child data
        //   listDataChild.put(listDataHeader.get(0), Shop);
//        listDataChild.put(listDataHeader.get(0), profilemanager);
//        listDataChild.put(listDataHeader.get(0),dashboard);
        listDataChild.put(listDataHeader.get(1), mymanager);
//        listDataChild.put(listDataHeader.get(2), kycmanager);
        listDataChild.put(listDataHeader.get(2), shopNow);
        // listDataChild.put(listDataHeader.get(4),reward);
        listDataChild.put(listDataHeader.get(3), profilemanager);
        listDataChild.put(listDataHeader.get(4), financialreport);
        //listDataChild.put(listDataHeader.get(7),multiplefunds);
        listDataChild.put(listDataHeader.get(5), currencywallet);
//        listDataChild.put(listDataHeader.get(6),logout);

        //        1. Dashboard
//        2. My Network --> Sponsor New, Matching Tree (Level Details), Direct Referrals, My Team
//        3. E-Pin Manager --> Purchase E-Pin, List E-Pin
//        4. Activate Account
//        5. Shop --> Shop Now, Orders, Delivery Status
//        6. Profile Manager --> Your Profile, Welcome Letter, KYC Status
//        7. Financial Report --> Payout Report, Payout Statement, Payout Deduction, TDS Report
//        8. Currency Wallet --> Statement
//        9. Logout

    }
    //Internet Connection check


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

    public AlertDialog.Builder buildDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or Wifi to access this. Press ok to Exit");

        builder.setPositiveButton("OK", (dialogInterface, i) -> finish());
        return builder;
    }

    public void snack() {
        Snackbar sna = Snackbar.make(dashboard, "Coming Soon", Snackbar.LENGTH_LONG);
        View snackbarView = sna.getView();
        TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        tv.setTextColor(getResources().getColor(R.color.colorWhite));
        // Change the Snackbar default background color
        snackbarView.setBackgroundColor(getResources().getColor(R.color.tabselectedcolor));
        sna.show();
    }

    public void Snackbar(String msg) {
        Snackbar sna = Snackbar.make(dashboard, msg, Snackbar.LENGTH_LONG);
        View snackbarView = sna.getView();
        TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        tv.setTextColor(getResources().getColor(R.color.colorWhite));
        // Change the Snackbar default background color
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
        sna.show();
    }

    public void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void dialog() {
        Dialog myDialog = new Dialog(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            myDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
            myDialog.getWindow().setBackgroundDrawableResource(R.drawable.side_nav_bar);
            myDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

            //  myDialog.getWindow().setTitle("Payout Report");
            //  myDialog.getWindow().getAttributes().setTitle("Payout Report");
        } else {
            myDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            myDialog.getWindow().setBackgroundDrawableResource(R.drawable.side_nav_bar);
            myDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        }

        myDialog.setContentView(R.layout.dialogshare);
        TextView leftid = (TextView) myDialog.findViewById(R.id.leftshare);
        TextView rightid = (TextView) myDialog.findViewById(R.id.rightshare);

        leftid.setOnClickListener(v -> Toast.makeText(context, "left", Toast.LENGTH_SHORT).show());
        rightid.setOnClickListener(v -> Toast.makeText(context, "right", Toast.LENGTH_SHORT).show());
    }
}
