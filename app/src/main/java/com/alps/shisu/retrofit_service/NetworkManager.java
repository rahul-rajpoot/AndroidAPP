package com.alps.shisu.retrofit_service;

public class NetworkManager {
    private static NetworkManager instance;
    private final ApiService apiService;
    //   private final ApiServiceList serviceList;
    private NetworkManager() {
        apiService = RetrofitManager.getInstance().create(ApiService.class);
        //serviceList = RetrofitManager.getInstance().create(ApiServiceList.class);
    }

    public static NetworkManager getInstance() {
        if (instance==null){
            instance=new NetworkManager();
        }
        return instance;
    }

    public ApiService getApiServices() {
        return apiService;
    }
}
