package com.example.asm_ph36893;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_ph36893.adapter.SanPham_Adapter;
import com.example.asm_ph36893.handle.Item_SanPham_Handle;
import com.example.asm_ph36893.model.Response_Model;
import com.example.asm_ph36893.model.SanPham;
import com.example.asm_ph36893.services.HttpRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Item_SanPham_Handle{
    private HttpRequest httpRequest;
    private ArrayList<SanPham> originList;
    private ArrayList<SanPham> displayList;

    private RecyclerView rcySanPham;
    Button btnAdd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcySanPham = findViewById(R.id.rcySanPham);
        httpRequest = new HttpRequest();
        httpRequest.callApi().getListSanPham().enqueue(getSanPhamsAPI);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.navigation_home) {
                    Intent Intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(Intent);
                    return true;
                } else if (itemId == R.id.navigation_cart) {
                    Intent Intent = new Intent(MainActivity.this, Cart.class);
                    startActivity(Intent);
                    return true;
                } else if (itemId == R.id.navigation_menu) {
                    Intent Intent = new Intent(MainActivity.this, Fayvorite.class);
                    startActivity(Intent);
                    return true;
                } else if (itemId == R.id.navigation_love) {
                    Intent Intent = new Intent(MainActivity.this, love.class);
                    startActivity(Intent);
                    return true;
                }
                return false;
            }
        });

    }
    private void getData(ArrayList<SanPham> list) {
        SanPham_Adapter adapter = new SanPham_Adapter(this, list, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        rcySanPham.setLayoutManager(gridLayoutManager);
        rcySanPham.setAdapter(adapter);
    }


    Callback<Response_Model<ArrayList<SanPham>>> getSanPhamsAPI = new Callback<Response_Model<ArrayList<SanPham>>>() {
        @Override
        public void onResponse(Call<Response_Model<ArrayList<SanPham>>> call, Response<Response_Model<ArrayList<SanPham>>> response) {
            if (response.isSuccessful()) {

                if (response.body().getStatus() == 200) {
                    ArrayList<SanPham> list = response.body().getData();
                    Log.d("List", "onResponse: " + list);
                    getData(list);

                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<ArrayList<SanPham>>> call, Throwable t) {
            Log.d(">>>GETLIST", "onFailure: " + t.getMessage());
        }
    };
    Callback<Response_Model<SanPham>> responseSanPhamtsAPI = new Callback<Response_Model<SanPham>>() {
        @Override
        public void onResponse(Call<Response_Model<SanPham>> call, Response<Response_Model<SanPham>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    httpRequest.callApi().getListSanPham().enqueue(getSanPhamsAPI);
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<SanPham>> call, Throwable t) {
            Log.d(">>>GESTLIST", "onFailure: " + t.getMessage());
        }
    };
    @Override
    public void Delete(String id) {

    }

    @Override
    public void Update(String id, SanPham sanpham) {

    }
}
