package com.rx.room.sample.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.rx.room.sample.db.LocalDataSource;
import com.rx.room.sample.entity.Shop;
import com.rx.room.sample.dao.ShopDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class ShopViewModel extends ViewModel {

    private ShopDao mShopDao;

    public ShopViewModel(LocalDataSource localDataSource) {
        mShopDao = localDataSource.shopDao;
    }

    public Flowable<List<Shop>> getShops() {
        return mShopDao.getShops();
    }

    Completable insertShop() {
        return Completable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                Shop shop = new Shop("shop1", "address1", 1);
                return mShopDao.insertShop(shop);
            }
        }).subscribeOn(Schedulers.io());
    }

    public Completable insertShopData() {
        return Completable.fromCallable((Callable<Void>) () -> {
            List<Shop> shops = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                shops.add(new Shop("Shop " + i, "Address" + i, i));
            }
            mShopDao.insertShops(shops);
            return null;
        }).subscribeOn(Schedulers.io());

    }

    public Completable deleteShop(Shop shop) {
        return Completable.fromAction(() -> mShopDao.deleteShop(shop)).subscribeOn(Schedulers.io());
    }

    public Completable updateShop(Shop shop) {
        return Completable.fromAction(()->mShopDao.updateShop(shop)).subscribeOn(Schedulers.io());
    }

    public Completable deleteAllShops(){
        return Completable.fromAction(()->mShopDao.deleteAll()).subscribeOn(Schedulers.io());
    }

}
