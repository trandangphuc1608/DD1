package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView ivProductImage;
    private TextView tvProductName, tvProductPrice, tvProductDesc, tvQuantity;
    private ImageButton ibBackDetail, ibCartDetail;
    private Button btnAddToCart;
    private TextView tvMinus, tvPlus;

    private RecyclerView rvRelatedProducts;
    private HomeProductAdapter relatedAdapter;
    private List<Product> relatedList;

    private int quantity = 1;
    private int currentProductId;
    private int currentCategoryId;
    private String currentImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        initViews();
        getDataFromIntent();

        rvRelatedProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        relatedList = new ArrayList<>();
        relatedAdapter = new HomeProductAdapter(this, relatedList);
        rvRelatedProducts.setAdapter(relatedAdapter);

        fetchRelatedProducts();
        setupListeners();
    }

    private void initViews() {
        ivProductImage = findViewById(R.id.ivProductImage);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvProductDesc = findViewById(R.id.tvProductDesc);
        tvQuantity = findViewById(R.id.tvQuantity);
        ibBackDetail = findViewById(R.id.ibBackDetail);
        ibCartDetail = findViewById(R.id.ibCartDetail);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        tvMinus = findViewById(R.id.tvMinus);
        tvPlus = findViewById(R.id.tvPlus);
        rvRelatedProducts = findViewById(R.id.rvRelatedProducts);
    }

    private void getDataFromIntent() {
        currentProductId = getIntent().getIntExtra("pId", -1);
        currentCategoryId = getIntent().getIntExtra("pCategoryId", -1);
        String name = getIntent().getStringExtra("pName");
        String price = getIntent().getStringExtra("pPrice");
        String desc = getIntent().getStringExtra("pDesc");
        currentImageUrl = getIntent().getStringExtra("pImageUrl");

        tvProductName.setText(name);
        tvProductPrice.setText(price);

        if (desc != null && !desc.isEmpty()) {
            tvProductDesc.setText(desc);
        } else {
            tvProductDesc.setText("Mô tả sản phẩm đang được cập nhật...");
        }

        Glide.with(this)
                .load(currentImageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(ivProductImage);
    }

    private void fetchRelatedProducts() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        apiService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> allProducts = response.body();
                    relatedList.clear();
                    for (Product p : allProducts) {
                        if (p.getCategoryId() == currentCategoryId && p.getId() != currentProductId) {
                            relatedList.add(p);
                        }
                    }
                    relatedAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
            }
        });
    }

    private void setupListeners() {
        ibBackDetail.setOnClickListener(v -> finish());

        ibCartDetail.setOnClickListener(v -> {
            Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("openTab", "cart");
            startActivity(intent);
            finish();
        });

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
            double rawPrice = 0;
            try {
                String priceStr = tvProductPrice.getText().toString()
                        .replace(".", "").replace(",", "").replace(" đ", "").trim();
                rawPrice = Double.parseDouble(priceStr);
            } catch (Exception ignored) {}

            Product p = new Product();
            p.setId(currentProductId);
            p.setName(tvProductName.getText().toString());
            p.setPrice(rawPrice);
            // Đã sửa thành setImageUrl
            p.setImageUrl(currentImageUrl);
            p.setDescription(tvProductDesc.getText().toString());
            p.setCategoryId(currentCategoryId);

            CartManager.getInstance().addToCart(p, quantity);
            Toast.makeText(this, "Đã thêm " + quantity + " món vào giỏ", Toast.LENGTH_SHORT).show();
        });
    }
}