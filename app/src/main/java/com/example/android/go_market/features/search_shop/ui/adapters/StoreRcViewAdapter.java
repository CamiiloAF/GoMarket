package com.example.android.go_market.features.search_shop.ui.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.go_market.features.detail_shop.domain.entities.ShopModel;
import com.example.android.go_market.features.detail_shop.ui.DetailShopActivity;
import com.example.android.persistence.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class StoreRcViewAdapter extends RecyclerView.Adapter<StoreRcViewAdapter.ViewHolder> {
    final Context context;

    List<ShopModel> shops = new ArrayList<>();

    public StoreRcViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ShopModel> shops) {
        if (shops == null) return;
        this.shops = shops;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StoreRcViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_shop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ShopModel shop = shops.get(position);

        holder.getTxtStoreName().setText(shop.getName());

        holder.getParentItemShop().setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailShopActivity.class);
            intent.putExtra("lat", shop.getLat());
            intent.putExtra("lng", shop.getLng());
            intent.putExtra("shopId", shop.getId());
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final View item;
        final TextView txtStoreName;
        final ViewGroup parentItemShop;

        public ViewHolder(@NonNull View item) {
            super(item);
            this.item = item;
            this.txtStoreName = item.findViewById(R.id.storeName);
            this.parentItemShop = item.findViewById(R.id.itemShopParent);
        }

        public TextView getTxtStoreName() {
            return txtStoreName;
        }

        public ViewGroup getParentItemShop() {
            return parentItemShop;
        }
    }
}
