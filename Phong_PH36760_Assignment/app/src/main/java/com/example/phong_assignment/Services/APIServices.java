package com.example.phong_assignment.Services;

import com.example.phong_assignment.Model.Bill;
import com.example.phong_assignment.Model.BillDetails;
import com.example.phong_assignment.Model.Cart;
import com.example.phong_assignment.Model.Category;
import com.example.phong_assignment.Model.Favorite;
import com.example.phong_assignment.Model.Product;
import com.example.phong_assignment.Model.Response_Model;
import com.example.phong_assignment.Model.User;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIServices {

    public static String BASE_URL = "http://10.0.2.2:3000/api/";

    //Add tất cả các thứ
    //1. add to cart
    @POST("add-cart")
    Call<Response_Model<Cart>> addToCart(@Body Cart cart);

    @POST("add-favorite")
    Call<Response_Model<Favorite>> addToFavorite(@Body Favorite favorite);

    @POST("add-bill")
    Call<Response_Model<Bill>> addBill(@Body Bill bill);

    @POST("add-bill-detail")
    Call<Response_Model<BillDetails>> addBillDetails(@Body BillDetails billDetails);

    //Xoá khỏi giỏ hàng
    @DELETE("delete-cart-by-id/{id}")
    Call<Response_Model<Cart>> deleteItemCart(@Path("id") String id);

    //Xoá khỏi favorite

    //Get list tất cả mọi thứ
    //1.list category
    @GET("get-list-category")
    Call<Response_Model<ArrayList<Category>>> getListCategory();

    //2. list product
    @GET("get-list-product/{categoryID}")
    Call<Response_Model<ArrayList<Product>>> getListProduct(@Path("categoryID") String id);

    //3,list cart
    @GET("get-list-cart/{userId}")
    Call<Response_Model<ArrayList<Cart>>> getListCart(@Path("userId") String id);

    //
    @GET("get-list-cart")
    Call<Response_Model<ArrayList<Cart>>> getListCart2();


    //4.list favorite
    @GET("get-list-favorite/{userId}")
    Call<Response_Model<ArrayList<Favorite>>> getListFavorite(@Path("userId") String id);

    //getlist bill
    @GET("get-list-bill-by-id/{userId}")
    Call<Response_Model<ArrayList<Bill>>> getListBill(@Path("userId") String id);


    //5. Search product
    @GET("search-product")
    Call<Response_Model<ArrayList<Product>>> searchProduct(@Query("key") String key);

    //5.getUserById
    @GET("get-user-by-id/{id}")
    Call<Response_Model<User>> getUserById(@Path("id") String id);

    //lấy thông tin product
    @GET("get-product-by-id/{productId}")
    Call<Response_Model<Product>> getProductById(@Path("productId") String productId);

    //login
    @POST("login")
    Call<Response_Model<User>> login(@Body User user);

    @Multipart
    @POST("register-send-email")
    Call<Response_Model<User>> register(@Part("emailUser") RequestBody email,
                                        @Part("username") RequestBody username,
                                        @Part("password") RequestBody password,
                                        @Part("fullname") RequestBody name,
                                        @Part("imageUser") RequestBody image,
                                        @Part("addressUser") RequestBody address,
                                        @Part("phoneNumber") RequestBody phoneNumber);

    //update
    @PUT("update-product/{id}")
    Call<Response_Model<Product>> updateProduct(@Path("id") String id, @Body Product product);

    //updateUser
    @Multipart
    @PUT("update-user/{id}")
    Call<Response_Model<User>> updateUser(@Path("id") String id,
                                          @PartMap Map<String, RequestBody> requestBodyMap,
                                          @Part MultipartBody.Part image);

    @DELETE("delete-cart-by-user/{id}")
    Call<Response_Model<Cart>> deleteCartByUserId(@Path("id") String userId);

    @DELETE("delete-favorite-by-id/{id}")
    Call<Response_Model<Favorite>> deleteFavorite(@Path("id") String id);

    //đổi mật khẩu
    @PUT("change-password/{id}")
    Call<Response_Model<User>> changePassword(@Path("id") String id, @Body User user);

}
