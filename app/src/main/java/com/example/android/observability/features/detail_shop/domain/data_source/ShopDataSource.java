package com.example.android.observability.features.detail_shop.domain.data_source;

import com.example.android.observability.features.detail_shop.domain.entities.ShopModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface ShopDataSource {
    Flowable<List<ShopModel>> getShops();

    Maybe<ShopModel> getShopById(int id);

    Flowable<List<ShopModel>> searchShops(String query);

    Completable insertOrUpdateShop(ShopModel shopModel);

    Completable deleteShop(int id);
}


