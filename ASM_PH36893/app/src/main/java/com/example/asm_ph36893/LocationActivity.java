package com.example.asm_ph36893;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asm_ph36893.adapter.Adapter_Item_District_Select_GHN;
import com.example.asm_ph36893.adapter.Adapter_Item_Province_Select_GHN;
import com.example.asm_ph36893.adapter.Adapter_Item_Ward_Select_GHN;
import com.example.asm_ph36893.model.District;
import com.example.asm_ph36893.model.DistrictRequest;
import com.example.asm_ph36893.model.Province;
import com.example.asm_ph36893.model.ResponseGHN;
import com.example.asm_ph36893.model.Ward;
import com.example.asm_ph36893.services.GHNRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationActivity extends AppCompatActivity {
    private GHNRequest request;
    private Spinner spProvince, spDistrict, spWard;
    private String productId, productTypeId, productName, description, WardCode;
    private double rate, price;
    private int image, DistrictID, ProvinceID;
    private Adapter_Item_Province_Select_GHN adapter_item_province_select_ghn;
    private Adapter_Item_District_Select_GHN adapter_item_district_select_ghn;
    private Adapter_Item_Ward_Select_GHN adapter_item_ward_select_ghn;
    private Button btn_thantoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        request = new GHNRequest();

        // Khởi tạo Spinner và gán bộ lắng nghe sự kiện
        spProvince = findViewById(R.id.sp_province);
        spDistrict = findViewById(R.id.sp_district);
        spWard = findViewById(R.id.sp_ward);
        spProvince.setOnItemSelectedListener(onItemSelectedListener);
        spDistrict.setOnItemSelectedListener(onItemSelectedListener);
        spWard.setOnItemSelectedListener(onItemSelectedListener);
        btn_thantoan = findViewById(R.id.btn_thantoan);
        btn_thantoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LocationActivity.this, "Thanh Toán Thành Công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LocationActivity.this, Cart.class);
                startActivity(intent);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productId = bundle.getString("productId");
            productTypeId = bundle.getString("productTypeId");
            productName = bundle.getString("productName");
            description = bundle.getString("description");
            rate = bundle.getDouble("rate");
            price = bundle.getDouble("price");
            image = bundle.getInt("image");
        }

        // Gửi yêu cầu lấy danh sách tỉnh/thành phố từ dịch vụ GHN
        request.callAPI().getListProvince().enqueue(responseProvince);
    }

    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.sp_province) {
                // Nếu người dùng chọn tỉnh/thành phố, gửi yêu cầu lấy danh sách quận/huyện tương ứng
                ProvinceID = ((Province) parent.getAdapter().getItem(position)).getProvinceID();
                DistrictRequest districtRequest = new DistrictRequest(ProvinceID);
                request.callAPI().getListDistrict(districtRequest).enqueue(responseDistrict);
            } else if (parent.getId() == R.id.sp_district) {
                // Nếu người dùng chọn quận/huyện, gửi yêu cầu lấy danh sách phường/xã tương ứng
                DistrictID = ((District) parent.getAdapter().getItem(position)).getDistrictID();
                request.callAPI().getListWard(DistrictID).enqueue(responseWard);
            } else if (parent.getId() == R.id.sp_ward) {
                // Lưu mã phường/xã khi người dùng chọn
                WardCode = ((Ward) parent.getAdapter().getItem(position)).getWardCode();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    Callback<ResponseGHN<ArrayList<Province>>> responseProvince = new Callback<ResponseGHN<ArrayList<Province>>>() {
        @Override
        public void onResponse(Call<ResponseGHN<ArrayList<Province>>> call, Response<ResponseGHN<ArrayList<Province>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getCode() == 200) {
                    // Nếu lấy danh sách tỉnh/thành phố thành công, hiển thị trong Spinner
                    ArrayList<Province> ds = new ArrayList<>(response.body().getData());
                    SetDataSpinProvince(ds);
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseGHN<ArrayList<Province>>> call, Throwable t) {
            Toast.makeText(LocationActivity.this, "Lấy dữ liệu bị lỗi", Toast.LENGTH_SHORT).show();
        }
    };

    Callback<ResponseGHN<ArrayList<District>>> responseDistrict = new Callback<ResponseGHN<ArrayList<District>>>() {
        @Override
        public void onResponse(Call<ResponseGHN<ArrayList<District>>> call, Response<ResponseGHN<ArrayList<District>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getCode() == 200) {
                    // Nếu lấy danh sách quận/huyện thành công, hiển thị trong Spinner
                    ArrayList<District> ds = new ArrayList<>(response.body().getData());
                    SetDataSpinDistrict(ds);
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseGHN<ArrayList<District>>> call, Throwable t) {

        }
    };

    Callback<ResponseGHN<ArrayList<Ward>>> responseWard = new Callback<ResponseGHN<ArrayList<Ward>>>() {
        @Override
        public void onResponse(Call<ResponseGHN<ArrayList<Ward>>> call, Response<ResponseGHN<ArrayList<Ward>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getCode() == 200) {
                    // Nếu lấy danh sách phường/xã thành công, hiển thị trong Spinner
                    ArrayList<Ward> ds = new ArrayList<>(response.body().getData());
                    if (response.body().getData() == null) return;
                    ds.addAll(response.body().getData());
                    SetDataSpinWard(ds);
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseGHN<ArrayList<Ward>>> call, Throwable t) {
            Toast.makeText(LocationActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
        }
    };

    private void SetDataSpinProvince(ArrayList<Province> ds) {
        // Hiển thị danh sách tỉnh/thành phố trong Spinner
        adapter_item_province_select_ghn = new Adapter_Item_Province_Select_GHN(this, ds);
        spProvince.setAdapter(adapter_item_province_select_ghn);
    }

    private void SetDataSpinDistrict(ArrayList<District> ds) {
        // Hiển thị danh sách quận/huyện trong Spinner
        adapter_item_district_select_ghn = new Adapter_Item_District_Select_GHN(this, ds);
        spDistrict.setAdapter(adapter_item_district_select_ghn);
    }

    private void SetDataSpinWard(ArrayList<Ward> ds) {
        // Hiển thị danh sách phường/xã trong Spinner
        adapter_item_ward_select_ghn = new Adapter_Item_Ward_Select_GHN(this, ds);
        spWard.setAdapter(adapter_item_ward_select_ghn);
    }
}
