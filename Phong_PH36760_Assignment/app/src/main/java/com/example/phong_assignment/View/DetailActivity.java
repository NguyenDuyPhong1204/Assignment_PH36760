package com.example.phong_assignment.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.phong_assignment.Model.Cart;
import com.example.phong_assignment.Model.Favorite;
import com.example.phong_assignment.Model.Product;
import com.example.phong_assignment.Model.Response_Model;
import com.example.phong_assignment.R;
import com.example.phong_assignment.Services.HttpRequest;
import com.example.phong_assignment.databinding.ActivityDetailBinding;


import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private HttpRequest httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        DecimalFormat decimalFormat = new DecimalFormat("###.###");
        String productId = intent.getStringExtra("productId");
        String nameProduct = intent.getStringExtra("nameProduct");
        String imageProduct = intent.getStringExtra("imageProduct");
        String description = intent.getStringExtra("descriptionProduct");
        double priceProduct = intent.getDoubleExtra("priceProduct",0.0);




        Glide.with(getApplicationContext()).load(imageProduct).into(binding.imgFruitDetails);
        binding.tvNamefruitDetails.setText(nameProduct);
        binding.tvPricefruitDetails.setText(decimalFormat.format(priceProduct));
        binding.tvDescription.setText(description);

        SharedPreferences sharedPreferences = getSharedPreferences("INFO",MODE_PRIVATE);
        String userId = sharedPreferences.getString("id","");

        httpRequest = new HttpRequest();
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = new Cart();

                cart.setProductId(productId);
                cart.setUserId(userId);
                cart.setProductName(nameProduct);
                cart.setProductPrice(priceProduct);
                cart.setProductImage(imageProduct);
                cart.setQuantity(1);
                httpRequest.callApi().addToCart(cart).enqueue(responseCart);
            }
        });

        binding.btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite();
                favorite.setUserId(userId);
                favorite.setProductId(productId);

                httpRequest.callApi().addToFavorite(favorite).enqueue(responseFavorite);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    Callback<Response_Model<Cart>> responseCart = new Callback<Response_Model<Cart>>() {
        @Override
        public void onResponse(Call<Response_Model<Cart>> call, Response<Response_Model<Cart>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    Toast.makeText(DetailActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<Cart>> call, Throwable t) {
            Toast.makeText(DetailActivity.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
            Log.d("ErrorCart", "onFailure: " + t.getMessage());
        }
    };
    Callback<Response_Model<Favorite>> responseFavorite = new Callback<Response_Model<Favorite>>() {
        @Override
        public void onResponse(Call<Response_Model<Favorite>> call, Response<Response_Model<Favorite>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    Toast.makeText(DetailActivity.this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<Favorite>> call, Throwable t) {
            Toast.makeText(DetailActivity.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
            Log.d("ErrorFavorite", "onFailure: " + t.getMessage());
        }
    };
}