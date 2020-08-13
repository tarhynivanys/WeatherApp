package com.kozin.weatherweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.kozin.weatherweather.Common.Common;
import com.kozin.weatherweather.ModelCurrent.WeatherResult;
import com.kozin.weatherweather.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kozin.weatherweather.Common.Common.GOOGLE_AUTOCOMPLETE_API_ID;

public class MainActivity extends AppCompatActivity {

    private ImageView imgIcon;
    private TextView tvValueFeelsLike, tvValueHumidity, tvPressure, tvTempValue, tvCity, tvCountry;
    private RelativeLayout rlWeather;
    private RecyclerView recyclerView;

    private String autocompleteCityName;

    static String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the SDK
        Places.initialize(getApplicationContext(), GOOGLE_AUTOCOMPLETE_API_ID);

        //create a new Places client instance
        PlacesClient placesClient = Places.createClient(this);

        //initialize the AutocompleteSupportFragment
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);

        //Specify the types of place data to return
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i("TAG", "Place: " + place.getName() + ", " + place.getId());
                autocompleteCityName = place.getName();
                cityName = autocompleteCityName;
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i("TAG", "ERROR: " + status);
            }
        });

        tvCity = findViewById(R.id.tvCity);
        tvTempValue = findViewById(R.id.tvTempValue);
        tvCountry = findViewById(R.id.tvCountry);
        tvValueFeelsLike = findViewById(R.id.tvValueFeelsLike);
        tvValueHumidity = findViewById(R.id.tvValueHumidity);
        tvPressure = findViewById(R.id.tvValuePressure);

        imgIcon = findViewById(R.id.imgIcon);

        rlWeather = (RelativeLayout) findViewById(R.id.rlWeather);

        recyclerView = findViewById(R.id.rvRecyclerView);

    }

    /**
     * method of a retrofit library initialization
     */
    public void parseJSON() {
        Log.i("TAG", " " + autocompleteCityName);

        //Retrofit library initialization
        RetrofitClient.getInstance()
                .getJSONApi()
                .getWeatherByCity(cityName, Common.WEATHER_API_ID, "metric")
                .enqueue(new Callback<WeatherResult>() {
                    @Override
                    public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                        WeatherResult weatherResult = response.body();

                        Picasso.get().load(new StringBuilder("http://openweathermap.org/img/wn/")
                                .append(weatherResult.getWeather().get(0).getIcon())
                                .append(".png").toString()).into(imgIcon);

                        tvCity.setText(weatherResult.getName());
                        tvCountry.setText(weatherResult.getSys().getCountry());
                        tvTempValue.setText(String.format("%s°", weatherResult.getMain().getTemp()));
                        tvValueHumidity.setText(String.format("%d%%", weatherResult.getMain().getHumidity()));
                        tvValueFeelsLike.setText(String.format("%s°", weatherResult.getMain().getFeels_like()));
                        tvPressure.setText(String.format("%d hpa", weatherResult.getMain().getPressure()));

                        Log.d("TAG", "USPEH");
                    }

                    @Override
                    public void onFailure(Call<WeatherResult> call, Throwable t) {
                        System.out.println(t.getMessage());

                        Log.d("TAG", "OSHIBKA");
                    }
                });

        rlWeather.setVisibility(View.VISIBLE);
    }

    /**
     * refresh button on click method
     *
     * @param view
     */
    public void refreshInfo(View view) {
        cityName = autocompleteCityName;
        parseJSON();
    }

    /**
     * by pressing weather button, you will redirect onto next activity (SecondActivity.class),
     * where you will be able to see weather for the next 5 days, divided by 3 hours per forecast
     *
     * @param view
     */
    public void weatherInfo(View view) {
        Intent myIntent = new Intent(view.getContext(), SecondActivity.class);
        startActivity(myIntent);

    }

    /**
     * by pressing map button, you will redirect onto map fragment
     *
     * @param view
     */
    public void openGoogleMap(View view) {
        Intent myIntent = new Intent(view.getContext(), MapActivity.class);
        startActivityForResult(myIntent, 0);

    }

    /**
     *
     * in this method you will get city name from MapActivity.class,
     * received from retrofit instance by latitude and longitude
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 0) {
            cityName = data.getStringExtra("city");
            parseJSON();
        }

    }

}