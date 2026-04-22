package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class ShopFragment extends Fragment {

    private TextInputEditText etSearchShop;
    private CardView cvShopProduct1, cvShopProduct2, cvShopProduct3, cvShopProduct4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        etSearchShop = view.findViewById(R.id.etSearchShop);
        cvShopProduct1 = view.findViewById(R.id.cvShopProduct1);
        cvShopProduct2 = view.findViewById(R.id.cvShopProduct2);
        cvShopProduct3 = view.findViewById(R.id.cvShopProduct3);
        cvShopProduct4 = view.findViewById(R.id.cvShopProduct4);

        // Gán sự kiện click cho từng món
        if (cvShopProduct1 != null) {
            cvShopProduct1.setOnClickListener(v -> goToDetail("Burger Bò Phô Mai", "65.000 đ", R.drawable.img_shop_burger, "Burger bò tươi nướng kèm phô mai tan chảy và rau xanh."));
        }
        if (cvShopProduct2 != null) {
            cvShopProduct2.setOnClickListener(v -> goToDetail("Gà Rán Giòn", "45.000 đ", R.drawable.img_shop_ga_ran, "Gà rán giòn rụm bên ngoài, mọng nước bên trong."));
        }
        if (cvShopProduct3 != null) {
            cvShopProduct3.setOnClickListener(v -> goToDetail("Khoai Tây Chiên", "25.000 đ", R.drawable.img_shop_khoai_tay, "Khoai tây cắt sợi chiên vàng rắc muối tiêu hảo hạng."));
        }
        if (cvShopProduct4 != null) {
            cvShopProduct4.setOnClickListener(v -> goToDetail("Coca Cola Lớn", "15.000 đ", R.drawable.img_shop_coca, "Nước giải khát Coca Cola lạnh sảng khoái ngày hè."));
        }

        // Xử lý tìm kiếm
        etSearchShop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().toLowerCase().trim();
                filterProduct(cvShopProduct1, "burger bò phô mai", keyword);
                filterProduct(cvShopProduct2, "gà rán giòn", keyword);
                filterProduct(cvShopProduct3, "khoai tây chiên", keyword);
                filterProduct(cvShopProduct4, "coca cola lớn", keyword);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    // Hàm phụ để chuyển trang kèm dữ liệu
    private void goToDetail(String name, String price, int image, String desc) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra("pName", name);
        intent.putExtra("pPrice", price);
        intent.putExtra("pImage", image);
        intent.putExtra("pDesc", desc);
        startActivity(intent);
    }

    private void filterProduct(CardView cardView, String productName, String keyword) {
        if (cardView != null) {
            if (productName.contains(keyword)) {
                cardView.setVisibility(View.VISIBLE);
            } else {
                cardView.setVisibility(View.GONE);
            }
        }
    }
}