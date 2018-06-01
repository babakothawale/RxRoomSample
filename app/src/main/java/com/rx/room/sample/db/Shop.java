package com.rx.room.sample.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "shop")
public class Shop {
    @PrimaryKey(autoGenerate = true)
    long id;
    @ColumnInfo(name = "shop_name")
    String name;
    @ColumnInfo(name = "shop_address")
    String address;

    public Shop(){}

    @Ignore
    public Shop(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    @Ignore
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Ignore
    public String getName() {
        return name;
    }

    @Ignore
    public String getAddress() {
        return address;
    }
}
