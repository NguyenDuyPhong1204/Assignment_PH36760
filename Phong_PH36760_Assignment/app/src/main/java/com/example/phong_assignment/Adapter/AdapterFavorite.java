package com.example.phong_assignment.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phong_assignment.Model.Favorite;
import com.example.phong_assignment.Model.Product;
import com.example.phong_assignment.Model.Response_Model;
import com.example.phong_assignment.R;
import com.example.phong_assignment.Services.HttpRequest;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.viewHolep>{

    private Context context;
    private ArrayList<Favorite> list;
    private ArrayList<Product> productList;
    private HttpRequest httpRequest;

    public interface DeleteItem{
        void delete (String id);
    }
    private DeleteItem deleteItem;

    public AdapterFavorite(Context context, ArrayList<Favorite> list, HttpRequest httpRequest, DeleteItem deleteItem) {
        this.context = context;
        this.list = list;
        this.httpRequest = httpRequest;
        this.deleteItem = deleteItem;
    }

    @NonNull
    @Override
    public viewHolep onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite,parent,false);
        return new viewHolep(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolep holder, int position) {
        Favorite favorite = list.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###.###");
        // Tìm sản phẩm tương ứng trong productList dựa trên productId của yêu thích

        String productId = favorite.getProductId();

        httpRequest.callApi().getProductById(productId).enqueue(new Callback<Response_Model<Product>>() {
            @Override
            public void onResponse(Call<Response_Model<Product>> call, Response<Response_Model<Product>> response) {
                if (response.isSuccessful()) {
                    Product product = response.body().getData();
                    // Hiển thị thông tin sản phẩm trên giao diện người dùng
                    holder.tvName.setText(product.getProductName());
                    holder.tvPrice.setText(decimalFormat.format(product.getProductPrice()));
                    Glide.with(context).load(product.getProductImage()).into(holder.imgProduct);
                    // Xử lý các sự kiện khác nếu cần
                }
            }

            @Override
            public void onFailure(Call<Response_Model<Product>> call, Throwable t) {
                Log.d("GetProductById", "onFailure: " + t.getMessage());
            }
        });

        holder.imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Xoá khỏi giỏ hàng")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteItem.delete(favorite.getFavoriteId());
                                Log.d("IdCart", "onClick: " + favorite.getFavoriteId());
                                Toast.makeText(context, "Đã xoá khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.create().show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolep extends RecyclerView.ViewHolder{
        TextView tvName, tvPrice;
        ImageView imgProduct, imgFavorite;
        LinearLayout btnAddToCart;
        public viewHolep(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_namefruit_fv);
            tvPrice = itemView.findViewById(R.id.tv_pricefruit_fv);
            imgProduct = itemView.findViewById(R.id.img_itemfruit_fv);
            btnAddToCart = itemView.findViewById(R.id.btn_addtoCart_fv);
            imgFavorite = itemView.findViewById(R.id.img_favorite_fv);
        }
    }


}
