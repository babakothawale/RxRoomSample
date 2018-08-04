package com.rx.room.sample.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.rx.room.sample.entity.Owner;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface OwnerDao {

    @Query("select * from owner")
    public Flowable<List<Owner>> getOwners();

    @Query("select * from owner where owner_id = :ownerId")
    public Owner getOwner(long ownerId);

    @Query("Delete from owner where owner_id = :ownerId")
    public int delete(long ownerId);


    @Insert
    void insertOwners(List<Owner> Owners);

    @Insert
    Long insertOwner(Owner owner);

    @Update
    void updateOwner(Owner owner);

    @Query("delete from owner")
    void deleteAll();
}
