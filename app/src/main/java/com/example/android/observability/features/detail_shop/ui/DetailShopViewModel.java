package com.example.android.observability.features.detail_shop.ui;

import androidx.lifecycle.ViewModel;

import com.example.android.observability.core.CurrentUser;
import com.example.android.observability.features.detail_shop.domain.data_source.ShopDataSource;
import com.example.android.observability.features.detail_shop.domain.entities.ShopModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

public class DetailShopViewModel extends ViewModel {

    private final ShopDataSource mDataSource;

    public DetailShopViewModel(ShopDataSource mDataSource) {
        this.mDataSource = mDataSource;
    }

    public Flowable<List<ShopModel>> getShops() {
        return mDataSource.getShops();
    }

    public Flowable<List<ShopModel>> searchShops(String query) {
        return mDataSource.searchShops(query);
    }

    public Maybe<ShopModel> getShopById(int id) {
        return mDataSource.getShopById(id);
    }

    public Completable insertOrUpdateShop(ShopModel shopModel) {
        return mDataSource.insertOrUpdateShop(shopModel);
    }

    public Completable deleteShop(int id) {
        return mDataSource.deleteShop(id);
    }

    public boolean isMyShop(String shopUserName) {
        return CurrentUser.getUser().getUserName().equals(shopUserName);
    }
}
