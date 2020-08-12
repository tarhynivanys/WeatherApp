package com.kozin.weatherweather.Retrofit;

import com.kozin.weatherweather.ModelCurrent.WeatherResult;
import com.kozin.weatherweather.ModelFuture.WeatherForecastResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("weather")
    Call<WeatherResult> getWeatherByCity(@Query("q") String city_name,
                                         @Query("appid") String appid,
                                         @Query("units") String unit);

    @GET("forecast")
    Call<WeatherForecastResult> getWeatherForecastByCity(@Query("q") String city_name,
                                                               @Query("appid") String appid,
                                                               @Query("units") String unit);

}
