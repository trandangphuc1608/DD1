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

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        CardView cvHomeProduct1 = view.findViewById(R.id.cvHomeProduct1);
        CardView cvHomeProduct2 = view.findViewById(R.id.cvHomeProduct2);

        // Xử lý Click cho món thứ nhất
        if (cvHomeProduct1 != null) {
            cvHomeProduct1.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra("pName", "Combo Burger Bò");
                intent.putExtra("pPrice", "125.000 đ");
                intent.putExtra("pImage", R.drawable.img_burger); // Tên file trong drawable của bạn
                intent.putExtra("pDesc", "Combo tiết kiệm bao gồm 1 Burger bò phô mai, 1 khoai tây chiên và 1 ly Coca mát lạnh.");
                startActivity(intent);
            });
        }

        // Xử lý Click cho món thứ hai
        if (cvHomeProduct2 != null) {
            cvHomeProduct2.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra("pName", "Gà rán phần lớn");
                intent.putExtra("pPrice", "180.000 đ");
                intent.putExtra("pImage", R.drawable.img_ga_ran);
                intent.putExtra("pDesc", "Phần gà rán đặc biệt với 6 miếng gà siêu giòn, tẩm ướp gia vị độc quyền.");
                startActivity(intent);
            });
        }

        return view;
    }
}