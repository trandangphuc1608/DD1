package com.example.hitcapp;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShopActivity extends AppCompatActivity {

    private ImageButton ibBackShop, ibCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shop);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
    }

    private void initViews() {
        ibBackShop = findViewById(R.id.ibBackShop);
        ibCart = findViewById(R.id.ibCart);
    }

    private void setupListeners() {
        ibBackShop.setOnClickListener(v -> finish());

        ibCart.setOnClickListener(v ->
                Toast.makeText(ShopActivity.this, "Mở Giỏ hàng", Toast.LENGTH_SHORT).show()
        );
    }
}