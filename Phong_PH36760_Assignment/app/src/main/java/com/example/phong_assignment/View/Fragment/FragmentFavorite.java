package com.example.phong_assignment.View.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phong_assignment.Adapter.AdapterCart;
import com.example.phong_assignment.Adapter.AdapterFavorite;
import com.example.phong_assignment.Model.Cart;
import com.example.phong_assignment.Model.Favorite;
import com.example.phong_assignment.Model.Product;
import com.example.phong_assignment.Model.Response_Model;
import com.example.phong_assignment.R;
import com.example.phong_assignment.Services.HttpRequest;
import com.example.phong_assignment.databinding.FragmentFavoriteBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentFavorite extends Fragment implements AdapterFavorite.DeleteItem{
    private FragmentFavoriteBinding binding;
    private AdapterFavorite adapterFavorite;
    private HttpRequest httpRequest;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        binding = FragmentFavoriteBinding.bind(view);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("INFO", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("id", "");
        httpRequest = new HttpRequest();
        httpRequest.callApi().getListFavorite(userId).enqueue(getFavorite);

        return view;
    }

    Callback<Response_Model<ArrayList<Favorite>>> getFavorite = new Callback<Response_Model<ArrayList<Favorite>>>() {
        @Override
        public void onResponse(Call<Response_Model<ArrayList<Favorite>>> call, Response<Response_Model<ArrayList<Favorite>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    ArrayList<Favorite> list = response.body().getData();
                    //tạo danh sách sản phẩm tương ứng

                    adapterFavorite = new AdapterFavorite(getContext(), list, httpRequest, FragmentFavorite.this);
                    binding.rcvFavorite.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    binding.rcvFavorite.setAdapter(adapterFavorite);
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<ArrayList<Favorite>>> call, Throwable t) {
            Log.d("GetListFavorite", "onFailure: " + t.getMessage());
        }
    };

    Callback<Response_Model<Favorite>> responFavorite = new Callback<Response_Model<Favorite>>() {
        @Override
        public void onResponse(Call<Response_Model<Favorite>> call, Response<Response_Model<Favorite>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    httpRequest.callApi().getListFavorite(userId).enqueue(getFavorite);
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<Favorite>> call, Throwable t) {
            Log.d(">>>GetListCart", "onFailure: " + t.getMessage());
        }
    };

    @Override
    public void delete(String id) {
        httpRequest.callApi().deleteFavorite(id).enqueue(responFavorite);
    }
}