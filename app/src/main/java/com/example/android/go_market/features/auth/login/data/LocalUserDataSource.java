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

package com.example.android.go_market.features.auth.login.data;

import com.example.android.go_market.features.auth.login.domain.data_source.UserDataSource;
import com.example.android.go_market.features.auth.login.domain.entities.UserModel;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Using the Room database as a data source.
 */
public class LocalUserDataSource implements UserDataSource {

    private final UserDao mUserDao;

    public LocalUserDataSource(UserDao userDao) {
        mUserDao = userDao;
    }

    @Override
    public Single<UserModel> getUserByUserNameAndPassword(String userName, String password) {
        return mUserDao.getUserByUserNameAndPassword(userName, password);
    }

    @Override
    public Completable insertOrUpdateUser(UserModel user) {
        return mUserDao.insertUser(user);
    }

}
