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

package com.example.android.go_market.features.auth.login.domain.data_source;

import com.example.android.go_market.features.auth.login.domain.entities.UserModel;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Access point for managing user data.
 */
public interface UserDataSource {

    /**
     * Gets the user from the data source.
     *
     * @return the user from the data source.
     */
    Single<UserModel> getUserByUserNameAndPassword(String userName, String password);

    /**
     * Inserts the user into the data source, or, if this is an existing user, updates it.
     *
     * @param user the user to be inserted or updated.
     */
    Completable insertOrUpdateUser(UserModel user);

}
