package io.maddevs.openfreecabs.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rustam on 19.06.16.
 */
public class LocationManagerHelper {
    private Location lastLocation;
    private Context context;
    private LocationManager locationManager;
    private LocationListener locationListener;

    public enum RequestStatus {
        Success, HaveLastKnownLocation, NoAvailableProviders, NoPermission
    }

    public LocationManagerHelper(Context context, final HelperLocationListener helperLocationListener) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Location", "onLocationChanged " + location);
                lastLocation = location;
                helperLocationListener.onLocationChanged(new LatLng(location.getLatitude(), location.getLongitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("Location", "onStatusChanged " + status);
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("Location", "onProviderEnabled " + provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Location", "onProviderDisabled " + provider);
            }
        };
    }

    public RequestStatus requestUpdate() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return RequestStatus.NoPermission;
        }

        boolean haveActiveProvider = false;

        for (String provider : locationManager.getProviders(true)) {
            Log.d("Location", "onLocationChanged " + provider);
            if (!provider.equals(LocationManager.PASSIVE_PROVIDER)) {
                haveActiveProvider = true;
                locationManager.requestSingleUpdate(provider, locationListener, Looper.getMainLooper());
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null && lastLocation != null && location.getAccuracy() > lastLocation.getAccuracy()) {
                    lastLocation = location;
                } else if (location != null && lastLocation == null) {
                    lastLocation = location;
                }
            }
        }

        if (haveActiveProvider) {
            return lastLocation != null ? RequestStatus.HaveLastKnownLocation : RequestStatus.Success;
        } else {
            return RequestStatus.NoAvailableProviders;
        }
    }

    public LatLng getLastLocation() {
        return new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
    }

    public interface HelperLocationListener {
        void onLocationChanged(LatLng location);
    }
}
