package com.alps.shisu.retrofit_service;


import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {


    @GET
    Call<ResponseBody> getDataFromUrlWithHeader(@Url String url);

    @POST("Saveorder")
    Call<ResponseBody> uploadkyc(
            @Body HashMap<String, String> jsonObject);

    @POST("ReceivedAmount")
    Call<ResponseBody> sendRecievingAmount(
            @Body HashMap<String, String> jsonObject);

    @POST("updateprofile")
    Call<ResponseBody> updateprofile(
            @Body HashMap<String, String> jsonObject);

//    @POST("Uploadkyc")
    @POST("UploadPAN.ashx")
    Call<ResponseBody> uploadPAN(
            @Body HashMap<String, String> jsonObject);

    @POST("BankUpdate.ashx")
    Call<ResponseBody> uploadBankDetails(
            @Body HashMap<String, String> jsonObject);

    @POST("UploadAddress.ashx")
    Call<ResponseBody> uploadAdhar(
            @Body HashMap<String, String> jsonObject);

    @GET("SaveInventory")
    Call<ResponseBody> saveInventory(
            @Query("brid") String brid,
            @Query("model") String model,
            @Query("color") String color,
            @Query("chassisno") String chassisno,
            @Query("motorno") String motorno,
            @Query("batteryno") String batteryno,
            @Query("controllerno") String controllerno,
            @Query("chargerno") String chargerno
            );



//1. Login
//    https://saharaevols.net/API/v1/login?userid={userid}&password={Password}&deviceid={deviceid}
//
//            2. Forget Password
//    https://saharaevols.net/API/v1/ResetPassword?userid={userid}
//
//            3. Inventory List
//    https://saharaevols.net/API/v1/inventory?brid={brid}
//
//            4. Recent Invoice
//    https://saharaevols.net/API/v1/Invoice?brid={brid}&date={date(dd-MM-yyyy)}
//
//            5. Product List
//    https://saharaevols.net/API/v1/Product?brid={brid}
//
//            6. Save Inventory
//    https://saharaevols.net/API/v1/SaveInventory?brid={brid}&model={pname}&color={color}&chassisno={chassisno}
//            &motorno={motorno}&batteryno={batteryno}&controllerno={controllerno}&chargerno={chargerno}
//
//7. Get Customer Details
//    https://saharaevols.net/API/v1/Getcustomersdetails?custmobile={Customer Mobile No.}
//
//            8. Chassis No. List
//    https://saharaevols.net/API/v1/ChassisList?brid={brid}
//
//            9. Chassis No. Details
//    https://saharaevols.net/API/v1/ChassisDetails?chassisno={Chassis No.}

/**********/
//    @GET("v1/datavalue/getkey/{Device_Id}")
//    Call<List<SubscriberKeyResponse>> getSubscriptionKeyWithDeviceId(@Path("Device_Id") String deviceId);
//
//    @GET("v1/Datavalue/theme/{Secretkey}")
//    Call<List<ThemeColorResponse>> getThemeColors(
//            @Header("Authorization") String header,
//            @Path("Secretkey") String securetkey);
//
//    @GET("/v1/datavalue/getpackage/{DeviceId}")
//    Call<ResponseBody> getPackagesInstalled(
////            @Header("Authorization") String header,
////            @Path("SecuretKey") String securetkey,
//            @Path("DeviceId") String deviceId);

}
