package com.rx.room.sample.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.rx.room.sample.db.LocalDataSource;
import com.rx.room.sample.dao.OwnerDao;
import com.rx.room.sample.entity.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class OwnerViewModel extends ViewModel {
    private OwnerDao mOwnerDao;
    
    public OwnerViewModel(LocalDataSource dataSource){
        mOwnerDao = dataSource.ownerDao;
    }

    public Flowable<List<Owner>> getOwners() {
        return mOwnerDao.getOwners();
    }

    Completable insertOwner() {
        return Completable.fromCallable((Callable<Long>) () -> {
            Owner Owner = new Owner("Owner1", "address1", "1234567890");
            return mOwnerDao.insertOwner(Owner);
        }).subscribeOn(Schedulers.io());
    }

    public Completable insertOwnerData() {
        return Completable.fromCallable((Callable<Void>) () -> {
            List<Owner> Owners = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Owners.add(new Owner("Owner " + i, "Address" + i, i+"098765432"));
            }
            mOwnerDao.insertOwners(Owners);
            return null;
        }).subscribeOn(Schedulers.io());

    }

    Completable deleteOwner(Owner Owner) {
        return Completable.fromAction(() -> mOwnerDao.delete(Owner.getId())).subscribeOn(Schedulers.io());
    }

    Completable updateOwner(Owner Owner) {
        return Completable.fromAction(()->mOwnerDao.updateOwner(Owner)).subscribeOn(Schedulers.io());
    }

    Completable deleteAllOwners(){
        return Completable.fromAction(()->mOwnerDao.deleteAll()).subscribeOn(Schedulers.io());
    }
}
