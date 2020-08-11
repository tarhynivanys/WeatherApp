package com.kozin.weatherweather.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private Retrofit instance;
    private static RetrofitClient mInstance;

    private RetrofitClient() {
        instance = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public IOpenWeatherMap getJSONApi() {
        return instance.create(IOpenWeatherMap.class);
    }

}
