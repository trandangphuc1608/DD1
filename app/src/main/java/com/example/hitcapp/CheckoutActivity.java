package com.example.hitcapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    private ImageButton ibBackCheckout;
    private TextInputEditText etCheckoutName, etCheckoutPhone, etCheckoutAddress;
    private RadioGroup rgPaymentMethod;
    private RadioButton rbOnline;
    private TextView tvCheckoutTotal;
    private Button btnConfirmOrder;
    private LinearLayout llOrderSummaryContainer;

    private double totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        initViews();
        loadOrderSummary();
        setupListeners();
    }

    private void initViews() {
        ibBackCheckout = findViewById(R.id.ibBackCheckout);
        etCheckoutName = findViewById(R.id.etCheckoutName);
        etCheckoutPhone = findViewById(R.id.etCheckoutPhone);
        etCheckoutAddress = findViewById(R.id.etCheckoutAddress);
        rgPaymentMethod = findViewById(R.id.rgPaymentMethod);
        rbOnline = findViewById(R.id.rbOnline);
        tvCheckoutTotal = findViewById(R.id.tvCheckoutTotal);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);
        llOrderSummaryContainer = findViewById(R.id.llOrderSummaryContainer);
    }

    private void loadOrderSummary() {
        List<CartItem> cartItems = CartManager.getInstance().getCartList();
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        llOrderSummaryContainer.removeAllViews();
        double subTotal = 0;

        if (cartItems != null && !cartItems.isEmpty()) {
            for (CartItem item : cartItems) {
                if (item.getProduct() == null) continue;

                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setPadding(0, 8, 0, 8);

                TextView tvName = new TextView(this);
                tvName.setText(item.getQuantity() + "x " + item.getProduct().getName());
                tvName.setTextColor(Color.parseColor("#333333"));
                tvName.setTextSize(16);
                LinearLayout.LayoutParams paramsName = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                tvName.setLayoutParams(paramsName);

                TextView tvPrice = new TextView(this);
                double itemTotal = item.getProduct().getPrice() * item.getQuantity();
                tvPrice.setText(formatter.format(itemTotal) + " đ");
                tvPrice.setTextColor(Color.parseColor("#333333"));
                tvPrice.setTextSize(16);
                tvPrice.setTypeface(null, Typeface.BOLD);

                row.addView(tvName);
                row.addView(tvPrice);
                llOrderSummaryContainer.addView(row);

                subTotal += itemTotal;
            }
        }

        totalAmount = subTotal;
        tvCheckoutTotal.setText(formatter.format(totalAmount) + " đ");
    }

    private void setupListeners() {
        ibBackCheckout.setOnClickListener(v -> finish());

        btnConfirmOrder.setOnClickListener(v -> {
            String name = etCheckoutName.getText().toString().trim();
            String phone = etCheckoutPhone.getText().toString().trim();
            String address = etCheckoutAddress.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin giao hàng!", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isOnlinePayment = rbOnline.isChecked();

            // Gọi API lưu đơn hàng trước khi xử lý thanh toán
            submitOrderToDatabase(name, phone, address, isOnlinePayment);
        });
    }

    private void submitOrderToDatabase(String name, String phone, String address, boolean isOnlinePayment) {
        btnConfirmOrder.setEnabled(false);

        int currentUserId = 4;

        // Truyền currentUserId vào đầu tiên
        OrderRequest orderRequest = new OrderRequest(currentUserId, name, phone, address, totalAmount, isOnlinePayment, CartManager.getInstance().getCartList());

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        apiService.createOrder(orderRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                btnConfirmOrder.setEnabled(true);
                if (response.isSuccessful()) {
                    processPayment(isOnlinePayment);
                } else {
                    try {
                        String errorDetails = response.errorBody() != null ? response.errorBody().string() : "Lỗi không xác định";
                        android.util.Log.e("LOI_API_DON_HANG", "Mã lỗi: " + response.code() + " - Chi tiết: " + errorDetails);
                        Toast.makeText(CheckoutActivity.this, "Lỗi Server: " + response.code(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(CheckoutActivity.this, "Lỗi dữ liệu từ Server!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                btnConfirmOrder.setEnabled(true);
                Toast.makeText(CheckoutActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void processPayment(boolean isOnlinePayment) {
        if (isOnlinePayment) {
            Toast.makeText(this, "Đang khởi tạo thanh toán VNPAY...", Toast.LENGTH_SHORT).show();
            String realVnpayUrl = VnPayHelper.generatePayUrl((long) totalAmount);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(realVnpayUrl));
            startActivity(browserIntent);
        } else {
            Toast.makeText(this, "Đặt hàng thành công! Vui lòng chú ý điện thoại.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CheckoutActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        CartManager.getInstance().getCartList().clear();
    }
}