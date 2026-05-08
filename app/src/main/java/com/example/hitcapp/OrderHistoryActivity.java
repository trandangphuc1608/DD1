package com.example.hitcapp;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity {

    private ImageButton ibBackHistory;
    private RecyclerView rvOrderHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        ibBackHistory = findViewById(R.id.ibBackHistory);
        rvOrderHistory = findViewById(R.id.rvOrderHistory);

        rvOrderHistory.setLayoutManager(new LinearLayoutManager(this));

        ibBackHistory.setOnClickListener(v -> finish());

        fetchOrderHistory();
    }

    private void fetchOrderHistory() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        apiService.getOrderHistory().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(@NonNull Call<List<Order>> call, @NonNull Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OrderAdapter adapter = new OrderAdapter(response.body());
                    rvOrderHistory.setAdapter(adapter);
                } else {
                    Toast.makeText(OrderHistoryActivity.this, "Không có dữ liệu đơn hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Order>> call, @NonNull Throwable t) {
                Toast.makeText(OrderHistoryActivity.this, "Lỗi kết nối Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}