package io.maddevs.openfreecabs.presenters;

import com.google.android.gms.maps.model.LatLng;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.maddevs.openfreecabs.models.CompanyModel;
import io.maddevs.openfreecabs.models.response.NearestResponse;
import io.maddevs.openfreecabs.utils.ApiClient;
import io.maddevs.openfreecabs.utils.DataStorage;
import io.maddevs.openfreecabs.utils.LocationManagerHelper;
import io.maddevs.openfreecabs.views.MainActivity;
import io.maddevs.openfreecabs.views.interfaces.MainInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rustam on 27.08.16.
 */
public class MainPresenter implements LocationManagerHelper.HelperLocationListener {
    MainInterface mainInterface;
    LocationManagerHelper locationManagerHelper;
    public List<CompanyModel> companies;

    public MainPresenter(MainActivity mainActivity) {
        mainInterface = mainActivity;
        locationManagerHelper = new LocationManagerHelper(mainActivity, this);
        companies = DataStorage.instance.companies;
    }

    public void onMapReady() {
        if (companies != null && companies.size() > 0) {
            DataStorage.instance.companies = companies;
            mainInterface.showDrivers(companies);
        }

        locationManagerHelper.requestUpdate();
        switch (locationManagerHelper.requestUpdate()) {
            case NoAvailableProviders:
//                new MaterialDialog.Builder(this)
//                        .title(R.string.attention)
//                        .content(R.string.no_available_providers)
//                        .positiveText(R.string.settings)
//                        .onPositive(new MaterialDialog.SingleButtonCallback() {
//                            @Override
//                            public void onClick(MaterialDialog dialog, DialogAction which) {
//                                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                                startActivity(callGPSSettingIntent);
//                            }
//                        })
//                        .negativeText(R.string.close).show();
//                break;
            case NoPermission:
//                ActivityCompat.requestPermissions(this, new String[]{
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION
//                }, requestPermissionCode);
//                break;
            case HaveLastKnownLocation:
//                animateToMyLocation = 2;
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationManagerHelper.getLastLocation(), 17));
//                break;
            case Success:
//                animateToMyLocation = 1;
                break;
        }
    }

    @Override
    public void onLocationChanged(LatLng location) {
        getNearest(location);
        mainInterface.toMyLocation(location);
    }

    public void getNearest(LatLng location) {
        ApiClient.instance.getNearest(location.latitude, location.longitude, new Callback<NearestResponse>() {
            @Override
            public void onResponse(Call<NearestResponse> call, Response<NearestResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().success && response.body().companies != null) {
                        companies = response.body().companies;
                        Collections.sort(companies, new Comparator<Object>() {
                            @Override
                            public int compare(Object lhs, Object rhs) {
                                if (((CompanyModel) lhs).drivers.size() > ((CompanyModel) rhs).drivers.size()) {
                                    return -1;
                                } else if (((CompanyModel) lhs).drivers.size() < ((CompanyModel) rhs).drivers.size()) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            }
                        });
                        DataStorage.instance.companies = companies;
                        mainInterface.showDrivers(companies);
                    }
                }
            }

            @Override
            public void onFailure(Call<NearestResponse> call, Throwable t) {

            }
        });
    }
}
