package com.example.phong_assignment.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.phong_assignment.Adapter.AdapterCart;
import com.example.phong_assignment.Model.Bill;
import com.example.phong_assignment.Model.Cart;
import com.example.phong_assignment.Model.Response_Model;
import com.example.phong_assignment.R;
import com.example.phong_assignment.Services.HttpRequest;
import com.example.phong_assignment.View.PayActivity;
import com.example.phong_assignment.databinding.FragmentCartBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentCart extends Fragment implements AdapterCart.CartListener{

    private FragmentCartBinding binding;
    private ArrayList<Cart> listCart = new ArrayList<>();
    private AdapterCart adapterCart;
    private HttpRequest httpRequest;
    private String userId;
    private double total;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        binding = FragmentCartBinding.bind(view);
        httpRequest = new HttpRequest();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("INFO", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("id", "");
        Log.d("UserId", "onCreateView: " + userId);

        httpRequest.callApi().getListCart(userId).enqueue(getListCart);

        binding.btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy danh sách sản phẩm từ adapter
                String address = binding.edtAddress.getText().toString();
                if(address.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập địa chỉ nhận hàng", Toast.LENGTH_SHORT).show();
                }else{
                    Bill bill = new Bill();
                    bill.setUserId(userId);
                    bill.setTotal(total);
                    bill.setAddress(address);
                    httpRequest.callApi().addBill(bill).enqueue(responseBill);
                    httpRequest.callApi().deleteCartByUserId(userId).enqueue(responseCart);
                }
            }
        });

        return view;
    }


    Callback<Response_Model<ArrayList<Cart>>> getListCart = new Callback<Response_Model<ArrayList<Cart>>>() {
        @Override
        public void onResponse(Call<Response_Model<ArrayList<Cart>>> call, Response<Response_Model<ArrayList<Cart>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    ArrayList<Cart> list = response.body().getData();
                    listCart = list;
                    binding.rcvCart.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterCart = new AdapterCart(getContext(), list, FragmentCart.this);
                    binding.rcvCart.setAdapter(adapterCart);
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<ArrayList<Cart>>> call, Throwable t) {
            Log.d(">>>GetListCart", "onFailure: " + t.getMessage());
        }
    };

    Callback<Response_Model<Cart>> responseCart = new Callback<Response_Model<Cart>>() {
        @Override
        public void onResponse(Call<Response_Model<Cart>> call, Response<Response_Model<Cart>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    httpRequest.callApi().getListCart(userId).enqueue(getListCart);
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<Cart>> call, Throwable t) {
            Log.d(">>>GetListCart", "onFailure: " + t.getMessage());
        }
    };


    Callback<Response_Model<Bill>> responseBill = new Callback<Response_Model<Bill>>() {
        @Override
        public void onResponse(Call<Response_Model<Bill>> call, Response<Response_Model<Bill>> response) {
            if(response.isSuccessful()){
                if(response.body().getStatus() == 200){
                    Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<Bill>> call, Throwable t) {
            Toast.makeText(getContext(), "Đặt mua sản phẩm không thành công !", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void delete(String id) {
        httpRequest.callApi().deleteItemCart(id).enqueue(responseCart);
    }

    @Override
    public void tongSoLuong(int soLuong) {

    }

    @Override
    public void tongTien(double tongTien) {
        binding.tvTongTien.setText(String.valueOf(tongTien));
        total = tongTien;
    }
}