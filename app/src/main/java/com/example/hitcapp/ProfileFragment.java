package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        LinearLayout llOrderHistory = view.findViewById(R.id.llOrderHistory);
        LinearLayout llAddress = view.findViewById(R.id.llAddress);
        LinearLayout llHelp = view.findViewById(R.id.llHelp);
        Button btnLogout = view.findViewById(R.id.btnLogout);

        llOrderHistory.setOnClickListener(v ->
                Toast.makeText(getActivity(), "Tính năng Lịch sử đơn hàng", Toast.LENGTH_SHORT).show()
        );

        llAddress.setOnClickListener(v ->
                Toast.makeText(getActivity(), "Tính năng Địa chỉ giao hàng", Toast.LENGTH_SHORT).show()
        );

        llHelp.setOnClickListener(v ->
                Toast.makeText(getActivity(), "Tính năng Trung tâm trợ giúp", Toast.LENGTH_SHORT).show()
        );

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        return view;
    }
}