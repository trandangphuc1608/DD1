package com.example.hitcapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageButton ibBackDetail;
    private TextView tvMinus, tvPlus, tvQuantity;
    private Button btnAddToCart;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom); // Bỏ padding trái phải để ảnh tràn viền
            return insets;
        });

        initViews();
        setupListeners();
    }

    private void initViews() {
        ibBackDetail = findViewById(R.id.ibBackDetail);
        tvMinus = findViewById(R.id.tvMinus);
        tvPlus = findViewById(R.id.tvPlus);
        tvQuantity = findViewById(R.id.tvQuantity);
        btnAddToCart = findViewById(R.id.btnAddToCart);
    }

    private void setupListeners() {
        // Nút Quay lại
        ibBackDetail.setOnClickListener(v -> finish());

        // Nút Giảm số lượng
        tvMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
            }
        });

        // Nút Tăng số lượng
        tvPlus.setOnClickListener(v -> {
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
        });

        // Nút Thêm vào giỏ hàng
        btnAddToCart.setOnClickListener(v -> {
            Toast.makeText(ProductDetailActivity.this, "Đã thêm " + quantity + " sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            finish(); // Thêm xong tự động quay về màn hình trước đó
        });
    }
}