package com.example.asm_ph36893;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_ph36893.model.Account;
import com.example.asm_ph36893.model.Response_Model;
import com.example.asm_ph36893.services.HttpRequest;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Creat_account extends AppCompatActivity {

    EditText txtemaildk, txtpassdk;
    Button btndangky;

    private HttpRequest httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_account);

        txtemaildk = findViewById(R.id.txtemaildk);
        txtpassdk = findViewById(R.id.txtpassdk);
        btndangky = findViewById(R.id.btndangky);
        httpRequest=new HttpRequest();
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestBody _email = RequestBody.create(MediaType.parse("multipart/form-data"), txtemaildk.getText().toString());
                RequestBody _password = RequestBody.create(MediaType.parse("multipart/form-data"), txtpassdk.getText().toString());


                Account account = new Account();
                account.setEmail(txtemaildk.getText().toString());
                account.setPassword(txtpassdk.getText().toString());


                httpRequest.callApi().register(account).enqueue(responseAccount);
            }
        });

    }
    Callback<Response_Model<Account>> responseAccount = new Callback<Response_Model<Account>>() {


        //Phương thức này được gọi khi request đăng ký người dùng được gửi thành công
        @Override
        public void onResponse(Call<Response_Model<Account>> call, Response<Response_Model<Account>> response) {
            if(response.isSuccessful()){
                if (response.body().getStatus() == 200) {
                    Toast.makeText(Creat_account.this, "dk succ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Creat_account.this, Login.class));
                    finish();
                } else {
                    Toast.makeText(Creat_account.this, "Dk fail", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<Account>> call, Throwable t) {
            Log.e("Lỗi", t.getMessage());
        }
    };

}