package com.example.phong_assignment.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.phong_assignment.databinding.ActivityPayBinding;
import com.example.phong_assignment.R;

import java.util.ArrayList;

public class PayActivity extends AppCompatActivity {
    private ActivityPayBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        ArrayList<String> productIds = intent.getStringArrayListExtra("productIds");
        Log.d("PayUserId", "onCreate: " + userId);
        Log.d("PayProductId", "onCreate: " + productIds);
    }
}