package com.example.hitcapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;
    private final String BASE_URL = "http://phucapi.somee.com/";

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        if (product == null) return;

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        if (holder.tvProductName != null) holder.tvProductName.setText(product.getName());
        if (holder.tvProductPrice != null) holder.tvProductPrice.setText(formatter.format(product.getPrice()) + " đ");

        // Đã sửa thành getImageUrl()
        String imagePath = product.getImageUrl();
        String tempUrl = imagePath;
        if (imagePath != null && !imagePath.startsWith("http")) {
            tempUrl = BASE_URL + (imagePath.startsWith("/") ? "" : "/") + imagePath;
        }
        final String finalImageUrl = tempUrl;

        if (holder.ivProductImage != null) {
            Glide.with(context)
                    .load(finalImageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.ivProductImage);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("pId", product.getId());
            intent.putExtra("pName", product.getName());
            intent.putExtra("pPrice", formatter.format(product.getPrice()) + " đ");
            intent.putExtra("pImageUrl", finalImageUrl);
            intent.putExtra("pCategoryId", product.getCategoryId());
            intent.putExtra("pDesc", product.getDescription());
            context.startActivity(intent);
        });

        if (holder.btnAddCart != null) {
            holder.btnAddCart.setOnClickListener(v -> {
                CartManager.getInstance().addToCart(product, 1);
                Toast.makeText(context, "Đã thêm " + product.getName() + " vào giỏ", Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView tvProductName, tvProductPrice;
        Button btnAddCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            btnAddCart = itemView.findViewById(R.id.btnAddCart);
        }
    }
}