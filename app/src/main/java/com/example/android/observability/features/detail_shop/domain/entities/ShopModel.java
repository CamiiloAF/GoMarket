package com.example.android.observability.features.detail_shop.domain.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "shops")
public class ShopModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @NonNull
    private String name;

    @NonNull
    private String nit;

    @NonNull
    private String description;

    @NonNull
    private String hoursOfOperation;

    private double lat;

    private double lng;

    private String userName;

    public ShopModel(Integer id, @NonNull String name, @NonNull String nit, @NonNull String description, @NonNull String hoursOfOperation, double lat, double lng, String userName) {
        this.id = id;
        this.name = name;
        this.nit = nit;
        this.description = description;
        this.hoursOfOperation = hoursOfOperation;
        this.lat = lat;
        this.lng = lng;
        this.userName = userName;
    }

    public ShopModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getNit() {
        return nit;
    }

    public void setNit(@NonNull String nit) {
        this.nit = nit;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getHoursOfOperation() {
        return hoursOfOperation;
    }

    public void setHoursOfOperation(@NonNull String hoursOfOperation) {
        this.hoursOfOperation = hoursOfOperation;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }


    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}