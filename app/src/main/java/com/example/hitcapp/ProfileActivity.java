package com.example.hitcapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton ibBackProfile;
    private TextInputEditText etProfileName, etProfilePhone;
    private Button btnSaveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
    }

    private void initViews() {
        ibBackProfile = findViewById(R.id.ibBackProfile);
        etProfileName = findViewById(R.id.etProfileName);
        etProfilePhone = findViewById(R.id.etProfilePhone);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
    }

    private void setupListeners() {
        // Nút quay lại màn hình trước
        ibBackProfile.setOnClickListener(v -> finish());

        // Nút lưu thông tin
        btnSaveProfile.setOnClickListener(v -> {
            String name = etProfileName.getText().toString().trim();
            String phone = etProfilePhone.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(ProfileActivity.this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileActivity.this, "Cập nhật hồ sơ thành công!", Toast.LENGTH_SHORT).show();
                // Nơi gọi API để lưu dữ liệu lên server hoặc database nội bộ
            }
        });
    }
}