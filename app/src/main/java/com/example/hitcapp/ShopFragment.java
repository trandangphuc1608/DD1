package com.example.hitcapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopFragment extends Fragment {

    private RecyclerView rvShopProducts;
    private TextInputEditText etSearchShop;
    private ProductAdapter adapter;
    private List<Product> productList;
    private List<Product> filteredList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // [cite: 375]
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        // Ánh xạ View
        rvShopProducts = view.findViewById(R.id.rvShopProducts);
        etSearchShop = view.findViewById(R.id.etSearchShop);

        // FIX 1: Thêm LayoutManager (Sử dụng GridLayoutManager 2 cột cho đẹp)
        rvShopProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Khởi tạo danh sách [cite: 376, 377]
        productList = new ArrayList<>();
        filteredList = new ArrayList<>();

        // Khởi tạo Adapter và gán vào RecyclerView [cite: 377, 378]
        if (getContext() != null) {
            adapter = new ProductAdapter(getContext(), filteredList);
            rvShopProducts.setAdapter(adapter);
        }

        // Gọi API lấy dữ liệu [cite: 378]
        fetchProductsFromApi();

        // Xử lý tìm kiếm [cite: 379, 380]
        setupSearch();

        return view;
    }

    private void setupSearch() {
        etSearchShop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void fetchProductsFromApi() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Product>> call = apiService.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                // FIX 2: Kiểm tra isAdded() để đảm bảo Fragment vẫn tồn tại khi kết quả trả về
                if (!isAdded() || getContext() == null) return;

                if (response.isSuccessful() && response.body() != null) {
                    productList.clear();
                    productList.addAll(response.body());

                    filteredList.clear();
                    filteredList.addAll(productList);

                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không thể lấy danh sách sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                if (!isAdded() || getContext() == null) return;
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(productList);
        } else {
            String query = text.toLowerCase().trim();
            for (Product item : productList) {
                if (item.getName().toLowerCase().contains(query)) {
                    filteredList.add(item);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}