package com.kozin.weatherweather;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kozin.weatherweather.Common.Common;
import com.kozin.weatherweather.ModelFuture.WeatherForecastResult;
import com.kozin.weatherweather.RecyclerViewAdapter.MyAdapter;
import com.kozin.weatherweather.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kozin.weatherweather.MainActivity.cityName;

public class SecondActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private WeatherForecastResult weatherForecastResults;

    private MyAdapter myAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);

        recyclerView = (RecyclerView) findViewById(R.id.rvRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        parseJson();

    }

    /**
     * Retrofit library initialization
     */
    private void parseJson() {

        RetrofitClient.getInstance()
                .getJSONApi()
                .getWeatherForecastByCity(cityName, Common.WEATHER_API_ID, "metric")
                .enqueue(new Callback<WeatherForecastResult>() {
                    @Override
                    public void onResponse(Call<WeatherForecastResult> call, Response<WeatherForecastResult> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            weatherForecastResults = response.body();

                            myAdapter = new MyAdapter(SecondActivity.this, weatherForecastResults);
                            recyclerView.setAdapter(myAdapter);
                        }

                        System.out.println("" + weatherForecastResults);

                        Log.d("TAG", "YEEEEEEEEEEEEEEEEEEEEEEES");

                    }

                    @Override
                    public void onFailure(Call<WeatherForecastResult> call, Throwable t) {
                        System.out.println(t.getMessage());

                        Toast.makeText(SecondActivity.this,"Oops! Something went wrong!",Toast.LENGTH_SHORT).show();

                        Log.d("TAG", "NOOOOOOOOOOOOOOOOOOO");
                    }
                });

    }
}
