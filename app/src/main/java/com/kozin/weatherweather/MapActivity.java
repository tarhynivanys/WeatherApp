package com.kozin.weatherweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kozin.weatherweather.Common.Common;
import com.kozin.weatherweather.ModelCurrent.Main;
import com.kozin.weatherweather.ModelCurrent.WeatherResult;
import com.kozin.weatherweather.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    GoogleMap map;
    Marker marker1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }

    /**
     * method of map readiness to be clicked or get some markers on map
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setOnMarkerClickListener(this);

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.clear();

                marker1 = map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(latLng.latitude + " : " + latLng.longitude)
                        .snippet("This is my spot!")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

            }
        });

    }

    /**
     * on marker click method
     *
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        //if marker is placed and clicked, than this statement will work
        if (marker.equals(marker1)) {
            Log.i("TAG", "AAAAAAAAAAAAAAAAAAAAAA");

            RetrofitClient.getInstance()
                    .getJSONApi()
                    .getWeatherByLatLng(String.valueOf(marker1.getPosition().latitude),
                            String.valueOf(marker1.getPosition().longitude),
                            Common.WEATHER_API_ID, "metric")
                    .enqueue(new Callback<WeatherResult>() {
                        @Override
                        public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                            WeatherResult weatherResult = response.body();

                            Intent intent = new Intent();
                            intent.putExtra("city", weatherResult.getName());
                            setResult(RESULT_OK, intent);
                            finish();

                            Log.d("TAG", "USPEH");
                        }

                        @Override
                        public void onFailure(Call<WeatherResult> call, Throwable t) {
                            System.out.println(t.getMessage());

                            Log.d("TAG", "OSHIBKA");
                        }
                    });

        }

        return false;
    }
}
