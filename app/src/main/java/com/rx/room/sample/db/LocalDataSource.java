package com.rx.room.sample.db;

import com.rx.room.sample.dao.OwnerDao;
import com.rx.room.sample.dao.ShopDao;

public class LocalDataSource {
    public ShopDao shopDao;
    public OwnerDao ownerDao;

    public LocalDataSource(ShopDao shopDao, OwnerDao ownerDao){
        this.ownerDao = ownerDao;
        this.shopDao = shopDao;
    }
}
