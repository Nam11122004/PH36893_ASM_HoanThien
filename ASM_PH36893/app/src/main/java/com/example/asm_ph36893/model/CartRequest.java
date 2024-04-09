package com.example.asm_ph36893.model;

import com.google.gson.annotations.SerializedName;

public class CartRequest {

    private String Cartid;

    private String tensp;

    private String mota;

    private String gia;

    private String hinhanh;

    private String quantity;

    public CartRequest() {
    }

    public CartRequest(String cartid, String tensp, String mota, String gia, String hinhanh, String quantity) {
        Cartid = cartid;
        this.tensp = tensp;
        this.mota = mota;
        this.gia = gia;
        this.hinhanh = hinhanh;
        this.quantity = quantity;
    }

    public String getCartid() {
        return Cartid;
    }

    public void setCartid(String cartid) {
        Cartid = cartid;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    // Phương thức để tăng số lượng sản phẩm trong giỏ hàng
    public void increaseQuantity() {
        int quantityInt = Integer.parseInt(quantity);
        quantityInt++;
        quantity = String.valueOf(quantityInt);
    }

    // Phương thức để giảm số lượng sản phẩm trong giỏ hàng
    public void decreaseQuantity() {
        int quantityInt = Integer.parseInt(quantity);
        if (quantityInt > 0) {
            quantityInt--;
            quantity = String.valueOf(quantityInt);
        }
    }
}
