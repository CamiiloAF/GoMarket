package com.example.android.go_market.features.search_shop.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.go_market.Injection;
import com.example.android.go_market.features.auth.login.ui.ViewModelFactory;
import com.example.android.go_market.features.detail_shop.ui.DetailShopViewModel;
import com.example.android.go_market.features.search_shop.ui.adapters.StoreRcViewAdapter;
import com.example.android.persistence.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.android.go_market.Injection.provideShopDataSource;

public class SearchActivity extends AppCompatActivity {
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private SearchView searchView;
    private StoreRcViewAdapter adapter;
    private DetailShopViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializeViewModel();

        setUpSearchView();

        setUpRcView();
    }

    private void initializeViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(provideShopDataSource(this));
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(DetailShopViewModel.class);
    }

    private void setUpRcView() {
        final RecyclerView rcView = findViewById(R.id.rcViewShops);
        adapter = new StoreRcViewAdapter(getBaseContext());
        rcView.setAdapter(adapter);
        rcView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rcView.setHasFixedSize(true);
    }

    private void setUpSearchView() {
        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mDisposable.add(mViewModel.searchShops(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(shopModels -> adapter.setData(shopModels),
                                throwable -> Log.e("TAG", "Unable to register username", throwable)));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

}