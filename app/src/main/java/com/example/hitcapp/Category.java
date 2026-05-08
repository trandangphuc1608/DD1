package com.example.hitcapp;

public class Category {
    private int id;
    private String name;
    private String iconUrl;
    private String colorCode;

    // Constructor mặc định cho API (Gson)
    public Category() {}

    // Constructor để tự tạo mục "Tất cả" trong code
    public Category(int id, String name, String iconUrl, String colorCode) {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
        this.colorCode = colorCode;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getIconUrl() { return iconUrl; }
    public String getColorCode() { return colorCode; }
}