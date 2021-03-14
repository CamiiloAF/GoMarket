package com.example.android.go_market.features.home.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.go_market.Injection;
import com.example.android.go_market.features.auth.login.ui.ViewModelFactory;
import com.example.android.go_market.features.detail_shop.domain.entities.ShopModel;
import com.example.android.go_market.features.detail_shop.ui.DetailShopActivity;
import com.example.android.go_market.features.detail_shop.ui.DetailShopViewModel;
import com.example.android.go_market.features.search_shop.ui.SearchActivity;
import com.example.android.persistence.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.android.go_market.Injection.provideShopDataSource;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnInfoWindowClickListener {

    private final int MY_PERMISSION_REQUEST_CODE = 982;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private GoogleMap mMap;
    private DetailShopViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        checkPermissions();
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(provideShopDataSource(this));
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(DetailShopViewModel.class);

        FloatingActionButton mFABSearch = findViewById(R.id.FABSearch);
        mFABSearch.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION};

            ActivityCompat.requestPermissions(this, permissions, MY_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                this.finishAffinity();
            }
        }
    }

    //    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray)
//    {
//        when (requestCode) {
//        MY_PERMISSION_REQUEST_CODE ->
//        {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            {
//                buildLocationRequest()
//                buildLocationCallback()
//                if (location_switch.isChecked)
//                    displayLocation()
//            }
//        }
//    }
//    }

    @Override
    protected void onStart() {
        super.onStart();

        mDisposable.add(mViewModel.getShops()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shopModels -> {
                            mMap.clear();
                            shopModels.forEach(this::addMarker);
                        },
                        throwable -> Log.e("TAG", "Unable to get username", throwable)));
    }

    private void addMarker(ShopModel shop) {
        final LatLng latLng = new LatLng(shop.getLat(), shop.getLng());

        final BitmapDescriptor markerIcon = BitmapDescriptorFactory.defaultMarker(
                mViewModel.isMyShop(shop.getUserName())
                        ? BitmapDescriptorFactory.HUE_BLUE
                        : BitmapDescriptorFactory.HUE_RED);

        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .snippet(String.valueOf(shop.getId()))
                .icon(markerIcon)
                .title(shop.getName()));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
    }


    @Override
    public void onMapLongClick(LatLng latLng) {
        Intent intent = new Intent(this, DetailShopActivity.class);
        intent.putExtra("lat", latLng.latitude);
        intent.putExtra("lng", latLng.longitude);
        startActivity(intent);
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        final int shopId = Integer.parseInt(marker.getSnippet());

        Intent intent = new Intent(this, DetailShopActivity.class);
        intent.putExtra("lat", marker.getPosition().latitude);
        intent.putExtra("lng", marker.getPosition().longitude);
        intent.putExtra("shopId", shopId);
        startActivity(intent);
    }
}