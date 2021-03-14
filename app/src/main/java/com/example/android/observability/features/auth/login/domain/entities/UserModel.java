package com.example.android.observability.features.auth.login.domain.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "users")
public class UserModel implements Serializable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    private String mUserName;

    @NonNull
    @ColumnInfo(name = "password")
    private String mPassword;

    public UserModel(@NonNull String userName, @NonNull String password) {
        mUserName = userName;
        mPassword = password;
    }

    public UserModel() {
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }
}