package com.example.asm_ph36893.model;

import com.google.gson.annotations.SerializedName;

public class SanPham {
    @SerializedName("_id")
    private String id;
    @SerializedName("tensp")
    private String tensp;
    @SerializedName("mota")
    private String mota;
    @SerializedName("gia")
    private String gia;
    @SerializedName("hinhanh")
    private String hinhanh;

    public SanPham() {
    }

    public SanPham(String id, String tensp, String mota, String gia, String hinhanh) {
        this.id = id;
        this.tensp = tensp;
        this.mota = mota;
        this.gia = gia;
        this.hinhanh = hinhanh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
