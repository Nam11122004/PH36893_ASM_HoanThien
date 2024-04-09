package com.example.asm_ph36893.services;


import static com.example.asm_ph36893.services.ApiServices.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {
    private static HttpRequest instance;
    private ApiServices apiServices;

    public HttpRequest(){
        //create retrofit
        apiServices = new Retrofit.Builder()
                .baseUrl(BASE_URL)//khởi tạo đối tượng retrofit và cấu hình thông số
                .addConverterFactory(GsonConverterFactory.create())//chuyển đổi đối tượng gson sang java
                .build().create(ApiServices.class);
    }

    public static synchronized HttpRequest getInstance() {
        if (instance == null) {
            instance = new HttpRequest();
        }
        return instance;
    }
    public ApiServices callApi(){
        //Get retrofit
        return apiServices;
    }
}

