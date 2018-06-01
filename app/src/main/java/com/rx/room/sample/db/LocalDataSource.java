package com.rx.room.sample.db;

public class LocalDataSource {
    public ShopDao shopDao;

    public LocalDataSource(ShopDao shopDao){
        this.shopDao = shopDao;
    }
}
