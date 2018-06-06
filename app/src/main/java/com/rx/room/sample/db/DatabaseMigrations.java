package com.rx.room.sample.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;
import android.util.Log;

public class DatabaseMigrations {

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.d("DatabaseMigration", "migrate: from 1 to 2");
            database.execSQL("CREATE TABLE  owner ( _id INTEGER PRIMARY KEY NOT NULL, owner_id INTEGER NOT NULL, name TEXT, phone TEXT, address TEXT )" );
            database.execSQL("alter table shop add column owner_id INTEGER NOT NULL DEFAULT 1");
        }
    };
}
