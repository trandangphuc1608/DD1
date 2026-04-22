package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;

public class CartFragment extends Fragment {

    private TextView tvQuantity1, tvQuantity2, tvTotalAmount;
    private TextView btnMinus1, btnPlus1, btnMinus2, btnPlus2;
    private Button btnCheckout;

    // Giá cứng của sản phẩm
    private final int PRICE_BURGER = 65000;
    private final int PRICE_COCA = 15000;

    // Số lượng ban đầu
    private int quantity1 = 1;
    private int quantity2 = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // 1. Ánh xạ các view
        tvQuantity1 = view.findViewById(R.id.tvQuantity1);
        tvQuantity2 = view.findViewById(R.id.tvQuantity2);
        tvTotalAmount = view.findViewById(R.id.tvTotalAmount);

        btnMinus1 = view.findViewById(R.id.btnMinus1);
        btnPlus1 = view.findViewById(R.id.btnPlus1);
        btnMinus2 = view.findViewById(R.id.btnMinus2);
        btnPlus2 = view.findViewById(R.id.btnPlus2);

        btnCheckout = view.findViewById(R.id.btnCheckout);

        // 2. Thiết lập sự kiện click
        setupEventListeners();

        // Tính tổng tiền lần đầu
        updateTotalPrice();

        return view;
    }

    private void setupEventListeners() {
        // Sản phẩm 1 (Burger)
        btnPlus1.setOnClickListener(v -> {
            quantity1++;
            tvQuantity1.setText(String.valueOf(quantity1));
            updateTotalPrice();
        });

        btnMinus1.setOnClickListener(v -> {
            if (quantity1 > 0) { // Cho phép về 0 (xem như bỏ món)
                quantity1--;
                tvQuantity1.setText(String.valueOf(quantity1));
                updateTotalPrice();
            }
        });

        // Sản phẩm 2 (Coca)
        btnPlus2.setOnClickListener(v -> {
            quantity2++;
            tvQuantity2.setText(String.valueOf(quantity2));
            updateTotalPrice();
        });

        btnMinus2.setOnClickListener(v -> {
            if (quantity2 > 0) {
                quantity2--;
                tvQuantity2.setText(String.valueOf(quantity2));
                updateTotalPrice();
            }
        });

        // Nút đặt hàng
        btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CheckoutActivity.class);
            startActivity(intent);
        });
    }

    // Hàm tính toán và định dạng lại tổng tiền
    private void updateTotalPrice() {
        int total = (quantity1 * PRICE_BURGER) + (quantity2 * PRICE_COCA);

        // Định dạng số thành kiểu tiền tệ Việt Nam: 145.000 đ
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        tvTotalAmount.setText(formatter.format(total) + " đ");
    }
}