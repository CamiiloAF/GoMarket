package com.example.android.go_market.features.detail_shop.data;

import com.example.android.go_market.features.detail_shop.domain.data_source.ShopDataSource;
import com.example.android.go_market.features.detail_shop.domain.entities.ShopModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

public class LocalShopDataSource implements ShopDataSource {
    private final ShopDao mShopDao;

    public LocalShopDataSource(ShopDao shopDao) {
        mShopDao = shopDao;
    }

    @Override
    public Flowable<List<ShopModel>> getShops() {
        return mShopDao.getShops();
    }

    @Override
    public Maybe<ShopModel> getShopById(int id) {
        return mShopDao.getShopById(id);
    }

    @Override
    public Flowable<List<ShopModel>> searchShops(String query) {
        return mShopDao.searchShops(query);
    }

    @Override
    public Completable insertOrUpdateShop(ShopModel shopModel) {
        return mShopDao.insertShop(shopModel);
    }

    @Override
    public Completable deleteShop(int id) {
        return mShopDao.deleteShop(id);
    }
}
