package com.rx.room.sample.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@android.arch.persistence.room.Database(entities = {Shop.class, Owner.class}, version = 2)
public abstract class SampleDatabase extends RoomDatabase {
    private static volatile SampleDatabase INSTANCE;

    public abstract ShopDao shopDao();
    public abstract OwnerDao ownerDao();

    public static SampleDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SampleDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), SampleDatabase.class, "roomsample.db")
                            .addCallback(mOnCreateDbCallback)
                            .addMigrations(DatabaseMigrations.MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback mOnCreateDbCallback = new Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

        }
    };

}
