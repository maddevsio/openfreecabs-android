package io.maddevs.openfreecabs.views;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import io.maddevs.openfreecabs.R;
import io.maddevs.openfreecabs.models.CompanyModel;
import io.maddevs.openfreecabs.models.DriverModel;
import io.maddevs.openfreecabs.presenters.MainPresenter;
import io.maddevs.openfreecabs.utils.TouchableMapFragment;
import io.maddevs.openfreecabs.utils.TouchableWrapper;
import io.maddevs.openfreecabs.views.interfaces.MainInterface;

public class MainActivity extends FragmentActivity implements MainInterface, OnMapReadyCallback {
    GoogleMap map;
    LatLng lastTarget;
    Marker lastMarker;
    MainPresenter presenter;
    TouchableMapFragment mapFragment;

    Handler mapScrollHandler = new Handler();
    Runnable mapScrollRunnable = new Runnable() {
        @Override
        public void run() {
            if (!map.getCameraPosition().target.equals(lastTarget)) {
                lastTarget = map.getCameraPosition().target;
                presenter.getNearest(lastTarget);
            } else {
                mapScrollHandler.postDelayed(this, 100);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

//        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        mapFragment = (TouchableMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
                Log.d()
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
        presenter.getNearest(location);
    }

    @Override
    public void showDrivers(List<CompanyModel> companies) {
        map.clear();
        for (CompanyModel company : companies) {
            for (DriverModel driver : company.drivers) {
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(driver.latitude, driver.longitude))
                        .title(company.name));
            }
        }
    }
}
