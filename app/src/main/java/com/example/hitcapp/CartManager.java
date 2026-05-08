package com.example.hitcapp;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartList;

    private CartManager() {
        cartList = new ArrayList<>();
    }

    // Lấy bộ nhớ chung
    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public List<CartItem> getCartList() {
        return cartList;
    }

    // Hàm thêm vào giỏ (Nếu trùng món sẽ tự động cộng dồn số lượng)
    public void addToCart(Product product, int quantity) {
        for (CartItem item : cartList) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cartList.add(new CartItem(product, quantity));
    }
}