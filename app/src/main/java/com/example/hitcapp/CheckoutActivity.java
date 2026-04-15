package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CheckoutActivity extends AppCompatActivity {

    private ImageButton ibBackCheckout;
    private Button btnConfirmOrder;
    private RadioGroup rgPaymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
    }

    private void initViews() {
        ibBackCheckout = findViewById(R.id.ibBackCheckout);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);
        rgPaymentMethod = findViewById(R.id.rgPaymentMethod);
    }

    private void setupListeners() {
        ibBackCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xác định phương thức thanh toán được chọn
                int selectedId = rgPaymentMethod.getCheckedRadioButtonId();
                String paymentType = "Tiền mặt";
                if (selectedId == R.id.rbMomo) paymentType = "Ví MoMo";
                else if (selectedId == R.id.rbBank) paymentType = "Thẻ ngân hàng";

                Toast.makeText(CheckoutActivity.this, "Đặt hàng thành công bằng " + paymentType + "!", Toast.LENGTH_LONG).show();

                // Quay về Trang chủ và xóa lịch sử các trang trước đó
                Intent intent = new Intent(CheckoutActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}