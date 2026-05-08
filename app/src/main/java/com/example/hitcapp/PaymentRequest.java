package com.example.hitcapp;

public class PaymentRequest {
    private double amount;
    // Bạn có thể thêm các biến khác (như orderId, description...) nếu Swagger của bạn yêu cầu

    public PaymentRequest(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}