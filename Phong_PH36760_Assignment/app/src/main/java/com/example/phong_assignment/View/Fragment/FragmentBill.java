package com.example.phong_assignment.View.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phong_assignment.Adapter.AdapterBill;
import com.example.phong_assignment.Model.Bill;
import com.example.phong_assignment.Model.Response_Model;
import com.example.phong_assignment.R;
import com.example.phong_assignment.Services.HttpRequest;
import com.example.phong_assignment.databinding.FragmentBillBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentBill extends Fragment {
    private FragmentBillBinding binding;
    private HttpRequest httpRequest;
    private AdapterBill adapterBill;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        binding = FragmentBillBinding.bind(view);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("INFO", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("id", "");

        httpRequest = new HttpRequest();
        httpRequest.callApi().getListBill(userId).enqueue(getListBill);
        return view;
    }

    Callback<Response_Model<ArrayList<Bill>>> getListBill = new Callback<Response_Model<ArrayList<Bill>>>() {
        @Override
        public void onResponse(Call<Response_Model<ArrayList<Bill>>> call, Response<Response_Model<ArrayList<Bill>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    ArrayList<Bill> list = response.body().getData();
                    adapterBill = new AdapterBill(getContext(), list, httpRequest);
                    binding.rcvBill.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.rcvBill.setAdapter(adapterBill);
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<ArrayList<Bill>>> call, Throwable t) {
            Log.d("GetListBill", "onFailure: " + t.getMessage());
        }
    };

}