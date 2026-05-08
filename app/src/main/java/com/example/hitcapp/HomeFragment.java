package com.example.hitcapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView rvHomeProducts, rvCategories;
    private HomeProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;

    private List<Category> categoryList;
    private List<Product> allProductList;
    private List<Product> displayProductList;

    private TextView tvViewAllProducts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvHomeProducts = view.findViewById(R.id.rvHomeProducts);
        rvCategories = view.findViewById(R.id.rvCategories);
        tvViewAllProducts = view.findViewById(R.id.tvViewAllProducts);

        rvHomeProducts.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // Khởi tạo danh sách Sản phẩm
        allProductList = new ArrayList<>();
        displayProductList = new ArrayList<>();
        productAdapter = new HomeProductAdapter(getActivity(), displayProductList);
        rvHomeProducts.setAdapter(productAdapter);

        // Khởi tạo danh sách Danh mục
        categoryList = new ArrayList<>();
        // ĐÃ SỬA: Truyền đúng 3 tham số và xử lý đối tượng Category trong Lambda
        categoryAdapter = new CategoryAdapter(getActivity(), categoryList, category -> {
            if (category != null) {
                filterProducts(category.getId()); // Lấy ID từ đối tượng category
            }
        });
        rvCategories.setAdapter(categoryAdapter);

        tvViewAllProducts.setOnClickListener(v -> {
            if (getActivity() != null) {
                BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);
                if (bottomNav != null) {
                    bottomNav.setSelectedItemId(R.id.nav_shop);
                }
            }
        });

        fetchCategories();
        fetchFeaturedProducts();

        return view;
    }

    private void filterProducts(int categoryId) {
        displayProductList.clear();

        if (categoryId == -1) {
            displayProductList.addAll(allProductList);
        } else {
            for (Product p : allProductList) {
                if (p.getCategoryId() == categoryId) {
                    displayProductList.add(p);
                }
            }
        }

        productAdapter.notifyDataSetChanged();

        if (displayProductList.isEmpty() && categoryId != -1) {
            Toast.makeText(getActivity(), "Danh mục này chưa có sản phẩm nổi bật", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchCategories() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        apiService.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList.clear();
                    // Thêm mục "Tất cả"
                    categoryList.add(new Category(-1, "Tất cả", "https://cdn-icons-png.flaticon.com/512/1046/1046874.png", "#EEEEEE"));
                    categoryList.addAll(response.body());

                    // ĐÃ SỬA: Sử dụng notifyDataSetChanged() nếu adapter không có hàm updateData
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                if (getContext() != null) {
                    Toast.makeText(getActivity(), "Lỗi tải danh mục", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchFeaturedProducts() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        apiService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allProductList.clear();
                    allProductList.addAll(response.body());
                    displayProductList.clear();
                    displayProductList.addAll(allProductList);
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                if (getContext() != null) {
                    Toast.makeText(getActivity(), "Lỗi tải sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}