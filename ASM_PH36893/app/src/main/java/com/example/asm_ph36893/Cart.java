package com.example.asm_ph36893;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_ph36893.adapter.Cart_Adapter;
import com.example.asm_ph36893.model.CartRequest;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    private ArrayList<CartRequest> cartItems;
    private Cart_Adapter cartAdapter;
    private RecyclerView rcvCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        rcvCart = findViewById(R.id.rcv_cart);

        // Kiểm tra xem rcvCart đã được khởi tạo chưa
        if (rcvCart != null) {
            // Lấy danh sách các mặt hàng từ CartManager
            cartItems = CartMannager.getInstance().getCartItems();

            // Khởi tạo và gán LayoutManager cho RecyclerView
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rcvCart.setLayoutManager(layoutManager);

            // Khởi tạo và gán Adapter cho RecyclerView
            cartAdapter = new Cart_Adapter(this, cartItems, this);
            rcvCart.setAdapter(cartAdapter);
        } else {
            // Xử lý trường hợp RecyclerView không được tìm thấy
            // (nếu id rcvCart không khớp với id trong layout XML)
            // Ví dụ: Log một thông báo lỗi, hoặc thực hiện các hành động phù hợp
        }
    }
}