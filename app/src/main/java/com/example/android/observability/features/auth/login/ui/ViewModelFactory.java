/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.observability.features.auth.login.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.observability.features.auth.login.domain.data_source.UserDataSource;
import com.example.android.observability.features.detail_shop.domain.data_source.ShopDataSource;
import com.example.android.observability.features.detail_shop.ui.DetailShopViewModel;

/**
 * Factory for ViewModels
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Object mDataSource;

    public ViewModelFactory(Object dataSource) {
        mDataSource = dataSource;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel((UserDataSource) mDataSource);
        } else if (modelClass.isAssignableFrom(DetailShopViewModel.class)) {
            return (T) new DetailShopViewModel((ShopDataSource) mDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
