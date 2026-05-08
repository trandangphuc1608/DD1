package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView rvCart;
    private TextView tvTotalAmount;
    private Button btnCheckout;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        rvCart = view.findViewById(R.id.rvCart);
        tvTotalAmount = view.findViewById(R.id.tvTotalAmount);
        btnCheckout = view.findViewById(R.id.btnCheckout);

        rvCart.setLayoutManager(new LinearLayoutManager(getActivity()));

        // ĐÃ SỬA: Lấy dữ liệu thực từ Bộ nhớ chung CartManager
        cartItemList = CartManager.getInstance().getCartList();

        cartAdapter = new CartAdapter(getActivity(), cartItemList, this::calculateTotal);
        rvCart.setAdapter(cartAdapter);

        // Tính tiền ban đầu
        calculateTotal();

        btnCheckout.setOnClickListener(v -> {
            if (cartItemList.isEmpty()) {
                Toast.makeText(getActivity(), "Giỏ hàng đang trống!", Toast.LENGTH_SHORT).show();
            } else {
                // Tính lại tổng tiền để gửi đi
                double total = 0;
                for (CartItem item : cartItemList) {
                    total += (item.getProduct().getPrice() * item.getQuantity());
                }

                Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                intent.putExtra("TOTAL_AMOUNT", total);
                startActivity(intent);
            }
        });

        return view;
    }

    // Refresh lại giỏ hàng mỗi khi mở lại trang
    @Override
    public void onResume() {
        super.onResume();
        if (cartAdapter != null) {
            cartAdapter.notifyDataSetChanged();
            calculateTotal();
        }
    }

    private void calculateTotal() {
        double total = 0;
        for (CartItem item : cartItemList) {
            total += (item.getProduct().getPrice() * item.getQuantity());
        }

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        tvTotalAmount.setText(formatter.format(total) + " đ");
    }
}