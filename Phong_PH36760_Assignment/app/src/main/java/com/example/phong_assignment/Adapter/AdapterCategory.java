package com.example.phong_assignment.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phong_assignment.Model.Category;
import com.example.phong_assignment.Model.Product;
import com.example.phong_assignment.Model.Response_Model;
import com.example.phong_assignment.R;
import com.example.phong_assignment.Services.HttpRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.viewHolep>{

    private Context context;
    private ArrayList<Category> list;
    private AdapterProduct adapterProduct;
    private HttpRequest httpRequest;

    public AdapterCategory(Context context, ArrayList<Category> list, AdapterProduct adapterProduct) {
        this.context = context;
        this.list = list;
        this.adapterProduct = adapterProduct;
    }

    @NonNull
    @Override
    public viewHolep onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new viewHolep(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolep holder, int position) {
        Category category = list.get(position);
        httpRequest = new HttpRequest();
        holder.tvTitle.setText(category.getTitle());
        Glide.with(context).load(category.getImage()).into(holder.img_category);

        if(position == 0){
            httpRequest.callApi().getListProduct(category.getCategoryId()).enqueue(getListProduct);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpRequest.callApi().getListProduct(category.getCategoryId()).enqueue(getListProduct);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class viewHolep extends RecyclerView.ViewHolder{
        TextView tvTitle;
        ImageView img_category;
        public viewHolep(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_category);
            img_category = itemView.findViewById(R.id.img_category);
        }
    }
    Callback<Response_Model<ArrayList<Product>>> getListProduct = new Callback<Response_Model<ArrayList<Product>>>() {
        @Override
        public void onResponse(Call<Response_Model<ArrayList<Product>>> call, Response<Response_Model<ArrayList<Product>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    ArrayList<Product> list = response.body().getData();
                    adapterProduct.updateData(list);
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<ArrayList<Product>>> call, Throwable t) {
            Log.d(">>>GetListProduct", "onFailure: " + t.getMessage());
        }
    };

}
