package com.example.asm_ph36893.handle;


import com.example.asm_ph36893.model.SanPham;

public interface Item_SanPham_Handle {
    public void Delete(String id);
    public void Update(String id, SanPham sanpham);
}
