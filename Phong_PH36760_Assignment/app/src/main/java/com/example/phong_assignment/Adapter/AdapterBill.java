package com.example.phong_assignment.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phong_assignment.Model.Bill;
import com.example.phong_assignment.Model.Response_Model;
import com.example.phong_assignment.Model.User;
import com.example.phong_assignment.R;
import com.example.phong_assignment.Services.HttpRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterBill extends RecyclerView.Adapter<AdapterBill.viewHolep> {
    private Context context;
    private ArrayList<Bill> list;
    private HttpRequest httpRequest;

    public AdapterBill(Context context, ArrayList<Bill> list, HttpRequest httpRequest) {
        this.context = context;
        this.list = list;
        this.httpRequest = httpRequest;
    }

    @NonNull
    @Override
    public viewHolep onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new viewHolep(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolep holder, int position) {
        Bill bill = list.get(position);
        String userId = bill.getUserId();
        Log.d("UserIdBill", "onBindViewHolder: " + userId);
        holder.tvBillId.setText(bill.getBillId());
        Log.d("DateBill", "onBindViewHolder: " + bill.getCreateAt());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        try {
            Date createdAtDate = null;
            try {
                createdAtDate = sdf.parse(bill.getCreateAt());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            // Định dạng lại thời gian theo định dạng mong muốn
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            String formattedDate = outputFormat.format(createdAtDate);
            // Hiển thị thời gian đặt hàng trong TextView
            holder.tvDate.setText(formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
            holder.tvDate.setText("Invalid Date");
        }
        httpRequest.callApi().getUserById(userId).enqueue(new Callback<Response_Model<User>>() {
            @Override
            public void onResponse(Call<Response_Model<User>> call, Response<Response_Model<User>> response) {
                if (response.isSuccessful()) {
                    User user = response.body().getData();
                    holder.tvUserId.setText(user.getFullname());
                }
            }

            @Override
            public void onFailure(Call<Response_Model<User>> call, Throwable t) {
                Log.d("GetUserBill", "onFailure: " + t.getMessage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class viewHolep extends RecyclerView.ViewHolder {
        TextView tvBillId, tvUserId, tvDate;

        public viewHolep(@NonNull View itemView) {
            super(itemView);
            tvBillId = itemView.findViewById(R.id.tv_billId);
            tvUserId = itemView.findViewById(R.id.tv_nameUser);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
