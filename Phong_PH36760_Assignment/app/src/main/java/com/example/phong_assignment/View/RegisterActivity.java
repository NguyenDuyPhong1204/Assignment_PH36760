package com.example.phong_assignment.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.phong_assignment.Model.Response_Model;
import com.example.phong_assignment.Model.User;
import com.example.phong_assignment.R;
import com.example.phong_assignment.Services.HttpRequest;
import com.example.phong_assignment.databinding.ActivityRegisterBinding;

import java.util.concurrent.TimeoutException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private HttpRequest httpRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        httpRequest = new HttpRequest();
        binding.btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.edtEmail.getText().toString();
                String username = binding.edtUsername.getText().toString();
                String password = binding.edtPassword.getText().toString();
                RequestBody _email = RequestBody.create(MediaType.parse("multipart/form-data"),email);
                RequestBody _username = RequestBody.create(MediaType.parse("multipart/form-data"), username);
                RequestBody _password = RequestBody.create(MediaType.parse("multipart/form-data"), password);
                RequestBody fullname = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                RequestBody image = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                RequestBody address = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                RequestBody phoneNumber = RequestBody.create(MediaType.parse("multipart/form-date"),"");

                httpRequest.callApi().register(_email,_username,_password, fullname, image,address, phoneNumber).enqueue(responseUser);
                Log.d("DuLieu", "onClick: " + email + username + password);
            }
        });

        binding.tvDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    Callback<Response_Model<User>> responseUser = new Callback<Response_Model<User>>() {
        @Override
        public void onResponse(Call<Response_Model<User>> call, Response<Response_Model<User>> response) {
            if (response.isSuccessful()) {

                if (response.body().getStatus() == 200) {
                    Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<User>> call, Throwable t) {
            if (t instanceof TimeoutException) {
                Toast.makeText(RegisterActivity.this, "Kết nối mạng không ổn định. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("Lỗi Callback", "onFailure: " + t.getMessage());
            }
        }
    };
}