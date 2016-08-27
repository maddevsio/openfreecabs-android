package io.maddevs.openfreecabs.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.maddevs.openfreecabs.models.response.NearestResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rustam on 22.08.16.
 */
public class ApiClient {
    private static final String baseUrl = "https://eb6e911e.ngrok.io/";
    public static ApiClient instance = new ApiClient();

    private Retrofit retrofit;

    private ApiClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private OpenFreeCabsAPI dieselAPI() {
        return retrofit.create(OpenFreeCabsAPI.class);
    }

    public void getNearest(double lat, double lng, Callback<NearestResponse> callback) {
        dieselAPI().getNearest(lat, lng).enqueue(callback);
    }
}
