package com.example.phong_assignment.View;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.phong_assignment.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private HttpRequest httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        httpRequest = new HttpRequest();

        binding.tvDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        binding.btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                String _username = binding.edtUsename.getText().toString();
                String _password = binding.edtPassword.getText().toString();
                user.setUsername(_username);
                user.setPassword(_password);
                Log.d("UserName + pass", "onClick: " + _username + _password);
                httpRequest.callApi().login(user).enqueue(responseUser);
            }
        });
    }
    Callback<Response_Model<User>> responseUser = new Callback<Response_Model<User>>() {
        @Override
        public void onResponse(Call<Response_Model<User>> call, Response<Response_Model<User>> response) {
        if(response.isSuccessful()){
            if(response.body().getStatus() == 200){
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                //Lưu token, lưu device token, id
                SharedPreferences sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", response.body().getToken());
                editor.putString("refreshToken", response.body().getRefreshToken());
                editor.putString("id", response.body().getData().getUserId());
                editor.putString("password", response.body().getData().getPassword());
                editor.apply();
                //
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        }
        }

        @Override
        public void onFailure(Call<Response_Model<User>> call, Throwable t) {
            Log.d("ErrorLogin", "onFailure: " + t.getMessage());
        }
    };
}