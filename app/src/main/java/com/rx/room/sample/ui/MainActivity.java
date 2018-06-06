package com.rx.room.sample.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rx.room.sample.Adapter.OwnerAdapter;
import com.rx.room.sample.Adapter.ShopAdapter;
import com.rx.room.sample.Injection;
import com.rx.room.sample.R;
import com.rx.room.sample.ViewModelFactory;
import com.rx.room.sample.db.Owner;
import com.rx.room.sample.db.Shop;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewModelFactory mViewModelFactory;
    private ShopViewModel mShopViewModel;
    private OwnerViewModel mOwnerViewModel;
    private CompositeDisposable mDisposables = new CompositeDisposable();
    private RecyclerView mRecyclerView;
    private OwnerAdapter mOwnerAdapter;
    private OwnerAdapter.OnItemClickListener mOwnerOnItemClickListener = new OwnerAdapter.OnItemClickListener() {
        @Override
        public void onClick(Owner item) {
            Toast.makeText(MainActivity.this, "Owner selected : " + item.getId() + " : " + item.getName(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mShopViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ShopViewModel.class);
        mOwnerViewModel = ViewModelProviders.of(this, mViewModelFactory).get(OwnerViewModel.class);

        mRecyclerView = findViewById(R.id.list);

        init();

        Button button = findViewById(R.id.button_loadList);
        Button button_owner = findViewById(R.id.button_owner);
        Button button_insert_shop = findViewById(R.id.button_insert_shop);
        Button button_insert_owner = findViewById(R.id.button_insert_owner);

        button.setOnClickListener(this);
        button_owner.setOnClickListener(this);
        button_insert_owner.setOnClickListener(this);
        button_insert_shop.setOnClickListener(this);
    }

    public void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOwnerAdapter = new OwnerAdapter(new ArrayList<>(), mOwnerOnItemClickListener);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_delete_owners:
                //TODO: delete owners
                break;
            case R.id.button_delete_shops:
                mShopViewModel.deleteAllShops().subscribe();
                break;
            case R.id.button_insert_owner:
                loadOwners();
                break;
            case R.id.button_loadList:
                mDisposables.add(mShopViewModel.getShops()
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(shops -> {
                            Log.d("MainActivity", "accept: " + shops);
                            setShopAdapter(shops);
                        },Throwable::printStackTrace));
                break;
            case R.id.button_owner:
                mDisposables.add(mOwnerViewModel.getOwners().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Owner>>() {
                    @Override
                    public void accept(List<Owner> owners) throws Exception {
                        setOwnerAdapter(owners);
                    }
                }, Throwable::printStackTrace));
                break;
            case R.id.button_insert_shop:
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

                break;
        }
    }

    private void loadOwners() {
        mOwnerViewModel.insertOwnerData().subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposables.add(d);
            }

            @Override
            public void onComplete() {
                Log.d("MainActivity", "onComplete: insert owner complete");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("MainActivity", "onError: insert owner :: ", e);
            }
        });
    }

    private void setShopAdapter(List<Shop> shops) {
        ShopAdapter adapter = new ShopAdapter(getApplicationContext(), shops,
                new ShopAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Shop shop) {
                        Toast.makeText(getApplicationContext(), shop.getName(),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onDeleteClick(Shop shop) {
                        mShopViewModel.deleteShop(shop).subscribe();
                    }

                    @Override
                    public void onEditClick(Shop shop) {
                        final View view = getLayoutInflater().inflate(R.layout.layout_edit_shop, null);
                        EditText etName = view.findViewById(R.id.edittext_name);
                        etName.setText(shop.getName());
                        EditText etAddress = view.findViewById(R.id.edittext_address);
                        etAddress.setText(shop.getAddress());
                        EditText etOwner = view.findViewById(R.id.edittext_owner);
                        etOwner.setText(String.valueOf(shop.getOwnerId()));

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        shop.setName(String.valueOf(etName.getText()));
                                        shop.setAddress(String.valueOf(etAddress.getText()));
                                        mShopViewModel.updateShop(shop).subscribe();
                                    }
                                })
                                .setView(view);
                        builder.show();
                    }
                });

        mRecyclerView.setAdapter(adapter);
    }

    private void setOwnerAdapter(List<Owner> owners) {
        mRecyclerView.setAdapter(mOwnerAdapter);
        mOwnerAdapter.setList(owners);
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
