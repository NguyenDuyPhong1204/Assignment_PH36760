package com.example.phong_assignment.View;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.phong_assignment.Model.Response_Model;
import com.example.phong_assignment.Model.User;
import com.example.phong_assignment.R;
import com.example.phong_assignment.Services.HttpRequest;
import com.example.phong_assignment.databinding.ActivityProfileBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    private HttpRequest httpRequest;
    private File file;
    private String userId;
    private String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        httpRequest = new HttpRequest();

        SharedPreferences sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);
        userId = sharedPreferences.getString("id","");

        SharedPreferences imagePreferences = getSharedPreferences("Image", MODE_PRIVATE);
        String imageUri = imagePreferences.getString("imageUser" + userId, null);
        if (imageUri != null) {
            // Load ảnh từ đường dẫn đã lưu và hiển thị lên ImageView
            File imageFile = new File(imageUri);
            Glide.with(this).load(imageFile)
                    .thumbnail(Glide.with(this).load(R.mipmap.ic_launcher))
                    .centerCrop()
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.imgTakecamera);
        }

        binding.imgTakecamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        binding.btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, RequestBody> bodyMap = new HashMap<>();
                String fullName = binding.edtHoten.getText().toString();
                String address  = binding.edtAddress.getText().toString();
                String phoneNumber = binding.edtPhoneNumber.getText().toString();

                bodyMap.put("fullname", getRequestBody(fullName));
                bodyMap.put("addressUser", getRequestBody(address));
                bodyMap.put("phoneNumber", getRequestBody(phoneNumber));

                MultipartBody.Part muPart;
                if(file != null){
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
                    muPart = MultipartBody.Part.createFormData("imageUser", file.getName(), requestBody);
                }else{
                    muPart = null;
                }
                if(fullName.isEmpty() || address.isEmpty() || phoneNumber.isEmpty()){
                    Toast.makeText(ProfileActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    httpRequest.callApi().updateUser(userId,bodyMap,muPart).enqueue(responseUser);
                }
            }
        });

    }



    private void chooseImage() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            getImage.launch(intent);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    ActivityResultLauncher<Intent> getImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == Activity.RESULT_OK) {
                Intent data = o.getData();
                Uri imagePath = data.getData();

                file = createFileFromUri(imagePath, "imageUser" + userId);
                //gilde để load hinh
                Glide.with(ProfileActivity.this).load(file)
                        .thumbnail(Glide.with(ProfileActivity.this).load(R.mipmap.ic_launcher))
                        .centerCrop()
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(binding.imgTakecamera);
            }
        }
    });

    private File createFileFromUri(Uri path, String name) {
        File _file = new File(ProfileActivity.this.getCacheDir(), name + ".png");
        try {
            InputStream in = ProfileActivity.this.getContentResolver().openInputStream(path);
            OutputStream out = new FileOutputStream(_file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            return _file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private RequestBody getRequestBody(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), value);
    }
    Callback<Response_Model<User>> responseUser = new Callback<Response_Model<User>>() {
        @Override
        public void onResponse(Call<Response_Model<User>> call, Response<Response_Model<User>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    Toast.makeText(ProfileActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences1 = getSharedPreferences("Image", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    image = file.getAbsolutePath();
                    editor.putString("imageUser" + userId , image);
                    editor.apply();
                    Log.d("ProfileImage", "onClick: " + image);
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    finish();
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<User>> call, Throwable t) {
            Toast.makeText(ProfileActivity.this, "Update không thành công", Toast.LENGTH_SHORT).show();
            Log.d("UpdateSinhVien", "onFailure: " + t.getMessage());
        }
    };
}