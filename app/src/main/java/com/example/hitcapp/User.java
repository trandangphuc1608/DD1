package com.example.hitcapp;

public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private String token; // Dùng để lưu JWT token nếu Backend của bạn có yêu cầu bảo mật token

    // Constructor rỗng (Bắt buộc phải có để Retrofit tự động map dữ liệu)
    public User() {
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }
}