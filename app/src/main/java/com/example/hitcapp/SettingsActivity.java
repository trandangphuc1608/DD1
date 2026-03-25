package com.example.hitcapp;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton ibBackSettings;
    private Switch switchNotifications, switchDarkMode;
    private LinearLayout llChangePassword, llLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
    }

    private void initViews() {
        ibBackSettings = findViewById(R.id.ibBackSettings);
        switchNotifications = findViewById(R.id.switchNotifications);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        llChangePassword = findViewById(R.id.llChangePassword);
        llLanguage = findViewById(R.id.llLanguage);
    }

    private void setupListeners() {
        // Nút quay lại
        ibBackSettings.setOnClickListener(v -> finish());

        // Bật/tắt thông báo
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(SettingsActivity.this, "Đã bật thông báo", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SettingsActivity.this, "Đã tắt thông báo", Toast.LENGTH_SHORT).show();
            }
        });

        // Bật/tắt Dark Mode
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(SettingsActivity.this, "Đã bật Chế độ tối", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SettingsActivity.this, "Đã tắt Chế độ tối", Toast.LENGTH_SHORT).show();
            }
        });

        // Đổi mật khẩu
        llChangePassword.setOnClickListener(v ->
                Toast.makeText(SettingsActivity.this, "Mở màn hình Đổi mật khẩu", Toast.LENGTH_SHORT).show()
        );

        // Ngôn ngữ
        llLanguage.setOnClickListener(v ->
                Toast.makeText(SettingsActivity.this, "Chọn ngôn ngữ", Toast.LENGTH_SHORT).show()
        );
    }
}