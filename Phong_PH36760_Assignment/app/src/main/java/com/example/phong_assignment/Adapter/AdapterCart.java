package com.example.phong_assignment.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phong_assignment.Handle.HandleCart;
import com.example.phong_assignment.Model.Cart;
import com.example.phong_assignment.Model.Product;
import com.example.phong_assignment.Model.Response_Model;
import com.example.phong_assignment.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Callback;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.viewHolep> {

    private Context context;
    private ArrayList<Cart> list;
    private HandleCart handleCart;

    public interface CartListener {
        void delete(String id);

        void tongSoLuong(int soLuong);

        void tongTien(double tongTien);
    }

    private CartListener cartListener;
    private double tongTien, tien, tongTienAll;
    private double giaTien;
    private int soLuong, tongSoLuong;

    public AdapterCart(Context context, ArrayList<Cart> list, CartListener cartListener) {
        this.context = context;
        this.list = list;
        this.cartListener = cartListener;
        tongSoLuong = 0;
        tongTienAll = 0;
    }

    @NonNull
    @Override
    public viewHolep onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new viewHolep(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolep holder, int position) {
        Cart cart = list.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###.###");
        holder.tvName.setText(cart.getProductName());
        holder.tvPrice.setText(decimalFormat.format(cart.getProductPrice()));
        Glide.with(context).load(cart.getProductImage()).into(holder.img_product);


        giaTien = cart.getProductPrice();
        soLuong = cart.getQuantity();
        tien = giaTien * soLuong;
        tongTien = tien;

        tongTienAll += tien;
        cartListener.tongTien(tongTienAll);
        holder.tvTongTien.setText(decimalFormat.format(tongTien));
        tongSoLuong += soLuong;
        cartListener.tongSoLuong(tongSoLuong);
        holder.img_tangSl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.setQuantity(cart.getQuantity() + 1);
                holder.tvQuantity.setText(String.valueOf(cart.getQuantity()));

                giaTien = cart.getProductPrice();
                tongTien += giaTien;
                holder.tvTongTien.setText(decimalFormat.format(tongTien));

                tongSoLuong++;
                cartListener.tongSoLuong(tongSoLuong);

                tongTienAll += giaTien;
                cartListener.tongTien(tongTienAll);
            }
        });

        holder.img_giamSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.getQuantity() > 1) {
                    cart.setQuantity(cart.getQuantity() - 1);
                    holder.tvQuantity.setText(String.valueOf(cart.getQuantity()));

                    giaTien = cart.getProductPrice();
                    tongTien -= giaTien;
                    holder.tvTongTien.setText(decimalFormat.format(tongTien));

                    tongSoLuong--;
                    cartListener.tongSoLuong(tongSoLuong);

                    tongTienAll -= giaTien;
                    cartListener.tongTien(tongTienAll);
                }
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Xoá khỏi giỏ hàng")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cartListener.delete(cart.getCartId());
                                Log.d("IdCart", "onClick: " + cart.getCartId());
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

    class viewHolep extends RecyclerView.ViewHolder {
        ImageView img_product, img_tangSl, img_giamSL, imgDelete;
        TextView tvName, tvPrice, tvTongTien, tvQuantity;

        public viewHolep(@NonNull View itemView) {
            super(itemView);
            img_product = itemView.findViewById(R.id.img_product_cart);
            img_giamSL = itemView.findViewById(R.id.img_minus_cart);
            img_tangSl = itemView.findViewById(R.id.img_add_quantity_cart);
            tvName = itemView.findViewById(R.id.tv_name_product);
            tvTongTien = itemView.findViewById(R.id.tv_price_item_cart);
            tvPrice = itemView.findViewById(R.id.tv_price_product);
            tvQuantity = itemView.findViewById(R.id.tv_quantity_cart);
            imgDelete = itemView.findViewById(R.id.img_delete_cart);
        }
    }
    public ArrayList<Cart> getList() {
        return list;
    }

}
