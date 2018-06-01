package com.rx.room.sample.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.rx.room.sample.Adapter.ShopAdapter;
import com.rx.room.sample.Injection;
import com.rx.room.sample.R;
import com.rx.room.sample.ViewModelFactory;
import com.rx.room.sample.db.Shop;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private ViewModelFactory mViewModelFactory;
    private ShopViewModel mShopViewModel;
    private CompositeDisposable mDisposables = new CompositeDisposable();
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mShopViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ShopViewModel.class);

        mRecyclerView = findViewById(R.id.list);

        init();

        Button button = findViewById(R.id.button_loadList);
        Button button_owner = findViewById(R.id.button_owner);
        Button button_insert_shop = findViewById(R.id.button_insert_shop);
        Button button_insert_owner = findViewById(R.id.button_insert_owner);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDisposables.add(mShopViewModel.getShops()
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Shop>>() {
                    @Override
                    public void accept(List<Shop> shops) throws Exception {
                        Log.d("MainActivity", "accept: "+shops);
                        setShopAdapter(shops);
                    }
                }));
            }
        });

        button_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //TODO: load owner data
            }
        });

        button_insert_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mShopViewModel.insertShopData().subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposables.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("MainActivity", "onComplete: insert complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("MainActivity", "onError: insert shop :: ", e);
                    }
                });
            }
        });

        button_insert_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //TODO: insert owner data
            }
        });

    }

    public void init(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setShopAdapter(List<Shop> shops){
        ShopAdapter adapter = new ShopAdapter(getApplicationContext(), shops,
                new ShopAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Shop Item) {
                        Toast.makeText(getApplicationContext(), Item.getName(),
                                Toast.LENGTH_LONG).show();
                    }
                });

        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposables.clear();
    }
}
