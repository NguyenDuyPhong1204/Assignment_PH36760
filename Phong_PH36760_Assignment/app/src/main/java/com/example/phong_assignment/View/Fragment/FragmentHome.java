package com.example.phong_assignment.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.phong_assignment.Adapter.AdapterCategory;
import com.example.phong_assignment.Adapter.AdapterFavorite;
import com.example.phong_assignment.Adapter.AdapterProduct;
import com.example.phong_assignment.Model.Category;
import com.example.phong_assignment.Model.Favorite;
import com.example.phong_assignment.Model.Product;
import com.example.phong_assignment.Model.Response_Model;
import com.example.phong_assignment.R;
import com.example.phong_assignment.Services.HttpRequest;
import com.example.phong_assignment.View.ProfileActivity;
import com.example.phong_assignment.databinding.FragmentHomeBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentHome extends Fragment {

    private FragmentHomeBinding binding;
    private AdapterCategory adapterCategory;
    private AdapterProduct adapterProduct;
    private HttpRequest httpRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.bind(view);
        httpRequest = new HttpRequest();

        httpRequest.callApi().getListCategory().enqueue(getCategory);


        binding.imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Image", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getContext().getSharedPreferences("INFO", Context.MODE_PRIVATE);
        String userId = sharedPreferences1.getString("id", "");
        String image = sharedPreferences.getString("imageUser" + userId, "");

        Glide.with(getContext()).load(image).into(binding.imgUser);
        Log.d("ImageFragment", "onClick: " + image);
        return view;
    }


    Callback<Response_Model<ArrayList<Category>>> getCategory = new Callback<Response_Model<ArrayList<Category>>>() {
        @Override
        public void onResponse(Call<Response_Model<ArrayList<Category>>> call, Response<Response_Model<ArrayList<Category>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    ArrayList<Category> list = response.body().getData();
                    binding.rcvCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    adapterProduct = new AdapterProduct(getContext(), new ArrayList<>());

                    adapterCategory = new AdapterCategory(getContext(), list, adapterProduct);
                    binding.rcvCategory.setAdapter(adapterCategory);

                    binding.rcvFruit.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    binding.rcvFruit.setAdapter(adapterProduct);
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<ArrayList<Category>>> call, Throwable t) {
            Log.d(">>>GetListCategory", "onFailure: " + t.getMessage());
        }
    };


}