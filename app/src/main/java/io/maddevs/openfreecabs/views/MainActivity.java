package io.maddevs.openfreecabs.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import io.maddevs.openfreecabs.R;
import io.maddevs.openfreecabs.models.CompanyModel;
import io.maddevs.openfreecabs.models.DriverModel;
import io.maddevs.openfreecabs.presenters.MainPresenter;
import io.maddevs.openfreecabs.utils.DataStorage;
import io.maddevs.openfreecabs.utils.TouchableMapFragment;
import io.maddevs.openfreecabs.utils.TouchableWrapper;
import io.maddevs.openfreecabs.views.interfaces.MainInterface;

public class MainActivity extends AppCompatActivity implements MainInterface, OnMapReadyCallback {
    GoogleMap map;
    LatLng lastTarget;
    Marker lastMarker;
    MainPresenter presenter;
    TouchableMapFragment mapFragment;

    Handler mapScrollHandler = new Handler();
    Runnable mapScrollRunnable = new Runnable() {
        @Override
        public void run() {
            if (map.getCameraPosition().target.equals(lastTarget)) {
                presenter.getNearest(lastTarget);
            } else {
                lastTarget = map.getCameraPosition().target;
                mapScrollHandler.postDelayed(this, 100);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        mapFragment = (TouchableMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        findViewById(R.id.mainButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataStorage.instance.companies.size() > 0) {
                    startActivity(new Intent(MainActivity.this, NearCabListActivity.class));
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (lastMarker != null) {
                    lastMarker.hideInfoWindow();
                    if (lastMarker.equals(marker)) {
                        lastMarker = null;
                        return true;
                    }
                }

                marker.showInfoWindow();
                lastMarker = marker;

                return true;
            }
        });

        mapFragment.setTouchListener(new TouchableWrapper.OnTouchListener() {
            @Override
            public void onTouch() {
            }

            @Override
            public void onRelease() {
                mapScrollHandler.postDelayed(mapScrollRunnable, 100);
            }
        });

        presenter.onMapReady();
    }

    @Override
    public void toMyLocation(LatLng location) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
    }

    @Override
    public void showDrivers(List<CompanyModel> companies) {
        map.clear();
        for (final CompanyModel company : companies) {
            Picasso.with(this)
                    .load("http://icons.iconarchive.com/icons/icons-land/vista-map-markers/256/Map-Marker-Ball-Azure-icon.png")
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                            for (DriverModel driver : company.drivers) {
                                map.addMarker(new MarkerOptions()
                                                .position(new LatLng(driver.latitude, driver.longitude))
                                                .title(company.name)
                                                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                                );
                            }
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
        }
    }
}
