package com.example.android.go_market.features.detail_shop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.go_market.Injection;
import com.example.android.go_market.core.CurrentUser;
import com.example.android.go_market.features.auth.login.ui.ViewModelFactory;
import com.example.android.go_market.features.detail_shop.domain.entities.ShopModel;
import com.example.android.persistence.R;
import com.google.android.gms.maps.model.LatLng;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.android.go_market.Injection.provideShopDataSource;

public class DetailShopActivity extends AppCompatActivity {
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private DetailShopViewModel mViewModel;
    private EditText mShopName;
    private EditText mShopNit;
    private EditText mShopDescription;
    private EditText mShopHoursOperation;
    private Button mButtonSaveShop;
    private Button mButtonDeleteShop;
    private LatLng latLng;
    private int shopId;
    private ShopModel mShopModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_shop);

        initializeViewModel();
        setLatLngFomIntent();

        initializeViews();

        initializeShopModelIfIsUpdate();

        setOnClickListeners();
    }

    private void setLatLngFomIntent() {
        Intent i = getIntent();
        double lat = i.getDoubleExtra("lat", 0);
        double lng = i.getDoubleExtra("lng", 0);

        latLng = new LatLng(lat, lng);
        shopId = i.getIntExtra("shopId", -1);
    }

    private void initializeShopModelIfIsUpdate() {
        if (shopId != -1) {
            mDisposable.add(mViewModel.getShopById(shopId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(shopModel -> {
                                this.mShopModel = shopModel;
                                setTextIntoEditTexts();
                                if (shopId != -1 && !mViewModel.isMyShop(mShopModel.getUserName()))
                                    setViewReadOnly();
                            },
                            throwable -> Log.e("TAG", "Unable to register username", throwable)));
        }
    }

    private void setTextIntoEditTexts() {
        mShopName.setText(mShopModel.getName());
        mShopNit.setText(mShopModel.getNit());
        mShopDescription.setText(mShopModel.getDescription());
        mShopHoursOperation.setText(mShopModel.getHoursOfOperation());
    }

    private void initializeViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(provideShopDataSource(this));
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(DetailShopViewModel.class);
    }

    private void initializeViews() {
        mShopName = findViewById(R.id.editTextShopName);
        mShopNit = findViewById(R.id.editTextShopNit);
        mShopDescription = findViewById(R.id.editTextShopDescription);
        mShopHoursOperation = findViewById(R.id.editTextShopHoursOperation);

        mButtonSaveShop = findViewById(R.id.buttonSaveShop);
        mButtonDeleteShop = findViewById(R.id.buttonDeleteShop);
    }

    private void setViewReadOnly() {
        mShopName.setEnabled(false);
        mShopNit.setEnabled(false);
        mShopDescription.setEnabled(false);
        mShopHoursOperation.setEnabled(false);

        mButtonSaveShop.setVisibility(View.GONE);
        mButtonDeleteShop.setVisibility(View.GONE);
    }

    private void setOnClickListeners() {
        mButtonSaveShop.setOnClickListener(v -> saveShop());
        mButtonDeleteShop.setOnClickListener(v -> deleteShop());
    }

    private void saveShop() {
        mShopModel = buildShopModel();

        mDisposable.add(mViewModel.insertOrUpdateShop(mShopModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::finish,
                        throwable -> Log.e("TAG", "Unable to register username", throwable)));
    }

    private void deleteShop() {
        mShopModel = buildShopModel();

        mDisposable.add(mViewModel.deleteShop(mShopModel.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::finish,
                        throwable -> Log.e("TAG", "Unable to register username", throwable)));
    }

    private ShopModel buildShopModel() {
        String shopName = mShopName.getText().toString();
        String shopNit = mShopNit.getText().toString();
        String shopDescription = mShopDescription.getText().toString();
        String shopHoursOperation = mShopHoursOperation.getText().toString();

        return new ShopModel(
                shopId == -1 ? null : shopId,
                shopName,
                shopNit,
                shopDescription,
                shopHoursOperation,
                latLng.latitude,
                latLng.longitude,
                CurrentUser.getUser().getUserName()
        );
    }
}
