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

        // Ánh xạ 2 thẻ sản phẩm
        CardView cvHomeProduct1 = view.findViewById(R.id.cvHomeProduct1);
        CardView cvHomeProduct2 = view.findViewById(R.id.cvHomeProduct2);

        // Tạo sự kiện click mở trang Chi tiết
        View.OnClickListener openDetail = v -> {
            Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
            startActivity(intent);
        };

        // Gắn sự kiện cho các thẻ (nếu thẻ đó tồn tại)
        if (cvHomeProduct1 != null) cvHomeProduct1.setOnClickListener(openDetail);
        if (cvHomeProduct2 != null) cvHomeProduct2.setOnClickListener(openDetail);

        return view;
    }
}