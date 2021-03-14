package com.example.android.go_market;

import android.content.Context;

import com.example.android.go_market.core.persistence.UsersDatabase;
import com.example.android.go_market.features.auth.login.data.LocalUserDataSource;
import com.example.android.go_market.features.auth.login.domain.data_source.UserDataSource;
import com.example.android.go_market.features.auth.login.ui.ViewModelFactory;
import com.example.android.go_market.features.detail_shop.data.LocalShopDataSource;
import com.example.android.go_market.features.detail_shop.domain.data_source.ShopDataSource;

/**
 * Enables injection of data sources.
 */
public class Injection {

    public static UserDataSource provideUserDataSource(Context context) {
        UsersDatabase database = UsersDatabase.getInstance(context);
        return new LocalUserDataSource(database.userDao());
    }

    public static ShopDataSource provideShopDataSource(Context context) {
        UsersDatabase database = UsersDatabase.getInstance(context);
        return new LocalShopDataSource(database.shopDao());
    }

    public static ViewModelFactory provideViewModelFactory(Object dataSource) {
        return new ViewModelFactory(dataSource);
    }

}
