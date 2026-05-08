package com.example.hitcapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItemList;
    private OnCartChangeListener onCartChangeListener;
    // Đồng bộ với RetrofitClient
    private final String BASE_URL = "http://phucapi.somee.com/";

    public interface OnCartChangeListener {
        void onQuantityChanged();
    }

    public CartAdapter(Context context, List<CartItem> cartItemList, OnCartChangeListener listener) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.onCartChangeListener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItemList.get(position);
        if (item == null || item.getProduct() == null) return;

        Product product = item.getProduct();
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        holder.tvCartName.setText(product.getName());
        holder.tvCartPrice.setText(formatter.format(product.getPrice()) + " đ");
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

        String imagePath = product.getImageUrl();
        String fullUrl = (imagePath != null && !imagePath.startsWith("http"))
                ? BASE_URL + (imagePath.startsWith("/") ? "" : "/") + imagePath : imagePath;

        Glide.with(context).load(fullUrl).placeholder(R.drawable.ic_launcher_foreground).into(holder.ivCartImage);

        holder.btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyItemChanged(position);
            if (onCartChangeListener != null) onCartChangeListener.onQuantityChanged();
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                notifyItemChanged(position);
            } else {
                cartItemList.remove(position);
                notifyDataSetChanged();
            }
            if (onCartChangeListener != null) onCartChangeListener.onQuantityChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList != null ? cartItemList.size() : 0;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCartImage;
        TextView tvCartName, tvCartPrice, tvQuantity;
        TextView btnMinus, btnPlus; // Đã đổi sang TextView cho khớp XML

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCartImage = itemView.findViewById(R.id.ivCartImage);
            tvCartName = itemView.findViewById(R.id.tvCartName);
            tvCartPrice = itemView.findViewById(R.id.tvCartPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
        }
    }
}