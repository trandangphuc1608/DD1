package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class ShopFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        // Ánh xạ 4 thẻ sản phẩm
        CardView cvShopProduct1 = view.findViewById(R.id.cvShopProduct1);
        CardView cvShopProduct2 = view.findViewById(R.id.cvShopProduct2);
        CardView cvShopProduct3 = view.findViewById(R.id.cvShopProduct3);
        CardView cvShopProduct4 = view.findViewById(R.id.cvShopProduct4);

        // Tạo sự kiện click mở trang Chi tiết
        View.OnClickListener openDetail = v -> {
            Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
            startActivity(intent);
        };

        // Gắn sự kiện
        if (cvShopProduct1 != null) cvShopProduct1.setOnClickListener(openDetail);
        if (cvShopProduct2 != null) cvShopProduct2.setOnClickListener(openDetail);
        if (cvShopProduct3 != null) cvShopProduct3.setOnClickListener(openDetail);
        if (cvShopProduct4 != null) cvShopProduct4.setOnClickListener(openDetail);

        return view;
    }
}