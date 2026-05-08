package com.example.hitcapp;

import java.util.List;

public class OrderRequest {
    private int userId; // ĐÃ THÊM: ID của người dùng
    private String name;
    private String phone;
    private String address;
    private double totalAmount;
    private boolean isOnlinePayment;
    private List<CartItem> cartItems;

    // Cập nhật lại hàm khởi tạo (Constructor)
    public OrderRequest(int userId, String name, String phone, String address, double totalAmount, boolean isOnlinePayment, List<CartItem> cartItems) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.totalAmount = totalAmount;
        this.isOnlinePayment = isOnlinePayment;
        this.cartItems = cartItems;
    }

    // Getter và Setter cho userId
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public boolean isOnlinePayment() { return isOnlinePayment; }
    public void setOnlinePayment(boolean onlinePayment) { this.isOnlinePayment = onlinePayment; }

    public List<CartItem> getCartItems() { return cartItems; }
    public void setCartItems(List<CartItem> cartItems) { this.cartItems = cartItems; }
}