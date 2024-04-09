package com.example.asm_ph36893.services;

import com.example.asm_ph36893.model.Account;
import com.example.asm_ph36893.model.CartRequest;
import com.example.asm_ph36893.model.Response_Model;
import com.example.asm_ph36893.model.SanPham;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {

    // Địa chỉ URL của máy chủ
    String BASE_URL = "http://10.0.2.2:3000/api/";

    // Endpoint để đăng ký tài khoản và gửi email xác nhận
    @POST("register-send-email")
    Call<Response_Model<Account>> register(@Body Account account);

    // Endpoint để đăng nhập
    @POST("login")
    Call<Response_Model<Account>> login(@Body Account account);

    @GET("get-list-sanpham")
    Call<Response_Model<ArrayList<SanPham>>> getListSanPham();

    @GET("search-sanpham")
    Call<Response_Model<ArrayList<SanPham>>> searchSanPham(@Query("key") String key);


}
