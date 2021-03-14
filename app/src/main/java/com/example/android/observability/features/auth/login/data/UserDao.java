package com.example.android.observability.features.auth.login.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.observability.features.auth.login.domain.entities.UserModel;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users as ru WHERE ru.username=:userName AND ru.password=:password")
    Single<UserModel> getUserByUserNameAndPassword(String userName, String password);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertUser(UserModel user);
}
