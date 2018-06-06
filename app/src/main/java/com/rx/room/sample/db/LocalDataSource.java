package com.rx.room.sample.db;

public class LocalDataSource {
    public ShopDao shopDao;
    public OwnerDao ownerDao;

    public LocalDataSource(ShopDao shopDao, OwnerDao ownerDao){
        this.ownerDao = ownerDao;
        this.shopDao = shopDao;
    }
}
