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

package com.example.android.observability;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.android.observability.core.persistence.User;
import com.example.android.observability.features.auth.login.ui.LoginViewModel;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.Flowable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link LoginViewModel}
 */
public class LoginViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private UserDataSource mDataSource;

    @Captor
    private ArgumentCaptor<User> mUserArgumentCaptor;

    private LoginViewModel mViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mViewModel = new LoginViewModel(mDataSource);
    }

    @Test
    public void getUserName_whenNoUserSaved() {
        // Given that the UserDataSource returns an empty list of users
        when(mDataSource.getUser()).thenReturn(Flowable.empty());

        //When getting the user name
        mViewModel.getUserByUserNameAndPassword()
                .test()
                // The user name is empty
                .assertNoValues();
    }

    @Test
    public void getUserName_whenUserSaved() {
        // Given that the UserDataSource returns a user
        User user = new User("user name");
        when(mDataSource.getUser()).thenReturn(Flowable.just(user));

        //When getting the user name
        mViewModel.getUserByUserNameAndPassword()
                .test()
                // The correct user name is emitted
                .assertValue("user name");
    }

    @Test
    public void updateUserName_updatesNameInDataSource() {
        // Given that a completable is returned when inserting a user
        when(mDataSource.insertOrUpdateUser(any())).thenReturn(Completable.complete());

        // When updating the user name
        mViewModel.updateUserName("new user name")
                .test()
                .assertComplete();

        // The user name is updated in the data source
        verify(mDataSource).insertOrUpdateUser(mUserArgumentCaptor.capture());
        assertThat(mUserArgumentCaptor.getValue().getUserName(), Matchers.is("new user name"));
    }

}
