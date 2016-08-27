package io.maddevs.openfreecabs.views.interfaces;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.maddevs.openfreecabs.models.CompanyModel;

/**
 * Created by rustam on 27.08.16.
 */
public interface MainInterface {
    void toMyLocation(LatLng location);
    void showDrivers(List<CompanyModel> companies);
}
