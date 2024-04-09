package com.example.asm_ph36893;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_ph36893.adapter.Love_Adapter;
import com.example.asm_ph36893.model.CartRequest;

import java.util.ArrayList;

public class love extends AppCompatActivity {
    private ArrayList<CartRequest> cartItems;
    private Love_Adapter cartAdapter;
    private RecyclerView rcvLove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_love);
        // Khởi tạo loveItems
        rcvLove = findViewById(R.id.rcv_love);

        // Kiểm tra xem rcvCart đã được khởi tạo chưa
        if (rcvLove != null) {
            // Lấy danh sách các mặt hàng từ CartManager
            cartItems = CartMannager.getInstance().getCartItems();

            // Khởi tạo và gán LayoutManager cho RecyclerView
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rcvLove.setLayoutManager(layoutManager);

            // Khởi tạo và gán Adapter cho RecyclerView
            cartAdapter = new Love_Adapter (this, cartItems, this);
            rcvLove.setAdapter(cartAdapter);
        } else {
            // Xử lý trường hợp RecyclerView không được tìm thấy
            // (nếu id rcvCart không khớp với id trong layout XML)
            // Ví dụ: Log một thông báo lỗi, hoặc thực hiện các hành động phù hợp
        }
    }
}