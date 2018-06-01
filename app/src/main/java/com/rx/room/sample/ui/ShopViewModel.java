package com.rx.room.sample.ui;

import android.arch.lifecycle.ViewModel;

import com.rx.room.sample.db.LocalDataSource;
import com.rx.room.sample.db.Shop;
import com.rx.room.sample.db.ShopDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class ShopViewModel extends ViewModel {

    private ShopDao mShopDao;

    public ShopViewModel(LocalDataSource localDataSource){
        mShopDao = localDataSource.shopDao;
    }

    Flowable<List<Shop>> getShops(){
        return mShopDao.getShops();
    }

    Completable insertShop() {
        return Completable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                Shop shop = new Shop("shop1", "address1");
                return mShopDao.insertShop(shop);
            }
        }).subscribeOn(Schedulers.io());
    }

    public Completable insertShopData() {
        return Completable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                List<Shop> shops = new ArrayList<>();
                for (int i = 1; i <= 10; i++) {
                    shops.add(new Shop("Shop " + i,"Address" + i));
                }
                mShopDao.insertShops(shops);
                return null;
            }
        }).subscribeOn(Schedulers.io());

    }

    int deleteShop(Shop shop){
        return mShopDao.deleteShop(shop);
    }

    int updateShop(Shop shop){
        return mShopDao.updateShop(shop);
    }

}
