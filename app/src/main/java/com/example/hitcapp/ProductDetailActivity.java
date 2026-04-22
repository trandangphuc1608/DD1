package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView ivProductImage;
    private TextView tvProductName, tvProductPrice, tvProductDesc, tvQuantity;
    private ImageButton ibBackDetail;
    private Button btnAddToCart;
    private TextView tvMinus, tvPlus;
    private CardView cvRelatedProduct1, cvRelatedProduct2;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);

        initViews();

        // Nhận dữ liệu từ Intent gửi tới
        String name = getIntent().getStringExtra("pName");
        String price = getIntent().getStringExtra("pPrice");
        String desc = getIntent().getStringExtra("pDesc");
        int imageRes = getIntent().getIntExtra("pImage", R.drawable.ic_launcher_background);

        // Gán dữ liệu lên UI
        if (name != null) tvProductName.setText(name);
        if (price != null) tvProductPrice.setText(price);
        if (desc != null) tvProductDesc.setText(desc);
        ivProductImage.setImageResource(imageRes);

        setupListeners();
    }

    private void initViews() {
        ivProductImage = findViewById(R.id.ivProductImage);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvProductDesc = findViewById(R.id.tvProductDesc);
        tvQuantity = findViewById(R.id.tvQuantity);
        ibBackDetail = findViewById(R.id.ibBackDetail);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        tvMinus = findViewById(R.id.tvMinus);
        tvPlus = findViewById(R.id.tvPlus);

        // Ánh xạ 2 món liên quan
        cvRelatedProduct1 = findViewById(R.id.cvRelatedProduct1);
        cvRelatedProduct2 = findViewById(R.id.cvRelatedProduct2);
    }

    private void setupListeners() {
        ibBackDetail.setOnClickListener(v -> finish());

        tvPlus.setOnClickListener(v -> {
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
        });

        tvMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
            }
        });

        btnAddToCart.setOnClickListener(v -> {
            Toast.makeText(this, "Đã thêm " + quantity + " " + tvProductName.getText() + " vào giỏ", Toast.LENGTH_SHORT).show();
        });

        // XỬ LÝ CLICK SẢN PHẨM LIÊN QUAN
        if (cvRelatedProduct1 != null) {
            cvRelatedProduct1.setOnClickListener(v -> {
                Intent intent = new Intent(ProductDetailActivity.this, ProductDetailActivity.class);
                intent.putExtra("pName", "Khoai Tây Chiên");
                intent.putExtra("pPrice", "25.000 đ");
                intent.putExtra("pImage", R.drawable.img_shop_khoai_tay);
                intent.putExtra("pDesc", "Khoai tây tươi cắt sợi chiên vàng giòn, rắc thêm muối tiêu đậm đà.");
                startActivity(intent);
            });
        }

        if (cvRelatedProduct2 != null) {
            cvRelatedProduct2.setOnClickListener(v -> {
                Intent intent = new Intent(ProductDetailActivity.this, ProductDetailActivity.class);
                intent.putExtra("pName", "Coca Cola Lớn");
                intent.putExtra("pPrice", "15.000 đ");
                intent.putExtra("pImage", R.drawable.img_shop_coca);
                intent.putExtra("pDesc", "Ly Coca mát lạnh đầy đá, giúp sảng khoái tức thì.");
                startActivity(intent);
            });
        }
    }
}