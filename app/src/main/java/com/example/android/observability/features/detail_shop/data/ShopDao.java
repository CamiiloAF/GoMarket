package com.example.android.observability.features.detail_shop.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.observability.features.detail_shop.domain.entities.ShopModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface ShopDao {
    @Query("SELECT * FROM shops")
    Flowable<List<ShopModel>> getShops();

    @Query("SELECT * FROM shops WHERE id=:id")
    Maybe<ShopModel> getShopById(int id);

    @Query("SELECT * FROM shops WHERE name LIKE '%' || :query || '%'")
    Flowable<List<ShopModel>> searchShops(String query);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertShop(ShopModel shopModel);

    @Query("DELETE FROM shops WHERE id=:id")
    Completable deleteShop(int id);
}

