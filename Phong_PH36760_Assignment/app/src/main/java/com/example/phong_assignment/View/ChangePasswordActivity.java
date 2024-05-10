package com.example.phong_assignment.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.phong_assignment.Model.Response_Model;
import com.example.phong_assignment.Model.User;
import com.example.phong_assignment.R;
import com.example.phong_assignment.Services.HttpRequest;
import com.example.phong_assignment.databinding.ActivityChangePasswordBinding;
import com.example.phong_assignment.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    private ActivityChangePasswordBinding binding;
    private HttpRequest httpRequest;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        httpRequest = new HttpRequest();

        binding.btnChangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = binding.edtOldpass.getText().toString();
                String newPass = binding.edtNewpass.getText().toString();
                String confirmPass = binding.edtXnpass.getText().toString();

                // Kiểm tra xem mật khẩu cũ có trùng khớp với mật khẩu hiện tại không
                if (checkOldPassword(oldPass)) {
                    // Tiến hành đổi mật khẩu nếu mật khẩu cũ nhập đúng
                    if (newPass.equals(confirmPass)) {
                        // Gọi phương thức để thực hiện đổi mật khẩu
                        changePassword(oldPass, newPass);
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void changePassword(String oldPassword, String newPassword) {

        SharedPreferences sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "");
        User newUser = new User();
        newUser.setPassword(newPassword);

        httpRequest.callApi().changePassword(userId, newUser).enqueue(responseUser);

        // Gửi yêu cầu đổi mật khẩu đến máy chủ

    }

    Callback<Response_Model<User>> responseUser = new Callback<Response_Model<User>>() {
        @Override
        public void onResponse(Call<Response_Model<User>> call, Response<Response_Model<User>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    Toast.makeText(ChangePasswordActivity.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ChangePasswordActivity.this, MainActivity.class));
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<User>> call, Throwable t) {
            Toast.makeText(ChangePasswordActivity.this, "Thanh đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
        }
    };

    private boolean checkOldPassword(String oldPassword) {
        // Lấy mật khẩu hiện tại của người dùng từ SharedPreferences hoặc từ nguồn dữ liệu khác
        SharedPreferences sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);
        String currentPassword = sharedPreferences.getString("password", "");

        // So sánh mật khẩu nhập vào với mật khẩu hiện tại của người dùng
        return oldPassword.equals(currentPassword);
    }
}