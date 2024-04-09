package com.example.asm_ph36893;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_ph36893.model.Account;
import com.example.asm_ph36893.model.Response_Model;
import com.example.asm_ph36893.services.HttpRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    TextView txtCreat;
    EditText txtemail, txtpass;
    Button btnSignin;
    private HttpRequest httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtemail = findViewById(R.id.txtemail);
        txtpass = findViewById(R.id.txtpass);
        txtCreat = findViewById(R.id.txtCreat);
        btnSignin = findViewById(R.id.btnSignin);

        httpRequest = new HttpRequest();

        txtCreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Creat_account.class);
                startActivity(intent);
            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account = new Account();
                String email = txtemail.getText().toString().trim();
                String password = txtpass.getText().toString().trim();
                account.setEmail(email);
                account.setPassword(password);
                httpRequest.callApi().login(account).enqueue(responseAcccout);
            }
        });
    }

    Callback<Response_Model<Account>> responseAcccout = new Callback<Response_Model<Account>>() {
        @Override
        public void onResponse(Call<Response_Model<Account>> call, Response<Response_Model<Account>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    //lưu token, lưu device token, id
                    SharedPreferences sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", response.body().getToken());
                    editor.putString("refreshToken", response.body().getRefreshToken());
                    editor.putString("id", response.body().getData().get_id());
                    editor.apply();
                    Log.d("Token", "onResponse: " + response.body().getToken());
                    //sau khi chuyển màn hình chính
                    startActivity(new Intent(Login.this, MainActivity.class));
                } else {
                    Toast.makeText(Login.this, "Login fail", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<Account>> call, Throwable t) {
            Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    };
}