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

package com.example.android.go_market.features.auth.login.ui;

import androidx.lifecycle.ViewModel;

import com.example.android.go_market.features.auth.login.domain.data_source.UserDataSource;
import com.example.android.go_market.features.auth.login.domain.entities.UserModel;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * View Model for the {@link LoginActivity}
 */
public class LoginViewModel extends ViewModel {

    private final UserDataSource mDataSource;

    private UserModel mUser;

    public LoginViewModel(UserDataSource dataSource) {
        mDataSource = dataSource;
    }

    /**
     * Get the user name of the user.
     *
     * @return a {@link Flowable} that will emit every time the user name has been updated.
     */
    public Single<UserModel> getUserByUserNameAndPassword(String userName, String password) {
        Single<UserModel> userByUserNameAndPassword = mDataSource.getUserByUserNameAndPassword(userName, password);
        return userByUserNameAndPassword;

    }

    /**
     * Update the user name.
     *
     * @param userName the new user name
     * @return a {@link Completable} that completes when the user name is updated
     */
    public Completable registerUser(final String userName, String password) {
        // if there's no user, create a new user.
        // if we already have a user, then, since the user object is immutable,
        // create a new user, with the id of the previous user and the updated user name.
        mUser = new UserModel(userName, password);
        return mDataSource.insertOrUpdateUser(mUser);
    }
}
