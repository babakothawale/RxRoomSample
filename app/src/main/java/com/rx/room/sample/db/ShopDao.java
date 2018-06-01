package com.rx.room.sample.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ShopDao {

    @Query("SELECT * FROM shop")
    Flowable<List<Shop>> getShops();

    @Insert
    long insertShop(Shop shop);

    @Insert
    void insertShops(List<Shop> shops);

    @Delete
    int deleteShop(Shop shop);

    @Update
    int updateShop(Shop shop);
}
