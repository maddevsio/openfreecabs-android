package io.maddevs.openfreecabs.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rustam on 27.08.16.
 */
public class DriverModel {
    @SerializedName("lat")
    public double latitude;

    @SerializedName("lon")
    public double longitude;

    public String name;
}
