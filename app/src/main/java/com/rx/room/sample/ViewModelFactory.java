package com.rx.room.sample;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.rx.room.sample.db.LocalDataSource;
import com.rx.room.sample.ui.ShopViewModel;

/**
 * Factory for ViewModels
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final LocalDataSource mDataSource;

    public ViewModelFactory(LocalDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ShopViewModel.class)) {
            return (T) new ShopViewModel(mDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}