package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private SessionManager sessionManager;
    private TextView tvUserName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sessionManager = new SessionManager(getActivity());
        tvUserName = view.findViewById(R.id.tvUserName);

        // Hiển thị tên người dùng từ Session
        tvUserName.setText(sessionManager.getUserName());

        LinearLayout llOrderHistory = view.findViewById(R.id.llOrderHistory);
        Button btnLogout = view.findViewById(R.id.btnLogout);

        llOrderHistory.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), OrderHistoryActivity.class));
        });

        // Xử lý ĐĂNG XUẤT
        btnLogout.setOnClickListener(v -> {
            // 1. Xóa session
            sessionManager.logoutUser();

            // 2. Xóa giỏ hàng tạm thời
            CartManager.getInstance().getCartList().clear();

            Toast.makeText(getActivity(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();

            // 3. Quay về màn hình Login và xóa lịch sử chuyển trang
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }
}