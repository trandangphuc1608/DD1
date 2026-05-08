package com.example.hitcapp;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("api/auth/login")
    Call<User> login(@Body LoginRequest request);
    @POST("api/auth/register")
    Call<User> register(@Body RegisterRequest request);
    @GET("api/Products")
    Call<List<Product>> getProducts();
    @GET("api/Categories")
    Call<List<Category>> getCategories();
    @POST("api/Payments")
    Call<PaymentResponse> createPaymentUrl(@Body PaymentRequest request);
    @GET("api/orders")
    Call<List<Order>> getOrderHistory();
    @POST("api/orders")
    Call<Void> createOrder(@Body OrderRequest request);
}