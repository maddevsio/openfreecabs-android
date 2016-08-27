package io.maddevs.openfreecabs.utils;

import io.maddevs.openfreecabs.models.response.NearestResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by rustam on 23.08.16.
 */
public interface OpenFreeCabsAPI {
    @GET("nearest/{latitude}/{lng}")
    Call<NearestResponse> getNearest(@Path("latitude") double lat, @Path("lng") double lng);
}
