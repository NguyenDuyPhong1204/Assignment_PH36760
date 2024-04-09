package com.example.phong_assignment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phong_assignment.Model.Cart;
import com.example.phong_assignment.Model.Favorite;
import com.example.phong_assignment.Model.Product;
import com.example.phong_assignment.Model.Response_Model;
import com.example.phong_assignment.Model.User;
import com.example.phong_assignment.R;
import com.example.phong_assignment.Services.HttpRequest;
import com.example.phong_assignment.View.DetailActivity;
import com.example.phong_assignment.View.LoginActivity;
import com.example.phong_assignment.View.MainActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.viewHolep> {

    private Context context;
    private ArrayList<Product> list;
    private HttpRequest httpRequest;
    private User currentUser;
    private SharedPreferences sharedPreferences;
    private ArrayList<Favorite> listFavorite;
    private Product product;

    public AdapterProduct(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolep onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fruit, parent, false);
        return new viewHolep(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolep holder, int position) {
        product = list.get(position);
        httpRequest = new HttpRequest();
        sharedPreferences = context.getSharedPreferences("INFO", Context.MODE_PRIVATE);
        DecimalFormat decimalFormat = new DecimalFormat("###.###");
        holder.tvName.setText(product.getProductName());
        holder.tvPrice.setText(decimalFormat.format(product.getProductPrice()));
        Glide.with(context).load(product.getProductImage()).into(holder.imgProduct);
        Log.d("IMAGE PRODUCT", "image: " + product.getProductImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product1 = list.get(holder.getAdapterPosition());
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("productId", product1.getProductId());
                intent.putExtra("nameProduct", product1.getProductName());
                intent.putExtra("priceProduct", product1.getProductPrice());
                intent.putExtra("descriptionProduct", product1.getDescription());
                intent.putExtra("imageProduct", product1.getProductImage());

                holder.itemView.getContext().startActivity(intent);
            }
        });
        String userId = sharedPreferences.getString("id", "");

        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = new Cart();
                Product product1 = list.get(holder.getAdapterPosition());
                cart.setProductId(product1.getProductId());
                cart.setUserId(userId);
                cart.setProductName(product1.getProductName());
                cart.setProductPrice(product1.getProductPrice());
                cart.setProductImage(product1.getProductImage());
                cart.setQuantity(1);

                httpRequest.callApi().addToCart(cart).enqueue(responseCart);

            }
        });


        holder.imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite();
                Product productFavorite = list.get(holder.getAdapterPosition());
                favorite.setUserId(userId);
                favorite.setProductId(productFavorite.getProductId());
                Log.d("AddFavorite", "onClick: " + product.getProductId());
                if (product.getFavorite() == true) {
                    holder.imgFavorite.setImageResource(R.drawable.lover); // Đặt ảnh màu đỏ vào imgFavorite
                } else {
                    holder.imgFavorite.setImageResource(R.drawable.love); // Đặt ảnh mặc định của imgFavorite
                }
                httpRequest.callApi().addToFavorite(favorite).enqueue(responseFavorite);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolep extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView imgProduct, imgFavorite;
        LinearLayout btnAddToCart;

        public viewHolep(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_namefruit);
            tvPrice = itemView.findViewById(R.id.tv_pricefruit);
            imgProduct = itemView.findViewById(R.id.img_itemfruit);
            btnAddToCart = itemView.findViewById(R.id.btn_addtoCart);
            imgFavorite = itemView.findViewById(R.id.img_favorite);
        }
    }

    public void updateData(ArrayList<Product> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    Callback<Response_Model<Cart>> responseCart = new Callback<Response_Model<Cart>>() {
        @Override
        public void onResponse(Call<Response_Model<Cart>> call, Response<Response_Model<Cart>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<Cart>> call, Throwable t) {
            Toast.makeText(context, "Thêm không thành công", Toast.LENGTH_SHORT).show();
            Log.d("ErrorCart", "onFailure: " + t.getMessage());
        }
    };

    Callback<Response_Model<Favorite>> responseFavorite = new Callback<Response_Model<Favorite>>() {
        @Override
        public void onResponse(Call<Response_Model<Favorite>> call, Response<Response_Model<Favorite>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    product.setFavorite(!product.getFavorite());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response_Model<Favorite>> call, Throwable t) {
            Toast.makeText(context, "Thêm không thành công", Toast.LENGTH_SHORT).show();
            Log.d("ErrorFavorite", "onFailure: " + t.getMessage());
        }
    };



}
