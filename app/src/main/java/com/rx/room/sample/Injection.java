package com.rx.room.sample;

import android.content.Context;

import com.rx.room.sample.db.LocalDataSource;
import com.rx.room.sample.db.SampleDatabase;

public class Injection {

    public static LocalDataSource provideUserDataSource(Context context) {
        SampleDatabase database = SampleDatabase.getInstance(context);
        return new LocalDataSource(database.shopDao(), database.ownerDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        LocalDataSource dataSource = provideUserDataSource(context);
        return new ViewModelFactory(dataSource);
    }
}
