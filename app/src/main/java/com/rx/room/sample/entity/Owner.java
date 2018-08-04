package com.rx.room.sample.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "owner")
public class Owner {

    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    long id;
    @ColumnInfo(name = "owner_id")
    long ownerId;
    String name;
    String phone;
    String address;

    public Owner(String name, String address, String phone) {
        this.name = name;
        this.address=address;
        this.phone=phone;
    }



    public long getId() {
        return id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
