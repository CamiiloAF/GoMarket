package com.example.android.go_market.core;

import com.example.android.go_market.features.auth.login.domain.entities.UserModel;

abstract public class CurrentUser {
    static UserModel user;

    public static UserModel getUser() {
        return user;
    }

    public static void setUser(UserModel user) {
        CurrentUser.user = user;
    }
}
